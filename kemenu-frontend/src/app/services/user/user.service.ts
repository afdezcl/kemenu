import { Injectable } from '@angular/core'
import { HttpClient, HttpHeaders } from '@angular/common/http'
import { environment } from 'src/environments/environment'
import { RegisterBusiness } from '@models/auth/register.interface'

@Injectable({
  providedIn: 'root'
})

export class UserService {

  private noAuthHeader = { headers: new HttpHeaders({ NoAuth: 'True' }) };

  constructor(
    private _httpClient: HttpClient
  ) {}

  register(business: RegisterBusiness) {
    console.log(business)
    return this._httpClient
      .post(environment.apiBaseUrl + '/register', business, this.noAuthHeader)

  }

  login(email: string, digest: string) {
    return this._httpClient
      .post(environment.apiBaseUrl + '/login', { email, digest }, this.noAuthHeader);
  }
}
