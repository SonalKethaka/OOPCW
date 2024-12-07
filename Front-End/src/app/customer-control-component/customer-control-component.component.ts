import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { CustomerConrolService } from '../customer-conrol.service';


@Component({
  selector: 'app-customer-control-component',
  standalone: true,
  imports: [],
  templateUrl: './customer-control-component.component.html',
  styleUrl: './customer-control-component.component.css'
})
export class CustomerControlComponentComponent {
  regularCustomerCount: number = 0;
  vipCustomerCount: number = 0;
  currentRegularCustomers: number = 0;
  currentVipCustomers: number = 0;

  constructor(private customerController: CustomerConrolService) { }

  ngOnInit(): void {
    this.getCustomerCounts(); // Get current customer counts when the component is initialized
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

  // Adjust regular customers
  adjustRegularCustomers(): void {
    this.customerController.adjustRegularCustomers(this.regularCustomerCount)
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
    this.customerController.adjustVipCustomers(this.vipCustomerCount).subscribe(
      response => {
        console.log('VIP customers adjusted:', response);
        this.getCustomerCounts(); // Update customer counts after adjustment
      },
      error => {
        console.error('Error adjusting VIP customers:', error);
      }
    );
  }

  // Get the current number of regular and VIP customers
  getCustomerCounts() {
    this.customerController.getCustomerCounts().subscribe(response => {
      this.currentRegularCustomers = response.regularCustomers;
      this.currentVipCustomers = response.vipCustomers;
    });
  }
}
