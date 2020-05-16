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

  updateMenu(menuToUpdate){
    return this._httpClient.put(environment.apiBaseUrl + '/web/v1/menus', menuToUpdate);
  }

  getMenu(customerEmail: string) {
    return this._httpClient.get(environment.apiBaseUrl + `/web/v1/customer/${customerEmail}`);
  }

  getQRcode(params){
    return this._httpClient.get(environment.apiBaseUrl + `/web/v1/qr/customers/${params.customerId}/businesses/${params.businessId}/menus/${params.menuId}`);
  }
}

