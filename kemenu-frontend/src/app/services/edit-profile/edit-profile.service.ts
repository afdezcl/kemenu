import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {environment} from '@environments/environment';
import { ChangePassword } from '@models/edit-profile/changePassword.model';

@Injectable({
  providedIn: 'root'
})
export class EditProfileService {

  constructor(
    private httpClient: HttpClient
  ) { }

  changePassword(changePassword: ChangePassword) {
    return this.httpClient
               .patch(environment.apiBaseUrl + `/web/v1/customer/${changePassword.email}/password/change`, 
                    {password: changePassword.password, repeatedPassword: changePassword.repeatedPassword});
  }

}
