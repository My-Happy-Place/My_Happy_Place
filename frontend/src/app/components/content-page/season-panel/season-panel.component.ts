import { Component, OnInit, Input } from '@angular/core';
import { Season } from 'src/app/models/season';
import { ContentService } from 'src/app/services/content/content.service';

@Component({
  selector: 'app-season-panel',
  templateUrl: './season-panel.component.html',
  styleUrls: ['./season-panel.component.scss'],
})
export class SeasonPanelComponent implements OnInit {
  @Input() season!: Season;

  constructor(protected contentService: ContentService) {}

  ngOnInit(): void {}
}
