package com.estagio2.myhappyplace.dto;

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
public class SeriesDTO {

    private Integer idTMDB;

    private String name;

    private String overview;

    private String posterPath;

    private Integer numberOfEpisodes;

    private Integer numberOfSeasons;

    private String releaseDate;

    private String lastYear;

    private Boolean isTvShow;

    private Boolean isFavorite;

    public SeriesDTO(HashMap seriesTMDB) {
        this.idTMDB = (Integer) seriesTMDB.get("id");
        this.name = (String) seriesTMDB.get("name");
        this.overview = (String) seriesTMDB.get("overview");
        this.posterPath = (String) seriesTMDB.get("poster_path");
        this.numberOfEpisodes = (Integer) seriesTMDB.get("number_of_episodes");
        this.numberOfSeasons = (Integer) seriesTMDB.get("number_of_seasons");
        this.releaseDate = (String) seriesTMDB.get("first_air_date");
        this.lastYear = (String) seriesTMDB.get("last_air_date");
        this.isTvShow = true;
        this.isFavorite = false;
    }
    public SeriesDTO(HashMap seriesTMDB, boolean favorite) {
        this.idTMDB = (Integer) seriesTMDB.get("id");
        this.name = (String) seriesTMDB.get("name");
        this.overview = (String) seriesTMDB.get("overview");
        this.posterPath = (String) seriesTMDB.get("poster_path");
        this.numberOfEpisodes = (Integer) seriesTMDB.get("number_of_episodes");
        this.numberOfSeasons = (Integer) seriesTMDB.get("number_of_seasons");
        this.releaseDate = (String) seriesTMDB.get("first_air_date");
        this.lastYear = (String) seriesTMDB.get("last_air_date");
        this.isTvShow = true;
        this.isFavorite = favorite;
    }

    public SeriesDTO(HashMap seriesTMDB, String type) {
        this.idTMDB = (Integer) seriesTMDB.get("id");
        this.name = (String) seriesTMDB.get("name");
        this.overview = (String) seriesTMDB.get("overview");
        this.posterPath = (String) seriesTMDB.get("poster_path");
        this.numberOfEpisodes = (Integer) seriesTMDB.get("number_of_episodes");
        this.numberOfSeasons = (Integer) seriesTMDB.get("number_of_seasons");
        this.releaseDate = (String) seriesTMDB.get("first_air_date");
        this.lastYear = (String) seriesTMDB.get("last_air_date");
        this.isTvShow = true;
        this.isFavorite = (Boolean) seriesTMDB.get("isFavorite");
    }

    public List<SeriesDTO> isList(List<HashMap> seriesTMDB, boolean favorito, String type){
        List<SeriesDTO> listSeries = new ArrayList<>();
        if(favorito){
            for (HashMap serie : seriesTMDB){
                listSeries.add(new SeriesDTO(serie, favorito));
            }
        } else if(Objects.nonNull(type)) {
            for (HashMap serie : seriesTMDB){
                listSeries.add(new SeriesDTO(serie, type));
            }
        } else {
            for (HashMap serie : seriesTMDB){
                listSeries.add(new SeriesDTO(serie));
            }
        }

        return listSeries;
    }

    public HashMap convertHashMap(SeriesDTO seriesDTO){
        HashMap aux = new HashMap();
        aux.put("idTMDB", seriesDTO.idTMDB);
        aux.put("name", seriesDTO.name);
        aux.put("overview", seriesDTO.overview);
        aux.put("posterPath", seriesDTO.posterPath);
        aux.put("numberOfEpisodes", seriesDTO.numberOfEpisodes);
        aux.put("numberOfSeasons", seriesDTO.numberOfSeasons);
        aux.put("releaseDate", seriesDTO.releaseDate);
        aux.put("lastYear", seriesDTO.lastYear);
        aux.put("isTvShow", seriesDTO.isTvShow);
        aux.put("isFavorite", seriesDTO.isFavorite);
        return aux;
    }

    public boolean temNosFavoritos(Long id){
        return true;
    }
}
