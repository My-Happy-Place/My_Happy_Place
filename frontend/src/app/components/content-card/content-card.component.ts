import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-content-card',
  templateUrl: './content-card.component.html',
  styleUrls: ['./content-card.component.scss'],
})
//TODO: Criar modelos para filmes e séries
export class ContentCardComponent implements OnInit {
  @Input() item: any;
  releaseYear!: string;
  contentName!: string;
  readonly baseImagePath = 'https://image.tmdb.org/t/p/w400/';
  fullImagePath!: string;

  constructor() {}

  ngOnInit(): void {
    this.releaseYear = this.item.release_date || this.item.first_air_date;
    this.releaseYear = this.releaseYear.substring(0, 4);
    this.contentName = this.item.title || this.item.name;
    this.fullImagePath = this.baseImagePath + this.item.poster_path;
  }
}
