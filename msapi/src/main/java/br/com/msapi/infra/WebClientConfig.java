package br.com.msapi.infra;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient getWebClient(@Value("${spring.backend.host}") String host){
        return WebClient.builder().baseUrl(host).build();
    }
}
