import { TestBed } from '@angular/core/testing';

import { UserAjaxService } from './user-ajax.service';

describe('UserAjaxService', () => {
  let service: UserAjaxService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(UserAjaxService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
