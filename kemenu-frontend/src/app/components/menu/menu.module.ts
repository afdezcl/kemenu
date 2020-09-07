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
import { MenuDigitalModule } from './menu-digital/menu-digital.module';
import { MenuImageModule } from './menu-image/menu-image.module';
import { MenuAdvancedSettingsComponent } from './menu-advanced-settings/menu-advanced-settings.component';
import { MenuRoutingModule } from './menu-routing.module';
import { UploadImageButtonModule } from '../uploadImageButton/uploadImageButton.module';

@NgModule({
  imports: [
    HttpClientModule,
    CommonModule,
    TranslateModule,
    AccordionModule,
    FormsModule,
    ReactiveFormsModule,
    ShareButtonsModule,
    ShareIconsModule,
    MenuDigitalModule,
    MenuImageModule,
    MenuRoutingModule,
    UploadImageButtonModule
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
