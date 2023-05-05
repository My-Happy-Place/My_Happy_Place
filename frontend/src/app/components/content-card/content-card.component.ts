import { Component, Input, OnInit } from '@angular/core';
import { Content } from 'src/app/models/content';

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

  constructor() {}

  ngOnInit(): void {
    this.fullImagePath = this.baseImagePath + this.item.posterPath;
    this.releaseYear = this.item.releaseDate.substring(0, 4);
    this.isFavorite = this.item.isFavorite;
    this.favoriteIcon = this.isFavorite ? 'favorite' : 'favorite_border';
  }

  favorite(): void {
    this.favoriteIcon = this.isFavorite
      ? (this.favoriteIcon = 'favorite_border')
      : (this.favoriteIcon = 'favorite');
    this.isFavorite = !this.isFavorite;
  }
}
