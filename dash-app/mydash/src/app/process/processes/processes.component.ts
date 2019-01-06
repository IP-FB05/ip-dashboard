import { Component, OnInit } from '@angular/core';
import { Process } from '../process';
import { ProcessInstance } from '../processInstance';
import { ProcessService } from '../process.service';
import { ProcessesDialogComponent } from '../processes-dialog/processes-dialog.component';
import { AuthorizationService } from '../../login/auth/authorization.service';
import { MatDialog, MatDialogConfig, MatSnackBar } from '@angular/material'
import { ProcessStartComponent } from '../process-start/process-start.component'


@Component({
  selector: 'app-processes',
  templateUrl: './processes.component.html',
  styleUrls: ['./processes.component.css']
})

export class ProcessesComponent implements OnInit {

  processes: Process[];
  //selectedProcess: Process;
  searchText: string;
  processInstance: ProcessInstance;

  constructor(private processService: ProcessService,
    public dialog: MatDialog,
    public authorizationService: AuthorizationService) { }

  ngOnInit() {
    this.getProcesses();
  }

  getProcesses(): void {
    this.processService.getProcesses()
      .subscribe(processes => this.processes = processes);
  }

  add(process: Process): void {
    this.processService.addProcess(process)
      .subscribe(process => {
        this.processes.push(process);
        this.getProcesses();
      });
  }

  startProcess(process: Process): void {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.disableClose = false;
    dialogConfig.autoFocus = true;

    dialogConfig.data = {
      processDefinitionId: process.camunda_processID
    };

    let dialogRef = this.dialog.open(ProcessStartComponent, dialogConfig);

    dialogRef.afterClosed().subscribe(data => {
      console.log(data);
    });
  }

  addInstance(processInstance: ProcessInstance) {
    this.processService.addInstance(processInstance);
  }

  delete(process: Process): void {
    this.processes = this.processes.filter(p => p !== process);
    this.processService.deleteProcess(process).subscribe();
    if (process.warFile == null) {
      this.processService.deleteBPMNFromFileServer(process.bpmn).subscribe();
    } else {
      this.processService.deleteProcessFilesFromFileServer(process.bpmn, process.warFile).subscribe();
    }
  }

  openDialog() {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.disableClose = false;
    dialogConfig.autoFocus = true;

    dialogConfig.data = {
      processID: 0,
      name: "",
      description: "",
      // TODO
      pic: "Placeholder bis Fileserver",
      warFile: "Placeholder bis Fileserver",
      bpmn: "Placeholder bis Fileserver",
      added: "Now",
      camunda_processID: "None"
    };

    let dialogRef = this.dialog.open(ProcessesDialogComponent, dialogConfig);

    dialogRef.afterClosed().subscribe(
      data => {
        console.log(data);
        this.add(data);
      });
  }


}
