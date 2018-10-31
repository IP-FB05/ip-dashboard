import { Component, OnInit, Input } from '@angular/core';
import { Process } from '../process'; 

@Component({
  selector: 'app-process-detail',
  templateUrl: './process-detail.component.html',
  styleUrls: ['./process-detail.component.css']
})
export class ProcessDetailComponent implements OnInit {

  @Input() process: Process;

  constructor() { }

  ngOnInit() {
  }

}
