import {Component, OnInit, OnDestroy} from '@angular/core';
import {FormGroup, Validators, FormBuilder} from '@angular/forms';
import {AlertsService} from '@services/alerts/alerts.service';
import {Subscription} from 'rxjs';
import {ReCaptchaV3Service} from 'ng-recaptcha';
import {ForgotPassword} from '@models/auth/forgotPassword.interface';
import {AuthenticationService} from '@services/authentication/authentication.service';
import {TranslateService} from '@ngx-translate/core';
import Utils from '../../utils/utils';

@Component({
  selector: 'app-register',
  templateUrl: './forgotPassword.component.html',
  styleUrls: ['./forgotPassword.component.css'],
  providers: [AlertsService, ReCaptchaV3Service, AuthenticationService]
})

export class ForgotPasswordComponent implements OnInit, OnDestroy {
  private subscription: Subscription;
  forgotPasswordForm: FormGroup;

  constructor(
    private translate: TranslateService,
    private formBuilder: FormBuilder,
    private alertService: AlertsService,
    private authService: AuthenticationService,
    private recaptchaV3Service: ReCaptchaV3Service,
  ) {
  }

  ngOnInit() {
    this.forgotPasswordForm = this.formBuilder.group({
      email: ['', [Validators.required, Validators.email]],
    });
  }

  get form() {
    return this.forgotPasswordForm.controls;
  }

  onSubmit() {
    this.register();
  }

  private register(): void {
    this.subscription = this.recaptchaV3Service.execute('login')
      .subscribe((token) => this.registerAttempt(token));
  }

  private registerAttempt(token: string) {
    const user: ForgotPassword = {
      email: this.form.email.value,
      lang: Utils.getBrowserLang(),
      recaptchaToken: token
    };
    this.alertService.clear();
    this.authService.forgotPassword(user)
      .subscribe(() => {
          this.forgotPasswordForm.reset();
          this.alertService.success(this.translate.instant('SuccessForgotPassword'));
        },
        (error) => {
          this.alertService.success(this.translate.instant('SuccessForgotPassword'));
        }
      );

  }

  public ngOnDestroy() {
    if (this.subscription) {
      this.subscription.unsubscribe();
    }
  }

}
