package com.estagio2.myhappyplace.controllers;

import com.estagio2.myhappyplace.dto.AllTypesDTO;
import com.estagio2.myhappyplace.dto.MovieDTO;
import com.estagio2.myhappyplace.service.AllTypesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping(value = "/all-types")
@CrossOrigin(origins = "*")
public class AllTypesController {

    @Autowired
    AllTypesService allTypesService;

    @GetMapping("/collection/{id}")
    public ResponseEntity<HashMap> pesquisarColecao(@PathVariable Long id, @RequestParam(value = "name") String name,
                                                    @RequestParam(value = "page") Integer page){
        HashMap search = this.allTypesService.getCollection(id, name, page);
        return ResponseEntity.ok().body(search);
    }

    @GetMapping("/movies/{id}")
    public ResponseEntity<List<AllTypesDTO>> pesquisarFilmes(@PathVariable Long id, @RequestParam(value = "descricao") String descricao,
                                                             @RequestParam(value = "page") Integer page){
        List<AllTypesDTO> search = this.allTypesService.getMoviesByDescription(id, descricao, page);
        return ResponseEntity.ok().body(search);
    }

    @GetMapping("/multisearch/{id}")
    public ResponseEntity<List<AllTypesDTO>> pesquisarTodosOsTipos(@PathVariable Long id, @RequestParam(value = "descricao") String descricao,
                                                                   @RequestParam(value = "page") Integer page){
        List<AllTypesDTO> search = this.allTypesService.getAllTypesByDescription(id, descricao, page);
        return ResponseEntity.ok().body(search);
    }

    @GetMapping("/series/{id}")
    public ResponseEntity<List<AllTypesDTO>> pesquisarSeries(@PathVariable Long id, @RequestParam(value = "descricao") String descricao,
                                                   @RequestParam(value = "page") Integer page){
        List<AllTypesDTO> search = this.allTypesService.getSeriesByDescription(id, descricao, page);
        return ResponseEntity.ok().body(search);
    }

    @GetMapping("/trendingall/{id}")
    public ResponseEntity<List<AllTypesDTO>> buscarTrendingTodosOsTipos(@PathVariable Long id, @RequestParam(value = "timeWindow") String timeWindow,
                                                              @RequestParam(value = "page") Integer page){
        List<AllTypesDTO> trending = this.allTypesService.getTrendingAllTypes(id, timeWindow, page);
        return ResponseEntity.ok().body(trending);
    }

    @GetMapping("/trendingmovies/{id}")
    public ResponseEntity<List<AllTypesDTO>> buscarTrendingMovies(@PathVariable Long id, @RequestParam(value = "timeWindow") String timeWindow,
                                                        @RequestParam(value = "page") Integer page){
        List<AllTypesDTO> trending = this.allTypesService.getTrendingMovies(id, timeWindow, page);
        return ResponseEntity.ok().body(trending);
    }

    @GetMapping("/trendingseries/{id}")
    public ResponseEntity<List<AllTypesDTO>> buscarTrendingSeries(@PathVariable Long id, @RequestParam(value = "timeWindow") String timeWindow,
                                                        @RequestParam(value = "page") Integer page){
        List<AllTypesDTO> trending = this.allTypesService.getTrendingSeries(id, timeWindow, page);
        return ResponseEntity.ok().body(trending);
    }
}
