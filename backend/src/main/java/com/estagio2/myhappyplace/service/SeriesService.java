package com.estagio2.myhappyplace.service;

import com.estagio2.myhappyplace.dto.AllTypesDTO;
import com.estagio2.myhappyplace.dto.SeriesDTO;
import com.estagio2.myhappyplace.dto.UserDTO;
import com.estagio2.myhappyplace.dto.seriesseasons.SeriesSeasonsDTO;
import com.estagio2.myhappyplace.dto.seriesseasons.episodes.SeriesEpisodesDTO;
import com.estagio2.myhappyplace.entities.FavoriteMovies;
import com.estagio2.myhappyplace.entities.FavoriteSeries;
import com.estagio2.myhappyplace.repositories.FavoriteSeriesRepository;
import com.estagio2.myhappyplace.service.seriesseasons.SeriesSeasonsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.*;

@Service
public class SeriesService {
    public static String api_key = "7700f72b85d9932120594c5f27f336e1";
    @Autowired
    private WebClient webClient;
    @Autowired
    private FavoriteSeriesRepository favoriteSeriesRepository;
    @Autowired
    private UserService userService;

    @Autowired
    private SeriesSeasonsService seriesSeasonsService;


    @Transactional
    public Long saveFavoriteSerie(FavoriteSeries favoriteSeries){
        favoriteSeries = favoriteSeriesRepository.save(favoriteSeries);
        UserDTO user = userService.findById(favoriteSeries.getUserList().get(0).getId());
        user.getFavoriteSeries().add(favoriteSeries);
        userService.update(user);
        return favoriteSeries.getId();
    }

    @Transactional
    public void excluirFavoriteSerie(FavoriteSeries favoriteSeries){
        UserDTO user = userService.findById(favoriteSeries.getUserList().get(0).getId());
        user.getFavoriteSeries().removeIf(favoriteSeries1 -> Objects.equals(favoriteSeries1.getSerieId(), favoriteSeries.getSerieId()));
        favoriteSeries.getUserList().removeIf(user1 -> Objects.equals(user1.getId(), user.getId()));
        userService.update(user);
        favoriteSeriesRepository.deleteBySerieId(favoriteSeries.getSerieId());
    }

    @Transactional(readOnly = true)
    public FavoriteSeries findById(Integer id){
        Optional<FavoriteSeries> obj = favoriteSeriesRepository.findBySerieId(id);
        if(obj.isPresent()){
            FavoriteSeries favoriteSeries = obj.get();
            return favoriteSeries;
        }
        return new FavoriteSeries();
    }

    public SeriesDTO seriePorId(Long codigo){
//        String accessToken = requestToken();
        Mono<HashMap> monoSerie = this.webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/tv/{codigo}")
                        .queryParam("api_key", api_key)
                        .queryParam("language", "pt-BR")
                        .build(codigo))

                .retrieve()
                .bodyToMono(HashMap.class);

        HashMap serie = monoSerie.block();

