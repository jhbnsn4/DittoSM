import { TestBed } from '@angular/core/testing';

import { SessionAjaxService } from './session-ajax.service';

describe('SessionAjaxService', () => {
  let service: SessionAjaxService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SessionAjaxService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
