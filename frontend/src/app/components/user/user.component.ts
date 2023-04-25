import { ContentService } from 'src/app/services/content/content.service';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.scss']
})
export class UserComponent implements OnInit {
  favorites: any;
  trendingMovies: any;
  trendingTv: any;

  //TODO: retornar usuário logado
  user: String = 'Nícolas';

  constructor(private contentService: ContentService) {}

  ngOnInit(): void {
    this.contentService.getFavorites().subscribe((data) => {
      this.favorites = data;
    });
    this.contentService.getTrendingMovies().subscribe((data) => {
      this.trendingMovies = data.results;
    });
    this.contentService.getTrendingTvShows().subscribe((data) => {
      this.trendingTv = data.results;
    })
  }
}
