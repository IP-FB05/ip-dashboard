import { Component, OnInit } from '@angular/core';
import { AuthorizationService } from './login/auth/authorization.service';
@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  title = 'Dashboard zur Prozesslandschaft des FB05';
  
  constructor () {
  }  

  ngOnInit(): void {
  }
}
