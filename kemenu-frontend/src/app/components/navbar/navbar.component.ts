import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { BsModalRef, BsModalService } from 'ngx-bootstrap/modal';
import { LoginComponent } from '../login/login.component';
import { TranslateService } from '@ngx-translate/core';
import { AuthenticationService } from '@services/authentication/authentication.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {
  
  @ViewChild('navbarToggler') navbarToggler:ElementRef;

  public isCollapsed = true;
  public bsModalRef: BsModalRef;

  constructor(
    private modalService: BsModalService,
    private translate: TranslateService,
    public authService: AuthenticationService,
    private router: Router
  ) { }

  ngOnInit() {
  }

  openModalLogIn() {
    this.bsModalRef = this.modalService.show(LoginComponent);
  }

  changeLanguage(language: string) {
    this.translate.use(language);
  }

  onLogout() {
    this.authService.logout();
    this.router.navigateByUrl('');
    this.ngOnInit();
  }

  private navBarTogglerIsVisible() {
    return this.navbarToggler.nativeElement.offsetParent !== null;
  }

  collapseNav() {
    if (this.navBarTogglerIsVisible()) {
      this.navbarToggler.nativeElement.click();
    }
  }
}
