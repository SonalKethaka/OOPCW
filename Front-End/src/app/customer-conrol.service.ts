import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CustomerConrolService {

  private apiUrl = 'http://localhost:8080/api/tickets';

  constructor(private http: HttpClient) {}

  // // Adjust regular customers count
  // adjustRegularCustomers(count: number): Observable<any> {
  //   const params = new HttpParams().set('count', count.toString());
  //   return this.http.post(`${this.apiUrl}/configure/regularCustomers`, null, { params });
  // }

  // // Adjust VIP customers count
  // adjustVipCustomers(count: number): Observable<any> {
  //   const params = new HttpParams().set('count', count.toString());
  //   return this.http.post(`${this.apiUrl}/configure/vipCustomers`, null, { params });
  // }

  // adjustRegularCustomers(numCustomers: number): Observable<any> {
  //   return this.http.post(`${this.apiUrl}/configure/regularCustomers?numCustomers=${numCustomers}`, {});
  // }

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

  // Get current customer counts
  getCustomerCounts(): Observable<any> {
    return this.http.get(`${this.apiUrl}/customers`);
  }
}
