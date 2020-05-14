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


  createMenu(menuToSave) {
    return this._httpClient.post(environment.apiBaseUrl + '/web/v1/menus', menuToSave);
  }

  updateMenu(){

  }

  getMenu(customerEmail: string) {
    return this._httpClient.get(environment.apiBaseUrl + `/web/v1/menus/${customerEmail}`);
  }

}

