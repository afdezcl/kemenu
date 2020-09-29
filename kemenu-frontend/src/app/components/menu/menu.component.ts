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
import { CreateMenuNameComponent } from './menu-digital/create-menu-name/create-menu-name.component';

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.scss']
})
export class MenuComponent implements OnInit {

  public menusSaved: Menu[] = [];
  public menu: Menu[];
  public modalReference: BsModalRef;
  public businessId: string;
  public customerId: string;
  public thereIsChange = false;
  public menuId: string;
  public allergens: Allergen[] = AllAllergens;
  public newsletterStatus = '';
  public menuName: string;

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
    this.loadMenus();
  }

  loadMenus() {
    const customerEmail = this.authService.getUserEmail();
    this.menuService.getCustomer(customerEmail)
      .subscribe((response: any) => {
        this.customerId = response.id;
        this.businessId = response.businesses[0].id;
        this.newsletterStatus = response.newsletterStatus;
        if (response.businesses[0].menus.length !== 0) {
          this.menu = response.businesses[0].menus;
          console.log(this.menusSaved);
          for (let menu of response.businesses[0].menus) {
            this.matchAllergens(menu);
          }
        }
        this.checkNewsletterStatus();
      });
  }

  trackByMenuName(menu) {
    return menu.name;
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

  openCreateMenuName() {
    this.modalReference = this.modalService.show(CreateMenuNameComponent);
    this.modalReference.content.messageEvent.subscribe(name => {
      const menu = new Menu(
        [],
        name
      );
      this.menusSaved.push(menu);
      this.openCreateSection(menu);
    });
  }

  openCreateSection(menu: Menu) {
    this.modalReference = this.modalService.show(CreateSectionComponent);
    this.modalReference.content.messageEvent.subscribe((sectionName: string) => {
      this.addNewSection(menu, sectionName);
    });
  }

  private addNewSection(menu: Menu, sectionName: string) {
    const section = new Section(
      sectionName,
      []
    );
    this.menusSaved.find((menusSaved: Menu) => Object.is(menusSaved.name, menu.name)).sections.push(section);
    this.onSaveMenu(menu);
  }

  menuChange(menu: Menu) {
    const index = this.menusSaved.findIndex((menusSaved: Menu) => Object.is(menusSaved.name, menu.name));
    this.menusSaved[index] = menu;
    this.onSaveMenu(menu);
  }

  openShareQR() {
    const initialState = {
      shortUrlId: this.menusSaved[0].shortUrlId
    };
    this.modalReference = this.modalService.show(ShareQrComponent, { initialState });
  }

  onSaveMenu(menu: Menu) {
    if (menu.id) {
      this.updateMenu(menu);
    } else {
      this.createMenu(menu);
    }
    this.thereIsChange = false;
  }

  private createMenu(menu: Menu) {
    const menuSections = this.sanitizeAllergensMenuToUpdate(menu);
    const menuToSave = {
      businessId: this.businessId,
      sections: menuSections,
      imageUrl: menu.imageUrl,
      name: menu.name
    };
    this.menuService.createMenu(menuToSave)
      .subscribe((response: any) => {
        this.menusSaved.find((menusSaved: Menu) => Object.is(menusSaved.name, menu.name)).shortUrlId = response.shortUrlId;
        this.menusSaved.find((menusSaved: Menu) => Object.is(menusSaved.name, menu.name)).id = response.menuId;
        this.matchAllergens(menu);
        this.router.navigateByUrl('menu');
        this.showSuccessToasty();
      }, () => {
        this.showErrorToasty();
      });
  }

  private updateMenu(menu: Menu) {
    const menuSections = this.sanitizeAllergensMenuToUpdate(menu);
    const menuToUpdate = {
      businessId: this.businessId,
      menuId: menu.id,
      sections: menuSections,
      imageUrl: menu.imageUrl,
      currency: menu.currency,
      name: menu.name
    };
    this.menuService.updateMenu(menuToUpdate)
      .subscribe((response: string) => {
        this.menusSaved.find((menusSaved: Menu) => Object.is(menusSaved.name, menu.name)).id = response;
        this.matchAllergens(menu);
        this.showSuccessToasty();
      }, () => {
        this.showErrorToasty();
      });
  }

  sanitizeAllergensMenuToUpdate(menu: Menu) {
    const sections = menu.sections;
    sections.map((section: Section) => {
      section.dishes.map((dish: Dish) => {
        dish.allergens.map((allergen: Allergen) => delete allergen.imageName);
      });
    });
    return sections;
  }

  matchAllergens(menu: Menu) {
    this.menusSaved.find((menusSaved: Menu) => Object.is(menusSaved.name, menu.name)).sections.map((section: Section) => {
      section.dishes.map((dish: Dish) => {
        dish.allergens.map((allergen: Allergen) => {
          allergen.imageName = this.allergens.find(item => item.id === allergen.id).imageName;
        });
      });
    });
  }


  showSuccessToasty() {
    this.toasty.success(this.translate.instant('Saved Correctly'));
  }

  showErrorToasty() {
    this.toasty.error(this.translate.instant('Save error'));
  }
}
