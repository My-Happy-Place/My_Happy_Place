package com.estagio2.myhappyplace.controllers;

import com.estagio2.myhappyplace.dto.MovieDTO;
import com.estagio2.myhappyplace.dto.UserDTO;
import com.estagio2.myhappyplace.entities.FavoriteMovies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.estagio2.myhappyplace.service.MovieService;

import java.util.HashMap;

@RestController
@RequestMapping(value = "/movies")
@CrossOrigin(origins = "*")
public class MovieController {

    @Autowired
    MovieService movieService;

    @GetMapping("/{id}")
    public ResponseEntity<MovieDTO> buscarPorId(@PathVariable Long id){
        MovieDTO movie = this.movieService.moviePorId(id);
        return ResponseEntity.ok().body(movie);
    }
    @PostMapping
    public ResponseEntity<Void> salvarFilmeFavorito(@RequestBody FavoriteMovies favoriteMovies){
        Long id = this.movieService.saveFavoriteMovie(favoriteMovies);
        return ResponseEntity.noContent().build();
    }
    @PutMapping
    public ResponseEntity<Void> excluirFilmeFavorito(@RequestBody FavoriteMovies body){
        FavoriteMovies favoriteMovies = movieService.findById(body.getMovieId());
        if (favoriteMovies.getId() == null){
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
    public ResponseEntity<HashMap> getRecommendations(@PathVariable Long id){
        HashMap recommendations = this.movieService.getRecommendations(id);
        return ResponseEntity.ok().body(recommendations);
    }

    @GetMapping("/{id}/reviews")
    public ResponseEntity<HashMap> getReviews(@PathVariable Long id){
        HashMap reviews = this.movieService.getReviews(id);
        return ResponseEntity.ok().body(reviews);
    }

    @GetMapping("/{id}/similar")
    public ResponseEntity<HashMap> getSimilarMovies(@PathVariable Long id){
        HashMap similarMovies = this.movieService.getSimilarMovies(id);
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
    public ResponseEntity<HashMap> getNowPlaying(){
        HashMap nowPlaying = this.movieService.getNowPlaying();
        return ResponseEntity.ok().body(nowPlaying);
    }

    @GetMapping("/popular")
    public ResponseEntity<HashMap> getPopularTMDB(){
        HashMap popular = this.movieService.getPopularTMDB();
        return ResponseEntity.ok().body(popular);
    }

    @GetMapping("/toprated")
    public ResponseEntity<HashMap> getMaisVotadosTMDB(){
        HashMap maisVotados = this.movieService.getMaisVotadosTMDB();
        return ResponseEntity.ok().body(maisVotados);
    }
}
