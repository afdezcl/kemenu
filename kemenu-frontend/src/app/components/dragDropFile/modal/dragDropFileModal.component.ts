import {Component} from '@angular/core';

@Component({
  selector: 'app-drag-drop-file-modal',
  templateUrl: './dragDropFileModal.component.html',
  styleUrls: ['./dragDropFileModal.component.css']
})
export class DragDropFileModalComponent {

  file: any;

  uploadFile(event) {
    this.file = event.name;
  }
}
