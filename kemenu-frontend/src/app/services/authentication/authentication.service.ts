import {Injectable} from '@angular/core';
import {tap, map} from 'rxjs/operators';
import {HttpClient} from '@angular/common/http';
import {environment} from '@environments/environment';
import {Tokens} from '@models/auth/tokens.model';
import {Login} from '@models/auth/login.interface';
import {Register} from '@models/auth/register.interface';
import * as jwt_decode from 'jwt-decode';
import {ForgotPassword} from '@models/auth/forgotPassword.interface';
import {ChangePassword, ForgotPasswordId} from '@models/auth/changePassword.interface';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  private readonly JWT_TOKEN = 'JWT_TOKEN';
  private readonly REFRESH_TOKEN = 'REFRESH_TOKEN';
  private readonly USER_EMAIL = 'USER_EMAIL';
  private readonly USER_ROLE = 'USER_ROLE';

  constructor(
    private httpClient: HttpClient
  ) {
  }

  register(user: Register) {
    return this.httpClient
      .post(environment.apiBaseUrl + '/register', user);
  }

  login(user: Login) {
    return this.httpClient
      .post(environment.apiBaseUrl + '/login', user, {observe: 'response'})
      .pipe(map(response => {
        const tokens: Tokens = {
          jwt: response.headers.get('Authorization'),
          refreshToken: response.headers.get('JWT-Refresh-Token')
        };
        this.storeTokens(tokens);
      }));
  }

  forgotPassword(data: ForgotPassword) {
    return this.httpClient
      .post(environment.apiBaseUrl + '/public/forgot/password', data);
  }

  changePassword(forgotPasswordId: ForgotPasswordId, data: ChangePassword) {
    return this.httpClient
      .patch(environment.apiBaseUrl + '/public/forgot/password/' + forgotPasswordId.id + '/email/' + forgotPasswordId.email, data);
  }

  logout() {
    localStorage.clear();
  }

  isLoggedIn() {
    return !!this.getJwtToken();
  }

  refreshToken() {
    return this.httpClient.post<any>(environment.apiBaseUrl + '/refresh', {
      refreshToken: this.getRefreshToken()
    }, {observe: 'response'})
      .pipe(tap(response => {
        const tokens: Tokens = {
          jwt: response.headers.get('Authorization'),
          refreshToken: response.headers.get('JWT-Refresh-Token')
        };
        this.storeTokens(tokens);
      }));
  }

  getJwtToken() {
    return localStorage.getItem(this.JWT_TOKEN);
  }

  getUserEmail() {
    return localStorage.getItem(this.USER_EMAIL);
  }

  getUserRole() {
    return localStorage.getItem(this.USER_ROLE);
  }

  private getRefreshToken() {
    return localStorage.getItem(this.REFRESH_TOKEN);
  }

  private storeJwtToken(jwt: string) {
    localStorage.setItem(this.JWT_TOKEN, jwt);
  }

  private storeTokens(tokens: Tokens) {
    const jwtDecoded = jwt_decode(tokens.jwt);
    this.storeUserEmail(jwtDecoded.sub);
    this.storeUserRole(jwtDecoded.role[0]);
    localStorage.setItem(this.JWT_TOKEN, tokens.jwt);
    localStorage.setItem(this.REFRESH_TOKEN, tokens.refreshToken);
  }

  private removeTokens() {
    localStorage.removeItem(this.JWT_TOKEN);
    localStorage.removeItem(this.REFRESH_TOKEN);
  }

  private storeUserEmail(email: string) {
    localStorage.setItem(this.USER_EMAIL, email);
  }

  private storeUserRole(role: string) {
    localStorage.setItem(this.USER_ROLE, role);
  }

  refreshTokenHasExpirated(tokens: Tokens): boolean {
    const jwtDecoded = jwt_decode(tokens.refreshToken);
    const experationDate = new Date(jwtDecoded.exp * 1000);
    const now = new Date();
    return experationDate < now;
  }

  getRefreshCookie(shortUrlId: string) {
    return this.httpClient.get(environment.apiBaseUrl + '/show/' + shortUrlId);
  }
}
