import { Component } from '@angular/core';
import { BaseContentComponent } from '../base-content/base-content.component';

@Component({
  selector: 'app-detailed-content',
  templateUrl: './detailed-content.component.html',
  styleUrls: ['./detailed-content.component.scss'],
})
export class DetailedContentComponent extends BaseContentComponent {
  ngOnInit(): void {
    super.init();
  }
}
