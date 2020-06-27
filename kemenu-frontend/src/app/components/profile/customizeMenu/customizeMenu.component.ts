import { Component, OnInit } from '@angular/core';
import { Location } from '@angular/common';

@Component({
  selector: 'app-customizeMenu',
  templateUrl: './customizeMenu.component.html',
  styleUrls: ['./customizeMenu.component.css']
})
export class CustomizeMenuComponent implements OnInit {

  constructor(
    private location: Location,
  ) { }

  ngOnInit() {
  }

  goBack() {
    this.location.back();
  }
}
