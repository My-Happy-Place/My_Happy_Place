package com.estagio2.myhappyplace.service.seriesseasons;

import com.estagio2.myhappyplace.dto.SeriesDTO;
import com.estagio2.myhappyplace.dto.seriesseasons.SeriesSeasonsDTO;
import com.estagio2.myhappyplace.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@Service
public class SeriesSeasonsService {

    public static String api_key = "7700f72b85d9932120594c5f27f336e1";

    @Autowired
    private WebClient webClient;

    @Autowired
    private UserService userService;


    public SeriesSeasonsDTO getSeason(Long codigo, Long seasonNumber, Long id){
//        List<HashMap> seasons;
        Mono<HashMap> monoSeasons = this.webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/tv/{codigo}/season/{seasonNumber}")
                        .queryParam("api_key", api_key)
                        .queryParam("language", "pt-BR")
                        .build(codigo, seasonNumber))

                .retrieve()
                .bodyToMono(HashMap.class);

        HashMap season = Objects.requireNonNull(monoSeasons.block());
//        seasons = Objects.requireNonNull(Objects.requireNonNull(monoSeasons.block()).values().stream().toList());
//        seasons = (List<HashMap>) seasons.get(2);
//        String type = "S";
//        for (HashMap aux : seasons){
//            aux.put("isFavorite", userService.isFavorite((Integer) aux.get("id"), id));
//        }
//        SeriesSeasonsDTO seriesSeasonsDTO = new SeriesSeasonsDTO();

        return new SeriesSeasonsDTO(season);
    }
}
