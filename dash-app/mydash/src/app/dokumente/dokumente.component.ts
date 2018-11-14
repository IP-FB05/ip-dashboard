import { Component, OnInit, Inject } from '@angular/core';
import { Dokument } from '../dokument';
import { DokumentService } from '../dokument.service';




@Component({
  selector: 'app-dokumente',
  templateUrl: './dokumente.component.html',
  styleUrls: ['./dokumente.component.css']
})
export class DokumenteComponent implements OnInit {

  dokumente: Dokument[];

  constructor(private dokumenteService: DokumentService) { }

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



}
