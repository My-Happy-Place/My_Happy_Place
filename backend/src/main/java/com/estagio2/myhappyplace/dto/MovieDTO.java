package com.estagio2.myhappyplace.dto;

import com.estagio2.myhappyplace.entities.FavoriteMovies;
import com.estagio2.myhappyplace.entities.FavoriteSeries;
import com.estagio2.myhappyplace.entities.User;
import com.estagio2.myhappyplace.service.UserService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MovieDTO {

    private UserService userService = new UserService();

    private Integer idTMDB;

    private String name;

    private String overview;

    private String posterPath;

    private String releaseDate;

    private Integer runtime;

    private Boolean isTvShow;

    private Boolean isFavorite;


    public MovieDTO(HashMap movieTMDB) {
        this.idTMDB = (Integer) movieTMDB.get("id");
        this.name = (String) movieTMDB.get("title");
        this.overview = (String) movieTMDB.get("overview");
        this.posterPath = (String) movieTMDB.get("poster_path");
        this.releaseDate = (String) movieTMDB.get("release_date");
        this.runtime = (Integer) movieTMDB.get("runtime");
        this.isTvShow = false;
        this.isFavorite = false;
    }

    public MovieDTO(HashMap movieTMDB, boolean favorito) {
        this.idTMDB = (Integer) movieTMDB.get("id");
        this.name = (String) movieTMDB.get("title");
        this.overview = (String) movieTMDB.get("overview");
        this.posterPath = (String) movieTMDB.get("poster_path");
        this.releaseDate = (String) movieTMDB.get("release_date");
        this.runtime = (Integer) movieTMDB.get("runtime");
        this.isTvShow = false;
        this.isFavorite = favorito;
    }

    public List<MovieDTO> isList(List<HashMap> moviesTMDB, boolean favorito){
        List<MovieDTO> listMovie = new ArrayList<>();
        if(favorito){
            for (HashMap movie : moviesTMDB){
                listMovie.add(new MovieDTO(movie, favorito));
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
        aux.put("id", movieDTO.idTMDB);
        aux.put("name", movieDTO.name);
        aux.put("overview", movieDTO.overview);
        aux.put("posterPath", movieDTO.posterPath);
        aux.put("releaseDate", movieDTO.releaseDate);
        aux.put("runtime", movieDTO.runtime);
        aux.put("isTvShow", movieDTO.isTvShow);
        aux.put("isFavorite", movieDTO.isFavorite);
        return aux;
    }

    public boolean temNosFavoritos(Integer idTMDB, Long idUser){
         UserDTO user = userService.findById(idUser);

        return true;
    }
}
