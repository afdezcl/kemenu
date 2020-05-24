import {Component, OnInit, OnDestroy} from '@angular/core';
import {BsModalRef} from 'ngx-bootstrap/modal';
import {FormGroup, FormBuilder, Validators} from '@angular/forms';
import {Router} from '@angular/router';
import {AuthenticationService} from '@services/authentication/authentication.service';
import {Login} from '@models/auth/login.interface';
import {ReCaptchaV3Service} from 'ng-recaptcha';
import {Subscription} from 'rxjs';
import {AlertsService} from '@services/alerts/alerts.service';
import { TranslateService } from '@ngx-translate/core';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
  providers: [AuthenticationService, ReCaptchaV3Service]
})
export class LoginComponent implements OnInit, OnDestroy {

  private subscription: Subscription;
  loginForm: FormGroup;

  constructor(
    private formBuilder: FormBuilder,
    private translate: TranslateService,
    public bsModalRef: BsModalRef,
    private authService: AuthenticationService,
    private router: Router,
    private recaptchaV3Service: ReCaptchaV3Service,
    private alertService: AlertsService
  ) {
  }

  ngOnInit() {
    this.loginForm = this.formBuilder.group({
      email: ['', Validators.required],
      password: ['', Validators.required]
    });
  }

  get form() {
    return this.loginForm.controls;
  }

  public onSubmit() {
    this.login();
  }

  private login(): void {
    this.subscription = this.recaptchaV3Service.execute('login')
      .subscribe((token) => this.loginAttempt(token));
  }

  private loginAttempt(token: string) {
    const user: Login = {
      email: this.form.email.value,
      password: this.form.password.value,
      recaptchaToken: token
    };
    this.authService.login(user)
      .subscribe(result => {
          this.bsModalRef.hide();
          this.router.navigateByUrl('menu');
        },
        err => {
          this.alertService.error(this.translate.instant('Email wrong'));
        }
      );
  }


  public ngOnDestroy() {
    if (this.subscription) {
      this.subscription.unsubscribe();
    }
  }
}
