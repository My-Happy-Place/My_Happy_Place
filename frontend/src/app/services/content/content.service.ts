import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class ContentService {
  private jsonURL = '/assets/movies.json';
  private tmdbURL = 'https://api.themoviedb.org/3';
  private readonly apiKey = '7700f72b85d9932120594c5f27f336e1';

  constructor(private http: HttpClient) {}

  getFavorites(): Observable<any> {
    return this.http.get(this.jsonURL);
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

  getSearchResults(query: string): Observable<any> {
    return this.http.get(`${this.tmdbURL}/search/multi?api_key=${this.apiKey}&language=pt-BR&query=${query}`);
  }
}
