package com.original.swarm.autoconfigure;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import com.original.swarm.job.DirectedAcyclicGraphJobFactory;
import com.original.swarm.job.DuplicateJobChainDefinitionException;
import com.original.swarm.job.JobConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternResolver;

/**
 * @description:
 * @author: dingtao
 * @date: 2023/11/21
 */
@Configuration
@EnableConfigurationProperties(SwarmProperties.class)
@ConditionalOnClass(DirectedAcyclicGraphJobFactory.class)
public class SwarmAutoConfiguration {

    @Autowired
    private ResourcePatternResolver resourcePatternResolver;

    @Autowired
    private SwarmProperties properties;

    @Bean
    @ConditionalOnMissingBean(DirectedAcyclicGraphJobFactory.class)
    public DirectedAcyclicGraphJobFactory directedAcyclicGraphJobFactory() {
        return new DirectedAcyclicGraphJobFactory();
    }

    @Bean
    public Map<String, JobConfig> jobConfigMap() throws IOException {
        return this.loadJobConfigs();
    }


    /**
     * 从所有resources路径下加载JobConfig
     *
     * @return
     * @throws IOException
     */
    private Map<String, JobConfig> loadJobConfigs() throws IOException {
        Map<String, JobConfig> jobConfigMap = new HashMap<>();
        Resource[] resources =
            this.resourcePatternResolver.getResources(
                ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX + this.properties.getConfigPath());
        for (Resource resource : resources) {
            try (InputStream inputStream = resource.getInputStream()) {
                JobConfig jobConfig = JobConfig.ofInputStream(inputStream);
                if (jobConfigMap.containsKey(jobConfig.getChainName())) {
                    throw new DuplicateJobChainDefinitionException("Duplicate Job Chain " + jobConfig.getChainName());
                }
                jobConfigMap.put(jobConfig.getChainName(), jobConfig);
            }
        }
        return jobConfigMap;
    }
}
