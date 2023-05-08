package com.estagio2.myhappyplace.repositories;

import com.estagio2.myhappyplace.entities.FavoriteSeries;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FavoriteSeriesRepository extends JpaRepository<FavoriteSeries, Long> {

}
