import { Component, OnInit, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material';
import { FormBuilder, FormGroup} from '@angular/forms';
import { System } from "../system";

@Component({
  selector: 'app-systems-dialog',
  templateUrl: './systems-dialog.component.html',
  styleUrls: ['./systems-dialog.component.css']
})
export class SystemsDialogComponent implements OnInit {

  form: FormGroup;
  systemID: number;
  name: string;
  beschreibung: string;
  link: string;

  constructor(
    public fb: FormBuilder,
    public thisDialogRef: MatDialogRef<SystemsDialogComponent>,
    @Inject(MAT_DIALOG_DATA) {systemID, name, beschreibung, link }: System) {

      this.form = this.fb.group({
        systemID: 0,
        name: [this.name, []],
        beschreibung: [this.beschreibung, []],
        link: [this.link, []]
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
