import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Content } from 'src/app/models/content';

@Injectable({
  providedIn: 'root',
})
export class ContentService {
  userId: number = 1;
  readonly api = 'https://myhappyplace-production.up.railway.app';

  constructor(private http: HttpClient) {}

  getUserName(): Observable<any> {
    return this.http.get(`${this.api}/users/${this.userId}`);
  }

  getBaseImagePath(format: string) {
    return `https://image.tmdb.org/t/p/${format}/`;
  }

  getFavorites(): Observable<any> {
    return this.http.get(`${this.api}/users/${this.userId}/findFavorites`);
  }

  getPopularMovies(): Observable<any> {
    return this.http.get(
      `${this.api}/movies/popular?idUser=${this.userId}&page=1`
    );
  }

  getTrendingShows(): Observable<any> {
    return this.http.get(
      `${this.api}/all-types/trendingseries/${this.userId}?timeWindow=week&page=1`
    );
  }

  getContentDetails(mediaType: string, idTMDB: number): Observable<any> {
    const pathParam = mediaType == 'movie' ? 'movies' : 'series';
    return this.http.get(
      `${this.api}/${pathParam}/${idTMDB}?idUser=${this.userId}`
    );
  }

  getSearchResults(
    query: string,
    searchType: string = 'multisearch',
    page: number = 1
  ): Observable<any> {
    return this.http.get(
      `${this.api}/all-types/${searchType}/${this.userId}?descricao="${query}"&page=${page}`
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
      ? this.http.post(`${this.api}/${uri}`, body)
      : this.http.put(`${this.api}/${uri}`, body);
  }

  getSimilar(
    mediaType: 'movie' | 'tv',
    idTMDB: number,
    page: number = 1
  ): Observable<any> {
    const uri = mediaType == 'movie' ? 'movies' : 'series';
    return this.http.get(
      `${this.api}/${uri}/${idTMDB}/recommendations?idUser=${this.userId}&page=${page}`
    );
  }

  getShowSeasons(idTMDB: number): Observable<any> {
    return this.http.get(`${this.api}/series/${idTMDB}/seasons?idUser=1`);
  }

  filterContent(item: Content): boolean {
    return (
      item.name != null && item.overview != null && item.posterPath != null
    );
  }
}
