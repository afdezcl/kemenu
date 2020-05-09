import { Component, OnInit } from '@angular/core';
import { Validators, FormGroup, FormBuilder } from '@angular/forms';
import { Router } from '@angular/router';
import { MenuService } from '@services/menu/menu.service';
import { BsModalRef } from 'ngx-bootstrap/modal';

@Component({
  selector: 'app-create-section',
  templateUrl: './create-section.component.html',
  styleUrls: ['./create-section.component.css']
})
export class CreateSectionComponent implements OnInit {

 
  section: FormGroup;  

  constructor(
    private formBuilder: FormBuilder,
    public bsModalRef: BsModalRef,
    private _menuService: MenuService,
    private router : Router,
  ) { }

  ngOnInit() {
    this.section = this.formBuilder.group({
      name: ['', Validators.required]      
    });
  }

  get form() { return this.section.controls; }

  onSubmit() {
    this._menuService.createSection(this.form.name.value)
      .subscribe(
        result => {    
          this.router.navigateByUrl('')
      });
  }
}
