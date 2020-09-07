import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CookiesPolicyComponent } from './cookies-policy.component';
import { TranslateModule } from '@ngx-translate/core';
import { CookiesPolicyRoutingModule } from './cookies-policy-routing.module';

@NgModule({
  imports: [
    CommonModule,
    TranslateModule,
    CookiesPolicyRoutingModule
  ],
  declarations: [CookiesPolicyComponent]
})
export class CookiesPolicyModule { }
