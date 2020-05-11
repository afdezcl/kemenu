import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { tap, map, catchError } from 'rxjs/operators';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { environment } from '@environments/environment';
import { Tokens } from '@models/auth/tokens.model';
import { Login } from '@models/auth/login.interface';
import { Register } from '@models/auth/register.interface';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  private readonly JWT_TOKEN = 'JWT_TOKEN';
  private readonly REFRESH_TOKEN = 'REFRESH_TOKEN';

  constructor(
    private _httpClient: HttpClient
  ) { }

  register(user: Register) {
    console.log(user)
    return this._httpClient
      .post(environment.apiBaseUrl + '/register', user)
  }

  login(user: Login){
    return this._httpClient
      .post(environment.apiBaseUrl + '/login', user, {observe: 'response'})
      .pipe(map(response => {
        console.log("RESPONSE: ");
        console.log(response.headers.get('Authorization'));
        //console.log(response.headers.get('JWT-Refresh-Token: Bearer'))
        const tokens: Tokens = {
          jwt: response.headers.get('Authorization'),
          refreshToken: response.headers.get('JWT-Refresh-Token')
        }
        this.storeTokens(tokens)
      }));
  }

  logout() {
    this.removeTokens();
  }

  isLoggedIn() {
    return !!this.getJwtToken();
  }

  refreshToken() {
    return this._httpClient.post<any>(environment.apiBaseUrl + '/refresh', {
      'refreshToken': this.getRefreshToken()
    }).pipe(tap((tokens: Tokens) => {
      this.storeJwtToken(tokens.jwt);
    }));
  }

  getJwtToken() {
    return localStorage.getItem(this.JWT_TOKEN);
  }

  private getRefreshToken() {
    return localStorage.getItem(this.REFRESH_TOKEN);
  }

  private storeJwtToken(jwt: string) {
    localStorage.setItem(this.JWT_TOKEN, jwt);
  }

  private storeTokens(tokens: Tokens) {
    localStorage.setItem(this.JWT_TOKEN, tokens.jwt);
    localStorage.setItem(this.REFRESH_TOKEN, tokens.refreshToken);
  }

  private removeTokens() {
    localStorage.removeItem(this.JWT_TOKEN);
    localStorage.removeItem(this.REFRESH_TOKEN);
  }

}
