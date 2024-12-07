import { TestBed } from '@angular/core/testing';

import { CustomerConrolService } from './customer-conrol.service';

describe('CustomerConrolService', () => {
  let service: CustomerConrolService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CustomerConrolService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
