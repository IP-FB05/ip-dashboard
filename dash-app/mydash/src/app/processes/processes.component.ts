import { Component, OnInit } from '@angular/core';
import { Process } from '../process';
import { ProcessService } from '../process.service';

@Component({
  selector: 'app-processes',
  templateUrl: './processes.component.html',
  styleUrls: ['./processes.component.css']
})

export class ProcessesComponent implements OnInit {
 
  processes: Process[]; 
  selectedProcess: Process;
  
  constructor(private processService: ProcessService) { }

  ngOnInit() {
    this.getProcesses();
  }

  onSelect(process: Process): void {
    this.selectedProcess = process;
  }

  getProcesses(): void {
    this.processes = this.processService.getProcesses();
  }


}
