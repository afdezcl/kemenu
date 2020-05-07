import { Component, OnInit } from '@angular/core';
import { BsModalRef } from 'ngx-bootstrap/modal';
import { Subject } from 'rxjs';

@Component({
  selector: 'app-confirmDialog',
  templateUrl: './confirmDialog.component.html',
  styleUrls: ['./confirmDialog.component.css']
})
export class ConfirmDialogComponent implements OnInit {

  public title: string;
  public message: string;
  public onClose: Subject<boolean>;


  constructor(
    public bsModalRef: BsModalRef,
  ) { }

  ngOnInit() {
    this.onClose = new Subject();
  }

  public decline(): void {
    this.onClose.next(false);
    this.bsModalRef.hide();
  }

  public accept(): void {
    this.onClose.next(true);
    this.bsModalRef.hide();
  }

}