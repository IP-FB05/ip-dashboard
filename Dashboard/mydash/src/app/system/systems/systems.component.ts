import { Component, OnInit } from '@angular/core';
import { MatDialog, MatDialogConfig, MatDialogRef } from '@angular/material'

// Import Models
import { System } from '../system';

// Import Components
import { SystemsDialogComponent } from '../systems-dialog/systems-dialog.component';
import { SystemsDeleteDialogComponent } from '../systems-delete-dialog/systems-delete-dialog.component';

// Import Services
import { SystemService } from '../system.service';
import { TokenStorageService } from 'src/app/login/auth/token-storage.service';
import { AuthService } from 'src/app/login/auth/auth.service';

@Component({
  selector: 'app-systems',
  templateUrl: './systems.component.html',
  styleUrls: ['./systems.component.css']
})
export class SystemsComponent implements OnInit {

  public systems: System[];
  searchText: string;
  dialogRef: MatDialogRef<SystemsDeleteDialogComponent>;

  constructor(private systemService: SystemService, 
              public dialog: MatDialog,
              private token: TokenStorageService,
              private authService: AuthService) { }

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

  openDeleteDialog(system: System) {
    this.dialogRef = this.dialog.open(SystemsDeleteDialogComponent, {
      disableClose: false
    });
    
    this.dialogRef.componentInstance.confirmMessage = "Wollen Sie dieses System wirklich löschen ?"

    this.dialogRef.afterClosed().subscribe(data => {
      if(data) this.delete(system);

      this.dialogRef = null;
    });
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



