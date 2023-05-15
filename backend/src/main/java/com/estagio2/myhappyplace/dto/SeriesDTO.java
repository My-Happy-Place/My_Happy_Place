package com.estagio2.myhappyplace.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SeriesDTO {

    private Integer idTMDB;

    private String name;

    private String overview;

    private String posterPath;

    private Integer numberOfEpisodes;

    private Integer numberOfSeasons;

    private String lastYear;

    private Boolean isFavorite;

    public SeriesDTO(HashMap seriesTMDB) {
        this.idTMDB = (Integer) seriesTMDB.get("id");
        this.name = (String) seriesTMDB.get("name");
        this.overview = (String) seriesTMDB.get("overview");
        this.posterPath = (String) seriesTMDB.get("poster_path");
        this.numberOfEpisodes = (Integer) seriesTMDB.get("number_of_episodes");
        this.numberOfSeasons = (Integer) seriesTMDB.get("number_of_seasons");
        this.lastYear = (String) seriesTMDB.get("last_air_date");
    }

    public List<SeriesDTO> isList(List<HashMap> seriesTMDB){
        List<SeriesDTO> listSeries = new ArrayList<>();
        for (HashMap serie : seriesTMDB){
            listSeries.add(new SeriesDTO(serie));
        }
        return listSeries;
    }

    public HashMap convertHashMap(SeriesDTO seriesDTO){
        HashMap aux = new HashMap((Map) seriesDTO);
        return aux;
    }

    public boolean temNosFavoritos(Long id){
        return true;
    }
}
