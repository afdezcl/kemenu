import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { AlertsService } from '@services/alerts/alerts.service';
import { Location } from '@angular/common';
import { ResetPassword } from '@models/auth/resetPassword.interface';
import { AuthenticationService } from '@services/authentication/authentication.service';
import { Subscription } from 'rxjs';
import { ReCaptchaV3Service } from 'ng-recaptcha';
import { Login } from '@models/auth/login.interface';
import { TranslateService } from '@ngx-translate/core';

@Component({
  selector: 'app-reset-password',
  templateUrl: './resetPassword.component.html',
  styleUrls: ['./resetPassword.component.css'],
  providers: [AlertsService, AuthenticationService, ReCaptchaV3Service]
})
export class ResetPasswordComponent implements OnInit {

  private subscription: Subscription;
  resetPasswordForm: FormGroup;

  constructor(
    private formBuilder: FormBuilder,
    private alertService: AlertsService,
    private location: Location,
    private authService: AuthenticationService,
    private translate: TranslateService,
    private recaptchaV3Service: ReCaptchaV3Service,
  ) { }

  ngOnInit() {
    this.resetPasswordForm = this.formBuilder.group({
      oldPassword: ['', [Validators.required, Validators.minLength(8)]],
      password: ['', [Validators.required, Validators.minLength(8)]],
      repeatedPassword: ['']
    }, { validators: this.checkPasswords });
  }


  get form() {
    return this.resetPasswordForm.controls;
  }


  onSubmit() {
    this.resetPassword();
  }

  private resetPassword(): void {
    this.subscription = this.recaptchaV3Service.execute('login')
      .subscribe((token) => this.resetPasswordAttempt(token));

  }

  private resetPasswordAttempt(token: string) {
    const resetPassword: ResetPassword = {
      password: this.form.password.value,
      repeatedPassword: this.form.repeatedPassword.value,
    };
    const user: Login = {
      email: this.authService.getUserEmail(),
      password: this.form.oldPassword.value,
      recaptchaToken: token
    };
    this.authService.login(user)
      .subscribe(result => {
        this.authService.resetPassword(resetPassword)
          .subscribe(() => {
            this.alertService.success(this.translate.instant('Saved Correctly'));
            this.resetPasswordForm.reset();
          });
      },
        err => {
          this.alertService.error(this.translate.instant('Incorrect Password'));
        }
      );

  }

  checkPasswords(form: FormGroup) {
    const password = form.controls.password.value;
    const repeatedPassword = form.controls.repeatedPassword.value;

    return password === repeatedPassword ? null : { notSame: true };
  }

  goBack() {
    this.location.back();
  }

}
