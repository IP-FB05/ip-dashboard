import { Component, OnInit } from '@angular/core';
import { Dokument } from '../dokument';
import { DokumentService } from '../dokument.service';
import { MatDialog, MatDialogConfig } from '@angular/material'
import { DokumenteDialogComponent } from '../dokumente-dialog/dokumente-dialog.component';




@Component({
  selector: 'app-dokumente',
  templateUrl: './dokumente.component.html',
  styleUrls: ['./dokumente.component.css']
})
export class DokumenteComponent implements OnInit {

  dokumente: Dokument[];

  constructor(private dokumenteService: DokumentService, public dialog: MatDialog) { }

  ngOnInit() {
    this.getDokumente();
  }

  getDokumente(): void {
    this.dokumenteService.getDokumente()
      .subscribe(dokumente => this.dokumente = dokumente);
  }

  add(doc : Dokument): void {
    this.dokumenteService.addDokument(doc)
      .subscribe(dokument => {
        this.dokumente.push(dokument);
        this.getDokumente();
      });
  }


  delete(dokument: Dokument): void {
    this.dokumente = this.dokumente.filter(d => d !== dokument);
    this.dokumenteService.deleteDokument(dokument).subscribe();
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

    let dialogRef = this.dialog.open(DokumenteDialogComponent, dialogConfig);

    dialogRef.afterClosed().subscribe(data => {
      console.log(data);
      this.add(data);
    });
  }



}
