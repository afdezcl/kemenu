import {Component, OnInit} from '@angular/core';
import {TranslateService} from '@ngx-translate/core';

@Component({
  selector: 'app-faq',
  templateUrl: './faq.component.html',
  styleUrls: ['./faq.component.scss']
})
export class FaqComponent implements OnInit {

  faq;
  constructor(
    private translate: TranslateService
  ) {
  }

  ngOnInit() {
    this.faq = this.translate.getTranslation(this.translate.currentLang).subscribe(trans => trans);
  }
}
