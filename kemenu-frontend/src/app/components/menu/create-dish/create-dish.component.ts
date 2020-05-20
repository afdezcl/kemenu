import {Component, OnInit, Output, EventEmitter} from '@angular/core';
import {Validators, FormGroup, FormBuilder} from '@angular/forms';
import {BsModalRef} from 'ngx-bootstrap/modal';
import {Dish} from '@models/menu/dish.model';
import { Allergen, AllAllergens } from '@models/menu/allergen.model';

@Component({
  selector: 'app-create-dish',
  templateUrl: './create-dish.component.html',
  styleUrls: ['./create-dish.component.css']
})
export class CreateDishComponent implements OnInit {

  @Output() messageEvent = new EventEmitter<Dish>();
  public dishForm: FormGroup;
  public name: string;
  public description: string;
  public price: number;
  public allergens: Allergen[] = AllAllergens;
  public allergensListToShowOnLeft: Allergen[];
  public allergensListToShowOnRight: Allergen[];

  constructor(
    private formBuilder: FormBuilder,
    public bsModalRef: BsModalRef
  ) {
  }

  ngOnInit() {
    this.dishForm = this.formBuilder.group({
      name: [this.name, Validators.required],
      description: [this.description],
      price: [this.price, [Validators.required, Validators.min(0)]]
    });
    
    this.divideAllergensList();

  }

  get form() {
    return this.dishForm.controls;
  }

  private divideAllergensList(){
    let half_length = Math.ceil(this.allergens.length / 2);
    this.allergensListToShowOnLeft = this.allergens.slice(0, half_length);
    this.allergensListToShowOnRight = this.allergens.slice(half_length, this.allergens.length)    
  }

  onSubmit() {
    const dish = new Dish(
      this.form.name.value,
      this.form.description.value,
      this.form.price.value,
      []
    );
    this.messageEvent.emit(dish);
    this.bsModalRef.hide();
  }
}
