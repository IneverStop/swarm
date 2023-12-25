package com.original.swarm.job;

import java.util.Map;
import org.springframework.lang.Nullable;

/**
 * 构建Job的工厂接口
 *
 * @author: dingtao
 * @date: 2023/11/17
 */
public interface JobFactory {

    /**
     * 使用jobMap和jobConfig构建一个具有层次关系的Job
     *
     * @param jobMap
     * @param jobConfig
     * @return
     * @throws NoSuchJobDefinitionException  当某个job没有定义时抛出异常
     * @throws NoSuchEdgeDefinitionException 当某个边对应的job不存在时抛出异常
     */
    Job getJob(Map<String, Job> jobMap, JobConfig jobConfig)
        throws NoSuchJobDefinitionException, NoSuchEdgeDefinitionException;

    /**
     * 自动加载任务链
     *
     * @param chainName 任务链名称
     * @param param     整个任务链中所有job都共享的构造函数
     * @return
     * @throws NoSuchJobDefinitionException
     * @throws NoSuchEdgeDefinitionException
     */
    ChainJob getChainJob(String chainName, @Nullable Object param)
        throws NoSuchJobDefinitionException, NoSuchEdgeDefinitionException;

    /**
     * 从spring上下文中根据配置获取任务链
     *
     * @param jobConfig 任务配置
     * @param param     整个任务链中所有job都共享的构造函数
     * @return
     * @throws NoSuchJobDefinitionException
     * @throws NoSuchEdgeDefinitionException
     */
    ChainJob getChainJob(JobConfig jobConfig, @Nullable Object param)
        throws NoSuchJobDefinitionException, NoSuchEdgeDefinitionException;

}
