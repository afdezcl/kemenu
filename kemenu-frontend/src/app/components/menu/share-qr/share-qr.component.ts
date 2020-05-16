import { Component, OnInit, Input } from '@angular/core';
import { BsModalRef } from 'ngx-bootstrap/modal';
import { MenuService } from '@services/menu/menu.service';
import { DomSanitizer } from '@angular/platform-browser';


@Component({
  selector: 'app-share-qr',
  templateUrl: './share-qr.component.html',
  styleUrls: ['./share-qr.component.css']  
})
export class ShareQrComponent implements OnInit {
  
  @Input() customerId: string
  @Input() businessId: string
  @Input() menuId: string

  imageQRcode;
  url: string;

  constructor(
    public bsModalRef: BsModalRef,
    public _menuService: MenuService,
    private _sanitizer: DomSanitizer    
  ) { }

  ngOnInit() {
    this.getQRcode();
    this.url = `https://kemenu.com/customers/`+this.customerId+`/businesses/`+
                this.businessId+`/menus/`+this.menuId
  }

  getQRcode() {
    const params = {
      customerId: this.customerId,
      businessId: this.businessId,
      menuId: this.menuId
    }
    this._menuService.getQRcode(params)
      .subscribe((response: any) => {      
        this.imageQRcode = this._sanitizer.bypassSecurityTrustResourceUrl('data:image/png;base64,' + response.qr);                        
      })
  }

}
