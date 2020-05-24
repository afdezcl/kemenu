import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HomeComponent } from './home.component';
import { RouterModule } from '@angular/router';
import { TranslateModule } from '@ngx-translate/core';
import { DialogModule } from '@ui-controls/dialogs/dialog.module';

@NgModule({
  imports: [
    CommonModule,
    RouterModule,
    TranslateModule,
    DialogModule
  ],
  declarations: [HomeComponent]
})
export class HomeModule { }