        return new SeriesDTO(serie);
    }

    public List<SeriesDTO> listFavoriteSeries(List<Integer> idsSeries){
        List<HashMap> favorites = new ArrayList<>();
        if(!idsSeries.isEmpty()){
            for (Integer id : idsSeries){
                Mono<HashMap> monoMovie = this.webClient.get()
                        .uri(uriBuilder -> uriBuilder.path("/tv/{id}")
                                .queryParam("api_key", api_key)
                                .queryParam("language", "pt-BR")
                                .build(id))

                        .retrieve()
                        .bodyToMono(HashMap.class);

                assert favorites != null;
                favorites.add(monoMovie.block());
            }
        }
        SeriesDTO series = new SeriesDTO();
        return series.isList(favorites, true, null);
    }


    //------------- METODOS TMDB ---------------------------------------------------------------

    public List<SeriesDTO> getSeriesAiringToday(Long id, Integer page){
        List<HashMap> series;
        Mono<HashMap> monoSeries = this.webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/tv/airing_today")
                        .queryParam("api_key", api_key)
                        .queryParam("language", "pt-BR")
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
        SeriesDTO seriesDTO = new SeriesDTO();

        return seriesDTO.isList(series, false, type);
    }

    public List<SeriesDTO> getSeriesOnTheAir(Long id, Integer page){
        List<HashMap> series;
        Mono<HashMap> monoSeries = this.webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/tv/on_the_air")
                        .queryParam("api_key", api_key)
                        .queryParam("language", "pt-BR")
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
        SeriesDTO seriesDTO = new SeriesDTO();

        return seriesDTO.isList(series, false, type);
    }

    public List<SeriesDTO> getSeriesPopular(Long id, Integer page){
        List<HashMap> series;
        Mono<HashMap> monoSeries = this.webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/tv/popular")
                        .queryParam("api_key", api_key)
                        .queryParam("language", "pt-BR")
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
        SeriesDTO seriesDTO = new SeriesDTO();

        return seriesDTO.isList(series, false, type);
    }

    public List<SeriesDTO> getSeriesTopRated(Long id, Integer page){
        List<HashMap> series;
        Mono<HashMap> monoSeries = this.webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/tv/top_rated")
                        .queryParam("api_key", api_key)
                        .queryParam("language", "pt-BR")
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
        SeriesDTO seriesDTO = new SeriesDTO();

        return seriesDTO.isList(series, false, type);
    }

    public HashMap getAggregateCredits(Long codigo){
        Mono<HashMap> monoCredits = this.webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/tv/{codigo}/aggregate_credits")
                        .queryParam("api_key", api_key)
                        .queryParam("language", "pt-BR")
                        .build(codigo))

                .retrieve()
                .bodyToMono(HashMap.class);

        HashMap credits = monoCredits.block();

        return credits;
    }

    public HashMap getImages(Long codigo){
        Mono<HashMap> monoImages = this.webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/tv/{codigo}/images")
                        .queryParam("api_key", api_key)
                        .queryParam("language", "pt-BR")
                        .build(codigo))

                .retrieve()
                .bodyToMono(HashMap.class);

        HashMap images = monoImages.block();

        return images;
    }

    public List<SeriesDTO> getRecommendations(Long codigo, Long id, Integer page){
        List<HashMap> series;
        Mono<HashMap> monoSeries = this.webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/tv/{codigo}/recommendations")
                        .queryParam("api_key", api_key)
                        .queryParam("language", "pt-BR")
                        .queryParam("page", page)
                        .build(codigo))

                .retrieve()
                .bodyToMono(HashMap.class);

        series = Objects.requireNonNull(Objects.requireNonNull(monoSeries.block()).values().stream().toList());
        series = (List<HashMap>) series.get(2);
        String type = "S";
        for (HashMap aux : series){
            aux.put("isFavorite", userService.isFavorite((Integer) aux.get("id"), id));
        }
        SeriesDTO seriesDTO = new SeriesDTO();

        return seriesDTO.isList(series, false, type);
    }

    public HashMap getReviews(Long codigo, Integer page){
        Mono<HashMap> monoReviews = this.webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/tv/{codigo}/reviews")
                        .queryParam("api_key", api_key)
                        .queryParam("language", "pt-BR")
                        .queryParam("page", page)
                        .build(codigo))

                .retrieve()
                .bodyToMono(HashMap.class);

        HashMap reviews = monoReviews.block();

        return reviews;
    }

    public List<SeriesDTO> getSimilar(Long codigo, Long id, Integer page){
        List<HashMap> series;
        Mono<HashMap> monoSeries = this.webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/tv/{codigo}/similar")
                        .queryParam("api_key", api_key)
                        .queryParam("language", "pt-BR")
                        .queryParam("page", page)
                        .build(codigo))

                .retrieve()
                .bodyToMono(HashMap.class);

        series = Objects.requireNonNull(Objects.requireNonNull(monoSeries.block()).values().stream().toList());
        series = (List<HashMap>) series.get(2);
        String type = "S";
        for (HashMap aux : series){
            aux.put("isFavorite", userService.isFavorite((Integer) aux.get("id"), id));
        }
        SeriesDTO seriesDTO = new SeriesDTO();

        return seriesDTO.isList(series, false, type);
    }

    public HashMap getVideos(Long codigo){
        Mono<HashMap> monoVideos = this.webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/tv/{codigo}/videos")
                        .queryParam("api_key", api_key)
                        .queryParam("language", "pt-BR")
                        .build(codigo))

                .retrieve()
                .bodyToMono(HashMap.class);

        HashMap videos = monoVideos.block();

        return videos;
    }

    public HashMap getWatchProviders(Long codigo){
        Mono<HashMap> monoProviders = this.webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/tv/{codigo}/watch/providers")
                        .queryParam("api_key", api_key)
                        .queryParam("language", "pt-BR")
                        .build(codigo))

                .retrieve()
                .bodyToMono(HashMap.class);

        HashMap providers = monoProviders.block();

        return providers;
    }

    //---------------- SEASONS METHODS ---------------------------------------------------------
    public SeriesSeasonsDTO getSerieSeason(Long codigo, Long seasonNumber, Long id){
        return seriesSeasonsService.getSeason(codigo, seasonNumber, id);
    }

    public List<SeriesSeasonsDTO> getSeasonsList(Long codigo, Long id){
        SeriesDTO serie = seriePorId(codigo);
        List<SeriesSeasonsDTO> seasons = new ArrayList<>();

        for (int i = 1; i <= serie.getNumberOfSeasons(); i++) {
            seasons.add(seriesSeasonsService.getSeason(codigo, (long) i, id));
        }
        return seasons;
    }

    public HashMap getSerieSeasonAggregateCredits(Long codigo, Long seasonNumber){
        return seriesSeasonsService.getAggregateCredits(codigo, seasonNumber);
    }

    public HashMap getSerieSeasonCredits(Long codigo, Long seasonNumber){
        return seriesSeasonsService.getCredits(codigo, seasonNumber);
    }

    public HashMap getSerieSeasonImages(Long codigo, Long seasonNumber){
        return seriesSeasonsService.getImages(codigo, seasonNumber);
    }

    public HashMap getSerieSeasonVideos(Long codigo, Long seasonNumber){
        return seriesSeasonsService.getVideos(codigo, seasonNumber);
    }

    public HashMap getSerieSeasonWatchProviders(Long codigo, Long seasonNumber){
        return seriesSeasonsService.getWatchProviders(codigo, seasonNumber);
    }

    //---------------- EPISODES METHODS ---------------------------------------------------------

    public SeriesEpisodesDTO getSerieEpisode(Long codigo, Long seasonNumber, Long episodeNumber, Long id){
        return this.seriesSeasonsService.getSerieEpisode(codigo, seasonNumber, episodeNumber, id);
    }

    public HashMap getSerieEpisodeCredits(Long codigo, Long seasonNumber, Long episodeNumber){
        return this.seriesSeasonsService.getSerieEpisodeCredits(codigo, seasonNumber, episodeNumber);
    }

    public HashMap getSerieEpisodeImages(Long codigo, Long seasonNumber, Long episodeNumber){
        return this.seriesSeasonsService.getSerieEpisodeImages(codigo, seasonNumber, episodeNumber);
    }

    public HashMap getSerieEpisodeVideos(Long codigo, Long seasonNumber, Long episodeNumber){
        return this.seriesSeasonsService.getSerieEpisodeVideos(codigo, seasonNumber, episodeNumber);
    }
}
