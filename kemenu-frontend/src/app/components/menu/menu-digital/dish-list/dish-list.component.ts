import {Component, EventEmitter, Input, OnInit, Output, ViewChild} from '@angular/core';
import {Menu} from '@models/menu/menu.model';
import {Dish, SectionDish} from '@models/menu/dish.model';
import {Section, SectionIndex} from '@models/menu/section.model';
import {Allergen, AllAllergens} from '@models/menu/allergen.model';
import {moveItemInArray} from '@angular/cdk/drag-drop';

@Component({
  selector: 'app-dish-list',
  templateUrl: './dish-list.component.html',
  styleUrls: ['./dish-list.component.scss']
})
export class DishListComponent implements OnInit {

  @Input() public dishes: Dish[];
  @Input() editMode: boolean;
  @Input() sortMode: boolean;
  @Input() sectionCounter: number;
  @Output() editClicked: EventEmitter<SectionDish> = new EventEmitter<SectionDish>();
  @Output() deleteClicked: EventEmitter<SectionDish> = new EventEmitter<SectionDish>();
  @Output() changeDishOrder: EventEmitter<SectionIndex> = new EventEmitter<SectionIndex>();

  public menu: Menu;
  public menuId: string;
  public allergens: Allergen[] = AllAllergens;

  constructor() {}

  ngOnInit() {
    this.editMode = !!this.editMode;
  }

  moveSection(from, to) {
    if (to >= 0 && to <= this.dishes.length) {
      moveItemInArray(this.dishes, from, to);
    }
    this.changeOrder();
  }

  changeOrder() {
    this.changeDishOrder.emit(new SectionIndex(new Section('', this.dishes), this.sectionCounter));
  }

  deleteDish(dishToRemove: Dish, sectionIndex: number, dishIndex: number) {
    this.deleteClicked.emit(new SectionDish(dishToRemove, sectionIndex, dishIndex));
  }

  editDish(dishToEdit: Dish, sectionIndex: number, dishIndex: number) {
    this.editClicked.emit(new SectionDish(dishToEdit, sectionIndex, dishIndex));
  }
}
