import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, of, observable } from 'rxjs';
import { catchError, map, tap } from 'rxjs/operators';

// Import Models
import { ProcessInstance } from '../process/processInstance';

// Import Components

// Import Services
import { MessageService } from '../message.service';

@Injectable({
  providedIn: 'root'
})
export class MyprocessesService {

  constructor(
    private http: HttpClient,
    private messageService: MessageService) { }

    getProcessInstances(): Observable<ProcessInstance[]> {
      // TODO: send the message _after_ fetching the processes
      this.messageService.add('ProcessService: fetched processes');
      return this.http.get<ProcessInstance[]>("http://ec2-18-185-50-159.eu-central-1.compute.amazonaws.com:8080/engine-rest/history/process-instance")
        .pipe(
          tap(_ => this.log('fetched processes')),
          catchError(this.handleError('getProcesses', []))
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
