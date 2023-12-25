package com.original.swarm.job;

/**
 * 任务执行状态
 *
 * @author: dingtao
 * @date: 2023/11/16
 */
public enum JobRunningStatus {
    /**
     * 等待执行
     */
    WAITING,
    /**
     * 执行中
     */
    RUNNING,
    /**
     * 执行成功
     */
    SUCCEED,
    /**
     * 执行失败
     */
    FAILED
}
