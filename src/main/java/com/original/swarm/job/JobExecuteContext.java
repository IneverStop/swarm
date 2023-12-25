package com.original.swarm.job;

/**
 * 任务执行上下文
 *
 * @author: dingtao
 * @date: 2023/11/16
 */
public interface JobExecuteContext {

    /**
     * 是否归属于某个任务链
     *
     * @return
     */
    boolean belongToChainJob();

    /**
     * 如果归属于某个任务链，则返回任务链名称
     *
     * @return
     */
    String chainName();

    /**
     * 获取任务执行上下文参数
     *
     * @return
     */
    JobExecuteData getJobExecuteData();
}
