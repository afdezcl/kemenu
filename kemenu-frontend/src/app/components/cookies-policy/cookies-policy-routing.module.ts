import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { CookiesPolicyComponent } from './cookies-policy.component';

export const routes: Routes = [
    {
        path: '', component: CookiesPolicyComponent
    }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class CookiesPolicyRoutingModule { }
