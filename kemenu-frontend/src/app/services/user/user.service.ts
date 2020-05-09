import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {environment} from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  noAuthHeader = {headers: new HttpHeaders({NoAuth: 'True'})};

  constructor(private httpClient: HttpClient) {
  }

  registerCustomer(customer) {
    return this.httpClient.post(environment.apiBaseUrl + '/customers', customer);
  }

  login(email, digest) {
    return this.httpClient.post(environment.apiBaseUrl + '/login', {email, digest});
  }
}
