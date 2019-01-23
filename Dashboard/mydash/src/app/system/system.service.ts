import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { MatSnackBar } from '@angular/material';

import { Observable, of } from 'rxjs';
import { catchError, map, tap } from 'rxjs/operators';

// Import Models
import { System } from './system';

// Import Components

// Import Services
import { MessageService } from '../messages/message.service';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json', 'Authorization': 'Basic ' +  btoa('dashboard:dashboardPW') })
};

@Injectable({
  providedIn: 'root'
})

export class SystemService {

  //private systemsUrl = 'api/systems'; // URL to web api
  private systemsUrl = "http://149.201.176.231:9090/";

  constructor(
    private http: HttpClient,
    private messageService: MessageService,
    public snackBar: MatSnackBar) { }

  // GET Systems from the server
  getSystems(): Observable<System[]> {
    this.messageService.add('SystemService: fetched systems');
    return this.http.get<System[]>(this.systemsUrl + "systems", httpOptions)
      .pipe(
        tap(_ => this.log('fetched systems')),
        catchError(this.handleError('getSystems', []))
      );
  }


  // PUT: update the systems on the server */
  updateSystem(system: System): Observable<any> {
    this.messageService.add('SystemService: updated systems');
    return this.http.put(this.systemsUrl, system, httpOptions).pipe(
      tap(_ => this.log(`updated system id=${system.systemID}`)),
      catchError(this.handleError<any>('updateSystem'))
    );
  }

  // POST: add a new system to the server */
  addSystem(system: System): Observable<System> {
    if(!system.name || system.name == "" || !system.description || system.description == "" || !system.link || system.link == "") {
      
      if(!system.name || system.name == "") this.openSnackBar("System wurde nicht hinzugefügt! Name erforderlich!");
      else if(!system.description || system.description == "") this.openSnackBar("System wurde nicht hinzugefügt! Beschreibung erforderlich!");
      else if(!system.link || system.link == "") this.openSnackBar("System wurde nicht hinzugefügt! Link erforderlich !");
      else this.openSnackBar("System wurde nicht hinzugefügt !");
      return; 

    }
    this.openSnackBar("System wurde erfolgreich hinzugefügt !"); 
    this.messageService.add('SystemService: added system');
    return this.http.post<System>(this.systemsUrl + "systemAdd", system, httpOptions).pipe(
      tap(_ => this.log(`added system w/ id=${system.systemID}`)),
      catchError(this.handleError<System>('addSystem'))
    );
  }

  /** DELETE: delete the System from the server */
  deleteSystem(system: System | number): Observable<System> {
    const id = typeof system === 'number' ? system : system.systemID;
    const url = `http://149.201.176.231:9090/systemDelete/${id}`;

    this.openSnackBar("System wurde erfolgreich gelöscht !");
    this.messageService.add('SystemService: deleted system');
    return this.http.delete<System>(url, httpOptions).pipe(
      tap(_ => this.log(`deleted system id=${id}`)),
      catchError(this.handleError<System>('systemDelete'))
    );
  }

  private log(message: string) {
    this.messageService.add(`SystemService: ${message}`);
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
  // GET system by id. Return `undefined` when id not found
  getSystemNo404<Data>(id: number): Observable<System> {
    const url = `${this.systemsUrl}/?id=${id}`;
    return this.http.get<System[]>(url, httpOptions)
      .pipe(
        map(systems => systems[0]), // returns a {0|1} element array
        tap(h => {
          const outcome = h ? `fetched` : `did not find`;
          this.log(`${outcome} system id=${id}`);
        }),
        catchError(this.handleError<System>(`getSystem id=${id}`))
      );
  }

  // GET system by id. Will 404 if id not found
  getSystem(id: number): Observable<System> {
    const url = `${this.systemsUrl}/${id}`;
    // TODO: send the message _after_ fetching the system
    this.messageService.add(`SystemsService: fetched system id=${id}`);
    return this.http.get<System>(url, httpOptions).pipe(
      tap(_ => this.log(`fetched system id=${id}`)),
      catchError(this.handleError<System>(`getSystem id=${id}`))
    );
  }

  // GET systems whose name contains search term 
  searchSystem(term: string): Observable<System[]> {
    if (!term.trim()) {
      // if not search term, return empty system array.
      return of([]);
    }
    return this.http.get<System[]>(`${this.systemsUrl}/?name=${term}`, httpOptions).pipe(
      tap(_ => this.log(`found systems matching "${term}"`)),
      catchError(this.handleError<System[]>('searchSystems', []))
    );
  }
  */
}
