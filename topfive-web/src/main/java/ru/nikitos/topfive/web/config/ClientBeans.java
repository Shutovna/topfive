package ru.nikitos.topfive.web.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;
import org.springframework.web.client.RestClient;
import ru.nikitos.topfive.web.client.DefaultSongRestClient;
import ru.nikitos.topfive.web.client.DefaultTopRestClient;

@Configuration
public class ClientBeans {

    @Bean
    public DefaultTopRestClient topRestClient(
            @Value("${topfive.rest.service.tops.uri:http://localhost:8081}") String topBaseUri,
            @Value("${topfive.test.username:}") String restClientUsername,
            @Value("${topfive.test.password:}") String restClientPassword) {
        return new DefaultTopRestClient(
                RestClient.builder()
                        .baseUrl(topBaseUri)
                        .requestInterceptor(new BasicAuthenticationInterceptor(restClientUsername, restClientPassword))
                        .build()
        );
    }

    @Bean
    public DefaultSongRestClient songRestClient(
            @Value("${topfive.rest.service.tops.uri:http://localhost:8081}") String songBaseUri,
            @Value("${topfive.test.username:}") String restClientUsername,
            @Value("${topfive.test.password:}") String restClientPassword) {
        return new DefaultSongRestClient(
                RestClient.builder()
                        .baseUrl(songBaseUri)
                        .requestInterceptor(new BasicAuthenticationInterceptor(restClientUsername, restClientPassword))
                        .build()
        );
    }
}
