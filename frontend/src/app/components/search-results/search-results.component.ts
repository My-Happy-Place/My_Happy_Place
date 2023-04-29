import { ContentService } from './../../services/content/content.service';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

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
  results: any;

  ngOnInit(): void {
    this.route.params.subscribe((routeParams) => {
      this.search = routeParams['search'];

      this.router
        .navigateByUrl('/search', { skipLocationChange: true })
        .then(() => {
          this.router.navigate(['.', { myParam: this.search }]);
        });

      this.contentService.getSearchResults(this.search).subscribe((data) => {
        this.results = data.results;
        console.log(this.results);
      });
    });
  }
}
