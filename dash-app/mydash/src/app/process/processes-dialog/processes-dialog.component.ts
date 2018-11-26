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
  processID: number;
  name: string;
  description: string;
  pic: string;
  varFile: string;
  bpmn: string;
  added: string;

  constructor(
    public fb: FormBuilder,
    public thisDialogRef: MatDialogRef<ProcessesDialogComponent>,
    @Inject(MAT_DIALOG_DATA) {processID, name, description, pic, varFile, bpmn, added }: Process) {

      this.form = this.fb.group({
        processID: 0,
        name: [this.name, []],
        description: [this.description, []],
        // TODO
        pic: "Placeholder until Fileserver",
        varFile: "Placeholder until Fileserver",
        bpmn: "Placeholder until Fileserver",
        added: "Now"
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
