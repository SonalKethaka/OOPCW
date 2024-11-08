import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class TicketService {
  private baseUrl = 'http://localhost:8080/api/ticket';

  constructor(private http: HttpClient) {}

  startSystem(config: any): Observable<any> {
    const params = {
      totalTickets: config.totalTickets,
      ticketReleaseRate: config.ticketReleaseRate,
      customerRetrievalRate: config.customerRetrievalRate,
      numVendors: config.numVendors,
      numCustomers: config.numCustomers,
    };
    return this.http.post(`${this.baseUrl}/start`, null, { params });
  }

  stopSystem(): Observable<any> {
    return this.http.post(`${this.baseUrl}/stop`, null);
  }

  getTotalTickets(): Observable<number> {
    return this.http.get<number>(`${this.baseUrl}/totalTickets`);
  }
}
