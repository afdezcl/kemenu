import {Component, OnInit, Output, EventEmitter} from '@angular/core';
import {Validators, FormGroup, FormBuilder} from '@angular/forms';
import {BsModalRef} from 'ngx-bootstrap/modal';
import {Dish} from '@models/menu/dish.model';
import {Allergen, AllAllergens, AllergenRequestResponse} from '@models/menu/allergen.model';

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
  public selectedAllergens: Allergen[] = [];
  public allergens: Allergen[] = AllAllergens;
  public allergensListToShowOnLeft: Allergen[];
  public allergensListToShowOnRight: Allergen[];
  public showAllergens = false;

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
    console.log(this.selectedAllergens);
    this.divideAllergensList();

  }

  get form() {
    return this.dishForm.controls;
  }

  private divideAllergensList() {
    const halfLength = Math.ceil(this.allergens.length / 2);
    this.allergensListToShowOnLeft = this.allergens.slice(0, halfLength);
    this.allergensListToShowOnRight = this.allergens.slice(halfLength, this.allergens.length);
  }

  onSubmit() {
    const dish = new Dish(
      this.form.name.value,
      this.form.description.value,
      this.form.price.value,
      this.selectedAllergens
    );
    this.messageEvent.emit(dish);
    this.bsModalRef.hide();
  }

  activeShowAllergens() {
    this.showAllergens = !this.showAllergens;
  }

  changeAllergens(idAllergen: string) {
    if (this.selectedAllergens.find(item => item.id === idAllergen)) {
      this.selectedAllergens = this.selectedAllergens.filter(item => item.id !== idAllergen);
    } else {
      const allergen: Allergen = {
        id: idAllergen,
        name: this.allergens.find(item => item.id === idAllergen).name,
        imageName: this.allergens.find(item => item.id === idAllergen).imageName
      };
      this.selectedAllergens.push(allergen);
    }
  }

  allergenIsChecked(allergen: AllergenRequestResponse) {
    return this.selectedAllergens.find(item => Object.is(item.id, allergen.id));
  }
}
