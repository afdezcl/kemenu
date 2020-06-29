import {Component, Input, OnInit, ViewChild} from '@angular/core';
import {Menu} from '@models/menu/menu.model';
import {BsModalService} from 'ngx-bootstrap/modal';
import {Dish} from '@models/menu/dish.model';
import {TranslateService} from '@ngx-translate/core';
import {Allergen, AllAllergens} from '@models/menu/allergen.model';
import {CdkDragDrop, moveItemInArray} from '@angular/cdk/drag-drop';

@Component({
  selector: 'app-dish-list',
  templateUrl: './dish-list.component.html',
  styleUrls: ['./dish-list.component.scss']
})
export class DishListComponent implements OnInit {

  @Input() public dishes: Dish[];
  @Input() editMode: boolean;
  @Input() sectionCounter: number;
  public menu: Menu;
  public menuId: string;
  public allergens: Allergen[] = AllAllergens;

  constructor(
    private modalService: BsModalService,
    private translate: TranslateService
  ) {
  }

  ngOnInit() {
    this.editMode = !!this.editMode;
  }

  dropDish(event: CdkDragDrop<string[]>) {
    moveItemInArray(this.dishes, event.previousIndex, event.currentIndex);
  }

  getSectionIcon(sectionName: string) {
    const icon = this.translate.instant(sectionName.substr(0, 3).toUpperCase());
    return icon ? icon : 'spoon-and-fork';
  }

  openCreateDish(sectionIndex: number) {

  }

  private addNewDish(dish: Dish, sectionIndex: number) {

  }

  deleteDish(dishToRemove: Dish, sectionIndex: number) {

  }

  editDish(dishToEdit: Dish, sectionIndex: number, dishIndex: number) {

  }
}
