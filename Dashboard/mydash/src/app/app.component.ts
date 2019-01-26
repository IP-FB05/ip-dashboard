import { Component, OnInit } from '@angular/core';
import { TokenStorageService } from './login/auth/token-storage.service';
import { AuthService } from './login/auth/auth.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  title = 'Dashboard zur Prozesslandschaft des FB05';
  
  constructor (private token: TokenStorageService,
               private authService: AuthService) {
  }  

  ngOnInit(): void {
    if (this.token.getToken()) this.authService.isLoggedin = true;
    else window.sessionStorage.clear();
  }
  
}
