package com.estagio2.myhappyplace.dto.seriesseasons;

import com.estagio2.myhappyplace.dto.SeriesDTO;
import com.estagio2.myhappyplace.dto.seriesseasons.episodes.SeriesEpisodesDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SeriesSeasonsDTO {

    private Integer idTMDB;

    private String name;

    private String overview;

    private String posterPath;

    private String airDate;

    private List<SeriesEpisodesDTO> episodes = new ArrayList<>();

    private Boolean isFavorite;

    public SeriesSeasonsDTO(HashMap seriesSeasonsTMDB) {
        this.idTMDB = (Integer) seriesSeasonsTMDB.get("id");
        this.name = (String) seriesSeasonsTMDB.get("name");
        this.overview = (String) seriesSeasonsTMDB.get("overview");
        this.posterPath = (String) seriesSeasonsTMDB.get("poster_path");
        this.airDate = (String) seriesSeasonsTMDB.get("air_date");
        this.episodes = getEpisodesDTO(seriesSeasonsTMDB);
        this.isFavorite = false;
    }

    private List<SeriesEpisodesDTO> getEpisodesDTO(HashMap seriesSeasonsTMDB){
        List<HashMap> episodesList;
        List<SeriesEpisodesDTO> episodesDTOS = new ArrayList<>();
        episodesList = Objects.requireNonNull(seriesSeasonsTMDB.values().stream().toList());
        episodesList = (List<HashMap>) episodesList.get(7);
        for (HashMap ep : episodesList){
            episodesDTOS.add(new SeriesEpisodesDTO(ep));
        }
        return episodesDTOS;
    }
}
