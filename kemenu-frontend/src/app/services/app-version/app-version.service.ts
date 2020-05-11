import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AppVersionService {

  private noAuthHeader = { headers: new HttpHeaders({ NoAuth: 'True' }) };

  constructor(
    private _httpClient: HttpClient
  ) {}

  getVersionApp() {
    return this._httpClient.get(environment.apiBaseUrl + '/app/version', {});
  }


}
