import { Component, OnInit } from '@angular/core';
import { MatDialog, MatDialogConfig } from '@angular/material';

// Import Models
import { ProcessInstance } from '../process/processInstance';
import { Tasks } from './tasks';

// Import Components
import { DoTaskComponent } from './do-task/do-task.component';

// Import Services
import { MyprocessesService } from './myprocesses.service';
import { AuthService } from '../login/auth/auth.service';

@Component({
  selector: 'app-myprocesses',
  templateUrl: './myprocesses.component.html',
  styleUrls: ['./myprocesses.component.css']
})
export class MyprocessesComponent implements OnInit {

  public instances: ProcessInstance[];
  public tasks: Tasks[];
  public username: String;
  
  constructor(private myprocessService: MyprocessesService, public dialog: MatDialog, private authService: AuthService) { }

  ngOnInit() {
    this.getInstances();
    this.getTasks();
    this.username = JSON.parse(sessionStorage.getItem('currentUser')).id; 
  }

  getInstances(): void {
    this.myprocessService.getProcessInstances()
      .subscribe(instances => this.instances = instances);
  }

  getTasks(): void {
    this.myprocessService.getTasks()
      .subscribe(tasks => this.tasks = tasks);
  }

  doTask(instance: ProcessInstance) {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.disableClose = false;
    dialogConfig.autoFocus = true;

    dialogConfig.data = {
      instance: instance.id
    };

    let dialogRef = this.dialog.open(DoTaskComponent, dialogConfig);

    dialogRef.afterClosed().subscribe(data => {
      console.log(data);
    });

  }

}
