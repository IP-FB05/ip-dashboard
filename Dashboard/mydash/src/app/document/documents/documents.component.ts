import { Component, OnInit } from '@angular/core';
import { MatDialog, MatDialogConfig, MatDialogRef } from '@angular/material'

// Import Models
import { Document } from '../document';

// Import Components
import { DocumentsDialogComponent } from '../documents-dialog/documents-dialog.component';
import { DocumentsDeleteDialogComponent } from '../documents-delete-dialog/documents-delete-dialog.component'

// Import Services
import { DocumentService } from '../document.service';
import { AuthorizationService } from '../../login/auth/authorization.service'


@Component({
  selector: 'app-documents',
  templateUrl: './documents.component.html',
  styleUrls: ['./documents.component.css']
})
export class DocumentsComponent implements OnInit {

  documents: Document[];
  searchText: string;
  dialogRef: MatDialogRef<DocumentsDeleteDialogComponent>;

  constructor(
    private documentsService: DocumentService,
    public dialog: MatDialog,
    public authorizationService: AuthorizationService) { }

  ngOnInit() {
    this.getDokumente();
  }

  getDokumente(): void {
    this.documentsService.getDocumente()
      .subscribe(documents => this.documents = documents);
  }

  sortReverse() {
    this.documents.reverse();
  }

  add(doc: Document): void {
    this.documentsService.addDocument(doc)
      .subscribe(dokument => {
        this.documents.push(dokument);
        this.getDokumente();
      });
  }


  delete(document: Document): void {
    this.documents = this.documents.filter(d => d !== document);
    this.documentsService.deleteDocument(document).subscribe();
    this.documentsService.deleteDocumentFromFileServer(document.link).subscribe();
  }


  openDeleteDialog(document: Document) {
    this.dialogRef = this.dialog.open(DocumentsDeleteDialogComponent, {
      disableClose: false
    });
    
    this.dialogRef.componentInstance.confirmMessage = "Wollen Sie dieses Dokument wirklich löschen ?"

    this.dialogRef.afterClosed().subscribe(data => {
      if(data) this.delete(document);

      this.dialogRef = null;
    });
  }


  openDialog() {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.disableClose = false;
    dialogConfig.autoFocus = true;

    dialogConfig.data = {
      dokumentID: 0,
      kategoriename: "",
      name: "",
      lastChanged: new Date(),
      link: ""
    };

    let dialogRef = this.dialog.open(DocumentsDialogComponent, dialogConfig);

    dialogRef.afterClosed().subscribe(data => {
      if(data != 'Cancel') this.add(data);
    });
  }

  // Backend Filter
  filterDocuments(name: string) {
    this.documentsService.getDocumentsByCategory(name)
      .subscribe(documents => this.documents = documents);
  }

  sortDocuments() {
    this.documents.sort(this.compareDate);
  }

  compareDate(a, b) {
    if (a.lastChanged > b.lastChanged) return -1;
    if (a.lastChanged < b.lastChanged) return 1;
  }

}
