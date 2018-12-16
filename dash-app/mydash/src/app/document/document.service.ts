import { Injectable } from '@angular/core';
import { Document } from './document';
import { Observable, of } from 'rxjs';
import { MessageService } from '../message.service';

import { catchError, map, tap } from 'rxjs/operators';
import { HttpClient, HttpHeaders } from '@angular/common/http';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json', 'Authorization': 'Basic ' +  btoa('dashboard:dashboardPW') })
};

@Injectable({
  providedIn: 'root'
})

export class DocumentService {

  private documentsUrl = 'api/documents'; // URL to web api

  constructor(
    private http: HttpClient,
    private messageService: MessageService) { }

      // GET documents from the server
  getDocumente(): Observable<Document[]> {
    // TODO: send the message _after_ fetching the documents
    this.messageService.add('DocumentService: fetched documente');
    return this.http.get<Document[]>("http://localhost:9090/documents", httpOptions)
      .pipe(
        tap(_ => this.log('fetched documents')),
        catchError(this.handleError('getDocuments', []))
      );
  }

   // GET documents from the server
   getDocumentLimit(): Observable<Document[]> {
    // TODO: send the message _after_ fetching the documents
    this.messageService.add('DocumentService: fetched documente');
    return this.http.get<Document[]>("http://localhost:9090/documentsLimit", httpOptions)
      .pipe(
        tap(_ => this.log('fetched documents')),
        catchError(this.handleError('getDocuments', []))
      );
  }

 // GET document by id. Return `undefined` when id not found */
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

// GET document by id. Will 404 if id not found */
getDocument(id: number): Observable<Document> {
  const url = `${this.documentsUrl}/${id}`;
  // TODO: send the message _after_ fetching the document
  this.messageService.add(`DocumentService: fetched document id=${id}`);
  return this.http.get<Document>(url, httpOptions).pipe(
    tap(_ => this.log(`fetched document id=${id}`)),
    catchError(this.handleError<Document>(`getDocument id=${id}`))
  );
}

// GET documente whose name contains search term */
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


// PUT: update the documente on the server */
updateDocument(document: Document): Observable<any> {
  if(!document.name || !document.categoriename || !document.link) { return; }
  return this.http.post(this.documentsUrl, document, httpOptions).pipe(
    tap(_ => this.log(`updated document id=${document.documentID}`)),
    catchError(this.handleError<any>('updateDocument'))
  );
}

// POST: add a new document to the server */
addDocument(document: Document): Observable<Document> {
  if(!document.categoriename || document.categoriename == "" || !document.link || document.link == "" || !document.name || document.name == "") { return; }
  return this.http.post<Document>("http://localhost:9090/documentAdd", document, httpOptions).pipe(
    tap((Document: Document) => this.log(`added document w/ id=${document.documentID}`)),
    catchError(this.handleError<Document>('addDocument'))
  );
}

/** DELETE: delete the Document from the server */
deleteDocument(document: Document): Observable<Document> {
  const id = typeof document === 'number' ? document : document.documentID;
  const url = `http://localhost:9090/documentDelete/${id}`;

  return this.http.delete<Document>(url, httpOptions).pipe(
    tap(_ => this.log(`deleted document id=${id}`)),
    catchError(this.handleError<Document>('deleteDocument'))
  );
}

  // GET documents from the server
  getDocumentsByCategory(name: string): Observable<Document[]> {
    // TODO: send the message _after_ fetching the documents
    this.messageService.add('DocumentService: fetched documente');
    return this.http.get<Document[]>("http://localhost:9090/filter/documents?name="+ name, httpOptions)
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
}
