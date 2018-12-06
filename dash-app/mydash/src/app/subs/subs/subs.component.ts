import { Component, OnInit } from '@angular/core';
import { Subs } from '../subs';
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
