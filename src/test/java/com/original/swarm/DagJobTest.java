package com.original.swarm;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import com.original.swarm.job.AbstractChainJob;
import com.original.swarm.job.DirectedAcyclicGraphChainJob;
import com.original.swarm.job.DirectedAcyclicGraphJobFactory;
import com.original.swarm.job.Job;
import com.original.swarm.job.JobConfig;
import com.original.swarm.job.JobExecuteContext;
import com.original.swarm.job.JobExecuteData;

/**
 * @description:
 * @author: dingtao
 * @date: 2023/11/16
 */
public class DagJobTest {

    public static void main(String[] args) throws Exception {
        sampleTest();
    }

    public static void sampleTest() throws Exception {
        try (InputStream inputStream = AbstractChainJob.class
            .getResourceAsStream("/swarm/templates/Sample.json")) {
            if (null == inputStream) {
                throw new RuntimeException("Please config at Sample.json");
            }

            JobConfig jobConfig = JobConfig.ofInputStream(inputStream);
            Map<String, Job> jobMap = new HashMap<>();
            jobMap.put("A", jobExecuteContext -> System.out.println("doing A"));
            // B-Sub1和B-Sub2组成聚合任务，不需要定义聚合任务，只需要定义聚合任务的子任务
            jobMap.put("B-Sub1", jobExecuteContext -> System.out.println("doing B-Sub1"));
            jobMap.put("B-Sub2", jobExecuteContext -> System.out.println("doing B-Sub2"));
            jobMap.put("C", jobExecuteContext -> System.out.println("doing C"));
            jobMap.put("D", jobExecuteContext -> System.out.println("doing D"));
            jobMap.put("E", jobExecuteContext -> System.out.println("doing E"));
            jobMap.put("F", jobExecuteContext -> System.out.println("doing F"));
            jobMap.put("G", jobExecuteContext -> System.out.println("doing G"));
            DirectedAcyclicGraphChainJob dagJob = new DirectedAcyclicGraphJobFactory().getJob(jobMap, jobConfig);
            dagJob.execute(new JobExecuteContext() {
                private JobExecuteData jobData = new JobExecuteData();

                @Override
                public boolean belongToChainJob() {
                    return true;
                }

                @Override
                public String chainName() {
                    return "Sample";
                }

                @Override
                public JobExecuteData getJobExecuteData() {
                    return this.jobData;
                }
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
