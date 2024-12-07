import { TestBed } from '@angular/core/testing';

import { CustomerVendorControlService } from './customer-vendor-control.service';

describe('CustomerVendorControlService', () => {
  let service: CustomerVendorControlService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CustomerVendorControlService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
