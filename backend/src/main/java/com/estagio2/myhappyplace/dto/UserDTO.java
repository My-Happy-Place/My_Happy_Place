package com.estagio2.myhappyplace.dto;

import com.estagio2.myhappyplace.entities.FavoriteMovies;
import com.estagio2.myhappyplace.entities.FavoriteSeries;
import com.estagio2.myhappyplace.entities.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private Long id;

    private String name;

    private List<FavoriteMovies> favoriteMovies = new ArrayList<>();

    private List<FavoriteSeries> favoriteSeries = new ArrayList<>();

    public UserDTO(User user){
        this.id = user.getId();
        this.name = user.getName();
        this.favoriteMovies = user.getFavoriteMovies();
        this.favoriteSeries = user.getFavoriteSeries();
    }
}
