import { Component, OnInit, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef, MatSnackBar } from '@angular/material';
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
  description: string;
  link: string;

  constructor(
    public fb: FormBuilder,
    public thisDialogRef: MatDialogRef<SystemsDialogComponent>,
    @Inject(MAT_DIALOG_DATA) {systemID, name, description, link }: System) {

      this.form = this.fb.group({
        systemID: 0,
        name: [this.name, []],
        description: [this.description, []],
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
