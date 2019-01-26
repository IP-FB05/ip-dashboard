import { Injectable } from '@angular/core';
import { Observable, of, observable } from 'rxjs';
import { catchError, map, tap } from 'rxjs/operators';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { TestBed } from '@angular/core/testing';

// Import Models
import { ProcessInstance } from '../../process/processInstance';

// Import Components

// Import Services
import { MessageService } from '../../messages/message.service';

const httpOptionsCamundaREST = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json', 'Accept': 'application/json'  })
};

@Injectable({
  providedIn: 'root'
})
export class DoTaskService {

  constructor(
    private http: HttpClient,
    private messageService: MessageService) { }


    public getTaskId(instance: String): Observable<any> {
      const url = "http://ip-dash.ddnss.ch:8080/engine-rest/task?processInstanceId=" + instance;
      return this.http.get<any>(url);
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
