import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MenuDigitalComponent } from './menu-digital.component';
import { TranslateModule } from '@ngx-translate/core';
import { AccordionModule } from 'ngx-bootstrap/accordion';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { DragDropModule } from '@angular/cdk/drag-drop';
import { DishListModule } from './dish-list/dish-list.module';

@NgModule({
  imports: [
    CommonModule,
    TranslateModule,
    AccordionModule,
    FormsModule,
    ReactiveFormsModule,
    DragDropModule,
    DishListModule,
  ],
  exports: [
    MenuDigitalComponent
  ],
  declarations: [
    MenuDigitalComponent
  ]
})
export class MenuDigitalModule {
}
