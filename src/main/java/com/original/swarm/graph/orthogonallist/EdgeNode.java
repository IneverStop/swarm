package com.original.swarm.graph.orthogonallist;

import lombok.Data;

/**
 * 十字链表的边节点定义
 *
 * <p>
 * 例如，存在A，B两个节点，以及一条从A节点指向B节点的边( A -----> B ), 我们将箭头指向的位置B称为弧头，而箭头出发的位置A称为弧尾.
 * 对于这个示例，{@code tailIndex} 是A节点在节点列表中的下标索引，{@code headIndex} 是B节点在节点列表中的下标索引.
 * </p>
 * <p>
 * 另外假设存在( A -----> C )、( D -----> B)两条弧，那么对于这个示例，{@code tailNextLink}指向以A为弧尾的下一条边 ( A -----> C ),
 * 而{@code headNextLink}指向以B为弧头的下一条边 ( D -----> B )
 * </p>
 *
 * @author: dingtao
 * @date: 2023/11/14
 */
@Data
public class EdgeNode {

    /**
     * 弧尾顶点的位置
     */
    private int tailIndex;

    /**
     * 弧头顶点的位置
     */
    private int headIndex;

    /**
     * 下一条以{@code tailIndex}对应的节点为弧尾的边
     */
    private EdgeNode tailNextLink;

    /**
     * 下一条以{@code headIndex}对应的节点为弧头的边
     */
    private EdgeNode headNextLink;

    private EdgeNode() {

    }

    public EdgeNode(int tailIndex, int headIndex) {
        this.tailIndex = tailIndex;
        this.headIndex = headIndex;
    }

    /**
     * 给当前弧添加一个相同弧尾的下一条弧
     *
     * @param edgeNode
     */
    public void addTailNextLink(EdgeNode edgeNode) {
        EdgeNode currentTail = this.tailNextLink;
        if (null == currentTail) {
            // 当前弧对应的弧尾此前没有其他弧
            this.tailNextLink = edgeNode;
            return;
        }
        while (null != currentTail.tailNextLink) {
            // 找到最后一个节点
            currentTail = currentTail.tailNextLink;
        }
        currentTail.tailNextLink = edgeNode;
    }

    /**
     * 给当前弧添加一个相同弧头的下一条弧
     *
     * @param edgeNode
     */
    public void addHeadNextLink(EdgeNode edgeNode) {
        EdgeNode currentHead = this.headNextLink;
        if (null == currentHead) {
            // 当前弧对应的弧头此前没有其他弧
            this.headNextLink = edgeNode;
            return;
        }
        while (null != currentHead.headNextLink) {
            // 找到最后一个节点
            currentHead = currentHead.headNextLink;
        }
        currentHead.headNextLink = edgeNode;
    }
}
