package com.vimcar.client.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import static com.vimcar.client.util.Constants.PROFILE_NOT_TEST;

@Configuration
public class AppConfig {

    private int corePoolSize;
    private int maxPoolSize;

    @Autowired
    public AppConfig(@Value("${app.task.maxPoolSize}") int maxPoolSize,
                     @Value("${app.task.corePoolSize}") int corePoolSize) {
        this.maxPoolSize = maxPoolSize;
        this.corePoolSize = corePoolSize;
    }

    @Bean
    @Profile(PROFILE_NOT_TEST)
    public AsyncTaskExecutor executor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        return executor;
    }
}
