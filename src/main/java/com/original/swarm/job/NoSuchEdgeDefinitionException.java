package com.original.swarm.job;

/**
 * 没有这个边的定义
 *
 * @author: dingtao
 * @date: 2023/11/17
 */
public class NoSuchEdgeDefinitionException extends RuntimeException {
    public NoSuchEdgeDefinitionException(String msg) {
        super(msg);
    }

    public NoSuchEdgeDefinitionException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
