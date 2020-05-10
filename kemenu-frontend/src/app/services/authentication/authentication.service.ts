import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { tap, mapTo, catchError } from 'rxjs/operators';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { environment } from '@environments/environment';
import { Tokens } from '@models/auth/tokens.model';
import { Login } from '@models/auth/login.interface';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  private readonly JWT_TOKEN = 'JWT_TOKEN';
  private readonly REFRESH_TOKEN = 'REFRESH_TOKEN';
  private noAuthHeader = { headers: new HttpHeaders({ NoAuth: 'True' }) };
  private loggedUser: string;

  constructor(
    private _httpClient: HttpClient
  ) { }

  login(user: Login): Observable<boolean> {
    return this._httpClient.post<any>(environment.apiBaseUrl + '/login', user, this.noAuthHeader)
      .pipe(
        tap(tokens => this.doLoginUser(user.email, tokens)),
        mapTo(true),
        catchError(error => {
          alert(error.error);
          return of(false);
        }));
  }

  logout() {
    this.loggedUser = null;
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

  private doLoginUser(username: string, tokens: Tokens) {
    this.loggedUser = username;
    this.storeTokens(tokens);
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
