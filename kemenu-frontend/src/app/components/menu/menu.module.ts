import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MenuComponent } from './menu.component';
import { TranslateModule } from '@ngx-translate/core';
import { AccordionModule } from 'ngx-bootstrap/accordion';
import { CreateSectionComponent } from './menu-digital/create-section/create-section.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { CreateDishComponent } from './menu-digital/create-dish/create-dish.component';
import { ShareQrComponent } from './share-qr/share-qr.component';
import { ShareButtonsModule } from 'ngx-sharebuttons/buttons';
import { ShareIconsModule } from 'ngx-sharebuttons/icons';
import { HttpClientModule } from '@angular/common/http';
import { DragDropModule } from '@angular/cdk/drag-drop';
import { MenuDigitalModule } from './menu-digital/menu-digital.module';
import { MenuImageModule } from './menu-image/menu-image.module';
import { MenuAdvancedSettingsComponent } from './menu-advanced-settings/menu-advanced-settings.component';
import { UploadImageButtonModule } from '../uploadImageButton/uploadImageButton.module';
import { ToastrModule } from 'ngx-toastr';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

@NgModule({
  imports: [
    HttpClientModule,
    CommonModule,
    TranslateModule,
    AccordionModule,
    FormsModule,
    ReactiveFormsModule,
    DragDropModule,
    ShareButtonsModule.withConfig({
      debug: true
    }),
    ShareIconsModule.forRoot(),
    ToastrModule.forRoot(),
    MenuDigitalModule,
    MenuImageModule,
    UploadImageButtonModule,
    BrowserAnimationsModule
  ],
  declarations: [
    MenuComponent,
    CreateSectionComponent,
    CreateDishComponent,
    ShareQrComponent,
    MenuAdvancedSettingsComponent
  ]
})
export class MenuModule {
}
