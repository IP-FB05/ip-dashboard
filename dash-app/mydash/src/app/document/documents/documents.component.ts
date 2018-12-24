import { Component, OnInit } from '@angular/core';
import { Document } from '../document';
import { DocumentService } from '../document.service';
import { MatDialog, MatDialogConfig, MatSnackBar } from '@angular/material'
import { DocumentsDialogComponent } from '../documents-dialog/documents-dialog.component';




@Component({
  selector: 'app-documents',
  templateUrl: './documents.component.html',
  styleUrls: ['./documents.component.css']
})
export class DocumentsComponent implements OnInit {

  documents: Document[];
  searchText:string;
  //flag: boolean;

  constructor(private documentsService: DocumentService, public dialog: MatDialog, public snackBar:MatSnackBar) { }

  ngOnInit() {
    this.getDokumente();
    //this.flag = true;
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
      // TODO
      link: ""
    };

    let dialogRef = this.dialog.open(DocumentsDialogComponent, dialogConfig);

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

// Backend Filter
filterDocuments(name: string) {
  this.documentsService.getDocumentsByCategory(name)
  .subscribe(documents => this.documents = documents);
}



  /* Frontend Filter

  // Funktioniert aber verbuggt
 
  filterCategory(name: string) {
    this.getDokumente();
    for (var i = 0; i < this.documents.length; i++) {
      if(!(this.documents[i].categoriename===name)) {
        this.documents.splice(i,1);
        i--;
      }
    }
  }

  // Funktioniert nicht =(

  filterCategory(name: string) {
    this.getDokumente();
    this.documents.filter(function(document){
      return document.categoriename === name;
    });
  }

  test() {
    if(this.flag) {
      this.filterCategory("MCD");
      this.flag = false;
    } else {
      this.filterCategory("Informatik");
      this.flag = true;
    }
  }*/
  sortDocuments() {
    this.documents.sort(this.compareDate);
  } 

  compareDate(a, b) {
    if(a.lastChanged < b.lastChanged) return -1;
    if(a.lastChanged > b.lastChanged) return 1;   
  }

}
