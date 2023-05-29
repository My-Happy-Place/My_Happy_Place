import { Component } from '@angular/core';
import { BaseContentComponent } from '../base-content/base-content.component';

@Component({
  selector: 'app-content-card',
  templateUrl: './content-card.component.html',
  styleUrls: ['./content-card.component.scss'],
})
export class ContentCardComponent extends BaseContentComponent {
  ngOnInit(): void {
    super.init();
  }
}
