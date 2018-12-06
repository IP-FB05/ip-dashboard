import { Component, OnInit, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef, MatSnackBar } from '@angular/material';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Process } from '../process';
import { Observable } from 'rxjs';
import { UploadFileService } from 'src/app/upload/upload-file.service';
import { HttpEventType, HttpResponse } from '@angular/common/http';

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
  warFile: string;
  bpmn: string;
  added: string;
  camunda_processID: string;

  fileUploads: Observable<string[]>;
  selectedFiles: FileList;
  currentFileUpload: File;
  progress: { percentage: number } = { percentage : 0 };

  constructor(
    private uploadService: UploadFileService,
    public snackBar:MatSnackBar,
    public fb: FormBuilder,
    public thisDialogRef: MatDialogRef<ProcessesDialogComponent>,
    @Inject(MAT_DIALOG_DATA) {processID, name, description, pic, warFile, bpmn, added }: Process) {

      this.form = this.fb.group({
        processID: 0,
        name: [this.name, []],
        description: [this.description, []],
        // TODO
        pic: "Placeholder until Fileserver",
        warFile: "Placeholder until Fileserver",
        //bpmn: [this.bpmn, []], 
        bpmn: "Placeholder until Fileserver",
        added: "Now",
        camunda_processID: "None"
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
    let file = event.target.files[0];
    let fileName = file.name;
    this.bpmn = "http://localhost:9090/files/" + fileName;
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

  /*
  updateFile1() {
    this.bpmn = "http://localhost:9090/files/" + file.value;
  }
  */

}
