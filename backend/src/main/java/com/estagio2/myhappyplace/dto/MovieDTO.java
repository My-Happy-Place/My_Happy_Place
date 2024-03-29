package com.estagio2.myhappyplace.dto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MovieDTO {

    private Integer idTMDB;

    private String name;

    private String overview;

    private String posterPath;

    private String backdropPath;

    private String releaseDate;

    private Integer runtime;

    private String mediaType;

    private Boolean isFavorite;


    public MovieDTO(HashMap movieTMDB) {
        this.idTMDB = (Integer) movieTMDB.get("id");
        this.name = (String) movieTMDB.get("title");
        this.overview = (String) movieTMDB.get("overview");
        this.posterPath = (String) movieTMDB.get("poster_path");
        this.backdropPath = (String) movieTMDB.get("backdrop_path");
        this.releaseDate = (String) movieTMDB.get("release_date");
        this.runtime = (Integer) movieTMDB.get("runtime");
        this.mediaType = "movie";
        this.isFavorite = false;
    }

    public MovieDTO(HashMap movieTMDB, boolean favorito) {
        this.idTMDB = (Integer) movieTMDB.get("id");
        this.name = (String) movieTMDB.get("title");
        this.overview = (String) movieTMDB.get("overview");
        this.posterPath = (String) movieTMDB.get("poster_path");
        this.backdropPath = (String) movieTMDB.get("backdrop_path");
        this.releaseDate = (String) movieTMDB.get("release_date");
        this.runtime = (Integer) movieTMDB.get("runtime");
        this.mediaType = "movie";
        this.isFavorite = favorito;
    }

    public MovieDTO(HashMap movieTMDB, String type) {
        this.idTMDB = (Integer) movieTMDB.get("id");
        this.name = (String) movieTMDB.get("title");
        this.overview = (String) movieTMDB.get("overview");
        this.posterPath = (String) movieTMDB.get("poster_path");
        this.backdropPath = (String) movieTMDB.get("backdrop_path");
        this.releaseDate = (String) movieTMDB.get("release_date");
        this.runtime = (Integer) movieTMDB.get("runtime");
        this.mediaType = "movie";
        this.isFavorite = (Boolean) movieTMDB.get("isFavorite");
    }

    public List<MovieDTO> isList(List<HashMap> moviesTMDB, boolean favorito, String type){
        List<MovieDTO> listMovie = new ArrayList<>();
        if(favorito){
            for (HashMap movie : moviesTMDB){
                listMovie.add(new MovieDTO(movie, favorito));
            }
        } else if(Objects.nonNull(type)){
            for (HashMap movie : moviesTMDB){
                listMovie.add(new MovieDTO(movie, type));
            }
        } else {
            for (HashMap movie : moviesTMDB){
                listMovie.add(new MovieDTO(movie));
            }
        }

        return listMovie;
    }

    public HashMap convertHashMap(MovieDTO movieDTO){
        HashMap aux = new HashMap();
        aux.put("idTMDB", movieDTO.idTMDB);
        aux.put("name", movieDTO.name);
        aux.put("overview", movieDTO.overview);
        aux.put("posterPath", movieDTO.posterPath);
        aux.put("backdropPath", movieDTO.backdropPath);
        aux.put("releaseDate", movieDTO.releaseDate);
        aux.put("runtime", movieDTO.runtime);
        aux.put("mediaType", movieDTO.mediaType);
        aux.put("isFavorite", movieDTO.isFavorite);
        return aux;
    }

    public boolean temNosFavoritos(Integer idTMDB, Long idUser){

        return true;
    }
}
