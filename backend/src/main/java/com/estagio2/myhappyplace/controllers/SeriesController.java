package com.estagio2.myhappyplace.controllers;

import com.estagio2.myhappyplace.dto.SeriesDTO;
import com.estagio2.myhappyplace.dto.seriesseasons.SeriesSeasonsDTO;
import com.estagio2.myhappyplace.dto.seriesseasons.episodes.SeriesEpisodesDTO;
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
    public ResponseEntity<SeriesDTO> buscarPorId(@PathVariable Long id, @RequestParam(value = "idUser") Long idUser){
        SeriesDTO serie = this.seriesService.seriePorId(id, idUser);
        return ResponseEntity.ok().body(serie);
    }

    @PostMapping
    public ResponseEntity<String> salvarSerieFavorita(@RequestBody FavoriteSeries body){
        FavoriteSeries favoriteSeries = seriesService.findById(body.getSerieId());
        if (Objects.nonNull(favoriteSeries.getId())){
            return ResponseEntity.badRequest().body("Serie já favoritada.");
        }
        this.seriesService.saveFavoriteSerie(body);
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

    @GetMapping("/{id}/aggregateCredits")
    public ResponseEntity<HashMap> getSeriesAggregatedCredits(@PathVariable Long id){
        HashMap credits = this.seriesService.getAggregateCredits(id);
        return ResponseEntity.ok().body(credits);
    }

    @GetMapping("/{id}/images")
    public ResponseEntity<HashMap> getSeriesImages(@PathVariable Long id){
        HashMap images = this.seriesService.getImages(id);
        return ResponseEntity.ok().body(images);
    }

    @GetMapping("/{id}/recommendations")
    public ResponseEntity<List<SeriesDTO>> getRecommendationsBasedSerie(@PathVariable Long id, @RequestParam(value = "idUser") Long idUser,
                                                                @RequestParam(value = "page") Integer page){
        List<SeriesDTO> recommendations = this.seriesService.getRecommendations(id, idUser, page);
        return ResponseEntity.ok().body(recommendations);
    }

    @GetMapping("/{id}/reviews")
    public ResponseEntity<HashMap> getSerieReviews(@PathVariable Long id, @RequestParam(value = "page") Integer page){
        HashMap reviews = this.seriesService.getReviews(id, page);
        return ResponseEntity.ok().body(reviews);
    }

    @GetMapping("/{id}/similar")
    public ResponseEntity<List<SeriesDTO>> getSerieReviews(@PathVariable Long id, @RequestParam(value = "idUser") Long idUser,
                                                   @RequestParam(value = "page") Integer page){
        List<SeriesDTO> similarSeries = this.seriesService.getSimilar(id, idUser, page);
        return ResponseEntity.ok().body(similarSeries);
    }

    @GetMapping("/{id}/videos")
    public ResponseEntity<HashMap> getSerieReviews(@PathVariable Long id){
        HashMap videos = this.seriesService.getVideos(id);
        return ResponseEntity.ok().body(videos);
    }

    @GetMapping("/{id}/watchproviders")
    public ResponseEntity<HashMap> getWatchProvidersSerie(@PathVariable Long id){
        HashMap providers = this.seriesService.getWatchProviders(id);
        return ResponseEntity.ok().body(providers);
    }

    @GetMapping("/{id}/seasons/{seasonnumber}")
    public ResponseEntity<SeriesSeasonsDTO> getSerieSeason(@PathVariable Long id, @PathVariable Long seasonnumber,
                                                                   @RequestParam(value = "idUser") Long idUser){
        SeriesSeasonsDTO season = this.seriesService.getSerieSeason(id, seasonnumber, idUser);
        return ResponseEntity.ok().body(season);
    }

    @GetMapping("/{id}/seasons")
    public ResponseEntity<List<SeriesSeasonsDTO>> getSerieSeasonList(@PathVariable Long id, @RequestParam(value = "idUser") Long idUser){
        List<SeriesSeasonsDTO> seasonsList = this.seriesService.getSeasonsList(id, idUser);
        return ResponseEntity.ok().body(seasonsList);
    }

    @GetMapping("/{id}/seasons/{seasonnumber}/aggregatecredits")
    public ResponseEntity<HashMap> getSerieSeasonAggregateCredits(@PathVariable Long id, @PathVariable Long seasonnumber){
        HashMap credits = this.seriesService.getSerieSeasonAggregateCredits(id, seasonnumber);
        return ResponseEntity.ok().body(credits);
    }

    @GetMapping("/{id}/seasons/{seasonnumber}/credits")
    public ResponseEntity<HashMap> getSerieSeasonCredits(@PathVariable Long id, @PathVariable Long seasonnumber){
        HashMap credits = this.seriesService.getSerieSeasonCredits(id, seasonnumber);
        return ResponseEntity.ok().body(credits);
    }

    @GetMapping("/{id}/seasons/{seasonnumber}/images")
    public ResponseEntity<HashMap> getSerieSeasonImages(@PathVariable Long id, @PathVariable Long seasonnumber){
        HashMap images = this.seriesService.getSerieSeasonImages(id, seasonnumber);
        return ResponseEntity.ok().body(images);
    }

    @GetMapping("/{id}/seasons/{seasonnumber}/videos")
    public ResponseEntity<HashMap> getSerieSeasonVideos(@PathVariable Long id, @PathVariable Long seasonnumber){
        HashMap videos = this.seriesService.getSerieSeasonVideos(id, seasonnumber);
        return ResponseEntity.ok().body(videos);
    }

    @GetMapping("/{id}/seasons/{seasonnumber}/watchproviders")
    public ResponseEntity<HashMap> getSerieSeasonWatchProviders(@PathVariable Long id, @PathVariable Long seasonnumber){
        HashMap providers = this.seriesService.getSerieSeasonWatchProviders(id, seasonnumber);
        return ResponseEntity.ok().body(providers);
    }


    //---------------- EPISODES METHODS ---------------------------------------------------------------------------
    @GetMapping("/{id}/seasons/{seasonnumber}/episodes/{episodenumber}")
    public ResponseEntity<SeriesEpisodesDTO> getSerieEpisode(@PathVariable Long id, @PathVariable Long seasonnumber,
                                                                @PathVariable Long episodenumber, @RequestParam(value = "idUser") Long idUser){
        SeriesEpisodesDTO episode = this.seriesService.getSerieEpisode(id, seasonnumber, episodenumber, idUser);
        return ResponseEntity.ok().body(episode);
    }

    @GetMapping("/{id}/seasons/{seasonnumber}/episodes/{episodenumber}/credits")
    public ResponseEntity<HashMap> getSerieEpisodeCredits(@PathVariable Long id, @PathVariable Long seasonnumber,
                                                                          @PathVariable Long episodenumber){
        HashMap credits = this.seriesService.getSerieEpisodeCredits(id, seasonnumber, episodenumber);
        return ResponseEntity.ok().body(credits);
    }

    @GetMapping("/{id}/seasons/{seasonnumber}/episodes/{episodenumber}/images")
    public ResponseEntity<HashMap> getSerieEpisodeImages(@PathVariable Long id, @PathVariable Long seasonnumber,
                                                          @PathVariable Long episodenumber){
        HashMap images = this.seriesService.getSerieEpisodeImages(id, seasonnumber, episodenumber);
        return ResponseEntity.ok().body(images);
    }

    @GetMapping("/{id}/seasons/{seasonnumber}/episodes/{episodenumber}/videos")
    public ResponseEntity<HashMap> getSerieEpisodeVideos(@PathVariable Long id, @PathVariable Long seasonnumber,
                                                         @PathVariable Long episodenumber){
        HashMap videos = this.seriesService.getSerieEpisodeVideos(id, seasonnumber, episodenumber);
        return ResponseEntity.ok().body(videos);
    }
}
