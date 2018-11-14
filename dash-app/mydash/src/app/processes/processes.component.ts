import { Component, OnInit } from '@angular/core';
import { Process } from '../process';
import { ProcessService } from '../process.service';
import { MatDialog } from '@angular/material'
import { ProcessesDialogComponent} from '../processes-dialog/processes-dialog.component'


@Component({
  selector: 'app-processes',
  templateUrl: './processes.component.html',
  styleUrls: ['./processes.component.css']
})

export class ProcessesComponent implements OnInit {

  processes: Process[];
  //selectedProcess: Process;

  constructor(private processService: ProcessService, public dialog: MatDialog) { }

  ngOnInit() {
    this.getProcesses();
  }

  /*onSelect(process: Process): void {
    this.selectedProcess = process;
  }*/

  getProcesses(): void {
    this.processService.getProcesses()
      .subscribe(processes => this.processes = processes);
  }
  /* Original
    getProcesses(): void {
      this.processes = this.processService.getProcesses();
    }*/

  add(name: string): void {
    name = name.trim();
    if (!name) { return; }
    this.processService.addProcess({ name } as Process)
      .subscribe(process => {
        this.processes.push(process);
      });
  }


  delete(process: Process): void {
    this.processes = this.processes.filter(p => p !== process);
    this.processService.deleteProcess(process).subscribe();
  }

  openDialog() {
    let dialogRef = this.dialog.open(ProcessesDialogComponent, {
      width: '800px',
      height: '600px',
      //data: 'Test!'
    });
    dialogRef.afterClosed().subscribe(result => {
      console.log(`Dialog closed: ${result}`);
    });
  }
}
