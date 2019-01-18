import { Component, OnInit } from '@angular/core';
import { MatDialogRef } from '@angular/material';

// Import Models
// Import Components
// Import Services

@Component({
  selector: 'app-systems-delete-dialog',
  templateUrl: './systems-delete-dialog.component.html',
  styleUrls: ['./systems-delete-dialog.component.css']
})
export class SystemsDeleteDialogComponent implements OnInit {

  public confirmMessage: string;
  public confirm: boolean;
  constructor(
    public thisDialogRef: MatDialogRef<SystemsDeleteDialogComponent>
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
