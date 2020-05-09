import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { UserService } from 'src/app/services/user/user.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  registerForm: FormGroup;

  constructor(
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
    console.log(registerForm.value);
    this._userService.register(registerForm.value)
      .subscribe(result => {
        console.log(result);
      })
  }

  checkPasswords(form: FormGroup) {
    const pass = form.controls.password.value;
    const confirmPass = form.controls.confirmPassword.value;

    return pass === confirmPass ? null : { notSame: true };
  }
}
