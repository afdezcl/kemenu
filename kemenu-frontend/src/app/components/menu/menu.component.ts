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
import { ActivatedRoute, Router } from '@angular/router';
import { ModalPolicyComponent } from '../modal-policy/modal-policy.component';
import { ToastrService } from 'ngx-toastr';
import { Location } from '@angular/common';

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
  public menuName: string;

  constructor(
    private modalService: BsModalService,
    private translate: TranslateService,
    private menuService: MenuService,
    private authService: AuthenticationService,
    private router: Router,
    private route: ActivatedRoute,
    private toasty: ToastrService,
    private location: Location
  ) {
  }

  ngOnInit() {
    this.route.params.subscribe(params => {
      this.menuId = params['id'];
      this.loadMenus();
    });
  }

  loadMenus() {
    const customerEmail = this.authService.getUserEmail();
    this.menu = new Menu(
      [],
      ''
    );
    this.menuService.getCustomer(customerEmail)
      .subscribe((response: any) => {
        this.customerId = response.id;
        this.businessId = response.businesses[0].id;
        this.newsletterStatus = response.newsletterStatus;
        this.menu = response.businesses[0].menus[this.menuId];
        this.matchAllergens(this.menu);
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
    this.menu.sections.push(section);
    this.onSaveMenu(menu);
  }

  menuChange(menu: Menu) {
    //const index = this.menusSaved.findIndex((menusSaved: Menu) => Object.is(menusSaved.name, menu.name));
    this.menu = menu;
    this.onSaveMenu(menu);
  }

  openShareQR() {
    const initialState = {
      shortUrlId: this.menu.shortUrlId
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
        this.menu.shortUrlId = response.shortUrlId;
        this.menu.id = response.menuId;
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
        this.menu.id = response;
        this.matchAllergens(menu);
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
    this.menu.sections.map((section: Section) => {
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

  goToMenuList() {
    this.location.back();
  }
}
