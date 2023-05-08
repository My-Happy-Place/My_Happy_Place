package com.estagio2.myhappyplace.entities;

import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Table(name = "favoriteSeries")
@Entity
public class FavoriteSeries {

    @Id
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "favoriteSeries_id_seq")
//    @SequenceGenerator(name = "favoriteSeries_id_seq", sequenceName = "favoriteSeries_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany(mappedBy = "favoriteSeries")
    private List<User> userList = new ArrayList<User>();

    @Column(name = "serieId_TMDB")
    private Long serieId;
}
