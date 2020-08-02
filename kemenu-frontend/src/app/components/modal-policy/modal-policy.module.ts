import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ModalPolicyComponent } from './modal-policy.component';
import { TranslateModule } from '@ngx-translate/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    CommonModule,
    TranslateModule,
    RouterModule
  ],
  declarations: [ModalPolicyComponent]
})
export class ModalPolicyModule { }
