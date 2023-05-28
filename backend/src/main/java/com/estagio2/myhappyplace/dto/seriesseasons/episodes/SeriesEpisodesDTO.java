package com.estagio2.myhappyplace.dto.seriesseasons.episodes;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SeriesEpisodesDTO {

    private Integer idTMDB;

    private Integer episodeNumber;

    private String airDate;

    private String name;

    private String overview;

    private Integer runtime;

    private Integer seasonNumber;

    private Integer idSerie;

    private String stillPath;


    public SeriesEpisodesDTO(HashMap seriesEpisodesTMDB) {
        this.idTMDB = (Integer) seriesEpisodesTMDB.get("id");
        this.episodeNumber = (Integer) seriesEpisodesTMDB.get("episode_number");
        this.airDate = (String) seriesEpisodesTMDB.get("air_date");
        this.name = (String) seriesEpisodesTMDB.get("name");
        this.overview = (String) seriesEpisodesTMDB.get("overview");
        this.runtime = (Integer) seriesEpisodesTMDB.get("runtime");
        this.seasonNumber = (Integer) seriesEpisodesTMDB.get("season_number");
        this.idSerie = (Integer) seriesEpisodesTMDB.get("show_id");
        this.stillPath = (String) seriesEpisodesTMDB.get("still_path");
    }
}
