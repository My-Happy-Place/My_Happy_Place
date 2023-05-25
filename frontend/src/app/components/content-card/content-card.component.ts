import { Component } from '@angular/core';
import { BaseContent } from 'src/app/shared/app-material/base-content';

@Component({
  selector: 'app-content-card',
  templateUrl: './content-card.component.html',
  styleUrls: ['./content-card.component.scss'],
})
export class ContentCardComponent extends BaseContent {}
