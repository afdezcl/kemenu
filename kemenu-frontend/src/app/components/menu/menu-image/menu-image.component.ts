import {Component, EventEmitter, Input, Output} from '@angular/core';
import {Menu} from '@models/menu/menu.model';
import {BsModalRef, BsModalService} from 'ngx-bootstrap/modal';
import {ConfirmDialogComponent} from '@ui-controls/dialogs/confirmDialog/confirmDialog.component';
import {TranslateService} from '@ngx-translate/core';

@Component({
  selector: 'app-menu-image',
  templateUrl: './menu-image.component.html',
  styleUrls: ['./menu-image.component.scss']
})
export class MenuImageComponent {

  @Input() editMode: boolean;
  @Output() uploadedMenuImage: EventEmitter<Menu> = new EventEmitter<Menu>();
  @Input() menu: Menu;
  public modalReference: BsModalRef;

  constructor(
    private modalService: BsModalService,
    private translate: TranslateService
  ) {
  }

  removeMenuImage() {
    const initialState = {
      title: this.translate.instant('Delete Menu Image title'),
      message: this.translate.instant('Delete Menu Image description')
    };

    this.modalReference = this.modalService.show(ConfirmDialogComponent, {initialState});
    this.modalReference.content.onClose.subscribe((canDelete: boolean) => {
      if (canDelete) {
        this.menu.imageUrl = '';
        this.onSaveMenu();
      }
    });
  }

  onSaveMenu() {
    this.uploadedMenuImage.emit(this.menu);
  }

  handleFileUpload(event) {
    if (event) {
      this.menu.imageUrl = event.url;
      this.onSaveMenu();
    }
  }
}
