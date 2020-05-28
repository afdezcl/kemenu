import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Location } from '@angular/common';
import { EditProfileService } from '@services/edit-profile/edit-profile.service';
import { AuthenticationService } from '@services/authentication/authentication.service';
import { ChangePassword } from '@models/edit-profile/changePassword.model';
import { AlertsService } from '@services/alerts/alerts.service';
import {TranslateService} from '@ngx-translate/core';

@Component({
  selector: 'app-change-password',
  templateUrl: './change-password.component.html',
  styleUrls: ['./change-password.component.css']
})
export class ChangePasswordComponent implements OnInit {

  changePassword: FormGroup;
  constructor(
    private translate: TranslateService,
    private formBuilder: FormBuilder,
    private _location: Location,
    private _editProfile: EditProfileService,
    private _auth: AuthenticationService,
    private alertService: AlertsService,
  ) { }

  ngOnInit() {
    this.changePassword = this.formBuilder.group({
      password: ['', [Validators.required, Validators.minLength(8)]],
      confirmPassword: ['']
    }, { validators: this.checkPasswords });
  }
  get form() {
    return this.changePassword.controls;
  }

  checkPasswords(form: FormGroup) {
    const password = form.controls.password.value;
    const confirmPassword = form.controls.confirmPassword.value;

    return password === confirmPassword ? null : { notSame: true };
  }

  goBack() {
    this._location.back();
  }

  onSubmit(){
    const changePassword: ChangePassword = {
      email: this._auth.getUserEmail(),
      password: this.form.password.value,
      repeatedPassword: this.form.confirmPassword.value,
    };
    this.changePasswordAttempt(changePassword);

  }

  changePasswordAttempt(changePassword: ChangePassword){
    this.alertService.clear();
    this._editProfile.changePassword(changePassword)
        .subscribe(() => {
          this.alertService.success(this.translate.instant('Success Change Password'));          
        });

    this.changePassword.reset();
  }
}
