import { Component, OnInit, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material';
import { FormBuilder, FormGroup, FormControl } from '@angular/forms';
import { HttpEventType, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

// Import Models
import { Process } from '../process';
import { Usergroup } from 'src/app/usergroup/usergroup';

// Import Components

// Import Services
import { UploadFileService } from 'src/app/upload/upload-file.service';
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
  selectedUsergroups: number[];

  usergroups: Usergroup[];
  fileUploads: Observable<string[]>;
  selectedFiles: FileList;
  currentFileUpload: File;
  currentFileUpload1: File;


  constructor(
    private usergroupService: UsergroupService,
    private uploadService: UploadFileService,
    public fb: FormBuilder,
    public thisDialogRef: MatDialogRef<ProcessesDialogComponent>,
    @Inject(MAT_DIALOG_DATA) { processID, name, description, pic, warFile, bpmn, added, camunda_processID }: Process) {

    this.form = this.fb.group({
      processID: new FormControl('0'),
      name: new FormControl(this.name),
      description: new FormControl(this.description),
      pic: "Placeholder",
      warFile: new FormControl(this.warFile),
      bpmn: new FormControl(this.bpmn),
      added: "Now",
      camunda_processID: "None",
      selectedUsergroups: new FormControl(this.selectedUsergroups),
    });

  }

  ngOnInit() {
    this.usergroupService.getUsergroups()
      .subscribe(group => {
        this.usergroups = group;
        console.log('Groups successfully fetched: ' + JSON.stringify(group));
      });
  }

  getSelectedValue(event: number[]) {
    console.log(event);
    this.selectedUsergroups = event;
    console.log(this.selectedUsergroups);
  }

  onCloseConfirm() {
    this.thisDialogRef.close(this.form.value);
  }
  onCloseCancel() {
    this.thisDialogRef.close('Cancel');
  }


  selectFile(event) {
    this.selectedFiles = event.target.files;
    this.currentFileUpload = this.selectedFiles.item(0);
    this.form.controls.bpmn.setValue('http://localhost:9090/files/' + this.currentFileUpload.name);
  }

  selectFile1(event) {
    this.selectedFiles = event.target.files;
    this.currentFileUpload1 = this.selectedFiles.item(0);
    this.form.controls.warFile.setValue('http://localhost:9090/files/' + this.currentFileUpload1.name);
  }



  upload() {
    this.uploadService.pushFileToStorage(this.currentFileUpload).subscribe(event => {
      if (event.type === HttpEventType.UploadProgress) {
        //this.progress.percentage = Math.round(100 * event.loaded / event.total);
      } else if (event instanceof HttpResponse) {
        console.log('File is completely uploaded!');
      }
    });

    if (this.currentFileUpload1 != null) {
      this.uploadService.pushFileToStorage(this.currentFileUpload1).subscribe(event => {
        if (event.type === HttpEventType.UploadProgress) {
          //this.progress.percentage = Math.round(100 * event.loaded / event.total);
        } else if (event instanceof HttpResponse) {
          console.log('File is completely uploaded!');
        }
      });

    }
    this.selectedFiles = undefined;
  }


}
