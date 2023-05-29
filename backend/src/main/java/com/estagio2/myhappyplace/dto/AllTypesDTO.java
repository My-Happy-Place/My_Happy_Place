package com.estagio2.myhappyplace.dto;

import com.estagio2.myhappyplace.service.UserService;
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
public class AllTypesDTO {

    private Integer idTMDB;

    private String name;

    private String overview;

    private String posterPath;

    private String releaseDate;

    private Integer runtime;

    private Integer numberOfEpisodes;

    private Integer numberOfSeasons;

    private String lastYear;

    private Boolean isFavorite;

    private String mediaType;


    public AllTypesDTO(HashMap objectTMDB) {
        this.idTMDB = (Integer) objectTMDB.get("id");
        this.name = (objectTMDB.get("media_type").equals("movie")) ? (String) objectTMDB.get("title") : (String) objectTMDB.get("name");
        this.overview = (String) objectTMDB.get("overview");
        this.posterPath = (String) objectTMDB.get("poster_path");

        if(objectTMDB.get("media_type").equals("movie")){
            this.releaseDate = (String) objectTMDB.get("release_date");
            this.runtime = (Integer) objectTMDB.get("runtime");
        } else {
            this.numberOfEpisodes = (Integer) objectTMDB.get("number_of_episodes");
            this.numberOfSeasons = (Integer) objectTMDB.get("number_of_seasons");
            this.releaseDate = (String) objectTMDB.get("first_air_date");
            this.lastYear = (String) objectTMDB.get("last_air_date");
        }

        this.isFavorite = (Boolean) objectTMDB.get("isFavorite");
        this.mediaType = (String) objectTMDB.get("media_type");
    }

    public AllTypesDTO(HashMap objectTMDB, String type) {
        this.idTMDB = (Integer) objectTMDB.get("id");
        this.name = (type.equals("M")) ? (String) objectTMDB.get("title") : (String) objectTMDB.get("name");
        this.overview = (String) objectTMDB.get("overview");
        this.posterPath = (String) objectTMDB.get("poster_path");

        if(type.equals("M")){
            this.releaseDate = (String) objectTMDB.get("release_date");
            this.runtime = (Integer) objectTMDB.get("runtime");
        } else {
            this.numberOfEpisodes = (Integer) objectTMDB.get("number_of_episodes");
            this.numberOfSeasons = (Integer) objectTMDB.get("number_of_seasons");
            this.releaseDate = (String) objectTMDB.get("first_air_date");
            this.lastYear = (String) objectTMDB.get("last_air_date");
        }

        this.isFavorite = (Boolean) objectTMDB.get("isFavorite");
        this.mediaType = (String) objectTMDB.get("media_type");
    }

    public List<AllTypesDTO> isList(List<HashMap> objectsTMDB, String type){
        List<AllTypesDTO> listObjects = new ArrayList<>();
        if(Objects.nonNull(type)){
            for (HashMap movie : objectsTMDB){
                listObjects.add(new AllTypesDTO(movie, type));
            }
        } else {
            for (HashMap all : objectsTMDB){
                listObjects.add(new AllTypesDTO(all));
            }
        }

        return listObjects;
    }

    public HashMap convertHashMap(AllTypesDTO allTypesDTO){
        HashMap aux = new HashMap();
        aux.put("idTMDB", allTypesDTO.idTMDB);
        aux.put("name", allTypesDTO.name);
        aux.put("overview", allTypesDTO.overview);
        aux.put("posterPath", allTypesDTO.posterPath);
        aux.put("releaseDate", allTypesDTO.releaseDate);
        aux.put("runtime", allTypesDTO.runtime);
        aux.put("isFavorite", allTypesDTO.isFavorite);
        aux.put("mediaType", allTypesDTO.mediaType);
        return aux;
    }

    public boolean temNosFavoritos(Integer idTMDB, Long idUser){
        return true;
    }
}
