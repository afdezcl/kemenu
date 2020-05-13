import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ConfirmDialogComponent } from './confirmDialog/confirmDialog.component';
import { AlertComponent } from './alert/alert.component';
import { AlertModule } from 'ngx-bootstrap/alert';
import { TranslateModule } from '@ngx-translate/core';

@NgModule({
  imports: [
    CommonModule,
    TranslateModule,
    AlertModule.forRoot()
  ],
  declarations: [
    ConfirmDialogComponent,
    AlertComponent
  ],
  exports: [
    ConfirmDialogComponent,
    AlertComponent
  ]
})
export class DialogModule { }