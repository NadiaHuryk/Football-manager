import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PlayersDetailComponent } from './players-detail.component';

describe('PlayersDatailComponent', () => {
  let component: PlayersDetailComponent;
  let fixture: ComponentFixture<PlayersDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [PlayersDetailComponent]
    });
    fixture = TestBed.createComponent(PlayersDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
