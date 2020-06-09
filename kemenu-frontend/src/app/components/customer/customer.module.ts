import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CustomerComponent } from './customer.component';
import { AccordionModule } from 'ngx-bootstrap/accordion';
import { TranslateModule } from '@ngx-translate/core';

@NgModule({
  imports: [
    CommonModule,
    AccordionModule,
    TranslateModule
  ],
  declarations: [CustomerComponent]
})
export class CustomerModule { }
