import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ProfileComponent } from './profile.component';
import { AuthGuard } from '@ui-controls/guards/auth/auth.guard';
import { ResetPasswordComponent } from './resetPassword/resetPassword.component';
import { EditInformationComponent } from './edit-information/edit-information.component';

export const routes: Routes = [
    {
        path: '', component: ProfileComponent, canActivate: [AuthGuard],        
    },
    { 
      path: 'new-password', component: ResetPasswordComponent, canActivate: [AuthGuard] 
    },
    { 
      path: 'edit', component: EditInformationComponent, canActivate: [AuthGuard] 
    }

];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ProfileRoutingModule { }

