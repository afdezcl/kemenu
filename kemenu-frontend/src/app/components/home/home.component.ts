import { Component, OnInit } from '@angular/core';
import { AlertsService } from '@services/alerts/alerts.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  constructor(
    private alertService: AlertsService,
  ) { }

  ngOnInit() {
    this.checkVerifyEmail();
  }

  checkVerifyEmail(){
    this.alertService.clear();
    if(Object.is(localStorage.getItem('COOKIE-CONFIRMED-EMAIL'), 'true')){
      this.alertService.success('Su correo ha sido confirmado con éxito, puede iniciar sesión.');
    } else {
      this.alertService.error('El enlace de confirmación ha expirado.');
    }
    localStorage.removeItem('COOKIE-CONFIRMED-EMAIL')
  }

}
