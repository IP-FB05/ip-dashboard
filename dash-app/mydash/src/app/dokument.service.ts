import { Injectable } from '@angular/core';
import { Dokument } from './dokument';
import { Observable, of } from 'rxjs';
import { MessageService } from './message.service';

import { catchError, map, tap } from 'rxjs/operators';
import { HttpClient, HttpHeaders } from '@angular/common/http';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})

export class DokumentService {

  private dokumenteUrl = 'api/dokumente'; // URL to web api

  constructor(
    private http: HttpClient,
    private messageService: MessageService) { }

      // GET Dokumente from the server
  getDokumente(): Observable<Dokument[]> {
    // TODO: send the message _after_ fetching the dokumente
    this.messageService.add('DokumentService: fetched dokumente');
    return this.http.get<Dokument[]>("http://localhost/api/dokumente/read.php")
      .pipe(
        tap(_ => this.log('fetched dokumente')),
        catchError(this.handleError('getDokkumente', []))
      );
  }

 // GET dokument by id. Return `undefined` when id not found */
 getDokumentNo404<Data>(id: number): Observable<Dokument> {
  const url = `${this.dokumenteUrl}/?id=${id}`;
  return this.http.get<Dokument[]>(url)
    .pipe(
      map(dokumente => dokumente[0]), // returns a {0|1} element array
      tap(h => {
        const outcome = h ? `fetched` : `did not find`;
        this.log(`${outcome} dokument id=${id}`);
      }),
      catchError(this.handleError<Dokument>(`getDokument id=${id}`))
    );
}

// GET dokument by id. Will 404 if id not found */
getDokument(id: number): Observable<Dokument> {
  const url = `${this.dokumenteUrl}/${id}`;
  // TODO: send the message _after_ fetching the dokument
  this.messageService.add(`DokumentService: fetched dokument id=${id}`);
  return this.http.get<Dokument>(url).pipe(
    tap(_ => this.log(`fetched dokument id=${id}`)),
    catchError(this.handleError<Dokument>(`getDokument id=${id}`))
  );
}

// GET dokumente whose name contains search term */
searchDokument(term: string): Observable<Dokument[]> {
  if (!term.trim()) {
    // if not search term, return empty dokumente array.
    return of([]);
  }
  return this.http.get<Dokument[]>(`${this.dokumenteUrl}/?name=${term}`).pipe(
    tap(_ => this.log(`found dokument matching "${term}"`)),
    catchError(this.handleError<Dokument[]>('searchDokumente', []))
  );
}


// PUT: update the dokumente on the server */
updateDokument(dokument: Dokument): Observable<any> {
  return this.http.put(this.dokumenteUrl, dokument, httpOptions).pipe(
    tap(_ => this.log(`updated dokument id=${dokument.dokumentID}`)),
    catchError(this.handleError<any>('updateDokument'))
  );
}

// POST: add a new dokument to the server */
addDokument(dokument: Dokument): Observable<Dokument> {
  return this.http.post<Dokument>(this.dokumenteUrl, dokument, httpOptions).pipe(
    tap((Dokument: Dokument) => this.log(`added dokument w/ id=${dokument.dokumentID}`)),
    catchError(this.handleError<Dokument>('addDokument'))
  );
}

/** DELETE: delete the Dokument from the server */
deleteDokument(dokument: Dokument | number): Observable<Dokument> {
  const id = typeof dokument === 'number' ? dokument : dokument.dokumentID;
  const url = `${this.dokumenteUrl}/${id}`;

  return this.http.delete<Dokument>(url, httpOptions).pipe(
    tap(_ => this.log(`deleted dokument id=${id}`)),
    catchError(this.handleError<Dokument>('deleteDokument'))
  );
}


private log(message: string) {
  this.messageService.add(`DokumentService: ${message}`);
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
