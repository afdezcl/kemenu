import {Component, OnInit, OnDestroy} from '@angular/core';
import {FormGroup, Validators, FormBuilder} from '@angular/forms';
import {AlertsService} from '@services/alerts/alerts.service';
import {Subscription} from 'rxjs';
import {ReCaptchaV3Service} from 'ng-recaptcha';
import {Register} from '@models/auth/register.interface';
import {AuthenticationService} from '@services/authentication/authentication.service';
import {TranslateService} from '@ngx-translate/core';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css'],
  providers: [AlertsService, ReCaptchaV3Service, AuthenticationService]
})

export class RegisterComponent implements OnInit, OnDestroy {
  private subscription: Subscription;
  registerForm: FormGroup;

  constructor(
    private translate: TranslateService,
    private formBuilder: FormBuilder,
    private alertService: AlertsService,
    private authService: AuthenticationService,
    private recaptchaV3Service: ReCaptchaV3Service,
  ) {
  }

  ngOnInit() {
    this.registerForm = this.formBuilder.group({
      businessName: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(8)]],
      confirmPassword: ['']
    }, {validators: this.checkPasswords});
  }

  get form() {
    return this.registerForm.controls;
  }

  onSubmit() {
    this.register();
  }

  private register(): void {
    this.subscription = this.recaptchaV3Service.execute('login')
      .subscribe((token) => this.registerAttempt(token));
  }

  private registerAttempt(token: string) {
    const user: Register = {
      businessName: this.form.businessName.value,
      email: this.form.email.value,
      password: this.form.password.value,
      recaptchaToken: token,
      lang: this.getBrowserLang()
    };
    this.alertService.clear();
    this.authService.register(user)
      .subscribe(() => {
          this.registerForm.reset();
          this.alertService.success(this.translate.instant('Success Register'));
        },
        (error) => {
          this.alertService.error(this.translate.instant('Error Register'));
        }
      );

  }

  checkPasswords(form: FormGroup) {
    const password = form.controls.password.value;
    const confirmPassword = form.controls.confirmPassword.value;

    return password === confirmPassword ? null : {notSame: true};
  }

  public ngOnDestroy() {
    if (this.subscription) {
      this.subscription.unsubscribe();
    }
  }

  private getBrowserLang(): string {
    if (window.navigator.language.includes('es')) {
      return 'es';
    } else {
      return 'en';
    }
  }
}
