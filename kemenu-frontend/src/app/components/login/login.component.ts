import { Component, OnInit } from '@angular/core';
import { BsModalRef } from 'ngx-bootstrap/modal';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { UserService } from 'src/app/services/user/user.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
  providers: [UserService]
})
export class LoginComponent implements OnInit {

  login: FormGroup;  

  constructor(
    private formBuilder: FormBuilder,
    public bsModalRef: BsModalRef,
    private _userService: UserService,
    private router : Router,
  ) { }

  ngOnInit() {
    this.login = this.formBuilder.group({
      email: ['', Validators.required],
      digest: ['', Validators.required]
    });
  }

  get form() { return this.login.controls; }

  onSubmit() {
    
    this._userService.login(this.form.email.value, this.form.digest.value)
      .subscribe(
        result => { 
          this.bsModalRef.hide();            
          this.router.navigateByUrl('')
      });
  }
}
