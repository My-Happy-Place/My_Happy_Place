import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-search-results',
  templateUrl: './search-results.component.html',
  styleUrls: ['./search-results.component.scss'],
})
export class SearchResultsComponent implements OnInit {
  constructor(private route: ActivatedRoute) {}

  search!: string;

  ngOnInit(): void {
    this.route.params.subscribe((params) => {
      this.search = params['search'];
    });
  }
}
