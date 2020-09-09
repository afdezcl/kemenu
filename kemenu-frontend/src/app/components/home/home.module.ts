import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HomeComponent } from './home.component';
import { RouterModule } from '@angular/router';
import { TranslateModule } from '@ngx-translate/core';
import { DialogModule } from '@ui-controls/dialogs/dialog.module';
import { HomeRoutingModule } from './home-routing.module';
import { CarouselModule } from 'ngx-bootstrap/carousel';

@NgModule({
  imports: [
    CommonModule,
    RouterModule,
    TranslateModule,
    DialogModule,
    HomeRoutingModule,
    CarouselModule
  ],
  declarations: [HomeComponent]
})
export class HomeModule { }
