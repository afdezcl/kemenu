import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { TranslateModule } from '@ngx-translate/core';
import { CookiesBannerComponent } from './cookies-banner.component';

@NgModule({
  imports: [
    CommonModule,
    RouterModule,
    TranslateModule,
  ],
  declarations: [CookiesBannerComponent],
  exports: [CookiesBannerComponent]
})

export class CookiesBannerModule { }
