import { Component, OnInit } from '@angular/core';
import { AppVersionService } from '@services/app-version/app-version.service';
import { AppVersion } from '@models/appVersion.interface';
import { FaqComponent } from '../faq/faq.component';

@Component({
  selector: 'app-footer',
  templateUrl: './footer.component.html',
  styleUrls: ['./footer.component.css'],
  providers: [AppVersionService]
})
export class FooterComponent implements OnInit {

  appVersion;

  constructor(
    private appVersionService: AppVersionService
  ) {
  }

  ngOnInit() {
    this.appVersionService.getVersionApp()
      .subscribe((result: AppVersion) => {
        this.appVersion = result.version;
      });
  }
}
