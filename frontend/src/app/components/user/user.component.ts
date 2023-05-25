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
    this.contentService.getFavorites().subscribe((data) => {
      this.favorites = data;
    });

    this.contentService.getPopularMovies().subscribe((data) => {
      this.trendingMovies = data;
    });

    this.contentService.getTrendingShows().subscribe((data) => {
      this.trendingTv = data;

    });
  }
}
