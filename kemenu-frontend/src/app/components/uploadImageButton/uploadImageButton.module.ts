import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TranslateModule } from '@ngx-translate/core';

import { UploadImageButtonComponent } from './uploadImageButton.component';

@NgModule({
  imports: [
    CommonModule,
    TranslateModule,
  ],
  declarations: [
    UploadImageButtonComponent
  ],
  exports: [
    UploadImageButtonComponent
  ]
})
export class UploadImageButtonModule { }
