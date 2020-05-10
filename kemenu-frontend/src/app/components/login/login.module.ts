import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LoginComponent } from './login.component';
import { RouterModule } from '@angular/router';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { TranslateModule } from '@ngx-translate/core';
import { RECAPTCHA_V3_SITE_KEY, RecaptchaV3Module, RecaptchaFormsModule } from 'ng-recaptcha';
import { environment } from '@environments/environment';


@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    TranslateModule,
    RouterModule,
    RecaptchaV3Module,
    RecaptchaFormsModule
  ],
  declarations: [LoginComponent],
  
  providers: [
    { provide: RECAPTCHA_V3_SITE_KEY, useValue: environment.recaptchaToken }
  ]
})
export class LoginModule { }
