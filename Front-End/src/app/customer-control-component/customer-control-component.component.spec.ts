import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CustomerControlComponentComponent } from './customer-control-component.component';

describe('CustomerControlComponentComponent', () => {
  let component: CustomerControlComponentComponent;
  let fixture: ComponentFixture<CustomerControlComponentComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CustomerControlComponentComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CustomerControlComponentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
