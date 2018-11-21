import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MatchInfoToTextMessageComponent } from './match-info-to-text-message.component';

describe('MatchInfoToTextMessageComponent', () => {
  let component: MatchInfoToTextMessageComponent;
  let fixture: ComponentFixture<MatchInfoToTextMessageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MatchInfoToTextMessageComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MatchInfoToTextMessageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
