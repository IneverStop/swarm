package com.original.swarm;

import java.util.ArrayList;
import java.util.List;
import com.original.swarm.graph.orthogonallist.HeadNode;
import com.original.swarm.graph.orthogonallist.OrthogonalListDirectedAcyclicGraph;

/**
 * @description:
 * @author: dingtao
 * @date: 2023/11/14
 */
public class GraphTest {

    public static void main(String[] args) {
        List<HeadNode<String>> headNodes = new ArrayList<>();
        headNodes.add(new HeadNode<>("A"));
        headNodes.add(new HeadNode<>("B"));
        headNodes.add(new HeadNode<>("C"));
        headNodes.add(new HeadNode<>("D"));
        headNodes.add(new HeadNode<>("E"));
        headNodes.add(new HeadNode<>("F"));

        // 孤立点
        headNodes.add(new HeadNode<>("G"));
        OrthogonalListDirectedAcyclicGraph graph = new OrthogonalListDirectedAcyclicGraph(
            headNodes);

        graph.addEdge(0, 1);
        graph.addEdge(0, 2);
        graph.addEdge(1, 2);
        graph.addEdge(3, 2);
        graph.addEdge(4, 2);
        graph.addEdge(1, 5);
        graph.addEdge(2, 5);
        graph.addEdge(4, 5);

        // 环
//        graph.addEdge(5, 0);
        System.out.println(graph.topologicalSort());
        graph.consumeTopologicalSort(System.out::println);
    }
}
