package com.original.swarm.graph.orthogonallist;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.function.Consumer;
import com.original.swarm.graph.AbstractDirectedAcyclicGraph;
import com.original.swarm.graph.DirectedAcyclicGraph;

/**
 * 十字链表实现的有向无环图
 *
 * @author: dingtao
 * @date: 2023/11/14
 */
public class OrthogonalListDirectedAcyclicGraph<T> extends AbstractDirectedAcyclicGraph
    implements DirectedAcyclicGraph {

    /**
     * 所有的顶点
     */
    private List<HeadNode<T>> headNodes;

    public OrthogonalListDirectedAcyclicGraph() {

    }

    public OrthogonalListDirectedAcyclicGraph(List<HeadNode<T>> headNodes) {
        this.addHeadNodes(headNodes);
    }


    /**
     * 往DAG中添加顶点节点，这些顶点节点必须是初始化状态，即只有{@link HeadNode#getData()}, 而没有{@link HeadNode#getFirstIn()} 和
     * {@link HeadNode#getFirstOut()}
     *
     * @param headNodes
     */
    public void addHeadNodes(List<HeadNode<T>> headNodes) {
        if (null != this.headNodes) {
            // 只能初始化一次
            throw new UnsupportedOperationException("This DAG`s HeadNode array is already initialized.");
        }

        if (null == headNodes || headNodes.isEmpty()) {
            // DAG中至少要有一个节点
            throw new IllegalArgumentException("Please provide at least one HeadNode.");
        }
        for (int i = 0; i < headNodes.size(); i++) {
            HeadNode<T> headNode = headNodes.get(i);
            // 校验headNode是否是纯净的😀
            if (null == headNode) {
                throw new NullPointerException(
                    "When init DAG, every HeadNode could not be null, but HeadNode of index [" + i + "] is null.");
            }
            if (null != headNode.getFirstIn()) {
                throw new IllegalArgumentException(
                    "When init DAG, every HeadNode could not have HeadNode#firstIn, but HeadNode of index [" + i +
                        "] has HeadNode#firstIn.");
            }
            if (null != headNode.getFirstOut()) {
                throw new IllegalArgumentException(
                    "When init DAG, every HeadNode could not have HeadNode#firstOut, but HeadNode of index [" + i +
                        "] has HeadNode#firstOut.");
            }
        }
        // 检查完毕，初始化头节点
        this.headNodes = new ArrayList<>(headNodes);
    }

    @Override
    public void addEdge(int tailIndex, int headIndex) {
        if (!this.isIndexValid(tailIndex)) {
            throw new IndexOutOfBoundsException("tailIndex must between 0 and " + (this.size() - 1));
        }
        if (!this.isIndexValid(headIndex)) {
            throw new IndexOutOfBoundsException("headIndex must between 0 and " + (this.size() - 1));
        }

        // 弧尾对应的节点
        HeadNode<T> tailNode = this.indexOf(tailIndex);
        // 弧头对应的节点
        HeadNode<T> headNode = this.indexOf(headIndex);
        EdgeNode edgeNode = new EdgeNode(tailIndex, headIndex);

        if (null == tailNode.getFirstOut()) {
            // 弧尾节点没有创建过边
            tailNode.setFirstOut(edgeNode);
        } else {
            // 弧尾节点创建过边，则将弧尾节点的出边链表的最末尾连接到当前弧
            tailNode.getFirstOut().addTailNextLink(edgeNode);
        }

        if (null == headNode.getFirstIn()) {
            // 弧头节点没有创建过边
            headNode.setFirstIn(edgeNode);
        } else {
            // 弧头节点创建过边，则将弧头节点的入边链表的最末尾连接到当前弧
            headNode.getFirstIn().addHeadNextLink(edgeNode);
        }

        // 刷新出度和入度
        this.refreshDegrees();
    }

    /**
     * 贪心算法实现的拓扑排序
     */
    @Override
    public List<Integer> topologicalSort() {

        Queue<Integer> queue = new LinkedList<>();
        List<Integer> sortedOrder = new ArrayList<>(this.size());
        for (int i = 0; i < this.headNodes.size(); i++) {
            // 入度为0的节点加入队列
            if (this.indexOf(i).getInwardDegrees() == 0) {
                queue.offer(i);
            }
        }

        while (!queue.isEmpty()) {
            int headNodeIndex = queue.poll();
            sortedOrder.add(headNodeIndex);

            EdgeNode currentSameTailEdge = this.indexOf(headNodeIndex).getFirstOut();
            while (null != currentSameTailEdge) {
                HeadNode<T> node = this.indexOf(currentSameTailEdge.getHeadIndex());
                node.setInwardDegrees(node.getInwardDegrees() - 1);
                if (node.getInwardDegrees() == 0) {
                    queue.offer(currentSameTailEdge.getHeadIndex());
                }
                currentSameTailEdge = currentSameTailEdge.getTailNextLink();
            }

        }
        if (sortedOrder.size() != this.size()) {
            // 说明有环
            throw new IllegalArgumentException("This DAG has cycle.");
        }

        // 最后重新刷新度
        this.refreshDegrees();
        return sortedOrder;
    }

    /**
     * 按照拓扑排序进行消费
     *
     * @param consumer
     */
    public void consumeTopologicalSort(Consumer<T> consumer) {
        for (Integer headNodeIndex : this.topologicalSort()) {
            consumer.accept(this.indexOf(headNodeIndex).getData());
        }
    }

    /**
     * 判断下标索引是否有效
     *
     * @param index
     * @return
     */
    private boolean isIndexValid(int index) {
        return index >= 0 && index < this.size();
    }

    /**
     * 确保index有效，无效的话则抛出异常
     *
     * @param index
     */
    private void ensureIndexValid(int index) {
        if (!this.isIndexValid(index)) {
            throw new IndexOutOfBoundsException("index must between 0 and " + (this.size() - 1));
        }
    }

    /**
     * 获取指定索引的头节点
     *
     * @param index
     * @return
     */
    private HeadNode<T> indexOf(int index) {
        this.ensureIndexValid(index);
        return this.headNodes.get(index);
    }

    /**
     * 获取DAG节点数量
     *
     * @return
     */
    private int size() {
        return this.headNodes.size();
    }

    /**
     * 刷新DAG的所有节点的出度和入度
     */
    private void refreshDegrees() {
        for (HeadNode<T> headNode : this.headNodes) {
            headNode.refreshDegrees();
        }
    }
}
