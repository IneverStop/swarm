package com.original.swarm.job;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

/**
 * 构建DAG任务的工厂
 *
 * @author: dingtao
 * @date: 2023/11/17
 */
@Component
public class DirectedAcyclicGraphJobFactory implements JobFactory {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private Map<String, JobConfig> jobConfigMap;

    @Override
    public DirectedAcyclicGraphChainJob getJob(Map<String, Job> jobMap, JobConfig jobConfig)
        throws NoSuchJobDefinitionException, NoSuchEdgeDefinitionException {

        List<DirectedAcyclicGraphChainJobComb> jobCombs = new ArrayList<>(jobConfig.getJobs().size());
        for (JobConfig.JobInfo job : jobConfig.getJobs()) {
            DirectedAcyclicGraphChainJobComb jobComb = new DirectedAcyclicGraphChainJobComb();
            jobComb.setJobName(job.getName());
            if (null == job.getParallelJobs() || job.getParallelJobs().isEmpty()) {
                // 单体任务
                jobComb.assignParallelJob(this.getJobFromMap(jobMap, job.getName()));
            } else {
                // 聚合任务
                for (JobConfig.JobInfo parallelJob : job.getParallelJobs()) {
                    jobComb.assignParallelJob(this.getJobFromMap(jobMap, parallelJob.getName()));
                }
            }
            jobCombs.add(jobComb);
        }
        DirectedAcyclicGraphChainJob dagJob = new DirectedAcyclicGraphChainJob(jobCombs);
        for (JobConfig.EdgeInfo edge : jobConfig.getEdges()) {
            dagJob.addEdge(jobConfig.getIndexById(edge.getFromId()),
                jobConfig.getIndexById(edge.getToId()));
        }
        return dagJob;
    }


    /**
     * 获取map中的任务
     *
     * @param jobMap  任务map
     * @param jobName 即定义的bean的名称
     * @return
     * @throws NoSuchJobDefinitionException 没有在spring容器中查询到bean定义则抛出这个异常
     */
    private Job getJobFromMap(Map<String, Job> jobMap, String jobName)
        throws NoSuchJobDefinitionException {
        try {
            return jobMap.remove(jobName);
        } catch (Exception var) {
            throw new NoSuchJobDefinitionException("There is no such job called " + jobName, var);
        }
    }

    @Override
    public ChainJob getChainJob(String chainName, Object param)
        throws NoSuchJobDefinitionException, NoSuchEdgeDefinitionException {
        return this.getChainJob(this.jobConfigMap.get(chainName), param);
    }

    @Override
    public ChainJob getChainJob(JobConfig jobConfig, @Nullable Object param)
        throws NoSuchJobDefinitionException, NoSuchEdgeDefinitionException {
        List<DirectedAcyclicGraphChainJobComb> jobCombs = new ArrayList<>(jobConfig.getJobs().size());
        for (JobConfig.JobInfo job : jobConfig.getJobs()) {
            DirectedAcyclicGraphChainJobComb jobComb = new DirectedAcyclicGraphChainJobComb();
            jobComb.setJobName(job.getName());
            if (null == job.getParallelJobs() || job.getParallelJobs().isEmpty()) {
                // 单体任务
                jobComb.assignParallelJob(this.getJobFromApplicationContext(job.getName(), param));
            } else {
                // 聚合任务
                for (JobConfig.JobInfo parallelJob : job.getParallelJobs()) {
                    jobComb.assignParallelJob(this.getJobFromApplicationContext(parallelJob.getName(), param));
                }
            }
            jobCombs.add(jobComb);
        }
        DirectedAcyclicGraphChainJob dagJob = new DirectedAcyclicGraphChainJob(jobCombs);
        for (JobConfig.EdgeInfo edge : jobConfig.getEdges()) {
            dagJob.addEdge(jobConfig.getIndexById(edge.getFromId()),
                jobConfig.getIndexById(edge.getToId()));
        }
        return dagJob;
    }

    /**
     * 从spring应用上下文中获取job实例，可能是原型模式的也可能是单例模式的，取决用是否传递了param以及bean的定义
     *
     * @param jobName 即定义的bean的名称
     * @param param   可选bean定义构造参数
     * @return
     * @throws NoSuchJobDefinitionException 没有在spring容器中查询到bean定义则抛出这个异常
     */
    private Job getJobFromApplicationContext(String jobName, @Nullable Object param)
        throws NoSuchJobDefinitionException {
        try {
            return null == param ? (Job) this.applicationContext.getBean(jobName) :
                (Job) this.applicationContext.getBean(jobName, param);
        } catch (Exception var) {
            throw new NoSuchJobDefinitionException("There is no such job called " + jobName, var);
        }
    }
}
