import { Component, OnInit } from '@angular/core';
import { MatDialog, MatDialogConfig } from '@angular/material'

// Import Models
import { Document } from '../document';

// Import Components
import { DocumentsDialogComponent } from '../documents-dialog/documents-dialog.component';

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

  constructor(private documentsService: DocumentService,
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

  add(doc : Document): void {
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
      console.log(data);
      this.add(data);
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
    if(a.lastChanged < b.lastChanged) return -1;
    if(a.lastChanged > b.lastChanged) return 1;   
  }

}
