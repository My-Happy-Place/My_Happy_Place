package com.estagio2.myhappyplace.service;

import com.estagio2.myhappyplace.dto.SeriesDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.HashMap;

@Service
public class AllTypesService {

    public static String api_key = "7700f72b85d9932120594c5f27f336e1";

    @Autowired
    private WebClient webClient;

    public HashMap getCollection(String name){
        Mono<HashMap> monoSearch = this.webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/search/collection")
                        .queryParam("api_key", api_key)
                        .queryParam("language", "pt-BR")
                        .queryParam("query", name)
                        .build())

                .retrieve()
                .bodyToMono(HashMap.class);

        HashMap search = monoSearch.block();

        return search;
    }

    public HashMap getMoviesByDescription(String descricao){
        Mono<HashMap> monoMovies = this.webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/search/movie")
                        .queryParam("api_key", api_key)
                        .queryParam("language", "pt-BR")
                        .queryParam("query", descricao)
                        .build())

                .retrieve()
                .bodyToMono(HashMap.class);

        HashMap search = monoMovies.block();

        return search;
    }

    public HashMap getAllTypesByDescription(String descricao){
        Mono<HashMap> monoSearch = this.webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/search/multi")
                        .queryParam("api_key", api_key)
                        .queryParam("language", "pt-BR")
                        .queryParam("query", descricao)
                        .build())

                .retrieve()
                .bodyToMono(HashMap.class);

        HashMap search = monoSearch.block();

        return search;
    }

    public HashMap getSeriesByDescription(String descricao){
        Mono<HashMap> monoSeries = this.webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/search/tv")
                        .queryParam("api_key", api_key)
                        .queryParam("language", "pt-BR")
                        .queryParam("query", descricao)
                        .build())

                .retrieve()
                .bodyToMono(HashMap.class);

        HashMap search = monoSeries.block();

        return search;
    }

    public HashMap getTrendingAllTypes(String timeWindow){
        Mono<HashMap> monoTrending = this.webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/trending/all/{timeWindow}")
                        .queryParam("api_key", api_key)
                        .queryParam("language", "pt-BR")
                        .build(timeWindow))

                .retrieve()
                .bodyToMono(HashMap.class);

        HashMap trending = monoTrending.block();

        return trending;
    }

    public HashMap getTrendingMovies(String timeWindow){
        Mono<HashMap> monoTrending = this.webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/trending/movie/{timeWindow}")
                        .queryParam("api_key", api_key)
                        .queryParam("language", "pt-BR")
                        .build(timeWindow))

                .retrieve()
                .bodyToMono(HashMap.class);

        HashMap trendingMovies = monoTrending.block();

        return trendingMovies;
    }

    public HashMap getTrendingSeries(String timeWindow){
        Mono<HashMap> monoTrending = this.webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/trending/tv/{timeWindow}")
                        .queryParam("api_key", api_key)
                        .queryParam("language", "pt-BR")
                        .build(timeWindow))

                .retrieve()
                .bodyToMono(HashMap.class);

        HashMap trendingSeries = monoTrending.block();

        return trendingSeries;
    }
}
