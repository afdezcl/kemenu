import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AboutUsComponent } from './aboutUs.component';
import { RouterModule } from '@angular/router';
import { TranslateModule } from '@ngx-translate/core';
import { AboutUsRoutingModule } from './aboutUs-routing.module';

@NgModule({
  imports: [
    CommonModule,
    RouterModule,
    TranslateModule,
    AboutUsRoutingModule
  ],
  declarations: [AboutUsComponent]
})
export class AboutUsModule { }
