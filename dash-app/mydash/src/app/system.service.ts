import { Injectable } from '@angular/core';
import { System } from './system';
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

export class SystemService {

  private systemsUrl = 'api/systems'; // URL to web api

  constructor(
    private http: HttpClient,
    private messageService: MessageService) { }

  // GET Systems from the server
  getSystems(): Observable<System[]> {
    // TODO: send the message _after_ fetching the systems
    this.messageService.add('SystemService: fetched systems');
    return this.http.get<System[]>("http://localhost:8080/systems")
      .pipe(
        tap(_ => this.log('fetched systems')),
        catchError(this.handleError('getSystems', []))
      );
  }

  // GET system by id. Return `undefined` when id not found */
  getSystemNo404<Data>(id: number): Observable<System> {
    const url = `${this.systemsUrl}/?id=${id}`;
    return this.http.get<System[]>(url)
      .pipe(
        map(systems => systems[0]), // returns a {0|1} element array
        tap(h => {
          const outcome = h ? `fetched` : `did not find`;
          this.log(`${outcome} system id=${id}`);
        }),
        catchError(this.handleError<System>(`getSystem id=${id}`))
      );
  }

  // GET system by id. Will 404 if id not found */
  getSystem(id: number): Observable<System> {
    const url = `${this.systemsUrl}/${id}`;
    // TODO: send the message _after_ fetching the system
    this.messageService.add(`SystemsService: fetched system id=${id}`);
    return this.http.get<System>(url).pipe(
      tap(_ => this.log(`fetched system id=${id}`)),
      catchError(this.handleError<System>(`getSystem id=${id}`))
    );
  }

  // GET systems whose name contains search term */
  searchSystem(term: string): Observable<System[]> {
    if (!term.trim()) {
      // if not search term, return empty system array.
      return of([]);
    }
    return this.http.get<System[]>(`${this.systemsUrl}/?name=${term}`).pipe(
      tap(_ => this.log(`found systems matching "${term}"`)),
      catchError(this.handleError<System[]>('searchSystems', []))
    );
  }

  // PUT: update the systems on the server */
  updateSystem(system: System): Observable<any> {
    return this.http.put(this.systemsUrl, system, httpOptions).pipe(
      tap(_ => this.log(`updated system id=${system.systemID}`)),
      catchError(this.handleError<any>('updateSystem'))
    );
  }

  // POST: add a new system to the server */
  addSystem(system: System): Observable<System> {
    if(!system.name || system.name == "" || !system.description || system.description == "" || !system.link || system.link == "") { return; }
    return this.http.post<System>("http://localhost:8080/systemAdd", system, httpOptions).pipe(
      tap(_ => this.log(`added system w/ id=${system.systemID}`)),
      catchError(this.handleError<System>('addSystem'))
    );
  }

  /** DELETE: delete the System from the server */
  deleteSystem(system: System | number): Observable<System> {
    const id = typeof system === 'number' ? system : system.systemID;
    const url = `http://localhost:8080/systemDelete/${id}`;

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
}
