import { Injectable } from '@angular/core';
import { HttpHeaders, HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  noAuthHeader = { headers: new HttpHeaders({ 'NoAuth': 'True' }) };

  constructor(private _http: HttpClient) { }


  registerBand(user){
    return this._http.post(environment.apiBaseUrl + '/register', user);
  }

  login(email, digest) {    
    return this._http.post(environment.apiBaseUrl + '/login', {email, digest});
  }

  
}
