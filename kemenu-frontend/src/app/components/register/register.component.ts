import { Component, OnInit } from '@angular/core';
import { FormGroup, Validators, FormControl } from '@angular/forms';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {
  
  registerForm: FormGroup;
  
  constructor() { }

  ngOnInit() {
    this.registerForm = new FormGroup({
      restaurantName: new FormControl('', [Validators.required]),
      email: new FormControl('', [Validators.required, Validators.email]),
      digest: new FormControl('', [Validators.required, Validators.minLength(8)]),
      confirmDigest: new FormControl('')
    }, { validators: this.checkPasswords });
  }
  onSubmit(registerForm: FormGroup){
    console.log(registerForm.value);   
  }
  checkPasswords(form: FormGroup) {
    let pass = form.controls.digest.value;
    let confirmPass = form.controls.confirmDigest.value;

    return pass === confirmPass ? null : { notSame: true }
  }
}
