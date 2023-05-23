import { ContentService } from './../../services/content/content.service';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { finalize } from 'rxjs';
import { Content } from 'src/app/models/content';

@Component({
  selector: 'app-search-results',
  templateUrl: './search-results.component.html',
  styleUrls: ['./search-results.component.scss'],
})
export class SearchResultsComponent implements OnInit {
  constructor(
    private route: ActivatedRoute,
    // private router: Router,
    private contentService: ContentService
  ) {}

  search!: string;
  results: Content[] = [];
  isLoading: boolean = false;
  selectedRadio: string = 'multisearch';

  changeSearchType(): void {
    this.contentService
      .getSearchResults(this.search, this.selectedRadio)
      .subscribe((data) => {
        this.results = data;
      });
  }

  ngOnInit(): void {
    this.route.params.subscribe((routeParams) => {
      this.isLoading = true;
      this.search = routeParams['search'];

      // this.router
      //   .navigateByUrl('/search', { skipLocationChange: true })
      //   .then(() => {
      //     this.router.navigate(['.', { myParam: this.search }]);
      //   });

      this.results = [];

      this.contentService
        .getSearchResults(this.search)
        .pipe(finalize(() => (this.isLoading = false)))
        .subscribe((data) => {
          this.results = data;
          console.log(this.results);
        });
    });
  }
}
