package com.estagio2.myhappyplace.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Table(name = "usuario")
@Entity
public class User {

    @Id
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_id_seq")
//    @SequenceGenerator(name = "user_id_seq", sequenceName = "user_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToMany
    @JoinTable(name = "user_fav_movies",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "favorite_movie_id")})
    private List<FavoriteMovies> favoriteMovies = new ArrayList<FavoriteMovies>();

    @ManyToMany
    @JoinTable(name = "user_fav_series",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "favorite_serie_id")})
    private List<FavoriteSeries> favoriteSeries = new ArrayList<FavoriteSeries>();

}
