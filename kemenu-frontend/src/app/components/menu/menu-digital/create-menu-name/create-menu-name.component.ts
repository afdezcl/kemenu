import {Component, OnInit, Output, EventEmitter} from '@angular/core';
import {Validators, FormGroup, FormBuilder} from '@angular/forms';
import {BsModalRef} from 'ngx-bootstrap/modal';

@Component({
  selector: 'app-create-menu-name',
  templateUrl: './create-menu-name.component.html',
  styleUrls: ['./create-menu-name.component.scss']
})
export class CreateMenuNameComponent implements OnInit {
  
  @Output() messageEvent = new EventEmitter<string>();
  public menuNameForm: FormGroup;
  public name: string;
  public editing: boolean;

  constructor(
    private formBuilder: FormBuilder,
    public bsModalRef: BsModalRef
  ) {
  }

  ngOnInit() {
    this.menuNameForm = this.formBuilder.group({
      name: [this.name, Validators.required]
    });
  }

  get form() {
    return this.menuNameForm.controls;
  }

  onSubmit() {
    this.messageEvent.emit(this.form.name.value);
    this.bsModalRef.hide();
  }
}
