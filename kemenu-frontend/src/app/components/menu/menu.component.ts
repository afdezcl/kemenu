import { Component, OnInit } from '@angular/core';
import { Menu } from '@models/menu/menu.model';
import { BsModalRef, BsModalService, ModalOptions } from 'ngx-bootstrap/modal';
import { CreateSectionComponent } from './menu-digital/create-section/create-section.component';
import { Section } from '@models/menu/section.model';
import { Dish } from '@models/menu/dish.model';
import { TranslateService } from '@ngx-translate/core';
import { ShareQrComponent } from './share-qr/share-qr.component';
import { MenuService } from '@services/menu/menu.service';
import { AuthenticationService } from '@services/authentication/authentication.service';
import { Allergen, AllAllergens } from '@models/menu/allergen.model';
import { Router } from '@angular/router';
import { ModalPolicyComponent } from '../modal-policy/modal-policy.component';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.scss']
})
export class MenuComponent implements OnInit {

  public menu: Menu;
  public modalReference: BsModalRef;
  public businessId: string;
  public customerId: string;
  public thereIsChange = false;
  public menuId: string;
  public allergens: Allergen[] = AllAllergens;
  public newsletterStatus = '';

  constructor(
    private modalService: BsModalService,
    private translate: TranslateService,
    private menuService: MenuService,
    private authService: AuthenticationService,
    private router: Router,
    private toasty: ToastrService
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
        this.newsletterStatus = response.newsletterStatus;
        if (response.businesses[0].menus.length !== 0) {
          this.menu.sections = response.businesses[0].menus[0].sections;
          this.menu.shortUrlId = response.businesses[0].menus[0].shortUrlId;
          this.menu.imageUrl = response.businesses[0].menus[0].imageUrl;
          this.menu.id = response.businesses[0].menus[0].id;
          this.menu.currency = response.businesses[0].menus[0].currency;
          this.matchAllergens();
        }
        this.checkNewsletterStatus();
      });
  }

  private checkNewsletterStatus() {
    if (this.newsletterStatus === 'OLD') {
      const config: ModalOptions = {
        backdrop: 'static',
        keyboard: false,
        animated: true,
        ignoreBackdropClick: true,
      };
      this.modalReference = this.modalService.show(ModalPolicyComponent, config);
    }
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

  private addNewDish(dish: Dish, sectionIndex: number) {
    this.menu.sections[sectionIndex].dishes.push(dish);
    this.onSaveMenu();
  }

  menuChange(menu: Menu) {
    this.menu = menu;
    this.onSaveMenu();
  }

  openShareQR() {
    const initialState = {
      shortUrlId: this.menu.shortUrlId
    };
    this.modalReference = this.modalService.show(ShareQrComponent, { initialState });
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
        this.router.navigateByUrl('menu');
        this.showSuccessToasty();
      }, () => {
        this.showErrorToasty();
      });
  }

  private updateMenu() {
    const menuSections = this.sanitizeAllergensMenuToUpdate();
    const menuToUpdate = {
      businessId: this.businessId,
      menuId: this.menu.id,
      sections: menuSections,
      imageUrl: this.menu.imageUrl,
      currency: this.menu.currency
    };
    this.menuService.updateMenu(menuToUpdate)
      .subscribe((response: string) => {
        this.menu.id = response;
        this.matchAllergens();
        this.loadMenu();
        this.showSuccessToasty();
      }, () => {
        this.showErrorToasty();
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

  showSuccessToasty() {
    this.toasty.success(this.translate.instant('Saved Correctly'));
  }

  showErrorToasty() {
    this.toasty.error(this.translate.instant('Save error'));
  }
}
