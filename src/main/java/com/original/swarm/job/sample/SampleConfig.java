package com.original.swarm.job.sample;

import com.original.swarm.job.Job;
import org.springframework.context.annotation.Bean;

/**
 * @description:
 * @author: dingtao
 * @date: 2023/11/21
 */
public class SampleConfig {

    @Bean("A")
    public Job a() {
        return jobExecuteContext -> System.out.println("doing A");
    }

    @Bean("B")
    public Job b() {
        return jobExecuteContext -> {
            // do nothing
        };
    }

    @Bean("B-Sub1")
    public Job bSub1() {
        return jobExecuteContext -> System.out.println("doing B-Sub1");
    }

    @Bean("B-Sub2")
    public Job bSub2() {
        return jobExecuteContext -> System.out.println("doing B-Sub2");
    }

    @Bean("C")
    public Job c() {
        return jobExecuteContext -> System.out.println("doing C");
    }

    @Bean("D")
    public Job d() {
        return jobExecuteContext -> System.out.println("doing D");
    }

    @Bean("E")
    public Job e() {
        return jobExecuteContext -> System.out.println("doing E");
    }

    @Bean("F")
    public Job f() {
        return jobExecuteContext -> System.out.println("doing F");
    }

    @Bean("G")
    public Job g() {
        return jobExecuteContext -> System.out.println("doing G");
    }
}
