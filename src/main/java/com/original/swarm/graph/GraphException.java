package com.original.swarm.graph;

/**
 * 图相关的异常
 *
 * @author: dingtao
 * @date: 2023/11/17
 */
public class GraphException extends RuntimeException {
    public GraphException(String msg) {
        super(msg);
    }

    public GraphException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
