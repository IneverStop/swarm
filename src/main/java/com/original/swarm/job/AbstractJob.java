package com.original.swarm.job;

/**
 * 抽象任务
 *
 * @author: dingtao
 * @date: 2023/11/17
 */
public abstract class AbstractJob implements Job {

    /**
     * 任务名称
     */
    private String jobName;

    /**
     * 任务运行状态
     */
    private JobRunningStatus runningStatus;

    public AbstractJob() {
        this.runningStatus = JobRunningStatus.WAITING;
    }

    public String getJobName() {
        return this.jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public JobRunningStatus getRunningStatus() {
        return this.runningStatus;
    }

    public void setRunningStatus(JobRunningStatus runningStatus) {
        this.runningStatus = runningStatus;
    }

    /**
     * 任务是否被执行过
     *
     * @return
     */
    public boolean alreadyRun() {
        return !this.runningStatus.equals(JobRunningStatus.WAITING);
    }

    /**
     * 设置任务运行中
     */
    public void running() {
        this.runningStatus = JobRunningStatus.RUNNING;
    }

    /**
     * 设置任务运行成功
     */
    public void succeed() {
        this.runningStatus = JobRunningStatus.SUCCEED;
    }

    /**
     * 设置任务运行失败
     */
    public void failed() {
        this.runningStatus = JobRunningStatus.FAILED;
    }

    /**
     * 判断一个任务是不是抽象任务或者抽象任务的子类
     *
     * @param job
     * @return
     */
    public static boolean isAbstractJob(Job job) {
        return job instanceof AbstractJob;
    }
}
