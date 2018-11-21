import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AddNewMatchesComponent } from './add-new-matches.component';

describe('AddNewMatchesComponent', () => {
  let component: AddNewMatchesComponent;
  let fixture: ComponentFixture<AddNewMatchesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AddNewMatchesComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AddNewMatchesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
