import {Component, OnInit} from '@angular/core';
import {Demo} from '@models/demo-mock/demo.mock';
import {ShowMenu} from '@models/menu/showMenu.model';
import {ActivatedRoute} from '@angular/router';
import {MenuService} from '@services/menu/menu.service';

@Component({
  selector: 'app-customer',
  templateUrl: './customer.component.html',
  styleUrls: ['./customer.component.css']
})
export class CustomerComponent implements OnInit {

  menu: ShowMenu;
  cookieBASE64: string;
  shortUrlId: string;

  constructor(
    private router: ActivatedRoute,
    private menuService: MenuService
  ) {
  }

  ngOnInit() {
    this.menu = Demo;
    if (!Object.is(this.router.snapshot.url[0].path, 'demo')) {
      this.getDataToBuildMenu();
      this.menuService.getMenuById(this.shortUrlId)
        .subscribe((response: any) => {
          this.menu = response;
        });
    }
  }

  getDataToBuildMenu() {
    this.cookieBASE64 = localStorage.getItem('COOKIE-SHOW-MENU');
    const json = JSON.parse(atob(this.cookieBASE64));
    // this.menu = json
    this.shortUrlId = json.shortUrlId;
    localStorage.setItem('shortUrlId', this.shortUrlId);
  }

}
