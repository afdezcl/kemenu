import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {environment} from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class MenuService {

  constructor(
    private httpClient: HttpClient
  ) {
  }


  createMenu(menuToSave) {
    return this.httpClient.post(environment.apiBaseUrl + '/web/v1/menus', menuToSave);
  }

  updateMenu(menuToUpdate) {
    return this.httpClient.put(environment.apiBaseUrl + '/web/v1/menus', menuToUpdate);
  }

  getMenu(customerEmail: string) {
    return this.httpClient.get(environment.apiBaseUrl + `/web/v1/customer/${customerEmail}`);
  }

  getQRcode(shortUrlId) {
    return this.httpClient.get(environment.apiBaseUrl + `/web/v1/qr/${shortUrlId}`);
  }

  getMenuById(shortUrlId: string) {
    return this.httpClient.get(environment.apiBaseUrl + `/public/short/${shortUrlId}`);
  }
}

