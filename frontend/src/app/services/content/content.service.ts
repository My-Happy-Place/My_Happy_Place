import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class ContentService {
  private jsonURL = '/assets/movies.json';
  private readonly apiKey = '7700f72b85d9932120594c5f27f336e1';
  private readonly trendingURL = 'https://api.themoviedb.org/3/trending';

  constructor(private http: HttpClient) {}

  getFavorites() {
    return this.http.get(this.jsonURL);
  }

  getTrendingMovies(): Observable<any> {
    return this.http.get(
      `${this.trendingURL}/movie/week?api_key=${this.apiKey}&language=pt-BR`
    );
  }

  getTrendingTvShows(): Observable<any> {
    return this.http.get(
      `${this.trendingURL}/tv/week?api_key=${this.apiKey}&language=pt-BR`
    );
  }
}
