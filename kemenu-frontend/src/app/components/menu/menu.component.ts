import { Component, OnInit } from '@angular/core';
import { Menu } from '@models/menu/menu.model';
import { BsModalRef, BsModalService } from 'ngx-bootstrap/modal';
import { CreateSectionComponent } from './create-section/create-section.component'
import { Section } from '@models/menu/section.model';
import { CreateDishComponent } from './create-dish/create-dish.component';
import { Dish } from '@models/menu/dish.model';
import { ConfirmDialogComponent } from '@ui-controls/dialogs/confirmDialog/confirmDialog.component';

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
      '',
      []      
    )  
  }

  openCreateSection() {
    this.modalReference = this.modalService.show(CreateSectionComponent);
    this.modalReference.content.messageEvent.subscribe(name => {
      this.addNewSection(name)
    });
  }

  private addNewSection(name: string){
    const section = new Section(
      name,
      []
    )
    this.menu.sections.push(section)
  }

  openCreateDish(indexSection: number) {
    this.modalReference = this.modalService.show(CreateDishComponent);
    this.modalReference.content.messageEvent.subscribe(dish => {      
      this.addNewDish(dish, indexSection)
    });
  }

  private addNewDish(dish: Dish, indexSection: number){
    this.menu
        .sections[indexSection]
        .dishes.push(dish)
  }

  deleteSection(sectionToRemove: Section){
    const initialState = {
      title: 'Eliminar sección',
      message: '¿Está seguro que desea eliminar esta sección?'
    };

    this.modalReference = this.modalService.show(ConfirmDialogComponent, { initialState });
    this.modalReference.content.onClose.subscribe((canDelete: boolean) => {
      if(canDelete) {
        this.menu.sections = this.menu.sections.filter(section => section !== sectionToRemove)
      }
    })
  }

  editSection(sectionToEdit: Section, indexSection: number){
    const initialState = {
      name: sectionToEdit.name      
    };
    this.modalReference = this.modalService.show(CreateSectionComponent, { initialState });
    this.modalReference.content.messageEvent.subscribe(data => {
      this.menu.sections[indexSection].name = data
    });
  }


}
