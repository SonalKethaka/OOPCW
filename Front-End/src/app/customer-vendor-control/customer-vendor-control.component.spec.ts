import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CustomerVendorControlComponent } from './customer-vendor-control.component';

describe('CustomerVendorControlComponent', () => {
  let component: CustomerVendorControlComponent;
  let fixture: ComponentFixture<CustomerVendorControlComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CustomerVendorControlComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CustomerVendorControlComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
