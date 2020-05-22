import {Component, OnInit} from '@angular/core';
import {Menu} from '@models/menu/menu.model';
import {BsModalRef, BsModalService} from 'ngx-bootstrap/modal';
import {CreateSectionComponent} from './create-section/create-section.component';
import {Section} from '@models/menu/section.model';
import {CreateDishComponent} from './create-dish/create-dish.component';
import {Dish} from '@models/menu/dish.model';
import {ConfirmDialogComponent} from '@ui-controls/dialogs/confirmDialog/confirmDialog.component';
import {TranslateService} from '@ngx-translate/core';
import {ShareQrComponent} from './share-qr/share-qr.component';
import {MenuService} from '@services/menu/menu.service';
import {AuthenticationService} from '@services/authentication/authentication.service';
import {Allergen, AllAllergens, AllergenRequestResponse} from '@models/menu/allergen.model';

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.css']
})
export class MenuComponent implements OnInit {

  public menu: Menu;
  public modalReference: BsModalRef;
  public businessId: string;
  public customerId: string;
  public thereIsChange = false;
  public menuId: string;
  public allergens: Allergen[] = AllAllergens;

  constructor(
    private modalService: BsModalService,
    private translate: TranslateService,
    private menuService: MenuService,
    private authService: AuthenticationService
  ) {
  }

  ngOnInit() {
    this.loadMenu();
  }

  loadMenu() {
    const customerEmail = this.authService.getUserEmail();
    this.menu = new Menu(
      customerEmail,
      []
    );
    this.menuService.getMenu(customerEmail)
      .subscribe((response: any) => {
        this.customerId = response.id;
        this.businessId = response.businesses[0].id;
        if (response.businesses[0].menus.length !== 0) {
          this.menu.sections = response.businesses[0].menus[0].sections;
          this.menu.shortUrlId = response.businesses[0].menus[0].shortUrlId;
          this.menu.id = response.businesses[0].menus[0].id;
          this.matchAllergens();
        }
      });
  }

  openCreateSection() {
    this.modalReference = this.modalService.show(CreateSectionComponent);
    this.modalReference.content.messageEvent.subscribe(name => {
      this.addNewSection(name);
    });
  }

  private addNewSection(name: string) {
    const section = new Section(
      name,
      []
    );
    this.menu.sections.push(section);
    this.thereIsChange = true;
  }

  deleteSection(sectionToRemove: Section) {
    const initialState = {
      title: this.translate.instant('Delete Section title'),
      message: this.translate.instant('Delete Section description')
    };

    this.modalReference = this.modalService.show(ConfirmDialogComponent, {initialState});
    this.modalReference.content.onClose.subscribe((canDelete: boolean) => {
      if (canDelete) {
        this.menu.sections = this.menu.sections.filter(section => section !== sectionToRemove);
      }
      this.thereIsChange = true;
    });
  }

  editSection(sectionToEdit: Section, sectionIndex: number) {
    const initialState = {
      name: sectionToEdit.name
    };
    this.modalReference = this.modalService.show(CreateSectionComponent, {initialState});
    this.modalReference.content.messageEvent.subscribe(data => {
      this.menu.sections[sectionIndex].name = data;
      this.thereIsChange = true;
    });
  }

  openCreateDish(sectionIndex: number) {
    this.modalReference = this.modalService.show(CreateDishComponent);
    this.modalReference.content.messageEvent.subscribe(dish => {
      this.addNewDish(dish, sectionIndex);
      this.matchAllergens();
    });
  }

  private addNewDish(dish: Dish, sectionIndex: number) {
    this.menu.sections[sectionIndex].dishes.push(dish);
    this.thereIsChange = true;
  }

  deleteDish(dishToRemove: Dish, sectionIndex: number) {
    const initialState = {
      title: this.translate.instant('Delete Dish title'),
      message: this.translate.instant('Delete Dish description'),
    };

    this.modalReference = this.modalService.show(ConfirmDialogComponent, {initialState});
    this.modalReference.content.onClose.subscribe((canDelete: boolean) => {
      if (canDelete) {
        this.menu.sections[sectionIndex].dishes =
          this.menu.sections[sectionIndex].dishes.filter(dish => dish !== dishToRemove);
      }
      this.thereIsChange = true;
    });
  }

  editDish(dishToEdit: Dish, sectionIndex: number, dishIndex: number) {
    const initialState = {
      name: dishToEdit.name,
      description: dishToEdit.description,
      price: dishToEdit.price,
      selectedAllergens: dishToEdit.allergens
    };
    this.modalReference = this.modalService.show(CreateDishComponent, {initialState});
    this.modalReference.content.messageEvent.subscribe(data => {
      this.menu.sections[sectionIndex].dishes[dishIndex] = data;
      this.thereIsChange = true;
      this.matchAllergens();
    });
  }

  openShareQR() {
    const initialState = {
      shortUrlId: this.menu.shortUrlId
    };
    this.modalReference = this.modalService.show(ShareQrComponent, {initialState});
  }

  onSaveMenu() {
    if (this.menu.id) {
      this.updateMenu();
    } else {
      this.createMenu();
    }
    this.thereIsChange = false;
  }

  private createMenu() {
    const menuToSave = {
      businessId: this.businessId,
      sections: this.menu.sections
    };
    this.menuService.createMenu(menuToSave)
      .subscribe((response: any) => {
        this.menu.shortUrlId = response.shortUrlId;
        this.menu.id = response.menuId;
      });
  }

  private updateMenu() {
    const menuSections = this.sanitizeMenuToUpdate();
    const menuToUpdate = {
      businessId: this.businessId,
      menuId: this.menu.id,
      sections: menuSections
    };
    console.log(this.menu);
    this.menuService.updateMenu(menuToUpdate)
      .subscribe((response: string) => {
        this.menu.id = response;
        this.matchAllergens();
        console.log(this.menu);
      });
  }

  sanitizeMenuToUpdate() {
    const sections = this.menu.sections;
    console.log(this.menu);
    sections.map((section: Section) => {
      section.dishes.map((dish: Dish) => {
        dish.allergens.map((allergen: Allergen) => delete allergen.imageName);
      });
    });
    return sections;
  }

  matchAllergens() {
    this.menu.sections.map((section: Section) => {
      section.dishes.map((dish: Dish) => {
        dish.allergens.map((allergen: Allergen) => {
          allergen.imageName = this.allergens.find(item => item.id === allergen.id).imageName;
        });
      });
    });

    console.log(this.menu);
  }
}
