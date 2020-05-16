import { Component, OnInit } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';
import { filter } from 'rxjs/operators';
import { Router, NavigationEnd } from '@angular/router';
import { CookieService } from 'ngx-cookie-service';

declare var gtag;

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit  {
  title = 'kemenu-frontend';

  constructor(
    translate: TranslateService,
    private router: Router,
    private cookieService: CookieService
  ) {
    translate.setDefaultLang('es');
    translate.use('es');

    const navEndEvents$ = this.router.events
      .pipe(
        filter(event => event instanceof NavigationEnd)
      );
    navEndEvents$.subscribe((event: NavigationEnd) => {
      gtag('config', 'UA-166680150-1', {
        'page_path': event.urlAfterRedirects
      });
    });
  }
  
  ngOnInit() {        
    if(this.cookieService.get('show_menu')){      
      localStorage.setItem('COOKIE-SHOW-MENU', this.cookieService.get('show_menu'))
      this.router.navigateByUrl('/show')
    }
  }

  onActivate() {
    window.scroll(0, 0);
  }

}
