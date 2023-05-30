import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Content } from 'src/app/models/content';

@Injectable({
  providedIn: 'root',
})
export class ContentService {
  userId: number = 1;

  constructor(private http: HttpClient) {}

  getBaseImagePath(format: string) {
    return `https://image.tmdb.org/t/p/${format}/`;
  }

  getFavorites(): Observable<any> {
    return this.http.get(`api/users/${this.userId}/findFavorites`);
  }

  getPopularMovies(): Observable<any> {
    return this.http.get(`api/movies/popular?idUser=${this.userId}&page=1`);
  }

  getTrendingShows(): Observable<any> {
    return this.http.get(
      `api/all-types/trendingseries/${this.userId}?timeWindow=week&page=1`
    );
  }

  getContentDetails(mediaType: string, idTMDB: number): Observable<any> {
    const pathParam = mediaType == 'movie' ? 'movies' : 'series';
    return this.http.get(`api/${pathParam}/${idTMDB}`);
  }

  getSearchResults(
    query: string,
    searchType: string = 'multisearch',
    page: number = 1
  ): Observable<any> {
    return this.http.get(
      `api/all-types/${searchType}/${this.userId}?descricao="${query}"&page=${page}`
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
          id: this.userId,
        },
      ],
    };

    mediaType == 'tv' ? (body.serieId = id) : (body.movieId = id);

    return status
      ? this.http.post(`api/${uri}`, body)
      : this.http.put(`api/${uri}`, body);
  }

  getSimilar(
    mediaType: 'movie' | 'tv',
    idTMDB: number,
    page: number = 1
  ): Observable<any> {
    const uri = mediaType == 'movie' ? 'movies' : 'series';
    return this.http.get(
      `api/${uri}/${idTMDB}/recommendations?idUser=${this.userId}&page=${page}`
    );
  }

  getShowSeasons(idTMDB: number): Observable<any> {
    return this.http.get(`api/series/${idTMDB}/seasons?idUser=1`);
  }

  filterContent(item: Content): boolean {
    return (
      item.name != null && item.overview != null && item.posterPath != null
    );
  }
}
