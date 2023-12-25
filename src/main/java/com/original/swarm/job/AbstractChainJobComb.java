package com.original.swarm.job;

/**
 * 抽象链式任务聚合体
 *
 * @author: dingtao
 * @date: 2023/11/17
 */
public abstract class AbstractChainJobComb extends AbstractJobComb implements ChainJob {

    @Override
    public void anyFailed(JobExecuteContext jobExecuteContext) {
        if (!this.hasParallelJobs()) {
            return;
        }
        this.getParallelJobs().parallelStream().forEach(job -> {
            if (job instanceof AbstractChainJob && ((AbstractChainJob)job).alreadyRun()) {
                ((AbstractChainJob) job).anyFailed(jobExecuteContext);
            }
        });
    }
}
