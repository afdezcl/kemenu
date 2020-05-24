import {Component, OnInit} from '@angular/core';
import {AlertsService} from '@services/alerts/alerts.service';
import {TranslateService} from '@ngx-translate/core';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  constructor(
    private alertService: AlertsService,
    private translate: TranslateService
  ) {
  }

  ngOnInit() {
    this.checkVerifyEmail();
  }

  checkVerifyEmail() {
    this.alertService.clear();
    if (Object.is(localStorage.getItem('COOKIE-CONFIRMED-EMAIL'), 'true')) {
      this.alertService.success(this.translate.instant('Verify Email Success'));
    } else {
      this.alertService.error(this.translate.instant('Verify Email Error'));
    }
    localStorage.removeItem('COOKIE-CONFIRMED-EMAIL');
  }
}
