import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {environment} from '@environments/environment';
import { ChangePassword } from '@models/edit-profile/changePassword.model';
import { UpdateBusiness } from '@models/edit-profile/updateBusiness.model';
import { AuthenticationService } from '@services/authentication/authentication.service';

@Injectable({
  providedIn: 'root'
})
export class EditProfileService {

  constructor(
    private httpClient: HttpClient,
    private _auth: AuthenticationService
  ) { }

  changePassword(changePassword: ChangePassword) {
    return this.httpClient
               .patch(environment.apiBaseUrl + `/web/v1/customer/${changePassword.email}/password/change`, 
                    {password: changePassword.password, repeatedPassword: changePassword.repeatedPassword});
  }

  updateBusiness(updateBusiness: UpdateBusiness, businessId: string) {
    const email = this._auth.getUserEmail();
    return this.httpClient
               .put(environment.apiBaseUrl + `/web/v1/customer/${email}/business/${businessId}`, updateBusiness);
  }
}
