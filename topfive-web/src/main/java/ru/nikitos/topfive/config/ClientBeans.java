package ru.nikitos.topfive.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import ru.nikitos.topfive.client.DefaultTopRestClient;

@Configuration
public class ClientBeans {

    @Bean
    public DefaultTopRestClient topRestClient(
            @Value("${topfive.rest.service.tops.uri:http://localhost:8081}") String topBaseUri) {
        return new DefaultTopRestClient(
                RestClient.builder()
                        .baseUrl(topBaseUri)
                        .build()
        );
    }
}
