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
import { MenuDigitalModule } from './menu-digital/menu-digital.module';
import { MenuAdvancedSettingsComponent } from './menu-advanced-settings/menu-advanced-settings.component';
import { MenuRoutingModule } from './menu-routing.module';
import { UploadImageButtonModule } from '../uploadImageButton/uploadImageButton.module';
import { MenuImageComponent } from './menu-image/menu-image.component';
import { CreateMenuNameComponent } from './menu-digital/create-menu-name/create-menu-name.component';
import { TabsModule } from 'ngx-bootstrap/tabs';

import { SelectMenuComponent } from './select-menu/select-menu.component';


@NgModule({
  imports: [
    CommonModule,
    TranslateModule,
    AccordionModule,
    FormsModule,
    ReactiveFormsModule,
    ShareButtonsModule,
    ShareIconsModule,
    MenuDigitalModule,
    MenuRoutingModule,
    UploadImageButtonModule,
    TabsModule.forRoot()
  ],
  declarations: [
    MenuComponent,
    CreateMenuNameComponent,
    CreateSectionComponent,
    CreateDishComponent,
    ShareQrComponent,
    MenuAdvancedSettingsComponent,
    MenuImageComponent,
    SelectMenuComponent,
  ]
})
export class MenuModule {
}
