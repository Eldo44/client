package com.vimcar.client.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.concurrent.Executor;

import static com.vimcar.client.util.Constants.PROFILE_TEST;

@Configuration
@Profile(PROFILE_TEST)
public class TestConfig {
    @Bean
    public Executor executor() {
        return new CurrentThreadExecutor();
    }
}
