import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, of, observable } from 'rxjs';
import { catchError, map, tap } from 'rxjs/operators';

// Import Models
import { ProcessInstance } from '../process/processInstance';
import { Tasks } from './tasks';

// Import Components

// Import Services
import { MessageService } from '../messages/message.service';
import { AuthorizationService } from '../login/auth/authorization.service';

@Injectable({
  providedIn: 'root'
})
export class MyprocessesService {

  constructor(
    private http: HttpClient,
    private authorizationService: AuthorizationService,
    private messageService: MessageService) { }

    getProcessInstances(): Observable<ProcessInstance[]> {
      // TODO: send the message _after_ fetching the processes
      this.messageService.add('ProcessService: fetched processes');
      const httpOptionsCamundaREST = {
        headers: new HttpHeaders({ 'Content-Type': 'application/json', 'Authorization': 'Basic ' +  this.authorizationService.getAuthData()})
      };
      return this.http.get<ProcessInstance[]>("http://localhost:8080/engine-rest/history/process-instance", httpOptionsCamundaREST)
        .pipe(
          tap(_ => this.log('fetched processes')),
          catchError(this.handleError('getProcesses', []))
        );
    }

    getTasks(): Observable<Tasks[]> {
      // TODO: send the message _after_ fetching the tasks
      this.messageService.add('ProcessService: fetched tasks');
      const httpOptionsCamundaREST = {
        headers: new HttpHeaders({ 'Content-Type': 'application/json', 'Authorization': 'Basic ' +  this.authorizationService.getAuthData()})
      };
      return this.http.get<Tasks[]>("http://localhost:8080/engine-rest/history/task", httpOptionsCamundaREST)
        .pipe(
          tap(_ => this.log('fetched tasks')),
          catchError(this.handleError('getTasks', []))
        );
    }


    private log(message: string) {
      this.messageService.add(`MyprocessService: ${message}`);
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
