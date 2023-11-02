import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
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
  user: String = '';

  constructor(private contentService: ContentService, private router: Router) {
    this.contentService.getUserName().subscribe((data) => (this.user = data.name));
  }

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

  pickContent(mediaType: 'movie' | 'tv' | 'all') {
    let range = this.favorites;

    if (mediaType != 'all') {
      range = range.filter(
        (content: Content) => content.mediaType == mediaType
      );
    }

    let content = range[Math.floor(Math.random() * range.length)];

    this.router.navigate([`/${content.mediaType}`, content.idTMDB]);
  }
}
