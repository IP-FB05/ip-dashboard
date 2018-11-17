import { Component, OnInit } from '@angular/core';
import { System } from '../system';
import { SystemService } from '../system.service';
import { MatDialog, MatDialogConfig } from '@angular/material'
import { SystemsDialogComponent } from '../systems-dialog/systems-dialog.component';

@Component({
  selector: 'app-systems',
  templateUrl: './systems.component.html',
  styleUrls: ['./systems.component.css']
})
export class SystemsComponent implements OnInit {

  public systems: System[];

  constructor(private systemService: SystemService, public dialog: MatDialog) { }

  ngOnInit() {
    this.getSystems();
  }

  getSystems(): void {
    this.systemService.getSystems()
      .subscribe(systems => this.systems = systems);
  }

  add(sys: System): void {
      this.systemService.addSystem(sys)
      .subscribe(system => {
        this.systems.push(system);
        this.getSystems();
      });
  }
  
  delete (system: System): void {
    this.systems = this.systems.filter(s => s !== system);
    this.systemService.deleteSystem(system).subscribe();
  }

  openDialog() {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.disableClose = false;
    dialogConfig.autoFocus = true;

    dialogConfig.data = {
      systemID: 0,
      name: "",
      beschreibung: "",
      link: ""
    };

    let dialogRef = this.dialog.open(SystemsDialogComponent, dialogConfig);

    dialogRef.afterClosed().subscribe(data => {
      console.log(data);
      this.add(data);
    });
  }

}



