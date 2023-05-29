package com.estagio2.myhappyplace.service.seriesseasons;

import com.estagio2.myhappyplace.dto.SeriesDTO;
import com.estagio2.myhappyplace.dto.seriesseasons.SeriesSeasonsDTO;
import com.estagio2.myhappyplace.dto.seriesseasons.episodes.SeriesEpisodesDTO;
import com.estagio2.myhappyplace.service.UserService;
import com.estagio2.myhappyplace.service.seriesseasons.episodes.SeriesEpisodesService;
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

    @Autowired
    private SeriesEpisodesService seriesEpisodesService;


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

    public HashMap getAggregateCredits(Long codigo, Long seasonNumber){
        Mono<HashMap> monoCredits = this.webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/tv/{codigo}/season/{seasonNumber}/aggregate_credits")
                        .queryParam("api_key", api_key)
                        .queryParam("language", "pt-BR")
                        .build(codigo, seasonNumber))

                .retrieve()
                .bodyToMono(HashMap.class);

        HashMap credits = Objects.requireNonNull(monoCredits.block());

        return credits;
    }

    public HashMap getCredits(Long codigo, Long seasonNumber){
        Mono<HashMap> monoCredits = this.webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/tv/{codigo}/season/{seasonNumber}/credits")
                        .queryParam("api_key", api_key)
                        .queryParam("language", "pt-BR")
                        .build(codigo, seasonNumber))

                .retrieve()
                .bodyToMono(HashMap.class);

        HashMap credits = Objects.requireNonNull(monoCredits.block());

        return credits;
    }

    public HashMap getImages(Long codigo, Long seasonNumber){
        Mono<HashMap> monoImages = this.webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/tv/{codigo}/season/{seasonNumber}/images")
                        .queryParam("api_key", api_key)
                        .queryParam("language", "pt-BR")
                        .build(codigo, seasonNumber))

                .retrieve()
                .bodyToMono(HashMap.class);

        HashMap images = Objects.requireNonNull(monoImages.block());

        return images;
    }

    public HashMap getVideos(Long codigo, Long seasonNumber){
        Mono<HashMap> monoVideos = this.webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/tv/{codigo}/season/{seasonNumber}/videos")
                        .queryParam("api_key", api_key)
                        .queryParam("language", "pt-BR")
                        .build(codigo, seasonNumber))

                .retrieve()
                .bodyToMono(HashMap.class);

        HashMap videos = Objects.requireNonNull(monoVideos.block());

        return videos;
    }

    public HashMap getWatchProviders(Long codigo, Long seasonNumber){
        Mono<HashMap> monoProviders = this.webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/tv/{codigo}/season/{seasonNumber}/watch/providers")
                        .queryParam("api_key", api_key)
                        .queryParam("language", "pt-BR")
                        .build(codigo, seasonNumber))

                .retrieve()
                .bodyToMono(HashMap.class);

        HashMap providers = Objects.requireNonNull(monoProviders.block());

        return providers;
    }


    //---------------- EPISODES METHODS ---------------------------------------------------------

    public SeriesEpisodesDTO getSerieEpisode(Long codigo, Long seasonNumber, Long episodeNumber, Long id){
        return this.seriesEpisodesService.getEpisode(codigo, seasonNumber, episodeNumber, id);
    }

    public HashMap getSerieEpisodeCredits(Long codigo, Long seasonNumber, Long episodeNumber){
        return this.seriesEpisodesService.getCredits(codigo, seasonNumber, episodeNumber);
    }

    public HashMap getSerieEpisodeImages(Long codigo, Long seasonNumber, Long episodeNumber){
        return this.seriesEpisodesService.getImages(codigo, seasonNumber, episodeNumber);
    }

    public HashMap getSerieEpisodeVideos(Long codigo, Long seasonNumber, Long episodeNumber){
        return this.seriesEpisodesService.getVideos(codigo, seasonNumber, episodeNumber);
    }
}
