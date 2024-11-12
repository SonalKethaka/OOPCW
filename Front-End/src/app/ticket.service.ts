import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class TicketService {
  private baseUrl = 'http://localhost:8080/api/tickets';

  constructor(private http: HttpClient) {}

  getTicketsLeft(): Observable<number> {
    return this.http.get<number>(`${this.baseUrl}/total`);
  }

  // startSystem(): Observable<string> {
  //   return this.http.post<string>(`${this.baseUrl}/start`, {});
  // }

  startSystem(): Observable<string> {
    return this.http.post(`${this.baseUrl}/start`, {}, { responseType: 'text' });
}

  // stopSystem(): Observable<string> {
  //   return this.http.post<string>(`${this.baseUrl}/stop`, {});
  // }

  stopSystem(): Observable<string> {
    return this.http.post(`${this.baseUrl}/stop`, {}, { responseType: 'text', observe: 'body' });
}

  // getSystemStatus(): Observable<string> {
  //   return this.http.get<string>(`${this.baseUrl}/status`);
  // }
  getSystemStatus(): Observable<{status: string}> {
    return this.http.get<{ status: string }>(`${this.baseUrl}/status`, {
      headers: new HttpHeaders({ 'Content-Type': 'application/json' })
    });
  }
  
}
