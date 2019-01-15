import { Injectable } from '@angular/core';
import { HttpClient, HttpEvent, HttpRequest, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

// Import Models
// Import Components
// Import Services

const httpHeader = new HttpHeaders({'Authorization': 'Basic ' +  btoa('dashboard:dashboardPW') });

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json', 'Authorization': 'Basic ' +  btoa('dashboard:dashboardPW') })
};

@Injectable({
  providedIn: 'root'
})
export class UploadFileService {

  constructor(private http: HttpClient) { }

  pushFileToStorage(file: File): Observable<HttpEvent<{}>> {
    const formdata: FormData = new FormData();

    formdata.append('file', file);

    const req = new HttpRequest('POST', 'http://localhost:9090/uploadFile', formdata, {
      headers: httpHeader,
      reportProgress: true,
      responseType: 'text'
    });

    

    return this.http.request(req);
  }

  getFiles(): Observable<any> {
    return this.http.get('http://localhost:9090/listFiles', httpOptions);
  }
}