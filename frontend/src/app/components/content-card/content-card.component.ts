import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-content-card',
  templateUrl: './content-card.component.html',
  styleUrls: ['./content-card.component.scss'],
})
export class ContentCardComponent implements OnInit {
  @Input() item: any;
  releaseYear!: string;

  constructor() {}

  ngOnInit(): void {
    this.releaseYear = this.item.release_date;
    this.releaseYear = this.releaseYear.substring(0, 4);
  }
}
