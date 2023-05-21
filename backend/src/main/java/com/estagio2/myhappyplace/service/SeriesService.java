package com.estagio2.myhappyplace.service;

import com.estagio2.myhappyplace.dto.SeriesDTO;
import com.estagio2.myhappyplace.dto.UserDTO;
import com.estagio2.myhappyplace.entities.FavoriteMovies;
import com.estagio2.myhappyplace.entities.FavoriteSeries;
import com.estagio2.myhappyplace.repositories.FavoriteSeriesRepository;
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
        favoriteSeriesRepository.delete(favoriteSeries);
    }

    @Transactional(readOnly = true)
    public FavoriteSeries findById(Long id){
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

    public List<SeriesDTO> listFavoriteSeries(List<Long> idsSeries){
        List<HashMap> favorites = new ArrayList<>();
        if(!idsSeries.isEmpty()){
            for (Long id : idsSeries){
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
        return series.isList(favorites, true);
    }


    //------------- METODOS TMDB ---------------------------------------------------------------

    public HashMap getCredits(Long codigo){
        Mono<HashMap> monoCredits = this.webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/movie/{codigo}/credits")
                        .queryParam("api_key", api_key)
                        .queryParam("language", "pt-BR")
                        .build(codigo))

                .retrieve()
                .bodyToMono(HashMap.class);

        HashMap credits = monoCredits.block();

        return credits;
    }

}
