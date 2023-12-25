package com.original.swarm.job;

/**
 * 最基本的任务接口
 *
 * @author: dingtao
 * @date: 2023/11/16
 */
public interface Job {

    /**
     * 执行任务
     *
     * @param jobExecuteContext 任务执行上下文参数
     * @throws JobExecutionFailedException 执行失败的时候抛出异常
     */
    void execute(JobExecuteContext jobExecuteContext) throws JobExecutionFailedException;



}
