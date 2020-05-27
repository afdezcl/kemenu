import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ChangePasswordComponent } from './change-password/change-password.component';
import { AuthGuard } from '@ui-controls/guards/auth/auth.guard';
import { ProfileComponent } from './profile.component';

export const routes: Routes = [
  { path: 'profile', 
    component: ProfileComponent,  
    children: [
        { path: 'new-password', component: ChangePasswordComponent,  canActivate: [AuthGuard] }
    ] 
  }
];


@NgModule({
  imports: [
    RouterModule.forChild(routes)
  ],
  exports: [
    RouterModule
  ]
})
export class ProfileRoutingModule { }
