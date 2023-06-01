package com.estagio2.myhappyplace.controllers;

import com.estagio2.myhappyplace.dto.MovieDTO;
import com.estagio2.myhappyplace.dto.UserDTO;
import com.estagio2.myhappyplace.entities.FavoriteMovies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.estagio2.myhappyplace.service.MovieService;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping(value = "/movies")
@CrossOrigin(origins = "*")
public class MovieController {

    @Autowired
    MovieService movieService;

    @GetMapping("/{id}")
    public ResponseEntity<MovieDTO> buscarPorId(@PathVariable Long id, @RequestParam(value = "idUser")  Long idUser){
        MovieDTO movie = this.movieService.moviePorId(id, idUser);
        return ResponseEntity.ok().body(movie);
    }
    @PostMapping
    public ResponseEntity<String> salvarFilmeFavorito(@RequestBody FavoriteMovies body){
        FavoriteMovies favoriteMovies = movieService.findById(body.getMovieId());
        if (Objects.nonNull(favoriteMovies.getId())){
            return ResponseEntity.badRequest().body("Filme já favoritado.");
        }
        this.movieService.saveFavoriteMovie(body);
        return ResponseEntity.noContent().build();
    }
    @PutMapping
    public ResponseEntity<Void> excluirFilmeFavorito(@RequestBody FavoriteMovies body){
        FavoriteMovies favoriteMovies = movieService.findById(body.getMovieId());
        if (Objects.isNull(favoriteMovies.getId())){
            return ResponseEntity.notFound().build();
        }
        movieService.excluirFavoriteMovie(body);
        return ResponseEntity.noContent().build();
    }

    //----------------- METODOS TMDB ------------------------------------------------------------

    @GetMapping("/{id}/credits")
    public ResponseEntity<HashMap> getCredits(@PathVariable Long id){
        HashMap credtis = this.movieService.getCredits(id);
        return ResponseEntity.ok().body(credtis);
    }

    @GetMapping("/{id}/images")
    public ResponseEntity<HashMap> getImages(@PathVariable Long id){
        HashMap images = this.movieService.getImages(id);
        return ResponseEntity.ok().body(images);
    }

    @GetMapping("/{id}/recommendations")
    public ResponseEntity<List<MovieDTO>> getRecommendations(@PathVariable Long id, @RequestParam(value = "idUser") Long idUser,
                                                      @RequestParam(value = "page") Integer page){
        List<MovieDTO> recommendations = this.movieService.getRecommendations(id, idUser, page);
        return ResponseEntity.ok().body(recommendations);
    }

    @GetMapping("/{id}/reviews")
    public ResponseEntity<HashMap> getReviews(@PathVariable Long id, @RequestParam(value = "page") Integer page){
        HashMap reviews = this.movieService.getReviews(id, page);
        return ResponseEntity.ok().body(reviews);
    }

    @GetMapping("/{id}/similar")
    public ResponseEntity<List<MovieDTO>> getSimilarMovies(@PathVariable Long id,  @RequestParam(value = "idUser") Long idUser,
                                                    @RequestParam(value = "page") Integer page){
        List<MovieDTO> similarMovies = this.movieService.getSimilarMovies(id, idUser, page);
        return ResponseEntity.ok().body(similarMovies);
    }

    @GetMapping("/{id}/videos")
    public ResponseEntity<HashMap> getVideos(@PathVariable Long id){
        HashMap videos = this.movieService.getVideos(id);
        return ResponseEntity.ok().body(videos);
    }

    @GetMapping("/{id}/watchproviders")
    public ResponseEntity<HashMap> getWatchProviders(@PathVariable Long id){
        HashMap watchProviders = this.movieService.getWatchProviders(id);
        return ResponseEntity.ok().body(watchProviders);
    }

    @GetMapping("/nowplaying")
    public ResponseEntity<List<MovieDTO>> getNowPlaying(@RequestParam(value = "idUser") Long idUser, @RequestParam(value = "page") Integer page){
        List<MovieDTO> nowPlaying = this.movieService.getNowPlaying(idUser, page);
        return ResponseEntity.ok().body(nowPlaying);
    }

    @GetMapping("/popular")
    public ResponseEntity<List<MovieDTO>> getPopularTMDB(@RequestParam(value = "idUser") Long idUser, @RequestParam(value = "page") Integer page){
        List<MovieDTO> popular = this.movieService.getPopularTMDB(idUser, page);
        return ResponseEntity.ok().body(popular);
    }

    @GetMapping("/toprated")
    public ResponseEntity<List<MovieDTO>> getMaisVotadosTMDB(@RequestParam(value = "idUser") Long idUser, @RequestParam(value = "page") Integer page){
        List<MovieDTO> maisVotados = this.movieService.getMaisVotadosTMDB(idUser, page);
        return ResponseEntity.ok().body(maisVotados);
    }
}
