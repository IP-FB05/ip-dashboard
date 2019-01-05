import { Component, OnInit, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef, MatSnackBar } from '@angular/material';
import { FormBuilder, FormGroup, FormControl } from '@angular/forms';
import { Process } from '../process';
import { Observable } from 'rxjs';
import { UploadFileService } from 'src/app/upload/upload-file.service';
import { HttpEventType, HttpResponse } from '@angular/common/http';
import { Usergroup } from 'src/app/usergroup/usergroup';
import { UsergroupService } from 'src/app/usergroup/usergroup.service';

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
  usergroup: string;

  usergroups: Usergroup[];
  fileUploads: Observable<string[]>;
  selectedFiles: FileList;
  currentFileUpload: File;
  currentFileUpload1: File;
  //progress: { percentage: number } = { percentage : 0 };

  constructor(
    private usergroupService: UsergroupService,
    private uploadService: UploadFileService,
    public snackBar: MatSnackBar,
    public fb: FormBuilder,
    public thisDialogRef: MatDialogRef<ProcessesDialogComponent>,
    @Inject(MAT_DIALOG_DATA) { processID, name, description, pic, warFile, bpmn, added, camunda_processID }: Process) {

    this.form = this.fb.group({
      processID: new FormControl('0'),
      name: new FormControl(this.name),
      description: new FormControl(this.description),
      pic: "Placeholder until Fileserver",
      warFile: new FormControl(this.warFile),
      //bpmn: [this.bpmn, []], 
      bpmn: new FormControl(this.bpmn),
      added: "Now",
      camunda_processID: "None",
      usergroup: new FormControl(this.usergroup),
    });

  }

  ngOnInit() {
    this.usergroupService.getUsergroups()
      .subscribe(group => {
        this.usergroups = group;
        console.log('Groups successfully fetched: ' + JSON.stringify(group));
      });


  }


  onCloseConfirm() {
    this.thisDialogRef.close(this.form.value);
  }
  onCloseCancel() {
    this.thisDialogRef.close('Cancel');
  }

  openSnackBar() {
    this.snackBar.open('Hinzufügen erfolgreich', '', {
      duration: 2000,
    });
  }

  selectFile(event) {
    this.selectedFiles = event.target.files;
    this.currentFileUpload = this.selectedFiles.item(0);
    this.form.controls.bpmn.setValue('http://localhost:9090/files/' + this.currentFileUpload.name);
    this.form.controls.lastChanged.setValue(new Date());
  }

  selectFile1(event) {
    this.selectedFiles = event.target.files;
    this.currentFileUpload1 = this.selectedFiles.item(0);
    this.form.controls.warFile.setValue('http://localhost:9090/files/' + this.currentFileUpload1.name);
    this.form.controls.lastChanged.setValue(new Date());
  }



  upload() {
    //this.progress.percentage = 0;
    //this.currentFileUpload = this.selectedFiles.item(0);
    this.uploadService.pushFileToStorage(this.currentFileUpload).subscribe(event => {
      if (event.type === HttpEventType.UploadProgress) {
        //this.progress.percentage = Math.round(100 * event.loaded / event.total);
      } else if (event instanceof HttpResponse) {
        console.log('File is completely uploaded!');
      }
    });

    this.uploadService.pushFileToStorage(this.currentFileUpload1).subscribe(event => {
      if (event.type === HttpEventType.UploadProgress) {
        //this.progress.percentage = Math.round(100 * event.loaded / event.total);
      } else if (event instanceof HttpResponse) {
        console.log('File is completely uploaded!');
      }
    });


    this.selectedFiles = undefined;
  }


}
