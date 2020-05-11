import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { Validators, FormGroup, FormBuilder } from '@angular/forms';
import { BsModalRef } from 'ngx-bootstrap/modal';

@Component({
  selector: 'app-create-dish',
  templateUrl: './create-dish.component.html',
  styleUrls: ['./create-dish.component.css']
})
export class CreateDishComponent implements OnInit {

  @Output() messageEvent = new EventEmitter<string>();
  dishForm: FormGroup;

  constructor(
    private formBuilder: FormBuilder,
    public bsModalRef: BsModalRef
  ) { }

  ngOnInit() {
    this.dishForm = this.formBuilder.group({
      name: ['', Validators.required]      
    });
  }

  get form() { return this.dishForm.controls; }

  onSubmit() {
    this.messageEvent.emit(this.form.name.value)
    this.bsModalRef.hide()
  }

}
