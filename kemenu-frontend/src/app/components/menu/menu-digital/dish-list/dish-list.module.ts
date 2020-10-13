import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DishListComponent } from './dish-list.component';
import { TranslateModule } from '@ngx-translate/core';
import { AccordionModule } from 'ngx-bootstrap/accordion';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { DragDropModule } from '@angular/cdk/drag-drop';
import { LazyImgDirective } from '@ui-controls/directives/lazy-img.directive';

@NgModule({
  imports: [
    CommonModule,
    TranslateModule,
    AccordionModule,
    FormsModule,
    ReactiveFormsModule,
    DragDropModule,
  ],
  exports: [
    DishListComponent
  ],
  declarations: [
    DishListComponent
  ],
  providers: [
    LazyImgDirective
  ]
})
export class DishListModule {
}
