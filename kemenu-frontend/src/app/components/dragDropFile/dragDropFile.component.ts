import {Component, Input, OnInit} from '@angular/core';
import {BsModalRef, BsModalService} from 'ngx-bootstrap/modal';
import {DragDropFileModalComponent} from './modal/dragDropFileModal.component';

@Component({
  selector: 'app-drag-drop-file',
  templateUrl: './dragDropFile.component.html',
  styleUrls: ['./dragDropFile.component.css']
})
export class DragDropFileComponent implements OnInit {

  @Input() buttonText: string;

  bsModalRef: BsModalRef;

  constructor(private modalService: BsModalService) {
  }

  ngOnInit(): void {
  }

  openModal() {
    this.bsModalRef = this.modalService.show(DragDropFileModalComponent)
  }
}
