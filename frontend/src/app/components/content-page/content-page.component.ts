import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { finalize } from 'rxjs';
import { Content } from 'src/app/models/content';
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
  seasons: Season[] = [];
  similar: Content[] = [];

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
        .subscribe((response) => {
          this.content = response;

          this.fullImagePath =
            this.contentService.getBaseImagePath(`w${this.imageWidth}`) +
            this.content.posterPath;
          this.releaseYear = this.content.releaseDate.substring(0, 4);
          this.isTvShow = this.content.mediaType == 'tv';

          if (this.isTvShow) {
            this.contentService
              .getShowSeasons(response.idTMDB)
              .subscribe((data) => {
                console.log(data);
                this.seasons = data;
                this.seasons.forEach((value: Season, index: number) => {
                  value.seasonNumber = index + 1;
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

  updateDivHeight() {
    const imageElement = document.querySelector('img');
    this.imageHeight = imageElement?.clientHeight || 0;
  }
}
