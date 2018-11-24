import { Component, OnInit } from '@angular/core';
import { Document } from '../document';
import { DocumentService } from '../document.service';
import { MatDialog, MatDialogConfig } from '@angular/material'
import { DocumentsDialogComponent } from '../documents-dialog/documents-dialog.component';




@Component({
  selector: 'app-documents',
  templateUrl: './documents.component.html',
  styleUrls: ['./documents.component.css']
})
export class DocumentsComponent implements OnInit {

  documents: Document[];
  searchText:string;

  constructor(private documentsService: DocumentService, public dialog: MatDialog) { }

  ngOnInit() {
    this.getDokumente();
  }

  getDokumente(): void {
    this.documentsService.getDocumente()
      .subscribe(documents => this.documents = documents);
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
  }

  openDialog() {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.disableClose = false;
    dialogConfig.autoFocus = true;

    dialogConfig.data = {
      dokumentID: 0,
      kategoriename: "",
      name: "",
      lastChanged: "Now",
      // TODO
      link: "Placeholder bis Fileserver"
    };

    let dialogRef = this.dialog.open(DocumentsDialogComponent, dialogConfig);

    dialogRef.afterClosed().subscribe(data => {
      console.log(data);
      this.add(data);
    });
  }
}
