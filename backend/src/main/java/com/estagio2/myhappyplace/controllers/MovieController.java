package com.estagio2.myhappyplace.controllers;

import com.estagio2.myhappyplace.entities.FavoriteMovies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.estagio2.myhappyplace.service.MovieService;

import java.util.HashMap;

@RestController
@RequestMapping(value = "/movies")
public class MovieController {

    @Autowired
    MovieService movieService;

    @GetMapping("/{id}")
    public ResponseEntity<HashMap> buscarPorId(@PathVariable Long id){
        HashMap movie = this.movieService.moviePorId(id);
        return ResponseEntity.ok().body(movie);
    }

    @PostMapping
    public ResponseEntity<HashMap> salvarFilmeFavorito(@RequestBody FavoriteMovies favoriteMovies){
        Long id = this.movieService.saveFavoriteMovie(favoriteMovies);
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
        HashMap images = this.movieService.getCredits(id);
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
}
