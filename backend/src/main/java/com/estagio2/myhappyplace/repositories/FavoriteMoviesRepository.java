package com.estagio2.myhappyplace.repositories;

import com.estagio2.myhappyplace.entities.FavoriteMovies;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FavoriteMoviesRepository extends JpaRepository<FavoriteMovies, Long> {

}
