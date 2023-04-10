package com.estagio2.myhappyplace.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@Service
public class MovieService {

    public static String api_key = "7700f72b85d9932120594c5f27f336e1";

    @Autowired
    private WebClient webClient;

    public HashMap movie(Long codigo){
//        String accessToken = requestToken();
        Mono<HashMap> monoMovie = this.webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/movie/{codigo}")
                        .queryParam("api_key", api_key)
                        .build(codigo))

                .retrieve()
                .bodyToMono(HashMap.class);

        HashMap movie = monoMovie.block();

        return movie;
    }



//    public String requestToken(){
//        Mono<HashMap> monoToken =  this.webClient.get()
//                .uri(uriBuilder -> uriBuilder.path("/authentication/token/new")
//                        .queryParam("api_key", key)
//                        .build())
//                .retrieve()
//                .bodyToMono(HashMap.class);
//
//        HashMap access_token = monoToken.block();
////        createSession(key);
//        return access_token.get("request_token").toString();
//    }

//    public void createSession(String key){
//        this.webClient.post().uri(uriBuilder -> uriBuilder.path("/authentication/session/new")
//                .queryParam("api_key", key)
//                .build())
//                .retrieve()
//                .bodyToMono(HashMap.class);
//    }
}
