import { Injectable } from '@angular/core';
import { environment } from './environment/environment';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Configuration } from './configuration';

@Injectable({
  providedIn: 'root'
})
export class ConfigurationService {

  private apiServerUrl = environment.apiBaseUrl;

  constructor(private http: HttpClient) { }

  public addConfiguration(configuration: Configuration): Observable<Configuration> {
    return this.http.post<Configuration>(`${this.apiServerUrl}/api/configuration/save`, configuration);
  }
}
