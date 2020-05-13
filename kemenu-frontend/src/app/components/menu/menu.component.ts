import { Component, OnInit } from '@angular/core';
import { Menu } from '@models/menu/menu.model';
import { BsModalRef, BsModalService } from 'ngx-bootstrap/modal';
import { CreateSectionComponent } from './create-section/create-section.component'
import { Section } from '@models/menu/section.model';
import { CreateDishComponent } from './create-dish/create-dish.component';
import { Dish } from '@models/menu/dish.model';
import { ConfirmDialogComponent } from '@ui-controls/dialogs/confirmDialog/confirmDialog.component';
import { TranslateService } from '@ngx-translate/core';
import { ShareQrComponent } from './share-qr/share-qr.component';
import { MenuService } from '@services/menu/menu.service';
import { AuthenticationService } from '@services/authentication/authentication.service';

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
    private translate: TranslateService,
    private _menuService: MenuService,
    private _authService: AuthenticationService
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

  
  deleteSection(sectionToRemove: Section){
    const initialState = {
      title: this.translate.instant('Delete Section title'),
      message: this.translate.instant('Delete Section description')
    };
    
    this.modalReference = this.modalService.show(ConfirmDialogComponent, { initialState });
    this.modalReference.content.onClose.subscribe((canDelete: boolean) => {
      if(canDelete) {
        this.menu.sections = this.menu.sections.filter(section => section !== sectionToRemove)
      }
    })
  }
  
  editSection(sectionToEdit: Section, sectionIndex: number){
    const initialState = {
      name: sectionToEdit.name      
    };
    this.modalReference = this.modalService.show(CreateSectionComponent, { initialState });
    this.modalReference.content.messageEvent.subscribe(data => {
      this.menu.sections[sectionIndex].name = data
    });
  }
  
  openCreateDish(sectionIndex: number) {
    this.modalReference = this.modalService.show(CreateDishComponent);
    this.modalReference.content.messageEvent.subscribe(dish => {      
      this.addNewDish(dish, sectionIndex)
    });
  }
                  
  private addNewDish(dish: Dish, sectionIndex: number){
     this.menu
         .sections[sectionIndex]
         .dishes.push(dish)
  }

  deleteDish(dishToRemove: Dish, sectionIndex: number){
    const initialState = {
      title: this.translate.instant('Delete Dish title'),
      message: this.translate.instant('Delete Dish description'),
    };

    this.modalReference = this.modalService.show(ConfirmDialogComponent, { initialState });
    this.modalReference.content.onClose.subscribe((canDelete: boolean) => {
      if(canDelete) {
        this.menu.sections[sectionIndex].dishes = 
        this.menu.sections[sectionIndex].dishes.filter(dish => dish !== dishToRemove)
      }
    })
  }

  editDish(dishToEdit: Dish, sectionIndex: number, dishIndex: number){
    const initialState = {
      name: dishToEdit.name,
      description: dishToEdit.description,
      price: dishToEdit.price
    };
    this.modalReference = this.modalService.show(CreateDishComponent, { initialState });
    this.modalReference.content.messageEvent.subscribe(data => {
      this.menu.sections[sectionIndex].dishes[dishIndex] = data
    });
  }

  openShareQR(){
    this.modalReference = this.modalService.show(ShareQrComponent);
  }

  onSaveMenu(){
    const menuToSave = {
      customerEmail: this._authService.getUserEmail(),
      sections: this.menu.sections      
    }
    this._menuService.createMenu(menuToSave)
      .subscribe((response: string) => {
        this.menu.id = response        
      })
  }

}
