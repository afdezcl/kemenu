import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

export const routes: Routes = [

  {
    path: '', loadChildren: () =>
      import('./components/home/home.module').then(module => module.HomeModule)
  },
  {
    path: 'register', loadChildren: () =>
      import('./components/register/register.module').then(module => module.RegisterModule)
  },
  {
    path: 'cookies-policy', loadChildren: () =>
      import('./components/cookies-policy/cookies-policy.module').then(module => module.CookiesPolicyModule)
  },
  {
    path: 'aboutUs', loadChildren: () =>
      import('./components/aboutUs/aboutUs.module').then(module => module.AboutUsModule)
  },
  {
    path: 'menu', loadChildren: () =>
      import('./components/menu/menu.module').then(module => module.MenuModule)
  },
  {
    path: 'forgotPassword', loadChildren: () =>
      import('./components/forgotPassword/forgotPassword.module').then(module => module.ForgotPasswordModule)
  },
  {
    path: 'changePassword', loadChildren: () =>
      import('./components/changePassword/changePassword.module').then(module => module.ChangePasswordModule)
  },
  {
    path: 'profile', loadChildren: () =>
      import('./components/profile/profile.module').then(module => module.ProfileModule)
  },
  {
    path: 'demo', loadChildren: () =>
      import('./components/customer/customer.module').then(module => module.CustomerModule)
  },
  {
    path: 'show', loadChildren: () =>
      import('./components/customer/customer.module').then(module => module.CustomerModule)
  },
  {
    path: '**', redirectTo: ''
  }

];


@NgModule({
  declarations: [],
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
