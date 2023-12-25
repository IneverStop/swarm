package com.original.swarm.graph;

import java.util.List;

/**
 * 有向无环图接口
 *
 * @author: dingtao
 * @date: 2023/11/14
 */
public interface DirectedAcyclicGraph extends Graph {

    /**
     * 添加一条有向边，这条边从{@code tailIndex}对应节点 指向{@code headIndex}对应节点.
     * <p>
     * 即( tailIndexNode -----> headIndexNode )
     * </p>
     *
     * @param tailIndex 弧尾对应的节点下标索引
     * @param headIndex 弧头对应的节点下标索引
     */
    @Override
    void addEdge(int tailIndex, int headIndex);

    /**
     * 拓扑排序
     *
     * @return 拓扑排序后的下标索引
     */
    List<Integer> topologicalSort();

}
