package com.chenyudan.parent.message.threadpool;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Description: TODO
 *
 * @author Chyern
 * @since 2023/1/10 17:45
 */
@Configuration
public class SpringMessageThreadPoolConfig {

    @Bean("springMessageThreadPool")
    public ThreadPoolExecutor springMessageThreadPool() {
        return new ThreadPoolExecutor(5,
                10,
                10L,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(500));
    }
}
