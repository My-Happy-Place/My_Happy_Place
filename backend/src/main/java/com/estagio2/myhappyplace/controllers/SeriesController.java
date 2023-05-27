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
import java.util.Objects;

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
    public ResponseEntity<String> salvarSerieFavorita(@RequestBody FavoriteSeries body){
        FavoriteSeries favoriteSeries = seriesService.findById(body.getSerieId());
        if (Objects.nonNull(favoriteSeries.getId())){
            return ResponseEntity.badRequest().body("Serie já favoritada.");
        }
        this.seriesService.saveFavoriteSerie(favoriteSeries);
        return ResponseEntity.noContent().build();
    }

    @PutMapping
    public ResponseEntity<Void> excluirSerieFavorita(@RequestBody FavoriteSeries body){
        FavoriteSeries favoriteSeries = seriesService.findById(body.getSerieId());
        if (Objects.isNull(favoriteSeries.getId())){
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

    @GetMapping("/ontheair/{id}")
    public ResponseEntity<List<SeriesDTO>> getSeriesOnTheAir(@PathVariable Long id, @RequestParam(value = "page") Integer page){
        List<SeriesDTO> seriesOnTheAir = this.seriesService.getSeriesOnTheAir(id, page);
        return ResponseEntity.ok().body(seriesOnTheAir);
    }

    @GetMapping("/popular/{id}")
    public ResponseEntity<List<SeriesDTO>> getSeriesPopular(@PathVariable Long id, @RequestParam(value = "page") Integer page){
        List<SeriesDTO> seriesPopular = this.seriesService.getSeriesPopular(id, page);
        return ResponseEntity.ok().body(seriesPopular);
    }

    @GetMapping("/toprated/{id}")
    public ResponseEntity<List<SeriesDTO>> getSeriesTopRated(@PathVariable Long id, @RequestParam(value = "page") Integer page){
        List<SeriesDTO> seriesTopRated = this.seriesService.getSeriesTopRated(id, page);
        return ResponseEntity.ok().body(seriesTopRated);
    }
}
