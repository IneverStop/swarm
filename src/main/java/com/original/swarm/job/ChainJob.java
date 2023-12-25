package com.original.swarm.job;

/**
 * 可以组成任务链的job
 *
 * @author: dingtao
 * @date: 2023/11/17
 */
public interface ChainJob extends Job {

    /**
     * 整个任务链中任何一个任务失败了，则整个任务链中所有任务都会触发{@link #anyFailed}方法
     *
     * @param jobExecuteContext
     */
    void anyFailed(JobExecuteContext jobExecuteContext);
}
