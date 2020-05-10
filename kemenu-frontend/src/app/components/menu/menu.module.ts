import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MenuComponent } from './menu.component';
import { TranslateModule } from '@ngx-translate/core';
import { AccordionModule } from 'ngx-bootstrap/accordion';
import { CreateSectionComponent } from './create-section/create-section.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

@NgModule({
  imports: [
    CommonModule,
    TranslateModule,
    AccordionModule,
    FormsModule, 
    ReactiveFormsModule
  ],
  declarations: [
    MenuComponent,
    CreateSectionComponent
  ]
})
export class MenuModule { }
