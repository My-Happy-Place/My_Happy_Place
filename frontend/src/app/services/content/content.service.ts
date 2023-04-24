import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class ContentService {
  private apiUrl = '/assets/movies.json'

  constructor(private http: HttpClient) {}

  getContentData() {
    return this.http.get(this.apiUrl)
  }
}
