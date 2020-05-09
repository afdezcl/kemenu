import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private noAuthHeader = { headers: new HttpHeaders({ NoAuth: 'True' }) };

  constructor(private httpClient: HttpClient) {
  }

  register(business) {
    return this.httpClient.post(environment.apiBaseUrl + '/customers', business);
  }

  login(email: string, digest: string) {
    return this.httpClient.post(environment.apiBaseUrl + '/login', { email, digest });
  }
}
