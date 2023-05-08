package com.estagio2.myhappyplace.service;

import com.estagio2.myhappyplace.dto.UserDTO;
import com.estagio2.myhappyplace.entities.FavoriteMovies;
import com.estagio2.myhappyplace.repositories.FavoriteMoviesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MovieService {

    public static String api_key = "7700f72b85d9932120594c5f27f336e1";
    @Autowired
    private WebClient webClient;
    @Autowired
    private FavoriteMoviesRepository favoriteMoviesRepository;
    @Autowired
    private UserService userService;

    @Transactional
    public Long saveFavoriteMovie(FavoriteMovies favoriteMovies){
         favoriteMovies = favoriteMoviesRepository.save(favoriteMovies);
         UserDTO user = userService.findById(favoriteMovies.getUserList().get(0).getId());
         user.getFavoriteMovies().add(favoriteMovies);
         userService.update(user);
         return favoriteMovies.getId();
    }

    public HashMap moviePorId(Long codigo){
//        String accessToken = requestToken();
        Mono<HashMap> monoMovie = this.webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/movie/{codigo}")
                        .queryParam("api_key", api_key)
                        .queryParam("language", "pt-BR")
                        .build(codigo))

                .retrieve()
                .bodyToMono(HashMap.class);

        HashMap movie = monoMovie.block();

        return movie;
    }

    public List<HashMap> listAllFavorites(List<Long> idsMovies, List<Long> idsSeries){
        List<HashMap> favorites = new ArrayList<>();
        if(!idsMovies.isEmpty()){
            for (Long id : idsMovies){
                Mono<HashMap> monoMovie = this.webClient.get()
                        .uri(uriBuilder -> uriBuilder.path("/movie/{id}")
                                .queryParam("api_key", api_key)
                                .queryParam("language", "pt-BR")
                                .build(id))

                        .retrieve()
                        .bodyToMono(HashMap.class);

                assert favorites != null;
                favorites.add(monoMovie.block());
            }
        }
        if (!idsSeries.isEmpty()) {
            for (Long id : idsSeries) {
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

        return favorites;
    }

    public List<HashMap> listFavoriteMovies(List<Long> idsMovies){
        List<HashMap> favorites = new ArrayList<>();
        if(!idsMovies.isEmpty()){
            for (Long id : idsMovies){
                Mono<HashMap> monoMovie = this.webClient.get()
                        .uri(uriBuilder -> uriBuilder.path("/movie/{id}")
                                .queryParam("api_key", api_key)
                                .queryParam("language", "pt-BR")
                                .build(id))

                        .retrieve()
                        .bodyToMono(HashMap.class);

                assert favorites != null;
                favorites.add(monoMovie.block());
            }
        }

        return favorites;
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

    public HashMap getImages(Long codigo){
        Mono<HashMap> monoImages = this.webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/movie/{codigo}/images")
                        .queryParam("api_key", api_key)
                        .queryParam("language", "pt-BR")
                        .build(codigo))

                .retrieve()
                .bodyToMono(HashMap.class);

        HashMap images = monoImages.block();

        return images;
    }

    public HashMap getRecommendations(Long codigo){
        Mono<HashMap> monoRecommendations = this.webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/movie/{codigo}/recommendations")
                        .queryParam("api_key", api_key)
                        .queryParam("language", "pt-BR")
                        .build(codigo))

                .retrieve()
                .bodyToMono(HashMap.class);

        HashMap recommendations = monoRecommendations.block();

        return recommendations;
    }

    public HashMap getReviews(Long codigo){
        Mono<HashMap> monoReviews = this.webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/movie/{codigo}/reviews")
                        .queryParam("api_key", api_key)
                        .queryParam("language", "pt-BR")
                        .build(codigo))

                .retrieve()
                .bodyToMono(HashMap.class);

        HashMap reviews = monoReviews.block();

        return reviews;
    }
}
