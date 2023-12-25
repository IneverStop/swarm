package com.original.swarm.job;

/**
 * @description:
 * @author: dingtao
 * @date: 2023/11/16
 */
public interface JobComb extends Job {

    /**
     * 添加一个并行处理的任务
     * <p>
     * 使用者需要明确的知道这些任务会被并行执行，需要考虑CPU、IO、内存占用
     * </p>
     *
     * @param job
     */
    void assignParallelJob(Job job);

}
