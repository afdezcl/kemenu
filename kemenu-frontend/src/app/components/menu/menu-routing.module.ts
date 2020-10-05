import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { MenuComponent } from './menu.component';
import { AuthGuard } from '@ui-controls/guards/auth/auth.guard';
import { SelectMenuComponent } from './select-menu/select-menu.component';

export const routes: Routes = [
  {
    path: '',
    children: [
      { path: '', component: SelectMenuComponent, canActivate: [AuthGuard] },
      { path: ':id', component: MenuComponent, canActivate: [AuthGuard] }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class MenuRoutingModule { }
