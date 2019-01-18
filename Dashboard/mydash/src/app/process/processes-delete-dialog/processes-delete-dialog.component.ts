import { Component, OnInit } from '@angular/core';
import { MatDialogRef } from '@angular/material';

// Import Models
// Import Components
// Import Services

@Component({
  selector: 'app-processes-delete-dialog',
  templateUrl: './processes-delete-dialog.component.html',
  styleUrls: ['./processes-delete-dialog.component.css']
})
export class ProcessesDeleteDialogComponent implements OnInit {

  public confirmMessage: string;
  public confirm: boolean;
  constructor(
    public thisDialogRef: MatDialogRef<ProcessesDeleteDialogComponent>
  ) { }

  ngOnInit() {
  }

  onCloseConfirm() {
    this.confirm = true;
    this.thisDialogRef.close(this.confirm);
  }
  onCloseCancel() {
    this.confirm = false;
    this.thisDialogRef.close(this.confirm);
  }
}


