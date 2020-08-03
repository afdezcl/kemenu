import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CookiesPolicyComponent } from './cookies-policy.component';
import { TranslateModule } from '@ngx-translate/core';

@NgModule({
  imports: [
    CommonModule,
    TranslateModule
  ],
  declarations: [CookiesPolicyComponent]
})
export class CookiesPolicyModule { }
