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
        data.results.map((element: any) => {
          this.results.push({
            id: element.id,
            name: element.title == undefined ? element.name : element.title,
            overview: element.overview,
            posterPath: element.poster_path,
            releaseDate:
              element.release_date == undefined
                ? element.first_air_date
                : element.release_date,
          });
        });
        this.results = this.results.filter(
          (content) => content.posterPath !== null && content.overview !== ''
        );
      });
    });
  }
}
