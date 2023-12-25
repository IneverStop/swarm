package com.original.swarm.autoconfigure;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.util.ObjectUtils;

/**
 * @description:
 * @author: dingtao
 * @date: 2023/11/21
 */
@Data
@Slf4j
@ConfigurationProperties(prefix = "swarm")
public class SwarmProperties {
    /**
     * 默认的配置路径
     */
    public static final String DEFAULT_CONFIG_PATH = "/swarm/templates/*";


    /**
     * 任务链配置文件路径
     */
    private String configPath;


    /**
     * 获取任务链配置文件路径
     *
     * @return
     */
    public String getConfigPath() {
        if (ObjectUtils.isEmpty(this.configPath)) {
            return DEFAULT_CONFIG_PATH;
        } else {
            return this.configPath;
        }
    }
}
