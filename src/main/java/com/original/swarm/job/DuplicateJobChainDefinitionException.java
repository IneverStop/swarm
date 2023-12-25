package com.original.swarm.job;

/**
 * 没有这个job的定义
 *
 * @author: dingtao
 * @date: 2023/11/17
 */
public class DuplicateJobChainDefinitionException extends RuntimeException {
    public DuplicateJobChainDefinitionException(String msg) {
        super(msg);
    }

    public DuplicateJobChainDefinitionException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
