import { Component, OnInit } from '@angular/core';

// Import Models
import { Subs } from '../subs';

// Import Components

// Import Services
import { SubsService } from '../subs.service';

@Component({
  selector: 'app-subs',
  templateUrl: './subs.component.html',
  styleUrls: ['./subs.component.css']
})
export class SubsComponent implements OnInit {

  subs: Subs[];

  constructor(private subsService:SubsService) { }

  ngOnInit() {
  
  }

}
