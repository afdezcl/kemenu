import { Component, OnInit } from '@angular/core';
import { BsModalService, BsModalRef } from 'ngx-bootstrap/modal';
import { LoginComponent } from '../login/login.component';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {
  
  bsModalRef: BsModalRef;

  constructor(
    private modalService: BsModalService,
  ) { }

  ngOnInit() {
  }
  
  openModalLogIn(){    
    this.bsModalRef = this.modalService.show(LoginComponent);
  }
}
