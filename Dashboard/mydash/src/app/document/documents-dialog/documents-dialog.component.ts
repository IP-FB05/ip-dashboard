import { Component, OnInit, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef, MatSnackBar } from '@angular/material';
import { FormBuilder, FormGroup, FormControl } from '@angular/forms';
import { HttpEventType, HttpResponse } from '@angular/common/http';

// Import Models
import { Document } from '../document';
import { Category } from 'src/app/category/category';

// Import Components


// Import Services
import { UploadFileService } from 'src/app/upload/upload-file.service';
import { CategoryService } from 'src/app/category/category.service';



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
  lastChanged: Date;
  link: string;

  selectedFiles: FileList;
  currentFileUpload: File;
  selectedCategory: string;

  categories: Category[];


  constructor(
    public cs: CategoryService,
    public uploadService: UploadFileService,
    public fb: FormBuilder,
    public snackBar: MatSnackBar,
    public thisDialogRef: MatDialogRef<DocumentsDialogComponent>,
    @Inject(MAT_DIALOG_DATA) {documentID, categoriename, name, lastChanged, link }: Document) { 

      this.form = new FormGroup({
        documentID: new FormControl('0'),
        categoriename: new FormControl(this.categoriename),
        name: new FormControl(this.name),
        lastChanged: new FormControl(this.lastChanged),
        link: new FormControl(this.link)
      });
    }

  ngOnInit() {
    this.cs.getCategories()
      .subscribe(category => this.categories = category);
  }

  onCloseConfirm() {
    this.thisDialogRef.close(this.form.value);
  }
  onCloseCancel() {
    this.thisDialogRef.close('Cancel');
    this.openSnackBar("Vorgang abgebrochen");

  }

  getSelectedValue(event: string) {
    if(event != undefined) {
      this.selectedCategory = event;
      this.categoriename = this.selectedCategory;
    }
  }

  selectFile(event) {
    this.selectedFiles = event.target.files;
    this.currentFileUpload = this.selectedFiles.item(0);
    this.form.controls.link.setValue('http://localhost:9090/files/'+this.currentFileUpload.name);
    this.form.controls.lastChanged.setValue(new Date());
  }

  upload() {
    this.uploadService.pushFileToStorage(this.currentFileUpload).subscribe(event => {
      if (event.type === HttpEventType.UploadProgress) {
        //this.progress.percentage = Math.round(100 * event.loaded / event.total);
      } else if (event instanceof HttpResponse) {
        console.log('File is completely uploaded!');
      }
    });
 
    this.selectedFiles = undefined;
  }  

  openSnackBar(text: string) {
    this.snackBar.open(text, '', {
      duration: 2000,
    });
  }
}
