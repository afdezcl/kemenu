import { Component, OnInit } from '@angular/core';
import { Menu } from '@models/menu/menu.model';
import { Demo } from '@models/demo-mock/demo.mock';

@Component({
  selector: 'app-customer',
  templateUrl: './customer.component.html',
  styleUrls: ['./customer.component.css']
})
export class CustomerComponent implements OnInit {

  menuDemo: Menu = Demo;

  constructor() { }

  ngOnInit() {
  }

}
