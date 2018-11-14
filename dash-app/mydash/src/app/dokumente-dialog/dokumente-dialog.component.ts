import { Component, OnInit, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material';

@Component({
  selector: 'app-dokumente-dialog',
  templateUrl: './dokumente-dialog.component.html',
  styleUrls: ['./dokumente-dialog.component.css']
})
export class DokumenteDialogComponent implements OnInit {

  constructor(
    public thisDialogRef: MatDialogRef<DokumenteDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: string) { }

  ngOnInit() {
  }

  onCloseConfirm() {
    this.thisDialogRef.close('Confirm');
  }
  onCloseCancel() {
    this.thisDialogRef.close('Cancel');
  }

}
