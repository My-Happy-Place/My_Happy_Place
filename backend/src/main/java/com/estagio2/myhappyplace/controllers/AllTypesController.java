package com.estagio2.myhappyplace.controllers;

import com.estagio2.myhappyplace.dto.MovieDTO;
import com.estagio2.myhappyplace.service.AllTypesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping(value = "/all-types")
@CrossOrigin(origins = "*")
public class AllTypesController {

    @Autowired
    AllTypesService allTypesService;

    @GetMapping("/collection")
    public ResponseEntity<HashMap> pesquisarColecao(@RequestParam(value = "name") String name){
        HashMap search = this.allTypesService.getCollection(name);
        return ResponseEntity.ok().body(search);
    }

    @GetMapping("/movies")
    public ResponseEntity<HashMap> pesquisarFilmes(@RequestParam(value = "descricao") String descricao){
        HashMap search = this.allTypesService.getMoviesByDescription(descricao);
        return ResponseEntity.ok().body(search);
    }

    @GetMapping("/multisearch")
    public ResponseEntity<HashMap> pesquisarTodosOsTipos(@RequestParam(value = "descricao") String descricao){
        HashMap search = this.allTypesService.getAllTypesByDescription(descricao);
        return ResponseEntity.ok().body(search);
    }

    @GetMapping("/trendingall")
    public ResponseEntity<HashMap> buscarTrendingTodosOsTipos(@RequestParam(value = "timeWindow") String timeWindow){
        HashMap trending = this.allTypesService.getTrendingAllTypes(timeWindow);
        return ResponseEntity.ok().body(trending);
    }

    @GetMapping("/trendingmovies")
    public ResponseEntity<HashMap> buscarTrendingMovies(@RequestParam(value = "timeWindow") String timeWindow){
        HashMap trending = this.allTypesService.getTrendingMovies(timeWindow);
        return ResponseEntity.ok().body(trending);
    }

    @GetMapping("/trendingseries")
    public ResponseEntity<HashMap> buscarTrendingSeries(@RequestParam(value = "timeWindow") String timeWindow){
        HashMap trending = this.allTypesService.getTrendingSeries(timeWindow);
        return ResponseEntity.ok().body(trending);
    }
}
