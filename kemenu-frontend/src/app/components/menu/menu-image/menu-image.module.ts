import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MenuImageComponent } from './menu-image.component';
import { TranslateModule } from '@ngx-translate/core';
import { AccordionModule } from 'ngx-bootstrap/accordion';
import { HttpClientModule } from '@angular/common/http';
import { UploadImageButtonModule } from '../../uploadImageButton/uploadImageButton.module';

@NgModule({
  imports: [
    HttpClientModule,
    CommonModule,
    TranslateModule,
    AccordionModule,
    UploadImageButtonModule
  ],
  exports: [
    MenuImageComponent
  ],
  declarations: [
    MenuImageComponent
  ]
})
export class MenuImageModule { }
