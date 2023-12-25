package com.original.swarm.job;

/**
 * 任务执行失败时抛出的异常
 *
 * @author: dingtao
 * @date: 2023/11/17
 */
public class JobExecutionFailedException extends RuntimeException {

    public JobExecutionFailedException(String msg) {
        super(msg);
    }

    public JobExecutionFailedException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
