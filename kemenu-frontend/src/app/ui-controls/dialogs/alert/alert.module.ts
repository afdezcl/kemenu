import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AlertComponent } from './alert.component';
import { AlertModule } from 'ngx-bootstrap/alert';

@NgModule({
  imports: [
    CommonModule,
    AlertModule.forRoot()
  ],
  declarations: [AlertComponent],
  exports: [    
    AlertComponent
  ]
})
export class AlertModule { }
