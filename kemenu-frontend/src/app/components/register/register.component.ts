import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { UserService } from 'src/app/services/user/user.service';
import { Router } from '@angular/router';
import { AlertsService } from 'src/app/services/alerts/alerts.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css'],
  providers: [AlertsService, UserService]
})

export class RegisterComponent implements OnInit {

  registerForm: FormGroup

  constructor(
    private _alertService: AlertsService,    
    private _userService: UserService,
    private router: Router
  ) { }

  ngOnInit() {
    this.registerForm = new FormGroup({
      businessName: new FormControl('', [Validators.required]),
      email: new FormControl('', [Validators.required, Validators.email]),
      password: new FormControl('', [Validators.required, Validators.minLength(8)]),
      confirmPassword: new FormControl('')
    }, { validators: this.checkPasswords });
  }

  onSubmit(registerForm: FormGroup) {
    let newBusiness = this.buildFormWithoutConfirmPassword(registerForm)
    console.log(newBusiness.value);
    this._alertService.clear();
    this._userService.register(newBusiness.value)
      .subscribe(result => {
        console.log(result);
        this.registerForm.reset();
        this._alertService.success('Registrado con éxito, puedes iniciar sesión');
      },
        err => {
          console.log('Usuario ya registrado')
          this._alertService.error('Error al registrar, usuario ya registrado');
        }
      )
  }

  buildFormWithoutConfirmPassword(form: FormGroup): FormGroup {
    let newBusiness = new FormGroup({
      businessName: form.controls.businessName,
      email: form.controls.email,
      password: form.controls.password
    })
    return newBusiness;
  }

  checkPasswords(form: FormGroup) {
    const password = form.controls.password.value;
    const confirmPassword = form.controls.confirmPassword.value;

    return password === confirmPassword ? null : { notSame: true };
  }
}
