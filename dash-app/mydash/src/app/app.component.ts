import { Component, OnInit } from '@angular/core';
import { CookieService} from 'ngx-cookie-service';  

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  title = 'Dashboard zur Prozesslandschaft des FB05';
  //cookieUsername = '';
  //cookieUsersGroup = '';
  constructor ( private cookieService: CookieService) {

  } 
  
  ngOnInit(): void {
    this.cookieService.set('UserName', 'NULL');
    //this.cookieUsername = this.cookieService.get('UserName');
  }
}
