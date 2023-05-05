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
    //TODO:
    //preparar endpoint de buscar favoritos
    //validar conteúdos que vem favoritados
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
    //TODO: Verificar se mapeamento dos dados em um array de filmes/séries não deve ser feito já no service
    this.contentService.getTrendingMovies().subscribe((data) => {
      data.results.map((element: any) => {
        this.trendingMovies.push({
          id: element.id,
          name: element.title,
          overview: element.overview,
          posterPath: element.poster_path,
          releaseDate: element.release_date,
          runtime: element.runtime,
          isFavorite: false,
        });
      });
    });

    this.contentService.getTrendingTvShows().subscribe((data) => {
      data.results.map((element: any) => {
        this.trendingTv.push({
          id: element.id,
          name: element.name,
          overview: element.overview,
          posterPath: element.poster_path,
          numberOfEpisodes: element.numberOfEpisodes,
          numberOfSeasons: element.numberOfSeasons,
          releaseDate: element.first_air_date,
          lastYear: element.last_air_date,
          isFavorite: false,
        });
      });
    });
  }
}
