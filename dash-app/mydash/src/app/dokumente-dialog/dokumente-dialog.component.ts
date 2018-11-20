import { Component, OnInit, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Dokument } from '../dokument';

@Component({
  selector: 'app-dokumente-dialog',
  templateUrl: './dokumente-dialog.component.html',
  styleUrls: ['./dokumente-dialog.component.css']
})
export class DokumenteDialogComponent implements OnInit {

  form: FormGroup;
  dokumentID: number;
  kategoriename: string;
  name: string;
  lastChanged: string;
  link: string;


  constructor(
    public fb: FormBuilder,
    public thisDialogRef: MatDialogRef<DokumenteDialogComponent>,
    @Inject(MAT_DIALOG_DATA) {dokumentID, kategoriename, name, lastChanged, link }: Dokument) { 

      this.form = this.fb.group({
        dokumentID: 0,
        kategoriename: [this.kategoriename, []],
        name: [this.name, []],
        lastChanged: "Now",
        // TODO
        link: "Placeholder until Fileserver"
      });
    }

  ngOnInit() {
  }

  onCloseConfirm() {
    this.thisDialogRef.close(this.form.value);
  }
  onCloseCancel() {
    this.thisDialogRef.close('Cancel');
  }

}
