import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CustomerVendorControlService {

  private apiUrl = 'http://localhost:8080/api/tickets';

  constructor(private http: HttpClient) {}

  adjustRegularCustomers(numCustomers: number): Observable<string> {
    return this.http.post(
      `${this.apiUrl}/configure/regularCustomers?numCustomers=${numCustomers}`,
      {},
      { responseType: 'text' }
    );
  }
  
  adjustVipCustomers(numCustomers: number): Observable<string> {
    return this.http.post(
      `${this.apiUrl}/configure/vipCustomers?numCustomers=${numCustomers}`,
      {},
      { responseType: 'text' }
    );
  }

  adjustVendors(numVendors: number): Observable<string> {
    return this.http.post(
      `${this.apiUrl}/configure/vendors?numVendors=${numVendors}`,
      {},
      { responseType: 'text' }
    );
  }

  // Get current customer counts
  getCustomerCounts(): Observable<any> {
    return this.http.get(`${this.apiUrl}/customers`);
  }

  // Get current vendor counts
  getVendorCounts(): Observable<any> {
    return this.http.get(`${this.apiUrl}/vendors`);
  }
}
