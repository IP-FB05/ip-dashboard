import { Component, OnInit } from '@angular/core';
import { MatDialog, MatDialogConfig } from '@angular/material'

// Import Models
import { Process } from '../process';
import { ProcessInstance } from '../processInstance';

// Import Components
import { ProcessStartComponent } from '../process-start/process-start.component'
import { ProcessesDialogComponent } from '../processes-dialog/processes-dialog.component';

// Import Services
import { ProcessService } from '../process.service';
import { AuthorizationService } from '../../login/auth/authorization.service';
import { AuthService } from '../../login/auth/auth.service';

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
  processToUpload: Process;
  selectedUserGroups: number[];

  constructor(private processService: ProcessService,
    public dialog: MatDialog,
    public authorizationService: AuthorizationService,
    public authService: AuthService) { }

  ngOnInit() {
    this.getProcesses(this.authService.currentUser.role);
  }

  getProcesses(role: String): void {
    this.processService.getProcesses(role)
      .subscribe(processes => this.processes = processes);
  }

  add(process: Process, /*selectedUserGroups: number[]*/): void {
    /*
    if (selectedUserGroups != null) {
      console.log(selectedUserGroups);
      this.processService.addProcessWithUG(process, selectedUserGroups.map(Number));
      console.log(selectedUserGroups);
    } else {
      this.processService.addProcess(process)
        .subscribe(process => {
          this.processes.push(process);
          this.getProcesses();
        });
    }
    
  }
  */
    this.processService.addProcess(process)
      .subscribe(process => {
        this.processes.push(process);
        this.getProcesses(this.authService.currentUser.role);
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
    this.processService.deleteBPMNFromFileServer(process.bpmn).subscribe();
  }

  openDialog() {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.disableClose = false;
    dialogConfig.autoFocus = true;

    dialogConfig.data = {
      processID: 0,
      name: "",
      description: "",
      verbal: "",
      bpmn: "",
      added: "Now",
      camunda_processID: "None",
      allowed_usergroups: "",
    };

    let dialogRef = this.dialog.open(ProcessesDialogComponent, dialogConfig);

    dialogRef.afterClosed().subscribe(
      data => {

        this.processToUpload = new Process();
        this.processToUpload.name = data.name;
        this.processToUpload.description = data.description;
        this.processToUpload.verbal = data.verbal;
        this.processToUpload.bpmn = data.bpmn;
        this.processToUpload.added = data.added;
        this.processToUpload.camunda_processID = data.camunda_processID;
        this.processToUpload.allowed_usergroups = data.allowed_usergroups.toString();
        //this.selectedUserGroups = data.allowed_usergroups;


        //this.add(this.processToUpload, this.selectedUserGroups.map(Number));
        this.add(this.processToUpload);
      });
  }
}