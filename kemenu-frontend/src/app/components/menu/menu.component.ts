import { Component, OnInit } from '@angular/core';
import { Menu } from '@models/menu/menu.interface';
import { BsModalRef, BsModalService } from 'ngx-bootstrap/modal';
import { CreateSectionComponent } from './create-section/create-section.component'

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.css']
})
export class MenuComponent implements OnInit {

  menu: Menu;
  modalReference: BsModalRef;

  constructor(
    private modalService: BsModalService,
  ) { }

  ngOnInit() {
    
  }

  openCreateSection() {
    this.modalReference = this.modalService.show(CreateSectionComponent);
  }

}
