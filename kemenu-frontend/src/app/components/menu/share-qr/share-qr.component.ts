import { Component, OnInit, Input } from '@angular/core';
import { BsModalRef } from 'ngx-bootstrap/modal';
import { MenuService } from '@services/menu/menu.service';
import { DomSanitizer } from '@angular/platform-browser';
import { environment } from '../../../../environments/environment';

@Component({
  selector: 'app-share-qr',
  templateUrl: './share-qr.component.html',
  styleUrls: ['./share-qr.component.css']
})
export class ShareQrComponent implements OnInit {

  @Input() shortUrlId: string;

  imageQRcode;
  url: string;

  constructor(
    public bsModalRef: BsModalRef,
    public menuService: MenuService,
    private sanitizer: DomSanitizer
  ) {
  }

  ngOnInit() {
    this.getQRcode();
    this.url = environment.apiBaseUrl + '/show/' + this.shortUrlId;
  }

  getQRcode() {
    this.menuService.getQRcode(this.shortUrlId)
      .subscribe((response: any) => {
        this.imageQRcode = this.sanitizer.bypassSecurityTrustResourceUrl('data:image/png;base64,' + response.qr);
      });
  }
}
