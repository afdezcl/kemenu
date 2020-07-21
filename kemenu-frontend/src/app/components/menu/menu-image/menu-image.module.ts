import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {MenuImageComponent} from './menu-image.component';
import {TranslateModule} from '@ngx-translate/core';
import {AccordionModule} from 'ngx-bootstrap/accordion';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {ShareButtonsModule} from 'ngx-sharebuttons/buttons';
import {ShareIconsModule} from 'ngx-sharebuttons/icons';
import {HttpClientModule} from '@angular/common/http';
import {DragDropModule} from '@angular/cdk/drag-drop';
import {UploadImageButtonModule} from '../../uploadImageButton/uploadImageButton.module';

@NgModule({
  imports: [
    HttpClientModule,
    CommonModule,
    TranslateModule,
    AccordionModule,
    FormsModule,
    ReactiveFormsModule,
    DragDropModule,
    ShareButtonsModule.withConfig({
      debug: true
    }),
    ShareIconsModule.forRoot(),
    UploadImageButtonModule
  ],
  exports: [
    MenuImageComponent
  ],
  declarations: [
    MenuImageComponent
  ]
})
export class MenuImageModule {
}
