import { Component, OnInit } from '@angular/core';
import { MatDialog, MatDialogConfig, MatSnackBar } from '@angular/material'

// Import Models
import { Process } from '../process/process';
import { Document } from '../document/document';

// Import Components
import { ProcessesDialogComponent} from '../process/processes-dialog/processes-dialog.component'
import { DocumentsDialogComponent } from '../document/documents-dialog/documents-dialog.component';

// Import Services
import { ProcessService } from '../process/process.service';
import { DocumentService } from '../document/document.service';
import { AuthService } from '../login/auth/auth.service';
import { TokenStorageService } from '../login/auth/token-storage.service';



@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: [ './dashboard.component.css' ]
})
export class DashboardComponent implements OnInit {
  processes: Process[] = [];
  documents: Document[] = [];

  constructor(
    private processService: ProcessService,
    private documentsService: DocumentService, 
    public dialog: MatDialog,
    public snackBar: MatSnackBar,
    private authService: AuthService,
    private token: TokenStorageService) { }

  ngOnInit() {
    this.getProcesses();
    this.getDocuments();
  }

  getProcesses(): void {
    this.processService.getProcesses(this.token.getAuthoritiestoString())
      .subscribe(processes => this.processes = processes);
  }

  /*addProcess(process: Process): void {
    this.processService.addProcess(process)
      .subscribe(process => {
        this.processes.push(process);
        this.getProcesses();
      });
  }*/

  getDocuments(): void {
    this.documentsService.getDocumentLimit()
      .subscribe(documents => this.documents = documents);
  }

  addDocument(doc : Document): void {
    this.documentsService.addDocument(doc)
      .subscribe(dokument => {
        
        this.documents.push(dokument);
        this.getDocuments();
      });
  }

  openDialogProcess() {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.disableClose = false;
    dialogConfig.autoFocus = true;

    dialogConfig.data = {
      processID: 0,
      name: "",
      description: "",
      verbal: "",
      bpmn: "",
      added: "Now"
    };

    let dialogRef = this.dialog.open(ProcessesDialogComponent, dialogConfig);

    dialogRef.afterClosed().subscribe(data => {
      console.log(data);
      //this.addProcess(data);
    });
  }

  openDialogDocument() {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.disableClose = false;
    dialogConfig.autoFocus = true;

    dialogConfig.data = {
      dokumentID: 0,
      kategoriename: "",
      name: "",
      lastChanged: "1999-01-01",
      link: ""
    };

    let dialogRef = this.dialog.open(DocumentsDialogComponent, dialogConfig);

    dialogRef.afterClosed().subscribe(data => {
      console.log(data);
      this.addDocument(data);
    });
  }

  openSnackBar(text: string) {
    this.snackBar.open(text , '', {
      duration: 2000,
    });
  }

}