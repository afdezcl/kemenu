import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { Validators, FormGroup, FormBuilder } from '@angular/forms';
import { BsModalRef } from 'ngx-bootstrap/modal';
import { Dish } from '@models/menu/dish.model';

@Component({
  selector: 'app-create-dish',
  templateUrl: './create-dish.component.html',
  styleUrls: ['./create-dish.component.css']
})
export class CreateDishComponent implements OnInit {

  @Output() messageEvent = new EventEmitter<Dish>();
  dishForm: FormGroup;

  constructor(
    private formBuilder: FormBuilder,
    public bsModalRef: BsModalRef
  ) { }

  ngOnInit() {
    this.dishForm = this.formBuilder.group({
      name: ['', Validators.required],
      price: ['', Validators.required]      
    });
  }

  get form() { return this.dishForm.controls; }

  onSubmit() {
    const dish = new Dish (
      this.form.name.value,
      '',
      this.form.price.value,
      []
    )
    this.messageEvent.emit(dish)
    this.bsModalRef.hide()
  }

}
