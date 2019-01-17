import { Component, OnInit } from '@angular/core';
import { MatDialog, MatDialogConfig } from '@angular/material'

// Import Models
import { System } from '../system';

// Import Components
import { SystemsDialogComponent } from '../systems-dialog/systems-dialog.component';

// Import Services
import { SystemService } from '../system.service';
import { AuthorizationService } from 'src/app/login/auth/authorization.service';


@Component({
  selector: 'app-systems',
  templateUrl: './systems.component.html',
  styleUrls: ['./systems.component.css']
})
export class SystemsComponent implements OnInit {

  public systems: System[];
  searchText: string;

  constructor(private systemService: SystemService, 
              public dialog: MatDialog,
              private authorizationService: AuthorizationService) { }

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
      description: "",
      link: ""
    };

    let dialogRef = this.dialog.open(SystemsDialogComponent, dialogConfig);

    dialogRef.afterClosed().subscribe(data => {
      console.log(data);
      this.add(data);
    });
  }
}


