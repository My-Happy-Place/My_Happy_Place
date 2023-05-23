package com.estagio2.myhappyplace.service;

import com.estagio2.myhappyplace.dto.AllTypesDTO;
import com.estagio2.myhappyplace.dto.SeriesDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class AllTypesService {

    public static String api_key = "7700f72b85d9932120594c5f27f336e1";

    @Autowired
    private WebClient webClient;

    @Autowired
    private UserService userService;

    public HashMap getCollection(Long id, String name, Integer page){
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

    public List<AllTypesDTO> getMoviesByDescription(Long id, String descricao, Integer page){
        List<HashMap> movies;
        Mono<HashMap> monoMovies = this.webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/search/movie")
                        .queryParam("api_key", api_key)
                        .queryParam("language", "pt-BR")
                        .queryParam("query", descricao)
                        .queryParam("page", page)
                        .build())

                .retrieve()
                .bodyToMono(HashMap.class);

        movies = Objects.requireNonNull(Objects.requireNonNull(monoMovies.block()).values().stream().toList());
        movies = (List<HashMap>) movies.get(2);
        String type = "M";
        for (HashMap aux : movies){
            aux.put("isFavorite", userService.isFavorite((Integer) aux.get("id"), id));
        }
        AllTypesDTO allTypesDTO = new AllTypesDTO();

        return allTypesDTO.isList(movies, type);
    }

    public List<AllTypesDTO> getAllTypesByDescription(Long id, String descricao, Integer page){
        List<HashMap> list;
        Mono<HashMap> monoSearch = this.webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/search/multi")
                        .queryParam("api_key", api_key)
                        .queryParam("language", "pt-BR")
                        .queryParam("query", descricao)
                        .queryParam("page", page)
                        .build())

                .retrieve()
                .bodyToMono(HashMap.class);

        list = Objects.requireNonNull(Objects.requireNonNull(monoSearch.block()).values().stream().toList());
        list = (List<HashMap>) list.get(2);
//        String type = "M";
        for (HashMap aux : list){
            aux.put("isFavorite", userService.isFavorite((Integer) aux.get("id"), id));
        }
        AllTypesDTO allTypesDTO = new AllTypesDTO();

        return allTypesDTO.isList(list, null);
    }

    public List<AllTypesDTO> getSeriesByDescription(Long id, String descricao, Integer page){
        List<HashMap> series;
        Mono<HashMap> monoSeries = this.webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/search/tv")
                        .queryParam("api_key", api_key)
                        .queryParam("language", "pt-BR")
                        .queryParam("query", descricao)
                        .queryParam("page", page)
                        .build())

                .retrieve()
                .bodyToMono(HashMap.class);

        series = Objects.requireNonNull(Objects.requireNonNull(monoSeries.block()).values().stream().toList());
        series = (List<HashMap>) series.get(2);
        String type = "S";
        for (HashMap aux : series){
            aux.put("isFavorite", userService.isFavorite((Integer) aux.get("id"), id));
        }
        AllTypesDTO allTypesDTO = new AllTypesDTO();

        return allTypesDTO.isList(series, type);
    }

    public List<AllTypesDTO> getTrendingAllTypes(Long id, String timeWindow, Integer page){
        List<HashMap> trending;
        Mono<HashMap> monoTrending = this.webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/trending/all/{timeWindow}")
                        .queryParam("api_key", api_key)
                        .queryParam("language", "pt-BR")
                        .queryParam("page", page)
                        .build(timeWindow))

                .retrieve()
                .bodyToMono(HashMap.class);

        trending = Objects.requireNonNull(Objects.requireNonNull(monoTrending.block()).values().stream().toList());
        trending = (List<HashMap>) trending.get(2);
//        String type = "S";
        for (HashMap aux : trending){
            aux.put("isFavorite", userService.isFavorite((Integer) aux.get("id"), id));
        }
        AllTypesDTO allTypesDTO = new AllTypesDTO();

        return allTypesDTO.isList(trending, null);
    }

    public List<AllTypesDTO> getTrendingMovies(Long id, String timeWindow, Integer page){
        List<HashMap> trendingMovies;
        Mono<HashMap> monoTrending = this.webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/trending/movie/{timeWindow}")
                        .queryParam("api_key", api_key)
                        .queryParam("language", "pt-BR")
                        .queryParam("page", page)
                        .build(timeWindow))

                .retrieve()
                .bodyToMono(HashMap.class);

        trendingMovies = Objects.requireNonNull(Objects.requireNonNull(monoTrending.block()).values().stream().toList());
        trendingMovies = (List<HashMap>) trendingMovies.get(2);
        String type = "M";
        for (HashMap aux : trendingMovies){
            aux.put("isFavorite", userService.isFavorite((Integer) aux.get("id"), id));
        }
        AllTypesDTO allTypesDTO = new AllTypesDTO();

        return allTypesDTO.isList(trendingMovies, type);
    }

    public List<AllTypesDTO> getTrendingSeries(Long id, String timeWindow, Integer page){
        List<HashMap> trendingSeries;
        Mono<HashMap> monoTrending = this.webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/trending/tv/{timeWindow}")
                        .queryParam("api_key", api_key)
                        .queryParam("language", "pt-BR")
                        .queryParam("page", page)
                        .build(timeWindow))

                .retrieve()
                .bodyToMono(HashMap.class);

        trendingSeries = Objects.requireNonNull(Objects.requireNonNull(monoTrending.block()).values().stream().toList());
        trendingSeries = (List<HashMap>) trendingSeries.get(2);
        String type = "S";
        for (HashMap aux : trendingSeries){
            aux.put("isFavorite", userService.isFavorite((Integer) aux.get("id"), id));
        }
        AllTypesDTO allTypesDTO = new AllTypesDTO();

        return allTypesDTO.isList(trendingSeries, type);
    }
}
