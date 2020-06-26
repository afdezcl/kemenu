import {Component, OnInit} from '@angular/core';
import {Menu} from '@models/menu/menu.model';
import {BsModalRef, BsModalService} from 'ngx-bootstrap/modal';
import {Section} from '@models/menu/section.model';
import {ConfirmDialogComponent} from '@ui-controls/dialogs/confirmDialog/confirmDialog.component';
import {TranslateService} from '@ngx-translate/core';
import {MenuService} from '@services/menu/menu.service';
import {AuthenticationService} from '@services/authentication/authentication.service';
import {Allergen, AllAllergens} from '@models/menu/allergen.model';
import {DomSanitizer} from '@angular/platform-browser';
import {Router} from '@angular/router';

@Component({
  selector: 'app-menu-image',
  templateUrl: './menu-image.component.html',
  styleUrls: ['./menu-image.component.scss']
})
export class MenuImageComponent implements OnInit {

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
        }
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

  removeMenuImage() {
    const initialState = {
      title: this.translate.instant('Delete Menu Image title'),
      message: this.translate.instant('Delete Menu Image description')
    };

    this.modalReference = this.modalService.show(ConfirmDialogComponent, {initialState});
    this.modalReference.content.onClose.subscribe((canDelete: boolean) => {
      if (canDelete) {
        this.menu.imageUrl = '';
        this.onSaveMenu();
      }
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
    const menuToSave = {
      businessId: this.businessId,
      sections: {},
      imageUrl: this.menu.imageUrl
    };
    this.menuService.createMenu(menuToSave)
      .subscribe((response: any) => {
        this.menu.shortUrlId = response.shortUrlId;
        this.menu.id = response.menuId;
        this.router.navigateByUrl('show');
      });
  }

  private updateMenu() {
    const menuToUpdate = {
      businessId: this.businessId,
      menuId: this.menu.id,
      sections: {},
      imageUrl: this.menu.imageUrl
    };
    this.menuService.updateMenu(menuToUpdate)
      .subscribe((response: string) => {
        this.menu.id = response;
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
