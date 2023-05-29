package com.estagio2.myhappyplace.service.seriesseasons.episodes;

import com.estagio2.myhappyplace.dto.seriesseasons.SeriesSeasonsDTO;
import com.estagio2.myhappyplace.dto.seriesseasons.episodes.SeriesEpisodesDTO;
import com.estagio2.myhappyplace.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Objects;

@Service
public class SeriesEpisodesService {

    public static String api_key = "7700f72b85d9932120594c5f27f336e1";

    @Autowired
    private WebClient webClient;

    @Autowired
    private UserService userService;


    public SeriesEpisodesDTO getEpisode(Long codigo, Long seasonNumber, Long episodeNumber, Long id){
//        List<HashMap> seasons;
        Mono<HashMap> monoEpisodes = this.webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/tv/{codigo}/season/{seasonNumber}/episode/{episodeNumber}")
                        .queryParam("api_key", api_key)
                        .queryParam("language", "pt-BR")
                        .build(codigo, seasonNumber, episodeNumber))

                .retrieve()
                .bodyToMono(HashMap.class);

        HashMap episode = Objects.requireNonNull(monoEpisodes.block());
//        seasons = Objects.requireNonNull(Objects.requireNonNull(monoSeasons.block()).values().stream().toList());
//        seasons = (List<HashMap>) seasons.get(2);
//        String type = "S";
//        for (HashMap aux : seasons){
//            aux.put("isFavorite", userService.isFavorite((Integer) aux.get("id"), id));
//        }
//        SeriesSeasonsDTO seriesSeasonsDTO = new SeriesSeasonsDTO();

        return new SeriesEpisodesDTO(episode);
    }

    public HashMap getCredits(Long codigo, Long seasonNumber, Long episodeNumber){
        Mono<HashMap> monoCredits = this.webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/tv/{codigo}/season/{seasonNumber}/episode/{episodeNumber}/credits")
                        .queryParam("api_key", api_key)
                        .queryParam("language", "pt-BR")
                        .build(codigo, seasonNumber, episodeNumber))

                .retrieve()
                .bodyToMono(HashMap.class);

        HashMap credits = Objects.requireNonNull(monoCredits.block());

        return credits;
    }

    public HashMap getImages(Long codigo, Long seasonNumber, Long episodeNumber){
        Mono<HashMap> monoImages = this.webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/tv/{codigo}/season/{seasonNumber}/episode/{episodeNumber}/images")
                        .queryParam("api_key", api_key)
                        .queryParam("language", "pt-BR")
                        .build(codigo, seasonNumber, episodeNumber))

                .retrieve()
                .bodyToMono(HashMap.class);

        HashMap images = Objects.requireNonNull(monoImages.block());

        return images;
    }

    public HashMap getVideos(Long codigo, Long seasonNumber, Long episodeNumber){
        Mono<HashMap> monoVideos = this.webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/tv/{codigo}/season/{seasonNumber}/episode/{episodeNumber}/videos")
                        .queryParam("api_key", api_key)
                        .queryParam("language", "pt-BR")
                        .build(codigo, seasonNumber, episodeNumber))

                .retrieve()
                .bodyToMono(HashMap.class);

        HashMap videos = Objects.requireNonNull(monoVideos.block());

        return videos;
    }
}
