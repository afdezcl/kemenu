import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CustomerComponent } from './customer.component';
import { AccordionModule } from 'ngx-bootstrap/accordion';
import { TranslateModule } from '@ngx-translate/core';
import { MenuDigitalModule } from '../menu/menu-digital/menu-digital.module';
import { CustomerRoutingModule } from './customer-routing.module';
import { TabsModule } from 'ngx-bootstrap/tabs';

@NgModule({
  imports: [
    CommonModule,
    AccordionModule,
    TranslateModule,
    MenuDigitalModule,
    CustomerRoutingModule,
    TabsModule.forRoot()
  ],
  declarations: [CustomerComponent]
})
export class CustomerModule { }
