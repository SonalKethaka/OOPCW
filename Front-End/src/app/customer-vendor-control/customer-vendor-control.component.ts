import { Component } from '@angular/core';
import { CustomerVendorControlService } from '../customer-vendor-control.service';

@Component({
  selector: 'app-customer-vendor-control',
  standalone: true,
  imports: [],
  templateUrl: './customer-vendor-control.component.html',
  styleUrl: './customer-vendor-control.component.css'
})
export class CustomerVendorControlComponent {
  regularCustomerCount: number = 0;
  vipCustomerCount: number = 0;
  vendorCount: number = 0;
  currentRegularCustomers: number = 0;
  currentVipCustomers: number = 0;
  currentVendors: number = 0;

  constructor(private customerVendorService: CustomerVendorControlService) { }

  ngOnInit(): void {
    this.getCustomerCounts(); // Get current customer counts when the component is initialized
    this.getVendorCounts();// Get current vendor counts when the component is initialized
  }

  // Increment regular customers count
  incrementRegularCustomers() {
    this.regularCustomerCount++;
    this.adjustRegularCustomers();
  }

  // Decrement regular customers count
  decrementRegularCustomers() {
    if (this.regularCustomerCount > 0) {
      this.regularCustomerCount--;
      this.adjustRegularCustomers();
    }
  }

  // Increment VIP customers count
  incrementVipCustomers() {
    this.vipCustomerCount++;
    this.adjustVipCustomers();
  }

  // Decrement VIP customers count
  decrementVipCustomers() {
    if (this.vipCustomerCount > 0) {
      this.vipCustomerCount--;
      this.adjustVipCustomers();
    }
  }

  // Increment Vendors count
  incrementVendors() {
    this.vendorCount++;
    this.adjustVndors();
  }

  // Decrement Vendors count
  decrementVendors() {
    if (this.vendorCount > 0) {
      this.vendorCount--;
      this.adjustVndors();
    }
  }

  // Adjust regular customers
  adjustRegularCustomers(): void {
    this.customerVendorService.adjustRegularCustomers(this.regularCustomerCount)
      .subscribe({
        next: (response: string) => {
          console.log('Regular customers adjusted:', response);
          // Assuming success if we get a response
          this.getCustomerCounts();
        },
        error: (error: any) => {
          console.error('Error adjusting regular customers:', error);
          // Handle error (show user feedback, etc.)
        }
      });
  }

  // Adjust VIP customers
  adjustVipCustomers() {
    this.customerVendorService.adjustVipCustomers(this.vipCustomerCount).subscribe(
      response => {
        console.log('VIP customers adjusted:', response);
        this.getCustomerCounts(); // Update customer counts after adjustment
      },
      error => {
        console.error('Error adjusting VIP customers:', error);
      }
    );
  }

  // Adjust Vendors
  adjustVndors() {
    this.customerVendorService.adjustVendors(this.vendorCount).subscribe(
      response => {
        console.log('Vendors adjusted:', response);
        this.getVendorCounts(); // Update customer counts after adjustment
      },
      error => {
        console.error('Error adjusting Vendors:', error);
      }
    );
  }

  // Get the current number of regular and VIP customers
  getCustomerCounts() {
    this.customerVendorService.getCustomerCounts().subscribe(response => {
      this.currentRegularCustomers = response.regularCustomers;
      this.currentVipCustomers = response.vipCustomers;
    });
  }

  // Get the current number of Vendors
  getVendorCounts() {
    this.customerVendorService.getVendorCounts().subscribe(response => {
      this.currentVendors = response.regularCustomers;
    });
  }
}
