import { Component, OnInit } from '@angular/core';
import { BsModalRef, BsModalService } from 'ngx-bootstrap/modal';
import { LoginComponent } from '../login/login.component';
import { TranslateService } from '@ngx-translate/core';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {
  
  isCollapsed = true;
  bsModalRef: BsModalRef;

  constructor(
    private modalService: BsModalService,
    private translate: TranslateService
  ) { }

  ngOnInit() {
  }

  openModalLogIn() {
    this.bsModalRef = this.modalService.show(LoginComponent);
  }
  
  changeLanguage(language: string) {
    this.translate.use(language);
  }

}
