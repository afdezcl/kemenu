import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {TranslateModule} from '@ngx-translate/core';
import {AccordionModule} from 'ngx-bootstrap/accordion';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {FaqComponent} from './faq.component';

@NgModule({
  imports: [
    CommonModule,
    TranslateModule,
    AccordionModule,
    FormsModule,
    ReactiveFormsModule
  ],
  exports: [
    FaqComponent
  ],
  declarations: [
    FaqComponent
  ]
})
export class FaqModule {
}
