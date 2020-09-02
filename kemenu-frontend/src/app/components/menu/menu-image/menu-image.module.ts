import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MenuImageComponent } from './menu-image.component';
import { TranslateModule } from '@ngx-translate/core';
import { AccordionModule } from 'ngx-bootstrap/accordion';
import { HttpClientModule } from '@angular/common/http';
import { UploadImageButtonModule } from '../../uploadImageButton/uploadImageButton.module';
import { ShareButtonsModule } from 'ngx-sharebuttons/buttons';
import { ShareIconsModule } from 'ngx-sharebuttons/icons';

@NgModule({
  imports: [
    HttpClientModule,
    CommonModule,
    TranslateModule,
    AccordionModule,
    UploadImageButtonModule,
    ShareButtonsModule.withConfig({
      debug: true
    }),
    ShareIconsModule.forRoot(),
  ],
  exports: [
    MenuImageComponent
  ],
  declarations: [
    MenuImageComponent
  ]
})
export class MenuImageModule { }
