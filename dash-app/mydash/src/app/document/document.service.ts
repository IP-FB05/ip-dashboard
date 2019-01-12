import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { MatSnackBar } from '@angular/material';
import { catchError, map, tap } from 'rxjs/operators';
import { Observable, of } from 'rxjs';

// Import Models
import { Document } from './document';

// Import Components

// Import Services
import { MessageService } from '../message.service';



const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json', 'Authorization': 'Basic ' + btoa('dashboard:dashboardPW') })
};

@Injectable({
  providedIn: 'root'
})

export class DocumentService {

  //private documentsUrl = 'api/documents'; 

  private documentsUrl = "http://localhost:9090/"

  constructor(
    private http: HttpClient,
    private messageService: MessageService,
    private snackBar: MatSnackBar) { }

  // GET documents from the server
  getDocumente(): Observable<Document[]> {
    this.messageService.add('DocumentService: fetched documente');
    return this.http.get<Document[]>(this.documentsUrl + "documents", httpOptions)
      .pipe(
        tap(_ => this.log('fetched documents')),
        catchError(this.handleError('getDocuments', []))
      );
  }

  // GET documents from the server
  getDocumentLimit(): Observable<Document[]> {
    this.messageService.add('DocumentService: fetched documents');
    return this.http.get<Document[]>(this.documentsUrl + "documentsLimit", httpOptions)
      .pipe(
        tap(_ => this.log('fetched documents')),
        catchError(this.handleError('getDocuments', []))
      );
  }

  // POST: add a new document to the server */
  addDocument(document: Document): Observable<Document> {
    if (!document.categoriename || document.categoriename == "" || !document.link || document.link == "" || !document.name || document.name == "") { 
      if(!document.categoriename || document.categoriename == "") this.openSnackBar("Dokument wurde nicht hinzugefügt! Kategorieangabe erforderlich!");
      else if(!document.link || document.link == "") this.openSnackBar("Dokument wurde nicht hinzugefügt! Link erforderlich!");
      else if(!document.name || document.name == "") this.openSnackBar("Dokument wurde nicht hinzugefügt! Name erfoderlich !");
      else this.openSnackBar("Dokument wurde nicht hinzugefügt !");
      return; 
    }

    this.openSnackBar("Dokument wurde erfolgreich hinzugefügt");
    this.messageService.add('DocumentService: added document');
    return this.http.post<Document>(this.documentsUrl + "documentAdd", document, httpOptions).pipe(
      tap((Document: Document) => this.log(`added document w/ id=${document.documentID}`)),
      catchError(this.handleError<Document>('addDocument'))
    );
  }

  /** DELETE: delete the Document from the server */
  deleteDocument(document: Document): Observable<Document> {
    const id = typeof document === 'number' ? document : document.documentID;
    const url = `http://localhost:9090/documentDelete/${id}`;

    this.messageService.add('DocumentService: deleted document');
    return this.http.delete<Document>(url, httpOptions).pipe(
      tap(_ => this.log(`deleted document id=${id}`)),
      catchError(this.handleError<Document>('deleteDocument'))
    );
  }

  /** DELETE: delete the Document from the FileServer */
  deleteDocumentFromFileServer(link: string): Observable<Document> {
    const substring = link.substring(link.lastIndexOf("/") + 1);
    this.messageService.add('DocumentService: Deleted Document from FileServer');
    return this.http.delete<Document>(this.documentsUrl + "deleteFile?filename=" + substring, httpOptions).pipe(
      tap(_ => this.log(`deleted document from fileserver`)),
      catchError(this.handleError<Document>('deleteFile'))
    );
  }

  // GET documents from the server
  getDocumentsByCategory(name: string): Observable<Document[]> {
    this.messageService.add('DocumentService: fetched documente');
    return this.http.get<Document[]>(this.documentsUrl + "/filter/documents?name=" + name, httpOptions)
      .pipe(
        tap(_ => this.log('fetched documents')),
        catchError(this.handleError('getDocumentsByCategory', []))
      );
  }


  private log(message: string) {
    this.messageService.add(`DocumentService: ${message}`);
  }

  /**
  * Handle Http operation that failed.
  * Let the app continue.
  * @param operation - name of the operation that failed
  * @param result - optional value to return as the observable result
  */
  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {

      // TODO: send the error to remote logging infrastructure
      console.error(error); // log to console instead

      // TODO: better job of transforming error for user consumption
      this.log(`${operation} failed: ${error.message}`);

      // Let the app keep running by returning an empty result.
      return of(result as T);
    };
  }

  openSnackBar(text: string) {
    this.snackBar.open(text, '', {
      duration: 2000,
    });
  }









/*
  // GET document by id. Return `undefined` when id not found
  getDocumentNo404<Data>(id: number): Observable<Document> {
    const url = `${this.documentsUrl}/?id=${id}`;
    return this.http.get<Document[]>(url, httpOptions)
      .pipe(
        map(documente => documente[0]), // returns a {0|1} element array
        tap(h => {
          const outcome = h ? `fetched` : `did not find`;
          this.log(`${outcome} document id=${id}`);
        }),
        catchError(this.handleError<Document>(`getDocument id=${id}`))
      );
  }
  

  
  // GET document by id. Will 404 if id not found
  getDocument(id: number): Observable<Document> {
    const url = `${this.documentsUrl}/${id}`;
    // TODO: send the message _after_ fetching the document
    this.messageService.add(`DocumentService: fetched document id=${id}`);
    return this.http.get<Document>(url, httpOptions).pipe(
      tap(_ => this.log(`fetched document id=${id}`)),
      catchError(this.handleError<Document>(`getDocument id=${id}`))
    );
  }


  // GET documente whose name contains search term 
  searchDocument(term: string): Observable<Document[]> {
    if (!term.trim()) {
      // if not search term, return empty documente array.
      return of([]);
    }
    return this.http.get<Document[]>(`${this.documentsUrl}/?name=${term}`, httpOptions).pipe(
      tap(_ => this.log(`found document matching "${term}"`)),
      catchError(this.handleError<Document[]>('searchDocumente', []))
    );
  }


  // PUT: update the documente on the server
  updateDocument(document: Document): Observable<any> {
    if (!document.name || !document.categoriename || !document.link) { return; }
    return this.http.post(this.documentsUrl, document, httpOptions).pipe(
      tap(_ => this.log(`updated document id=${document.documentID}`)),
      catchError(this.handleError<any>('updateDocument'))
    );
  }

  */


}
