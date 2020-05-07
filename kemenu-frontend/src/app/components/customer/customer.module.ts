import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CustomerComponent } from './customer.component';
import { AccordionModule } from 'ngx-bootstrap/accordion';

@NgModule({
  imports: [
    CommonModule,
    AccordionModule
  ],
  declarations: [CustomerComponent]
})
export class CustomerModule { }
