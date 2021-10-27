import { TestBed } from '@angular/core/testing';

import { UserAjaxServiceService } from './user-ajax-service.service';

describe('UserAjaxServiceService', () => {
  let service: UserAjaxServiceService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(UserAjaxServiceService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
