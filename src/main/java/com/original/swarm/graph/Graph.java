package com.original.swarm.graph;

/**
 * 图的接口
 *
 * @author: dingtao
 * @date: 2023/11/9
 */
public interface Graph {

    /**
     * 添加一条边，具体边的含义和是否有向由子类确定
     *
     * @param oneIndex     边中其中一个顶点的下标索引
     * @param anotherIndex 边中另外一个顶点的下标索引
     */
    void addEdge(int oneIndex, int anotherIndex);
}
