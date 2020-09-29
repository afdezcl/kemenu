import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Menu } from '@models/menu/menu.model';
import { AuthenticationService } from '@services/authentication/authentication.service';
import { MenuService } from '@services/menu/menu.service';
import { BsModalRef, BsModalService } from 'ngx-bootstrap/modal';
import { CreateMenuNameComponent } from '../menu-digital/create-menu-name/create-menu-name.component';

@Component({
  selector: 'app-select-menu',
  templateUrl: './select-menu.component.html',
  styleUrls: ['./select-menu.component.scss']
})
export class SelectMenuComponent implements OnInit {
  
  public menusSaved: Menu[] = [];
  public modalReference: BsModalRef;
  
  constructor(
    private authService: AuthenticationService,
    private menuService: MenuService,
    private modalService: BsModalService,
    private router: Router,
  ) { }

  ngOnInit() {
    this.loadMenus();
  }

  loadMenus() {
    const customerEmail = this.authService.getUserEmail();
    this.menuService.getCustomer(customerEmail)
      .subscribe((response: any) => {
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
    });
  }

  handleFileUpload(event) {
    if (event) {
      this.menusSaved[0].imageUrl = event.url;
      this.createMenu(this.menusSaved[0]);
    }
  }
  private createMenu(menu: Menu) {

  }

  goToMenu(index) {    
    this.router.navigateByUrl(`/menu/${index}`);
  }

}
