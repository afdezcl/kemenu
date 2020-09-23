import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { Validators, FormGroup, FormBuilder } from '@angular/forms';
import { BsModalRef } from 'ngx-bootstrap/modal';

@Component({
  selector: 'app-create-section',
  templateUrl: './create-section.component.html'
})
export class CreateSectionComponent implements OnInit {

  @Output() messageEvent = new EventEmitter<string>();
  public section: FormGroup;
  public name: string;
  public editing: boolean;

  constructor(
    private formBuilder: FormBuilder,
    public bsModalRef: BsModalRef
  ) {
  }

  ngOnInit() {
    this.section = this.formBuilder.group({
      name: [this.name, Validators.required]
    });
  }

  get form() {
    return this.section.controls;
  }

  onSubmit() {
    this.messageEvent.emit(this.form.name.value);
    this.bsModalRef.hide();
  }
}
