import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs';
import { AlertsService } from 'src/app/services/alerts/alerts.service';


@Component({ selector: 'app-alert', templateUrl: 'alert.component.html' })

export class AlertComponent implements OnInit, OnDestroy {
  private subscription: Subscription;
  public message: any;  

  constructor(private alertService: AlertsService) { }

  ngOnInit() {
    this.subscription = this.alertService.getAlert()
      .subscribe(message => {
        this.message = message;        
      });
  }

  ngOnDestroy() {
    this.subscription.unsubscribe();
  }
}
