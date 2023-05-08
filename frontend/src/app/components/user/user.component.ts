import { Component, OnInit } from '@angular/core';
import { Content } from 'src/app/models/content';
import { Movie } from 'src/app/models/movie';
import { Show } from 'src/app/models/show';
import { ContentService } from 'src/app/services/content/content.service';

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.scss'],
})
export class UserComponent implements OnInit {
  favorites: Content[] = [];
  trendingMovies: Movie[] = [];
  trendingTv: Show[] = [];

  //TODO: retornar usuário logado
  user: String = 'Nícolas';

  constructor(private contentService: ContentService) {}

  ngOnInit(): void {
    this.contentService.getFavorites().subscribe((data: Object[]) => {
      data.map((element: any) => {
        this.favorites.push({
          id: element.id,
          name: element.title == undefined ? element.name : element.title,
          overview: element.overview,
          posterPath: element.poster_path,
          releaseDate:
            element.release_date == undefined
              ? element.first_air_date
              : element.release_date,
          isFavorite: true,
        });
      });
    });
    //TODO: Transferir tipagem dos dados para o backend.
    this.contentService.getTrendingMovies().subscribe((data) => {
      data.results.map((content: any) => {
        this.trendingMovies.push({
          id: content.id,
          name: content.title,
          overview: content.overview,
          posterPath: content.poster_path,
          releaseDate: content.release_date,
          runtime: content.runtime,
          isFavorite: this.contentService.favoritesIds.includes(content.id),
        });
      });
    });

    this.contentService.getTrendingTvShows().subscribe((data) => {
      data.results.map((content: any) => {
        this.trendingTv.push({
          id: content.id,
          name: content.name,
          overview: content.overview,
          posterPath: content.poster_path,
          numberOfEpisodes: content.numberOfEpisodes,
          numberOfSeasons: content.numberOfSeasons,
          releaseDate: content.first_air_date,
          lastYear: content.last_air_date,
          isFavorite: this.contentService.favoritesIds.includes(content.id),
        });
      });
    });
  }
}
