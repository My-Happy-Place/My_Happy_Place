import { Component, OnInit, Input } from '@angular/core';
import { Episode } from 'src/app/models/episode';
import { ContentService } from 'src/app/services/content/content.service';

@Component({
  selector: 'app-episode',
  templateUrl: './episode.component.html',
  styleUrls: ['./episode.component.scss'],
})
export class EpisodeComponent implements OnInit {
  @Input() episode!: Episode;

  constructor(protected contentService: ContentService) {}

  ngOnInit(): void {}
}
