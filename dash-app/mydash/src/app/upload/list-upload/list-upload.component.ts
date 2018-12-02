import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { UploadFileService } from '../upload-file.service';

@Component({
  selector: 'list-upload',
  templateUrl: './list-upload.component.html',
  styleUrls: ['./list-upload.component.css']
})
export class ListUploadComponent implements OnInit {

  fileUploads: Observable<string[]>;

  constructor(private uploadService: UploadFileService) { }

  ngOnInit() {
  }

  showFiles() {
    this.fileUploads = this.uploadService.getFiles();
  }

}
