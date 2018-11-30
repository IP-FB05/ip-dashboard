import { Component, OnInit } from '@angular/core';
import { Process } from '../process';
import { ProcessService } from '../process.service';
import { MatDialog, MatDialogConfig, MatSnackBar } from '@angular/material'
import { ProcessesDialogComponent} from '../processes-dialog/processes-dialog.component'


@Component({
  selector: 'app-processes',
  templateUrl: './processes.component.html',
  styleUrls: ['./processes.component.css']
})

export class ProcessesComponent implements OnInit {

  processes: Process[];
  //selectedProcess: Process;
  searchText:string;

  constructor(private processService: ProcessService, public dialog: MatDialog, public snackBar:MatSnackBar) { }

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

  add(process: Process): void {
    this.processService.addProcess(process)
      .subscribe(process => {
        this.processes.push(process);
        this.getProcesses();
      });
  }


  delete(process: Process): void {
    this.processes = this.processes.filter(p => p !== process);
    this.processService.deleteProcess(process).subscribe();
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
      varFile: "Placeholder bis Fileserver",
      bpmn: "Placeholder bis Fileserver",
      added: "Now"
    };

    let dialogRef = this.dialog.open(ProcessesDialogComponent, dialogConfig);

    dialogRef.afterClosed().subscribe(data => {
      console.log(data);
      this.add(data);
    });
  }

  openSnackBar() {
    this.snackBar.open('Löschen erfolgreich' , '', {
      duration: 2000,
    });
  }
}
