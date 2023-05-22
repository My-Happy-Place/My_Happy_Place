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
    public FavoriteMovies findById(Integer id){
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

    public List<HashMap> listAllFavorites(List<Integer> idsMovies, List<Integer> idsSeries){
        List<HashMap> favorites = new ArrayList<>();
        if(!idsMovies.isEmpty()){
            for (Integer id : idsMovies){
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
            for (Integer id : idsSeries) {
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

    public List<MovieDTO> listFavoriteMovies(List<Integer> idsMovies){
        List<HashMap> favorites = new ArrayList<>();
        if(!idsMovies.isEmpty()){
            for (Integer id : idsMovies){
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
        return movies.isList(favorites, true, null);
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

    public List<MovieDTO> getRecommendations(Long codigo, Long idUser,  Integer page){
        List<HashMap> recommendations;
        Mono<HashMap> monoRecommendations = this.webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/movie/{codigo}/recommendations")
                        .queryParam("api_key", api_key)
                        .queryParam("language", "pt-BR")
                        .queryParam("page", page)
                        .build(codigo))

                .retrieve()
                .bodyToMono(HashMap.class);

        recommendations = Objects.requireNonNull(Objects.requireNonNull(monoRecommendations.block()).values().stream().toList());
        recommendations = (List<HashMap>) recommendations.get(2);
        String type = "M";
        for (HashMap aux : recommendations){
            aux.put("isFavorite", userService.isFavorite((Integer) aux.get("id"), idUser));
        }
        MovieDTO movieDTO = new MovieDTO();

        return movieDTO.isList(recommendations, false, type);
    }

    public HashMap getReviews(Long codigo, Integer page){
        Mono<HashMap> monoReviews = this.webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/movie/{codigo}/reviews")
                        .queryParam("api_key", api_key)
                        .queryParam("language", "pt-BR")
                        .queryParam("page", page)
                        .build(codigo))

                .retrieve()
                .bodyToMono(HashMap.class);

        HashMap reviews = monoReviews.block();

        return reviews;
    }

    public List<MovieDTO> getSimilarMovies(Long codigo, Long idUser, Integer page){
        List<HashMap> similarMovies;
        Mono<HashMap> monoSimilar = this.webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/movie/{codigo}/similar")
                        .queryParam("api_key", api_key)
                        .queryParam("language", "pt-BR")
                        .queryParam("page", page)
                        .build(codigo))

                .retrieve()
                .bodyToMono(HashMap.class);

        similarMovies = Objects.requireNonNull(Objects.requireNonNull(monoSimilar.block()).values().stream().toList());
        similarMovies = (List<HashMap>) similarMovies.get(2);
        String type = "M";
        for (HashMap aux : similarMovies){
            aux.put("isFavorite", userService.isFavorite((Integer) aux.get("id"), idUser));
        }
        MovieDTO movieDTO = new MovieDTO();

        return movieDTO.isList(similarMovies, false, type);
    }

    public HashMap getVideos(Long codigo){
        Mono<HashMap> monoVideos = this.webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/movie/{codigo}/videos")
                        .queryParam("api_key", api_key)
                        .queryParam("language", "pt-BR")
                        .build(codigo))

                .retrieve()
                .bodyToMono(HashMap.class);

        HashMap videos = monoVideos.block();

        return videos;
    }

    public HashMap getWatchProviders(Long codigo){
        Mono<HashMap> monoWatchProviders = this.webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/movie/{codigo}/watch/providers")
                        .queryParam("api_key", api_key)
                        .queryParam("language", "pt-BR")
                        .build(codigo))

                .retrieve()
                .bodyToMono(HashMap.class);

        HashMap watchProviders = monoWatchProviders.block();

        return watchProviders;
    }

    public List<MovieDTO> getNowPlaying(Long idUser, Integer page){
        List<HashMap> movies;
        Mono<HashMap> monoNowPlaying = this.webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/movie/now_playing")
                        .queryParam("api_key", api_key)
                        .queryParam("language", "pt-BR")
                        .queryParam("page", page)
                        .queryParam("region", "BR")
                        .build())

                .retrieve()
                .bodyToMono(HashMap.class);

        movies = Objects.requireNonNull(Objects.requireNonNull(monoNowPlaying.block()).values().stream().toList());
        movies = (List<HashMap>) movies.get(3);
        String type = "M";
        for (HashMap aux : movies){
            aux.put("isFavorite", userService.isFavorite((Integer) aux.get("id"), idUser));
        }
        MovieDTO movieDTO = new MovieDTO();

        return movieDTO.isList(movies, false, type);
    }

    public List<MovieDTO> getPopularTMDB(Long idUser, Integer page){
        List<HashMap> populares;
        Mono<HashMap> monoPopular = this.webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/movie/popular")
                        .queryParam("api_key", api_key)
                        .queryParam("language", "pt-BR")
                        .queryParam("page", page)
                        .queryParam("region", "BR")
                        .build())

                .retrieve()
                .bodyToMono(HashMap.class);

        populares = Objects.requireNonNull(Objects.requireNonNull(monoPopular.block()).values().stream().toList());
        populares = (List<HashMap>) populares.get(2);
        String type = "M";
        for (HashMap aux : populares){
            aux.put("isFavorite", userService.isFavorite((Integer) aux.get("id"), idUser));
        }
        MovieDTO movieDTO = new MovieDTO();

        return movieDTO.isList(populares, false, type);
    }

    public List<MovieDTO> getMaisVotadosTMDB(Long idUser, Integer page){
        List<HashMap> maisVotados;
        Mono<HashMap> monoMaisVotados = this.webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/movie/top_rated")
                        .queryParam("api_key", api_key)
                        .queryParam("language", "pt-BR")
                        .queryParam("page", page)
                        .queryParam("region", "BR")
                        .build())

                .retrieve()
                .bodyToMono(HashMap.class);

        maisVotados = Objects.requireNonNull(Objects.requireNonNull(monoMaisVotados.block()).values().stream().toList());
        maisVotados = (List<HashMap>) maisVotados.get(2);
        String type = "M";
        for (HashMap aux : maisVotados){
            aux.put("isFavorite", userService.isFavorite((Integer) aux.get("id"), idUser));
        }
        MovieDTO movieDTO = new MovieDTO();

        return movieDTO.isList(maisVotados, false, type);
    }
}
