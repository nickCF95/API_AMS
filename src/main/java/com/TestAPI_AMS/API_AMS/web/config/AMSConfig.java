package com.TestAPI_AMS.API_AMS.web.config;

import com.TestAPI_AMS.API_AMS.custom.handlers.WebClientStatusCodeHandler;
import com.TestAPI_AMS.API_AMS.persistence.repository.mongoDB.EventosRepository;
import com.TestAPI_AMS.API_AMS.persistence.repository.mongoDB.LecturasMongoRepository;
import com.TestAPI_AMS.API_AMS.persistence.repository.mongoDB.RespuestaAccionesRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@EnableScheduling
@EnableMongoRepositories(basePackageClasses = {EventosRepository.class, LecturasMongoRepository.class, RespuestaAccionesRepository.class})
public class AMSConfig {

    ExchangeFilterFunction errorResponseFilter = ExchangeFilterFunction
            .ofResponseProcessor(WebClientStatusCodeHandler::exchangeFilterResponseProcessor);

    @Value("${url.API_AMS}")
    private String addressBaseUrl;

    @Bean
    public WebClient webClient() {
        return WebClient.builder().filter(errorResponseFilter).baseUrl(addressBaseUrl).build();
    }

    @Bean
    public RedisTemplate<?, ?> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<?, ?> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        return template;
    }

    @Bean
    public TaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
        threadPoolTaskScheduler.setPoolSize(5);
        threadPoolTaskScheduler.setThreadNamePrefix("ThreadPoolTaskScheduler");
        return threadPoolTaskScheduler;
    }
}
