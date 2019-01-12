import { Component } from '@angular/core';
import { Router } from '@angular/router';


// Import Models
// Import Components

// Import Services
import { AuthService } from '../login/auth/auth.service';


@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.css']
})

/*export class MenuComponent implements OnInit {

  constructor() { }

  ngOnInit() {
  }
}*/


export class MenuComponent {
  showFiller = false;
  constructor(public router: Router, 
              private authService: AuthService) { 
  }

  logout() {
    this.authService.logout();
  }
}
export class FormFieldOverviewExample { }