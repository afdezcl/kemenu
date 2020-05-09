import { Component, OnInit } from '@angular/core';
import { Menu } from '@models/menu/menu.model';
import { BsModalRef, BsModalService } from 'ngx-bootstrap/modal';
import { CreateSectionComponent } from './create-section/create-section.component'
import { Section } from '@models/menu/section.model';

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
    this.loadMenu();      
  }

  loadMenu(){
    this.menu = new Menu(
      []      
    )  
  }

  openCreateSection() {
    this.modalReference = this.modalService.show(CreateSectionComponent);
    this.modalReference.content.messageEvent.subscribe(data => {
      this.addNewSection(data)
    });
  }

  private addNewSection(name: string){
    const section = new Section(
      name,
      []
    )
    this.menu.sections.push(section)
  }


}
