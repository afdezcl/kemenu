import { Component, OnInit } from '@angular/core';
import { BsModalRef } from 'ngx-bootstrap/modal';
@Component({
  selector: 'app-share-qr',
  templateUrl: './share-qr.component.html',
  styleUrls: ['./share-qr.component.css']
})
export class ShareQrComponent implements OnInit {

  
  constructor(
    public bsModalRef: BsModalRef
  ) { }

  ngOnInit() {
  }

}
