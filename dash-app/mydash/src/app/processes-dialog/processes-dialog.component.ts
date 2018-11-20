import { Component, OnInit, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Process } from '../process';

@Component({
  selector: 'app-processes-dialog',
  templateUrl: './processes-dialog.component.html',
  styleUrls: ['./processes-dialog.component.css']
})
export class ProcessesDialogComponent implements OnInit {

  form: FormGroup;
  prozessID: number;
  name: string;
  beschreibung: string;
  bild: string;
  varDatei: string;
  bpmn: string;

  constructor(
    public fb: FormBuilder,
    public thisDialogRef: MatDialogRef<ProcessesDialogComponent>,
    @Inject(MAT_DIALOG_DATA) {prozessID, name, beschreibung, bild, varDatei, bpmn }: Process) {

      this.form = this.fb.group({
        prozessID: 0,
        name: [this.name, []],
        beschreibung: [this.beschreibung, []],
        // TODO
        bild: "Placeholder until Fileserver",
        varDatei: "Placeholder until Fileserver",
        bpmn: "Placeholder until Fileserver"
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
