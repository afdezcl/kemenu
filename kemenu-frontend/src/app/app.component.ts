import {Component, OnInit} from '@angular/core';
import {TranslateService} from '@ngx-translate/core';
import {filter} from 'rxjs/operators';
import {Router, NavigationEnd} from '@angular/router';
import {CookieService} from 'ngx-cookie-service';
import {AuthenticationService} from '@services/authentication/authentication.service';
import {Tokens} from '@models/auth/tokens.model';

declare var gtag;

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {

  private readonly JWT_TOKEN = 'JWT_TOKEN';
  private readonly REFRESH_TOKEN = 'REFRESH_TOKEN';
  cookieBASE64: string;

  constructor(
    translate: TranslateService,
    private router: Router,
    private cookieService: CookieService,
    private authService: AuthenticationService
  ) {
    translate.setDefaultLang(this.getBrowserLang());
    translate.use(this.getBrowserLang());

    const navEndEvents$ = this.router.events
      .pipe(
        filter(event => event instanceof NavigationEnd)
      );
    navEndEvents$.subscribe((event: NavigationEnd) => {
      gtag('config', 'UA-166680150-1', {
        page_path: event.urlAfterRedirects
      });
    });
  }

  ngOnInit() {
    if (this.cookieService.get('show_menu')) {
      localStorage.setItem('COOKIE-SHOW-MENU', this.cookieService.get('show_menu'));
      this.router.navigateByUrl('/show');
    }

    if (this.cookieService.get('confirmed_email')) {
      localStorage.setItem('COOKIE-CONFIRMED-EMAIL', this.cookieService.get('confirmed_email'));
      this.cookieService.delete('confirmed_email');
    }

    if (localStorage.getItem(this.JWT_TOKEN)) {
      this.checkExpirationToken();
    }
  }

  onActivate() {
    window.scroll(0, 0);
  }

  checkExpirationToken() {
    const tokens: Tokens = {
      jwt: localStorage.getItem(this.JWT_TOKEN),
      refreshToken: localStorage.getItem(this.REFRESH_TOKEN)
    };
    if (this.authService.refreshTokenHasExpirated(tokens)) {
      this.authService.logout();
    }
  }

  private getBrowserLang(): string {
    if (window.navigator.language.includes('es')) {
      return 'es';
    } else {
      return 'en';
    }
  }
}
