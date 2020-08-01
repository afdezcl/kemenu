import {Component, EventEmitter, OnInit, Output} from '@angular/core';

@Component({
  selector: 'app-cookies-banner',
  templateUrl: './cookies-banner.component.html',
  styleUrls: ['./cookies-banner.component.scss']
})
export class CookiesBannerComponent implements OnInit {

  @Output() accepted = new EventEmitter();


  constructor() { }

  ngOnInit() {
  }

  onAccept() {
    this.accepted.emit();
  }

}
