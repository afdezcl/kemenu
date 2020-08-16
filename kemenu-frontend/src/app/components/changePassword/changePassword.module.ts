import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ChangePasswordComponent } from './changePassword.component';
import { RouterModule } from '@angular/router';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { TranslateModule } from '@ngx-translate/core';
import { AlertModule } from 'ngx-bootstrap/alert';
import { DialogModule } from '@ui-controls/dialogs/dialog.module';
import { ChangePasswordRoutingModule } from './changePassword-routing.module';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    AlertModule.forRoot(),
    RouterModule,
    TranslateModule,
    DialogModule,
    ChangePasswordRoutingModule
  ],
  declarations: [ChangePasswordComponent]
})

export class ChangePasswordModule {
}
