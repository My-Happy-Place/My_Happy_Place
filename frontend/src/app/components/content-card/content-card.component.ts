import { Component, Input, OnInit } from '@angular/core';
import { NavigationExtras, Router } from '@angular/router';
import { Content } from 'src/app/models/content';
import { ContentService } from 'src/app/services/content/content.service';

@Component({
  selector: 'app-content-card',
  templateUrl: './content-card.component.html',
  styleUrls: ['./content-card.component.scss'],
})
export class ContentCardComponent implements OnInit {
  @Input() item!: Content;
  releaseYear!: string;
  readonly baseImagePath = 'https://image.tmdb.org/t/p/w400/';
  fullImagePath!: string;
  isFavorite!: boolean;
  favoriteIcon!: 'favorite' | 'favorite_border';

  constructor(private contentService: ContentService) {}

  ngOnInit(): void {
    this.fullImagePath = this.baseImagePath + this.item.posterPath;
    this.releaseYear = this.item.releaseDate.substring(0, 4);
    this.isFavorite = this.item.isFavorite;
    this.favoriteIcon = this.isFavorite ? 'favorite' : 'favorite_border';
  }

  changeFavoriteStatus(): void {
    this.favoriteIcon = this.isFavorite ? 'favorite_border' : 'favorite';
    this.isFavorite = !this.isFavorite;
    this.contentService
      .setFavoriteStatus(this.item.id, this.item.isTvShow, this.isFavorite)
      .subscribe();
  }
}
