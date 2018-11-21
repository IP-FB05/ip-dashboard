import { Component } from '@angular/core';
import { Router } from '@angular/router';
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
  constructor(public router: Router) { }
}
export class FormFieldOverviewExample { }