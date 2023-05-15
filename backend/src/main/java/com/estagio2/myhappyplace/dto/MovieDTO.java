package com.estagio2.myhappyplace.dto;

import com.estagio2.myhappyplace.entities.FavoriteMovies;
import com.estagio2.myhappyplace.entities.FavoriteSeries;
import com.estagio2.myhappyplace.entities.User;
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
public class MovieDTO {

    private Integer idTMDB;

    private String name;

    private String overview;

    private String posterPath;

    private String releaseDate;

    private Integer runtime;

    private Boolean isFavorite;


    public MovieDTO(HashMap movieTMDB) {
        this.idTMDB = (Integer) movieTMDB.get("id");
        this.name = (String) movieTMDB.get("title");
        this.overview = (String) movieTMDB.get("overview");
        this.posterPath = (String) movieTMDB.get("poster_path");
        this.releaseDate = (String) movieTMDB.get("release_date");
        this.runtime = (Integer) movieTMDB.get("runtime");
    }

    public List<MovieDTO> isList(List<HashMap> moviesTMDB){
        List<MovieDTO> listMovie = new ArrayList<>();
        for (HashMap movie : moviesTMDB){
            listMovie.add(new MovieDTO(movie));
        }
        return listMovie;
    }

    public HashMap convertHashMap(MovieDTO movieDTO){
        HashMap aux = new HashMap((Map) movieDTO);
        return aux;
    }

    public boolean temNosFavoritos(Long id){
        return true;
    }
}
