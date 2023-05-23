import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Content } from 'src/app/models/content';

@Injectable({
  providedIn: 'root',
})
export class ContentService {
  private jsonURL = '/assets/favorites.json';
  private tmdbURL = 'https://api.themoviedb.org/3';
  private readonly apiKey = '7700f72b85d9932120594c5f27f336e1';
  favoritesIds: number[] = [];

  constructor(private http: HttpClient) {}

  getFavorites(): Observable<any> {
    return this.http.get('api/users/1/findFavorites');
  }

  getTrendingMovies(): Observable<any> {
    return this.http.get(
      `${this.tmdbURL}/trending/movie/week?api_key=${this.apiKey}&language=pt-BR`
    );
  }

  getTrendingTvShows(): Observable<any> {
    return this.http.get(
      `${this.tmdbURL}/trending/tv/week?api_key=${this.apiKey}&language=pt-BR`
    );
  }

  getSearchResults(
    query: string,
    searchType: string = 'multisearch',
    page: number = 1
  ): Observable<any> {
    return this.http.get(
      `api/all-types/${searchType}/1?descricao="${query}"&page=${page}`
    );
  }

  setFavoriteStatus(
    id: number,
    isTvShow: boolean,
    status: boolean
  ): Observable<any> {
    const uri = isTvShow ? 'series' : 'movies';
    const body: any = {
      userList: [
        {
          id: 1,
        },
      ],
    };

    if (isTvShow) {
      body.serieId = id;
    } else {
      body.movieId = id;
    }

    return status
      ? this.http.post(`api/${uri}`, body)
      : this.http.put(`api/${uri}`, body);
  }
}
