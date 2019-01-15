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
  verbal: string;
  bpmn: string;
  added: string;
  camunda_processID: string;
  allowed_usergroups: string;


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
    @Inject(MAT_DIALOG_DATA) { processID, name, description, verbal, bpmn, added, camunda_processID }: Process) {

    this.form = this.fb.group({
      processID: new FormControl('0'),
      name: new FormControl(this.name),
      description: new FormControl(this.description),
      verbal: new FormControl(this.verbal),
      bpmn: new FormControl(this.bpmn),
      added: "Now",
      camunda_processID: "None",
      allowed_usergroups: new FormControl(this.allowed_usergroups),
    });

  }

  ngOnInit() {
    this.usergroupService.getUsergroups()
      .subscribe(group => {
        this.usergroups = group;
        console.log('Groups successfully fetched: ' + JSON.stringify(group));
      });
  }


  getSelectedValue(event: String) {
    if(event != undefined) {

      this.allowed_usergroups = event.toString();
      
      console.log(this.allowed_usergroups);
    } else this.allowed_usergroups = "";
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
