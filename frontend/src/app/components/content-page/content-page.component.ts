import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Content } from 'src/app/models/content';
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
  similar: Content[] = [];

  constructor(
    protected contentService: ContentService,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.route.params.subscribe((routeParams) => {
      this.contentService
        .getContentDetails(routeParams['media-type'], routeParams['id'])
        .subscribe((response) => {
          this.content = response;
          console.log(this.content);

          this.fullImagePath =
            this.contentService.getBaseImagePath(this.imageWidth) +
            this.content.posterPath;
          this.releaseYear = this.content.releaseDate.substring(0, 4);
          this.isTvShow = this.content.mediaType == 'tv';

          this.contentService
            .getSimilar(response.mediaType, response.idTMDB)
            .subscribe((data) => {
              this.similar = data;
            });
        });
    });
  }

  updateDivHeight() {
    const imageElement = document.querySelector('img');
    this.imageHeight = imageElement?.clientHeight || 0;
  }
}
