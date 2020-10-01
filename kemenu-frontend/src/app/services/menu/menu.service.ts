import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {environment} from 'src/environments/environment';
import {ShowMenu} from '@models/menu/showMenu.model';
import { Currency } from '@models/menu/currency.interface';

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
  
  deleteMenu(params) {
    return this.httpClient.delete(environment.apiBaseUrl + `/web/v1/customer/${params.email}/business/${params.businessId}/menus/${params.menuId}`);
  }

  getCustomer(customerEmail: string) {
    return this.httpClient.get(environment.apiBaseUrl + `/web/v1/customer/${customerEmail}`);
  }

  getQRcode(shortUrlId) {
    return this.httpClient.get(environment.apiBaseUrl + `/web/v1/qr/${shortUrlId}`);
  }

  getMenuById(shortUrlId: string) {
    return this.httpClient.get<ShowMenu>(environment.apiBaseUrl + `/public/short/${shortUrlId}`);
  }

  getCurrencies() {
    return this.httpClient.get<Currency[]>(environment.apiBaseUrl + `/web/v1/currencies`);
  }

}

