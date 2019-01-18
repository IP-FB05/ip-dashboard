import { Component } from '@angular/core';
import { MatDialogRef} from '@angular/material';


// Import Models
// Import Components
// Import Services


@Component({
  selector: 'app-documents-delete-dialog',
  templateUrl: './documents-delete-dialog.component.html',
  styleUrls: ['./documents-delete-dialog.component.css']
})
export class DocumentsDeleteDialogComponent {

  public confirmMessage: string;
  public confirm: boolean;

  constructor(
    public thisDialogRef: MatDialogRef<DocumentsDeleteDialogComponent>
  ) { }


    onCloseConfirm() {
      this.confirm = true;
      this.thisDialogRef.close(this.confirm);
    }
    onCloseCancel() {
      this.confirm = false;
      this.thisDialogRef.close(this.confirm);
    }
}
