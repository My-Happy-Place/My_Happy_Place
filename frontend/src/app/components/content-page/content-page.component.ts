import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { finalize } from 'rxjs';
import { Content } from 'src/app/models/content';
import { Episode } from 'src/app/models/episode';
import { Season } from 'src/app/models/season';
import { ContentService } from 'src/app/services/content/content.service';

@Component({
  selector: 'app-content-page',
  templateUrl: './content-page.component.html',
  styleUrls: ['./content-page.component.scss'],
})
export class ContentPageComponent {
  content!: Content;
  fullImagePath!: string;
  releaseYear!: string;
  isTvShow!: boolean;
  imageWidth = 342;
  imageHeight = 0;
  isLoading = false;
  favoriteIcon!: 'favorite' | 'favorite_border';
  seasons: Season[] = [];
  similar: Content[] = [];
  chosenEpisode!: Episode;

  constructor(
    protected contentService: ContentService,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.isLoading = true;
    this.route.params.subscribe((routeParams) => {
      this.contentService
        .getContentDetails(routeParams['media-type'], routeParams['id'])
        .pipe(finalize(() => (this.isLoading = false)))
        .subscribe((response: Content) => {
          this.content = response;

          this.fullImagePath =
            this.contentService.getBaseImagePath(`original`) +
            this.content.posterPath;
          this.releaseYear = this.content.releaseDate.substring(0, 4);
          this.isTvShow = this.content.mediaType == 'tv';
          this.favoriteIcon = this.content.isFavorite
            ? 'favorite'
            : 'favorite_border';

          if (this.isTvShow) {
            this.contentService
              .getShowSeasons(response.idTMDB)
              .subscribe((data) => {
                this.seasons = data;
                this.seasons.forEach((season: Season, index: number) => {
                  season.seasonNumber = index + 1;
                });
              });
          }

          this.contentService
            .getSimilar(response.mediaType, response.idTMDB)
            .subscribe((data: Content[]) => {
              this.similar = data.filter((item: Content) =>
                this.contentService.filterContent(item)
              );
            });
        });
    });
  }

  changeFavoriteStatus(): void {
    this.favoriteIcon = this.content.isFavorite
      ? 'favorite_border'
      : 'favorite';
    this.content.isFavorite = !this.content.isFavorite;
    this.contentService
      .setFavoriteStatus(
        this.content.idTMDB,
        this.content.mediaType,
        this.content.isFavorite
      )
      .subscribe();
  }

  updateDivHeight() {
    const imageElement = document.querySelector('img');
    this.imageHeight = imageElement?.clientHeight || 0;
  }

  pickEpisode() {
    const seasonNumber = Math.floor(Math.random() * this.seasons.length);
    const season = this.seasons[seasonNumber] as Season;
    const episodeNumber = Math.floor(Math.random() * season.episodes.length);
    this.chosenEpisode = season.episodes[episodeNumber];
  }
}
