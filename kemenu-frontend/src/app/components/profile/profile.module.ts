import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ProfileComponent } from './profile.component';
import { RouterModule } from '@angular/router';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { TranslateModule } from '@ngx-translate/core';
import { AlertModule } from 'ngx-bootstrap/alert';
import { DialogModule } from '@ui-controls/dialogs/dialog.module';
import { EditInformationComponent } from './edit-information/edit-information.component';
import { UploadImageButtonModule } from '../uploadImageButton/uploadImageButton.module';
import { CustomizeMenuComponent } from './customizeMenu/customizeMenu.component';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    AlertModule.forRoot(),
    RouterModule,
    TranslateModule,
    DialogModule,
    UploadImageButtonModule
  ],
  declarations: [
    ProfileComponent,
    EditInformationComponent,
    CustomizeMenuComponent
  ]
})
export class ProfileModule { }
