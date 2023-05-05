import { ContentService } from './../../services/content/content.service';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Content } from 'src/app/models/content';

@Component({
  selector: 'app-search-results',
  templateUrl: './search-results.component.html',
  styleUrls: ['./search-results.component.scss'],
})
export class SearchResultsComponent implements OnInit {
  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private contentService: ContentService
  ) {}

  search!: string;
  results: Content[] = [];

  ngOnInit(): void {
    this.route.params.subscribe((routeParams) => {
      this.search = routeParams['search'];

      this.router
        .navigateByUrl('/search', { skipLocationChange: true })
        .then(() => {
          this.router.navigate(['.', { myParam: this.search }]);
        });

      this.results = [];

      this.contentService.getSearchResults(this.search).subscribe((data) => {
        data.results = data.results.filter(
          (content: any) =>
            content.media_type == 'movie' || content.media_type == 'tv'
        );

        data.results.map((content: any) => {
          this.results.push({
            id: content.id,
            name: content.title == undefined ? content.name : content.title,
            overview: content.overview,
            posterPath: content.poster_path,
            releaseDate:
              content.release_date == undefined
                ? content.first_air_date
                : content.release_date,
            isFavorite: false,
          });
        });
        this.results = this.results.filter(
          (content) => content.posterPath !== null && content.overview !== ''
        );
      });
    });
  }
}
