import {Component, OnInit} from '@angular/core';
import {Menu} from '@models/menu/menu.model';
import {BsModalRef, BsModalService} from 'ngx-bootstrap/modal';
import {CreateSectionComponent} from '../create-section/create-section.component';
import {Section} from '@models/menu/section.model';
import {CreateDishComponent} from '../create-dish/create-dish.component';
import {Dish} from '@models/menu/dish.model';
import {ConfirmDialogComponent} from '@ui-controls/dialogs/confirmDialog/confirmDialog.component';
import {TranslateService} from '@ngx-translate/core';
import {MenuService} from '@services/menu/menu.service';
import {AuthenticationService} from '@services/authentication/authentication.service';
import {Allergen, AllAllergens} from '@models/menu/allergen.model';
import {DomSanitizer} from '@angular/platform-browser';
import {Router} from '@angular/router';
import {CdkDragDrop, moveItemInArray} from '@angular/cdk/drag-drop';

@Component({
  selector: 'app-menu-digital',
  templateUrl: './menu-digital.component.html',
  styleUrls: ['./menu-digital.component.scss']
})
export class MenuDigitalComponent implements OnInit {

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
    private authService: AuthenticationService,
    private router: Router,
    private sanitizer: DomSanitizer
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
    this.menuService.getCustomer(customerEmail)
      .subscribe((response: any) => {
        this.customerId = response.id;
        this.businessId = response.businesses[0].id;
        if (response.businesses[0].menus.length !== 0) {
          this.menu.sections = response.businesses[0].menus[0].sections;
          this.menu.shortUrlId = response.businesses[0].menus[0].shortUrlId;
          this.menu.imageUrl = response.businesses[0].menus[0].imageUrl;
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
    this.onSaveMenu();
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
      this.onSaveMenu();
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

  dropSection(event: CdkDragDrop<string[]>) {
    moveItemInArray(this.menu.sections, event.previousIndex, event.currentIndex);
    this.onSaveMenu();
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
    this.onSaveMenu();
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
        this.onSaveMenu();
      }
      this.thereIsChange = true;
    });
  }

  editDish(dishToEdit: Dish, sectionIndex: number, dishIndex: number) {
    const initialState = {
      name: dishToEdit.name,
      description: dishToEdit.description,
      price: dishToEdit.price,
      selectedAllergens: dishToEdit.allergens,
      imageUrl: dishToEdit.imageUrl,
      available: !dishToEdit.available
    };
    this.modalReference = this.modalService.show(CreateDishComponent, {initialState});
    this.modalReference.content.messageEvent.subscribe(data => {
      this.menu.sections[sectionIndex].dishes[dishIndex] = data;
      this.matchAllergens();
      this.onSaveMenu();
    });
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
    const menuSections = this.sanitizeAllergensMenuToUpdate();
    const menuToSave = {
      businessId: this.businessId,
      sections: menuSections,
      imageUrl: this.menu.imageUrl
    };
    this.menuService.createMenu(menuToSave)
      .subscribe((response: any) => {
        this.menu.shortUrlId = response.shortUrlId;
        this.menu.id = response.menuId;
        this.matchAllergens();
        this.router.navigateByUrl('show');
      });
  }

  private updateMenu() {
    const menuSections = this.sanitizeAllergensMenuToUpdate();
    const menuToUpdate = {
      businessId: this.businessId,
      menuId: this.menu.id,
      sections: menuSections,
      imageUrl: this.menu.imageUrl
    };
    this.menuService.updateMenu(menuToUpdate)
      .subscribe((response: string) => {
        this.menu.id = response;
        this.matchAllergens();
      });
  }

  sanitizeAllergensMenuToUpdate() {
    const sections = this.menu.sections;
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
  }

  handleFileUpload(event) {
    if (event) {
      this.menu.imageUrl = event.url;
      this.onSaveMenu();
    }
  }

  getMenuImageSanitized() {
    return this.sanitizer.bypassSecurityTrustResourceUrl(this.menu.imageUrl);
  }
}
