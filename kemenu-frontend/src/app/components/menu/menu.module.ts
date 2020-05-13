import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MenuComponent } from './menu.component';
import { TranslateModule } from '@ngx-translate/core';
import { AccordionModule } from 'ngx-bootstrap/accordion';
import { CreateSectionComponent } from './create-section/create-section.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { CreateDishComponent } from './create-dish/create-dish.component';
import { ShareQrComponent } from './share-qr/share-qr.component';
import { ShareButtonsModule } from 'ngx-sharebuttons/buttons';
import { ShareIconsModule } from 'ngx-sharebuttons/icons';
import { HttpClientModule } from '@angular/common/http';

@NgModule({
  imports: [
    HttpClientModule,
    CommonModule,
    TranslateModule,
    AccordionModule,
    FormsModule, 
    ReactiveFormsModule,
    ShareButtonsModule.withConfig({
      debug: true
    }),
    ShareIconsModule.forRoot()
  ],
  declarations: [
    MenuComponent,
    CreateSectionComponent,
    CreateDishComponent,
    ShareQrComponent
  ]
})
export class MenuModule { }
