package com.original.swarm.job;

import java.util.List;
import com.original.swarm.graph.DirectedAcyclicGraph;
import com.original.swarm.graph.orthogonallist.HeadNode;
import com.original.swarm.graph.orthogonallist.OrthogonalListDirectedAcyclicGraph;

/**
 * DAG 任务
 *
 * @author: dingtao
 * @date: 2023/11/16
 */
public class DirectedAcyclicGraphChainJob extends AbstractChainJob implements DirectedAcyclicGraph {

    /**
     * 代理的十字链表DAG
     */
    private final OrthogonalListDirectedAcyclicGraph<DirectedAcyclicGraphChainJobComb> delegateGraph;

    public DirectedAcyclicGraphChainJob(List<DirectedAcyclicGraphChainJobComb> jobCombs) {
        this.delegateGraph = new OrthogonalListDirectedAcyclicGraph<>(jobCombs.stream().map(HeadNode::new).toList());
    }


    @Override
    public void execute(JobExecuteContext jobExecuteContext) throws JobExecutionFailedException {
        // 拓扑排序后的下标索引
        try {
            this.delegateGraph.consumeTopologicalSort(job -> job.execute(jobExecuteContext));
        } catch (JobExecutionFailedException var) {
            this.anyFailed(jobExecuteContext);
            throw var;
        }
    }

    @Override
    public void anyFailed(JobExecuteContext jobExecuteContext) {
        this.delegateGraph.consumeTopologicalSort(job -> job.anyFailed(jobExecuteContext));
    }


    @Override
    public void addEdge(int tailIndex, int headIndex) {
        this.delegateGraph.addEdge(tailIndex, headIndex);
    }

    @Override
    public List<Integer> topologicalSort() {
        return this.delegateGraph.topologicalSort();
    }
}
