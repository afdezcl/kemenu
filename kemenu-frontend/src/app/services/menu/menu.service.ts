import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class MenuService {

  constructor(
    private _httpClient: HttpClient
  ) { }


  createSection(name: string){
    return this._httpClient.post(environment.apiBaseUrl + '/createSection', name);
  }

}

