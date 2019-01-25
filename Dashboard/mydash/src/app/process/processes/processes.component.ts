import { Component, OnInit } from '@angular/core';
import { MatDialog, MatDialogConfig, MatDialogRef } from '@angular/material'

// Import Models
import { Process } from '../process';
import { ProcessInstance } from '../processInstance';

// Import Components
import { ProcessStartComponent } from '../process-start/process-start.component'
import { ProcessesDialogComponent } from '../processes-dialog/processes-dialog.component';
import { ProcessesDeleteDialogComponent } from '../processes-delete-dialog/processes-delete-dialog.component';
import { LoginComponent } from 'src/app/login/login.component';

// Import Services
import { ProcessService } from '../process.service';
import { AuthorizationService } from '../../login/auth/authorization.service';
import { AuthService } from '../../login/auth/auth.service';
import { TokenStorageService } from 'src/app/login/auth/token-storage.service';
import { MenuComponent } from 'src/app/menu/menu.component';



@Component({
  selector: 'app-processes',
  templateUrl: './processes.component.html',
  styleUrls: ['./processes.component.css']
})

export class ProcessesComponent implements OnInit {

  processes: Process[];
  searchText: string;
  processInstance: ProcessInstance;
  processToUpload: Process;
  dialogRef: MatDialogRef<ProcessesDeleteDialogComponent>;
  role: String;

  constructor(private processService: ProcessService,
    public dialog: MatDialog,
    public authorizationService: AuthorizationService,
    public authService: AuthService,
    private token: TokenStorageService) { }

  ngOnInit() {
    this.role = this.token.getAuthorities().toString().toLowerCase();
    this.getProcesses(this.role);
  }

  getProcesses(role: String): void {
    this.processService.getProcesses(role)
      .subscribe(processes => this.processes = processes);
  }

  add(process: Process): void {
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
    this.processService.deleteProcessFromCamunda(process).subscribe();
  }

  openDeleteDialog(process: Process) {
    this.dialogRef = this.dialog.open(ProcessesDeleteDialogComponent, {
      disableClose: false
    });
    
    this.dialogRef.componentInstance.confirmMessage = "Wollen Sie diesen Prozess wirklich löschen ?"

    this.dialogRef.afterClosed().subscribe(data => {
      if(data) this.delete(process);

      this.dialogRef = null;
    });
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

        this.add(this.processToUpload);
      });
  }
}
