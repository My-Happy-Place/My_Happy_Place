package com.estagio2.myhappyplace.consumer;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class ConsumerApi {

    @Bean
    public WebClient webClient(WebClient.Builder builder){
        return builder.baseUrl("https://api.themoviedb.org/3")
                .defaultHeader(HttpHeaders.CONTENT_TYPE,
                        MediaType.APPLICATION_JSON_VALUE).build();
    }
}
