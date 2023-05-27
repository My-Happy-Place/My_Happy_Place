import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class ContentService {
  constructor(private http: HttpClient) {}

  getFavorites(): Observable<any> {
    return this.http.get('api/users/1/findFavorites');
  }

  getPopularMovies(): Observable<any> {
    return this.http.get(`api/movies/popular?idUser=1&page=1`);
  }

  getTrendingShows(): Observable<any> {
    return this.http.get(
      `api/all-types/trendingseries/1?timeWindow=week&page=1`
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
    mediaType: 'movie' | 'tv',
    status: boolean
  ): Observable<any> {
    const uri = mediaType == 'tv' ? 'series' : 'movies';
    const body: any = {
      userList: [
        {
          id: 1,
        },
      ],
    };

    mediaType == 'tv' ? (body.serieId = id) : (body.movieId = id);

    return status
      ? this.http.post(`api/${uri}`, body)
      : this.http.put(`api/${uri}`, body);
  }
}
