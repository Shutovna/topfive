package ru.nikitos.topfive.web.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;
import org.springframework.web.client.RestClient;
import ru.nikitos.topfive.web.client.DefaultTopRestClient;

@Configuration
public class ClientBeans {

    @Bean
    public DefaultTopRestClient topRestClient(
            @Value("${topfive.rest.service.tops.uri:http://localhost:8081}") String topBaseUri,
            @Value("${topfive.rest.service.username:}") String restClientUsername,
            @Value("${topfive.rest.service.password:}") String restClientPassword) {
        return new DefaultTopRestClient(
                RestClient.builder()
                        .baseUrl(topBaseUri)
                        .requestInterceptor(new BasicAuthenticationInterceptor(restClientUsername, restClientPassword))
                        .build()
        );
    }
}
