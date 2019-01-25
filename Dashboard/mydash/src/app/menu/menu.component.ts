import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';


// Import Models
// Import Components

// Import Services
import { TokenStorageService } from '../login/auth/token-storage.service';
import { AuthService } from '../login/auth/auth.service';



@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.css']
})

export class MenuComponent implements OnInit {

  info: any;
  showFiller = false;

  constructor(public router: Router, 
              private token: TokenStorageService,
              private authService: AuthService) { }

  ngOnInit() {
    this.info = {
      token: this.token.getToken(),
      username: this.token.getUsername(),
      authorities: this.token.getAuthorities()
    };
  }

  logout() {
    this.token.signOut();
    window.location.reload();
  }

  /*
  logout() {
    this.authService.logout();
  }
  */
}

export class FormFieldOverviewExample { }