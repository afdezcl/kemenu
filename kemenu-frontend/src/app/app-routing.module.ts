import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { RegisterComponent } from './components/register/register.component';
import { ForgotPasswordComponent } from './components/forgotPassword/forgotPassword.component';
import { HomeComponent } from './components/home/home.component';
import { MenuComponent } from './components/menu/menu.component';
import { CustomerComponent } from './components/customer/customer.component';
import { AuthGuard } from '@ui-controls/guards/auth/auth.guard';
import { ProfileComponent } from './components/profile/profile.component';
import { ChangePasswordComponent } from './components/profile/change-password/change-password.component';
import { EditInformationComponent } from './components/profile/edit-information/edit-information.component';
import {ChangePasswordComponent} from './components/changePassword/changePassword.component';
import {AboutUsComponent} from './components/aboutUs/aboutUs.component';

export const routes: Routes = [

  { path: '', component: HomeComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'forgotPassword', component: ForgotPasswordComponent },
  { path: 'changePassword', component: ChangePasswordComponent },
  { path: 'menu', component: MenuComponent, canActivate: [AuthGuard]  },
  { path: 'profile', component: ProfileComponent, canActivate: [AuthGuard] },
  { path: 'profile/new-password', component: ChangePasswordComponent,  canActivate: [AuthGuard] },
  { path: 'profile/edit', component: EditInformationComponent,  canActivate: [AuthGuard] },
  { path: 'demo', component: CustomerComponent },
  { path: 'show', component: CustomerComponent },
  { path: 'aboutUs', component: AboutUsComponent },
  { path: '**', redirectTo: '' }

];


@NgModule({
  declarations: [],
  imports: [
    RouterModule.forRoot(routes)
  ],
  exports: [
    RouterModule
  ]
})
export class AppRoutingModule { }
