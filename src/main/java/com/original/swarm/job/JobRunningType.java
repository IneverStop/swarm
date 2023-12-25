package com.original.swarm.job;

/**
 * 任务运行模式
 *
 * @author: dingtao
 * @date: 2023/11/16
 */
public enum JobRunningType {
    /**
     * 定时触发的任务
     */
    TIMING,
    /**
     * 手动触发的任务
     */
    MANU
}
