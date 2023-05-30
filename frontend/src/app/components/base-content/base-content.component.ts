import { Component, Input } from '@angular/core';
import { Content } from 'src/app/models/content';
import { ContentService } from 'src/app/services/content/content.service';

@Component({
  template: '',
})
export abstract class BaseContentComponent {
  @Input() item!: Content;
  releaseYear!: string;
  fullImagePath!: string;
  isFavorite!: boolean;
  favoriteIcon!: 'favorite' | 'favorite_border';

  constructor(private contentService: ContentService) {}

  init(): void {
    this.fullImagePath = this.contentService.getBaseImagePath('w400') + this.item.posterPath;
    this.releaseYear = this.item.releaseDate.substring(0, 4);
    this.isFavorite = this.item.isFavorite;
    this.favoriteIcon = this.isFavorite ? 'favorite' : 'favorite_border';
  }

  changeFavoriteStatus(): void {
    this.favoriteIcon = this.isFavorite ? 'favorite_border' : 'favorite';
    this.isFavorite = !this.isFavorite;
    this.contentService
      .setFavoriteStatus(this.item.idTMDB, this.item.mediaType, this.isFavorite)
      .subscribe();
  }
}
