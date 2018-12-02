import { Component, OnInit, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef, MatSnackBar } from '@angular/material';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Document } from '../document';
import { UploadFileService } from 'src/app/upload/upload-file.service';
import { HttpEventType, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

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
  _link: string;

  fileUploads: Observable<string[]>;
  selectedFiles: FileList;
  currentFileUpload: File;
  progress: { percentage: number } = { percentage : 0 };


  constructor(
    private uploadService: UploadFileService,
    public snackBar: MatSnackBar,
    public fb: FormBuilder,
    public thisDialogRef: MatDialogRef<DocumentsDialogComponent>,
    @Inject(MAT_DIALOG_DATA) {documentID, categoriename, name, lastChanged, link }: Document) { 

      this.form = this.fb.group({
        documentID: 0,
        categoriename: [this.categoriename, []],
        name: [this.name, []],
        lastChanged: "Now",
        // TODO
        //link: [this._link ,[]]
        link:"Placeholder until Fileserver"
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

  openSnackBar() {
    this.snackBar.open('Hinzufügen erfolgreich' , '', {
      duration: 2000,
    });
  }


  selectFile(event) {
    this.selectedFiles = event.target.files;
  }

  upload() {
    this.progress.percentage = 0;
 
    this.currentFileUpload = this.selectedFiles.item(0);
    this.uploadService.pushFileToStorage(this.currentFileUpload).subscribe(event => {
      if (event.type === HttpEventType.UploadProgress) {
        this.progress.percentage = Math.round(100 * event.loaded / event.total);
      } else if (event instanceof HttpResponse) {
        console.log('File is completely uploaded!');
      }
    });
 
    this.selectedFiles = undefined;
  }

  updateFile(file: HTMLInputElement) {
    this._link = file.value;
  }
}
