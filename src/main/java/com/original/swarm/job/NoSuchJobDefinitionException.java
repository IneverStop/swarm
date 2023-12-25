package com.original.swarm.job;

/**
 * 没有这个job的定义
 *
 * @author: dingtao
 * @date: 2023/11/17
 */
public class NoSuchJobDefinitionException extends RuntimeException {
    public NoSuchJobDefinitionException(String msg) {
        super(msg);
    }

    public NoSuchJobDefinitionException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
