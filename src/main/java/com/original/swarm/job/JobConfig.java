package com.original.swarm.job;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.alibaba.fastjson.JSON;
import lombok.Data;

/**
 * @description:
 * @author: dingtao
 * @date: 2023/11/16
 */
@Data
public class JobConfig {

    /**
     * 任务聚合体名称
     */
    private String chainName;

    /**
     * 任务列表
     */
    private List<JobInfo> jobs;

    /**
     * 边列表，对应的是{@link JobInfo#id}
     */
    private List<EdgeInfo> edges;

    /**
     * {@link #jobs}中{@link JobInfo#id}于其在{@link #jobs}中的下标对应关系
     */
    private Map<Integer, Integer> jobIdToJobsIndexMap;


    @Data
    public static class JobInfo {

        /**
         * 任务ID，从0开始，依次递增
         */
        private int id;

        /**
         * 任务名称
         */
        private String name;

        /**
         * 可以并行执行的任务，如果为空则说明是单体任务，否则是聚合任务
         */
        private List<JobInfo> parallelJobs;
    }

    @Data
    public static class EdgeInfo {

        /**
         * 从哪个id的任务开始
         */
        private int fromId;

        /**
         * 到哪个id的任务结束
         */
        private int toId;
    }

    /**
     * 从流中加载
     *
     * @param inputStream
     * @return
     * @throws IOException
     */
    public static JobConfig ofInputStream(InputStream inputStream) throws IOException {
        return ((JobConfig) JSON.parseObject(inputStream, JobConfig.class)).refreshIndexMap();
    }

    /**
     * 刷新下标map
     *
     * @return this
     */
    private JobConfig refreshIndexMap() {
        this.jobIdToJobsIndexMap = new HashMap<>(this.jobs.size());
        for (int i = 0; i < this.jobs.size(); i++) {
            this.jobIdToJobsIndexMap.put(this.jobs.get(i).getId(), i);
        }
        return this;
    }

    /**
     * 根据任务ID获取其在{@link #jobs}中的下标索引
     *
     * @param id
     * @return
     * @throws NoSuchEdgeDefinitionException 对应下标不存在则抛出边不存在的异常
     */
    public int getIndexById(int id) throws NoSuchEdgeDefinitionException {
        return this.jobIdToJobsIndexMap.get(id);
    }
}
