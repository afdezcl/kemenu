import { Component, OnInit } from '@angular/core';
import { Validators, FormGroup, FormBuilder } from '@angular/forms';
import { Router } from '@angular/router';
import { UserService } from '@services/user/user.service';
import { BsModalRef } from 'ngx-bootstrap/modal';

@Component({
  selector: 'app-create-section',
  templateUrl: './create-section.component.html',
  styleUrls: ['./create-section.component.css']
})
export class CreateSectionComponent implements OnInit {

 
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
