import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SeasonPanelComponent } from './season-panel.component';

describe('SeasonPanelComponent', () => {
  let component: SeasonPanelComponent;
  let fixture: ComponentFixture<SeasonPanelComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [SeasonPanelComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(SeasonPanelComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
