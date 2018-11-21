import { TestBed, inject } from '@angular/core/testing';

import { MatchInfoToTextMessageService } from './match-info-to-text-message.service';

describe('MatchInfoToTextMessageService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [MatchInfoToTextMessageService]
    });
  });

  it('should be created', inject([MatchInfoToTextMessageService], (service: MatchInfoToTextMessageService) => {
    expect(service).toBeTruthy();
  }));
});
