import { TestBed, inject } from '@angular/core/testing';

import { FootballTeamService } from './footballTEAM.service';

describe('FootballTeamService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [FootballTeamService]
    });
  });

  it('should be created', inject([FootballTeamService], (service: FootballTeamService) => {
    expect(service).toBeTruthy();
  }));
});
