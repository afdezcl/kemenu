import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CustomerComponent } from './customer.component';
import { AccordionModule } from 'ngx-bootstrap/accordion';
import { TranslateModule } from '@ngx-translate/core';
import { MenuDigitalModule } from '../menu/menu-digital/menu-digital.module';
import { MenuImageModule } from '../menu/menu-image/menu-image.module';
import { CustomerRoutingModule } from './customer-routing.module';

@NgModule({
  imports: [
    CommonModule,
    AccordionModule,
    TranslateModule,
    MenuDigitalModule,
    MenuImageModule,
    CustomerRoutingModule
  ],
  declarations: [CustomerComponent]
})
export class CustomerModule { }
