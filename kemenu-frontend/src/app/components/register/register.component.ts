import { Component, OnInit, OnDestroy } from '@angular/core';
import { FormGroup, Validators, FormBuilder } from '@angular/forms';
import { AlertsService } from '@services/alerts/alerts.service';
import { Subscription } from 'rxjs';
import { ReCaptchaV3Service } from 'ng-recaptcha';
import { Register } from '@models/auth/register.interface';
import { AuthenticationService } from '@services/authentication/authentication.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css'],
  providers: [AlertsService, ReCaptchaV3Service, AuthenticationService]
})
 
export class RegisterComponent implements OnInit, OnDestroy {
  private subscription: Subscription;
  registerForm: FormGroup

  constructor(
    private formBuilder: FormBuilder,
    private _alertService: AlertsService,
    private _authService: AuthenticationService,
    private recaptchaV3Service: ReCaptchaV3Service,    
  ) { }

  ngOnInit() {
    this.registerForm = this.formBuilder.group({
      businessName: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(8)]],
      confirmPassword: ['']
    }, { validators: this.checkPasswords });
  }

  get form() { return this.registerForm.controls; }

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
      recaptchaToken: token
    }
    this._alertService.clear();
    this._authService.register(user)
      .subscribe(result => {
        console.log(result);
        this.registerForm.reset();
        this._alertService.success('Registrado con éxito, puedes iniciar sesión');
      },
        (error) => {
          this._alertService.error('Error al registrar, este usuario ya existe');
        }
      );
      
  }

  checkPasswords(form: FormGroup) {
    const password = form.controls.password.value;
    const confirmPassword = form.controls.confirmPassword.value;

    return password === confirmPassword ? null : { notSame: true };
  }

  public ngOnDestroy() {
    if (this.subscription) {
      this.subscription.unsubscribe();
    }
  }
}
