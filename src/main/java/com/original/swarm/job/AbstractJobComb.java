package com.original.swarm.job;

import java.util.ArrayList;
import java.util.List;

/**
 * 抽象任务聚合体
 *
 * @author: dingtao
 * @date: 2023/11/16
 */
public abstract class AbstractJobComb extends AbstractJob implements JobComb {

    /**
     * 默认重试3次
     */
    public static final int DEFAULT_TRY_TIMES = 3;

    /**
     * 每个任务聚合体中有0或者多个可以并行执行的任务
     */
    private List<Job> parallelJobs;

    public void init() {
        this.parallelJobs = new ArrayList<>();
    }

    @Override
    public synchronized void assignParallelJob(Job job) {
        this.parallelJobs.add(job);
    }

    @Override
    public void execute(JobExecuteContext jobExecuteContext) throws JobExecutionFailedException {
        // 当前任务运行中
        this.running();
        if (!this.hasParallelJobs()) {
            // 当前任务运行成功
            this.succeed();
            return;
        }
        this.parallelJobs.parallelStream().forEach(job -> {
            // 子任务运行中
            if (AbstractJob.isAbstractJob(job)) {
                ((AbstractJob) job).running();
            }
            int tryTimes = 0;
            Exception exception = null;
            while (tryTimes < DEFAULT_TRY_TIMES) {
                try {
                    job.execute(jobExecuteContext);
                    // 子任务运行成功
                    if (AbstractJob.isAbstractJob(job)) {
                        ((AbstractJob) job).succeed();
                    }
                    return;
                } catch (Exception var) {
                    tryTimes++;
                    exception = var;
                }
            }
            // 子任务运行失败
            if (AbstractJob.isAbstractJob(job)) {
                ((AbstractJob) job).failed();
            }
            // 当前任务运行失败
            this.failed();
            throw new JobExecutionFailedException("Failed to execute parallel job", exception);
        });
        // 当前任务运行成功
        this.succeed();
    }

    /**
     * 是否有并行任务
     *
     * @return
     */
    public boolean hasParallelJobs() {
        return null != this.parallelJobs && this.parallelJobs.size() > 0;
    }

    public List<Job> getParallelJobs() {
        return this.parallelJobs;
    }
}
