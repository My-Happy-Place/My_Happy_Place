package com.estagio2.myhappyplace.service;

import com.estagio2.myhappyplace.dto.MovieDTO;
import com.estagio2.myhappyplace.dto.SeriesDTO;
import com.estagio2.myhappyplace.dto.UserDTO;
import com.estagio2.myhappyplace.entities.FavoriteMovies;
import com.estagio2.myhappyplace.entities.FavoriteSeries;
import com.estagio2.myhappyplace.entities.User;
import com.estagio2.myhappyplace.repositories.FavoriteMoviesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.*;

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
    @Transactional
    public void excluirFavoriteMovie(FavoriteMovies favoriteMovies){
        UserDTO user = userService.findById(favoriteMovies.getUserList().get(0).getId());
        user.getFavoriteMovies().removeIf(favoriteMovies1 -> Objects.equals(favoriteMovies1.getMovieId(), favoriteMovies.getMovieId()));
        favoriteMovies.getUserList().removeIf(user1 -> Objects.equals(user1.getId(), user.getId()));
        userService.update(user);
        favoriteMoviesRepository.delete(favoriteMovies);
    }

    @Transactional(readOnly = true)
    public FavoriteMovies findById(Long id){
        Optional<FavoriteMovies> obj = favoriteMoviesRepository.findByMovieId(id);
        if(obj.isPresent()){
            FavoriteMovies favoriteMovies = obj.get();
            return favoriteMovies;
        }
        return new FavoriteMovies();
    }

    public MovieDTO moviePorId(Long codigo){
//        String accessToken = requestToken();
        Mono<HashMap> monoMovie = this.webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/movie/{codigo}")
                        .queryParam("api_key", api_key)
                        .queryParam("language", "pt-BR")
                        .build(codigo))

                .retrieve()
                .bodyToMono(HashMap.class);

        HashMap movie = monoMovie.block();

        return new MovieDTO(movie);
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

                MovieDTO movie = new MovieDTO(Objects.requireNonNull(monoMovie.block()), true);
                assert favorites != null;
                favorites.add(movie.convertHashMap(movie));
            }
        }
        if (!idsSeries.isEmpty()) {
            for (Long id : idsSeries) {
                Mono<HashMap> monoSerie = this.webClient.get()
                        .uri(uriBuilder -> uriBuilder.path("/tv/{id}")
                                .queryParam("api_key", api_key)
                                .queryParam("language", "pt-BR")
                                .build(id))

                        .retrieve()
                        .bodyToMono(HashMap.class);

                SeriesDTO serie = new SeriesDTO(Objects.requireNonNull(monoSerie.block()), true);
                assert favorites != null;
                favorites.add(serie.convertHashMap(serie));
            }
        }

        return favorites;
    }

    public List<MovieDTO> listFavoriteMovies(List<Long> idsMovies){
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
        MovieDTO movies = new MovieDTO();
        return movies.isList(favorites, true);
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
