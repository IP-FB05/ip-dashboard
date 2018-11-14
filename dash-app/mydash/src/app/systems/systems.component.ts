import { Component, OnInit } from '@angular/core';
import { System } from '../system';
import { SystemService } from '../system.service';
import { MatDialog } from '@angular/material'
import { SystemsDialogComponent } from '../systems-dialog/systems-dialog.component';

@Component({
  selector: 'app-systems',
  templateUrl: './systems.component.html',
  styleUrls: ['./systems.component.css']
})
export class SystemsComponent implements OnInit {

  systems: System[];

  constructor(private systemService: SystemService, public dialog: MatDialog) { }

  ngOnInit() {
    this.getSystems();
  }

  getSystems(): void {
    this.systemService.getSystems()
      .subscribe(systems => this.systems = systems);
  }

  add(name: string): void {
    name = name.trim();
    if(!name) { return; }
      this.systemService.addSystem({ name } as System)
      .subscribe(system => {
        this.systems.push(system);
      });
  }
  
  
  delete (system: System): void {
    this.systems = this.systems.filter(s => s !== system);
    this.systemService.deleteSystem(system).subscribe();
  }

  openDialog() {
    let dialogRef = this.dialog.open(SystemsDialogComponent, {
      width: '800px',
      height: '600px',
      //data: 'Test!'
    });
    dialogRef.afterClosed().subscribe(result => {
      console.log(`Dialog closed: ${result}`);
    });
  }

}



