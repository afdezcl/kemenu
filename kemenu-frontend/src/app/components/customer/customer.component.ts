import { Component, OnInit } from '@angular/core';
import { Demo } from '@models/demo-mock/demo.mock';
import { ShowMenu } from '@models/menu/showMenu.model';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-customer',
  templateUrl: './customer.component.html',
  styleUrls: ['./customer.component.css']
})
export class CustomerComponent implements OnInit {

  menu: ShowMenu;
  cookieBASE64: string;

  constructor(
    private router: ActivatedRoute
  ) { }

  ngOnInit() {
    if(Object.is(this.router.snapshot.url[0].path, 'demo'))
      this.menu = Demo
    else 
      this.getMenu();
  }

  getMenu(){
    this.cookieBASE64 = localStorage.getItem('COOKIE-SHOW-MENU')
    const json = JSON.parse(atob(this.cookieBASE64))
    this.menu = json
  }

}
