import { Component, OnInit, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Document } from '../document';

@Component({
  selector: 'app-documents-dialog',
  templateUrl: './documents-dialog.component.html',
  styleUrls: ['./documents-dialog.component.css']
})
export class DocumentsDialogComponent implements OnInit {

  form: FormGroup;
  documentID: number;
  categoriename: string;
  name: string;
  lastChanged: string;
  link: string;


  constructor(
    public fb: FormBuilder,
    public thisDialogRef: MatDialogRef<DocumentsDialogComponent>,
    @Inject(MAT_DIALOG_DATA) {documentID, categoriename, name, lastChanged, link }: Document) { 

      this.form = this.fb.group({
        documentID: 0,
        categoryname: [this.categoriename, []],
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
