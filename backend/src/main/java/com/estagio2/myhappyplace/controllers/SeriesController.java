package com.estagio2.myhappyplace.controllers;

import com.estagio2.myhappyplace.dto.SeriesDTO;
import com.estagio2.myhappyplace.entities.FavoriteMovies;
import com.estagio2.myhappyplace.entities.FavoriteSeries;
import com.estagio2.myhappyplace.service.SeriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/series")
@CrossOrigin(origins = "*")
public class SeriesController {

    @Autowired
    SeriesService seriesService;

    @GetMapping("/{id}")
    public ResponseEntity<SeriesDTO> buscarPorId(@PathVariable Long id){
        SeriesDTO serie = this.seriesService.seriePorId(id);
        return ResponseEntity.ok().body(serie);
    }

    @PostMapping
    public ResponseEntity<Void> salvarSerieFavorita(@RequestBody FavoriteSeries favoriteSeries){
        Long id = this.seriesService.saveFavoriteSerie(favoriteSeries);
        return ResponseEntity.noContent().build();
    }

    @PutMapping
    public ResponseEntity<Void> excluirSerieFavorita(@RequestBody FavoriteSeries body){
        FavoriteSeries favoriteSeries = seriesService.findById(body.getSerieId());
        if (favoriteSeries.getId() == null){
            return ResponseEntity.notFound().build();
        }
        seriesService.excluirFavoriteSerie(body);
        return ResponseEntity.noContent().build();
    }


    //----------------- METODOS TMDB ------------------------------------------------------------

    @GetMapping("/airingtoday/{id}")
    public ResponseEntity<List<SeriesDTO>> getSeriesAiringToday(@PathVariable Long id, @RequestParam(value = "page") Integer page){
        List<SeriesDTO> seriesAiringToday = this.seriesService.getSeriesAiringToday(id, page);
        return ResponseEntity.ok().body(seriesAiringToday);
    }

    @GetMapping("/ontheair")
    public ResponseEntity<HashMap> getSeriesOnTheAir(){
        HashMap seriesOnTheAir = this.seriesService.getSeriesOnTheAir();
        return ResponseEntity.ok().body(seriesOnTheAir);
    }

    @GetMapping("/popular")
    public ResponseEntity<HashMap> getSeriesPopular(){
        HashMap seriesPopular = this.seriesService.getSeriesPopular();
        return ResponseEntity.ok().body(seriesPopular);
    }

    @GetMapping("/toprated")
    public ResponseEntity<HashMap> getSeriesTopRated(){
        HashMap seriesTopRated = this.seriesService.getSeriesTopRated();
        return ResponseEntity.ok().body(seriesTopRated);
    }
}
