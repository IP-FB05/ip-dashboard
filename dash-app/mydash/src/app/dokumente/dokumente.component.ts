import { Component, OnInit } from '@angular/core';
import { Dokument } from '../dokument';
import { DokumentService } from '../dokument.service';
import { MatDialog } from '@angular/material'
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

  add(name: string): void {
    name = name.trim();
    if (!name) { return; }
    this.dokumenteService.addDokument({ name } as Dokument)
      .subscribe(dokument => {
        this.dokumente.push(dokument);
      });
  }


  delete(dokument: Dokument): void {
    this.dokumente = this.dokumente.filter(d => d !== dokument);
    this.dokumenteService.deleteDokument(dokument).subscribe();
  }

  openDialog() {
    let dialogRef = this.dialog.open(DokumenteDialogComponent, {
      width: '800px',
      height: '600px',
      //data: 'Test!'
    });
    dialogRef.afterClosed().subscribe(result => {
      console.log(`Dialog closed: ${result}`);
    });
  }



}
