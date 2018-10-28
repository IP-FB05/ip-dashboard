import { Component, OnInit } from '@angular/core';
import { Process } from '../process';
import { PROCESSES } from '../mock-processes' 

@Component({
  selector: 'app-processes',
  templateUrl: './processes.component.html',
  styleUrls: ['./processes.component.css']
})
export class ProcessesComponent implements OnInit {
  process: Process ={
    id: 1,
    name: 'Prozess01',
    desc: 'BLABLABLABLA'
    
  };
  processes = PROCESSES;
 


  constructor() { }

  ngOnInit() {
  }


}
