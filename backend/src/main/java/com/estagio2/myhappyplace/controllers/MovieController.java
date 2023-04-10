package com.estagio2.myhappyplace.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.estagio2.myhappyplace.service.MovieService;

import java.util.HashMap;

@RestController
@RequestMapping(value = "/movies")
public class MovieController {

    @Autowired
    MovieService movieService;

    @GetMapping("/{id}")
    public ResponseEntity<HashMap> buscarPorId(@PathVariable Long id){
        HashMap movie = this.movieService.movie(id);
        return ResponseEntity.ok().body(movie);
    }


//    @GetMapping()
//    public ResponseEntity<String> teste(){
//        String texto = "Hello World";
//        return ResponseEntity.ok(texto);
//    }
}
