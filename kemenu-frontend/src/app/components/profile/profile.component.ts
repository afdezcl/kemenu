import {Component, OnInit} from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css'],
  providers: []
})
export class ProfileComponent implements OnInit {

  constructor(
    private router: Router
  ) { }

  ngOnInit() {
  }



  goToChangePassword(){
    
  }
}
