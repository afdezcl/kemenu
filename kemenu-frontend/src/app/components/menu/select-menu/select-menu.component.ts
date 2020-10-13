import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Menu } from '@models/menu/menu.model';
import { TranslateService } from '@ngx-translate/core';
import { AuthenticationService } from '@services/authentication/authentication.service';
import { MenuService } from '@services/menu/menu.service';
import { ConfirmDialogComponent } from '@ui-controls/dialogs/confirmDialog/confirmDialog.component';
import { BsModalRef, BsModalService } from 'ngx-bootstrap/modal';
import { ToastrService } from 'ngx-toastr';
import { CreateMenuNameComponent } from '../menu-digital/create-menu-name/create-menu-name.component';

@Component({
  selector: 'app-select-menu',
  templateUrl: './select-menu.component.html',
  styleUrls: ['./select-menu.component.scss']
})
export class SelectMenuComponent implements OnInit {

  public menusSaved: Menu[] = [];
  public customerId: string;
  public businessId: string;
  public modalReference: BsModalRef;

  constructor(
    private authService: AuthenticationService,
    private menuService: MenuService,
    private modalService: BsModalService,
    private router: Router,
    private translate: TranslateService,
    private toasty: ToastrService,
  ) { }

  ngOnInit() {
    this.loadMenus();
  }

  loadMenus() {
    const customerEmail = this.authService.getUserEmail();
    this.menuService.getCustomer(customerEmail)
      .subscribe((response: any) => {
        this.customerId = response.id;
        this.businessId = response.businesses[0].id;
        this.menusSaved = response.businesses[0].menus;
      });
  }

  openCreateMenuName() {
    this.modalReference = this.modalService.show(CreateMenuNameComponent);
    this.modalReference.content.messageEvent.subscribe(name => {
      const menu = new Menu(
        [],
        name
      );
      this.menusSaved.push(menu);
      this.createMenu(menu);
    });
  }

  openEditMenuName(menu: Menu) {
    const initialState = {
      name: menu.name
    };
    this.modalReference = this.modalService.show(CreateMenuNameComponent, { initialState });
    this.modalReference.content.messageEvent.subscribe(name => {
      this.menusSaved.find((menuSaved: Menu) => Object.is(menu.id, menuSaved.id)).name = name;
      this.onSaveMenu(menu);
    });
  }

  onDeleteMenu(menuToDelete: Menu) {
    const initialState = {
      title: this.translate.instant('Delete Menu title'),
      message: this.translate.instant('Delete Menu description')
    };

    this.modalReference = this.modalService.show(ConfirmDialogComponent, { initialState });
    this.modalReference.content.onClose.subscribe((canDelete: boolean) => {
      if (canDelete) {
        this.attemptToDelete(menuToDelete);
      }
    });
  }

  private attemptToDelete(menuToDelete: Menu) {
    const paramsToDelete = {
      email: this.authService.getUserEmail(),
      businessId: this.businessId,
      menuId: menuToDelete.id,
    };

    this.menuService.deleteMenu(paramsToDelete)
      .subscribe(() => {
        this.menusSaved = this.menusSaved.filter(menu => menu !== menuToDelete);
        this.showSuccessToasty();
      }, () => {
        this.showErrorToasty();
      });
  }

  onSaveMenu(menu: Menu) {
    if (menu.id) {
      this.updateMenu(menu);
    } else {
      this.createMenu(menu);
    }
  }

  private updateMenu(menu: Menu) {
    const menuToUpdate = {
      businessId: this.businessId,
      menuId: menu.id,
      sections: menu.sections,
      imageUrl: menu.imageUrl,
      currency: menu.currency,
      name: menu.name
    };
    this.menuService.updateMenu(menuToUpdate)
      .subscribe(() => {
        this.showSuccessToasty();
      }, () => {
        this.showErrorToasty();
      });
  }

  private createMenu(menu: Menu) {
    const menuToSave = {
      businessId: this.businessId,
      sections: [],
      imageUrl: '',
      name: menu.name
    };
    this.menuService.createMenu(menuToSave)
      .subscribe((response: any) => {
        menu.shortUrlId = response.shortUrlId;
        this.menusSaved.find((menuSaved: Menu) => Object.is(menu.name, menuSaved.name)).id = response.menuId;
        this.showSuccessToasty();
      }, () => {
        this.showErrorToasty();
      });
  }


  handleFileUpload(event) {
    if (event) {
      this.menusSaved[0].imageUrl = event.url;
      this.createMenu(this.menusSaved[0]);
    }
  }


  goToMenu(menuId: string) {
    this.router.navigateByUrl(`/menu/${menuId}`);
  }
  showSuccessToasty() {
    this.toasty.success(this.translate.instant('Saved Correctly'));
  }

  showErrorToasty() {
    this.toasty.error(this.translate.instant('Save error'));
  }
}
