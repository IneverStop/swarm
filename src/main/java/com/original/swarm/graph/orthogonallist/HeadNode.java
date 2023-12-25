package com.original.swarm.graph.orthogonallist;

import lombok.Data;

/**
 * 十字链表的顶点节点定义
 *
 * @author: dingtao
 * @date: 2023/11/14
 */
@Data
public class HeadNode<T> {

    /**
     * 顶点数据
     */
    private T data;

    /**
     * 以当前节点为头（即指向当前节点）的第一条边
     */
    private EdgeNode firstIn;

    /**
     * 以当前节点为尾（即从当前节点出发）的第一条边
     */
    private EdgeNode firstOut;

    /**
     * 入度
     */
    private int inwardDegrees;

    /**
     * 出度
     */
    private int outwardDegrees;

    private HeadNode() {

    }

    public HeadNode(T data) {
        this.data = data;
    }

    /**
     * 刷新出度和入度
     */
    public void refreshDegrees() {
        this.refreshInwardDegrees();
        this.refreshOutwardDegrees();
    }

    /**
     * 刷新当前节点的入度
     *
     * @return
     */
    private void refreshInwardDegrees() {
        EdgeNode currentIn = this.getFirstIn();
        if (null == currentIn) {
            this.inwardDegrees = 0;
            return;
        }
        int inwardDegrees = 0;
        while (null != currentIn) {
            inwardDegrees++;
            currentIn = currentIn.getHeadNextLink();
        }
        this.inwardDegrees = inwardDegrees;
    }

    /**
     * 刷新当前节点的出度
     *
     * @return
     */
    private void refreshOutwardDegrees() {
        EdgeNode currentOut = this.getFirstOut();
        if (null == currentOut) {
            this.outwardDegrees = 0;
            return;
        }
        int outwardDegrees = 0;
        while (null != currentOut) {
            outwardDegrees++;
            currentOut = currentOut.getTailNextLink();
        }
        this.outwardDegrees = outwardDegrees;
    }
}
