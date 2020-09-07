import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NavbarComponent } from './navbar.component';
import { RouterModule } from '@angular/router';
import { BsDropdownModule } from 'ngx-bootstrap/dropdown';
import { TranslateModule } from '@ngx-translate/core';
import { CollapseModule } from 'ngx-bootstrap/collapse';
import { LoginModule } from '../login/login.module';

@NgModule({
  imports: [
    CommonModule,
    RouterModule,
    BsDropdownModule,
    CollapseModule,
    TranslateModule,
    LoginModule

  ],
  declarations: [NavbarComponent],
  exports: [NavbarComponent]
})
export class NavbarModule { }
