import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from '@angular/forms';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  registerForm: FormGroup;

  constructor() {
  }

  ngOnInit() {
    this.registerForm = new FormGroup({
      restaurantName: new FormControl('', [Validators.required]),
      email: new FormControl('', [Validators.required, Validators.email]),
      digest: new FormControl('', [Validators.required, Validators.minLength(8)]),
      confirmDigest: new FormControl('')
    }, {validators: this.checkPasswords});
  }

  onSubmit(registerForm: FormGroup) {
    console.log(registerForm.value);
  }

  checkPasswords(form: FormGroup) {
    const pass = form.controls.digest.value;
    const confirmPass = form.controls.confirmDigest.value;

    return pass === confirmPass ? null : {notSame: true};
  }
}
