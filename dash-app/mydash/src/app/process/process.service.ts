import { Injectable } from '@angular/core';
import { Process } from './process';
import { ProcessInstance } from './processInstance';
import { Observable, of, observable } from 'rxjs';
import { MessageService } from '../message.service';

import { catchError, map, tap } from 'rxjs/operators';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { System } from '../system/system';
import { MatSnackBar } from '@angular/material';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json', 'Authorization': 'Basic ' + btoa('dashboard:dashboardPW') })
};

const httpOptionsCamundaREST = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})

export class ProcessService {

  private processesUrl = 'api/processes'; // URL to web api
  private ob: Observable<any>;

  constructor(
    private http: HttpClient,
    private messageService: MessageService,
    public snackBar: MatSnackBar) { }

  // GET Processes from the server
  getProcesses(): Observable<Process[]> {
    // TODO: send the message _after_ fetching the processes
    this.messageService.add('ProcessService: fetched processes');
    return this.http.get<Process[]>("http://localhost:9090/processes", httpOptions)
      .pipe(
        tap(_ => this.log('fetched processes')),
        catchError(this.handleError('getProcesses', []))
      );
  }

  // GET process by id. Return `undefined` when id not found */
  getProcessNo404<Data>(id: number): Observable<Process> {
    const url = `${this.processesUrl}/?id=${id}`;
    return this.http.get<Process[]>(url, httpOptions)
      .pipe(
        map(processes => processes[0]), // returns a {0|1} element array
        tap(h => {
          const outcome = h ? `fetched` : `did not find`;
          this.log(`${outcome} process id=${id}`);
        }),
        catchError(this.handleError<Process>(`getProcess id=${id}`))
      );
  }

  // GET process by id. Will 404 if id not found */
  getProcess(id: number): Observable<Process> {
    const url = `http://localhost:9090/process/${id}`;
    // TODO: send the message _after_ fetching the process
    this.messageService.add(`ProcessService: fetched process id=${id}`);
    return this.http.get<Process>(url, httpOptions).pipe(
      tap(_ => this.log(`fetched process id=${id}`)),
      catchError(this.handleError<Process>(`getProcess id=${id}`))
    );
  }

  // GET processes whose name contains search term */
  searchProcess(term: string): Observable<Process[]> {
    if (!term.trim()) {
      // if not search term, return empty process array.
      return of([]);
    }
    return this.http.get<Process[]>(`${this.processesUrl}/?name=${term}`).pipe(
      tap(_ => this.log(`found processes matching "${term}"`)),
      catchError(this.handleError<Process[]>('searchProcesses', []))
    );
  }

  // PUT: update the process on the server */
  updateProcess(process: Process): Observable<any> {
    return this.http.put(this.processesUrl, process, httpOptions).pipe(
      tap(_ => this.log(`updated process id=${process.processID}`)),
      catchError(this.handleError<any>('updateProcess'))
    );
  }

  // POST: add a new process to the server */
  addProcess(process: Process): Observable<Process> {
    if (!process.name || process.name == "" || !process.description || process.description == "" || !process.bpmn || process.bpmn == "") { 
      if(!process.name || process.name == "") this.openSnackBar("Prozess wurde nicht hinzugefügt! Name erforderlich!");
      else if(!process.description || process.description == "") this.openSnackBar("Prozess wurde nicht hinzugefügt! Beschreibung erforderlich!");
      else if(!process.bpmn || process.bpmn == "") this.openSnackBar("Prozess wurde nicht hinzugefügt! Wähle BPMN-Datei aus !");
      else this.openSnackBar("Prozess wurde nicht hinzugefügt !");
      return; 
    }
    this.openSnackBar("Prozess wurde erfolgreich hinzugefügt !");
    return this.http.post<Process>("http://localhost:9090/processAdd", process, httpOptions).pipe(
      tap((process: Process) => this.log(`added process w/ id=${process.processID}`)),
      catchError(this.handleError<Process>('addProcess'))
    );
  }

  addProcessWithUG(process: Process, selectedUserGroups: number[]): Observable<Process> {
    if (!process.name || process.name == "" || !process.description || process.description == "" || !process.bpmn || process.bpmn == "" || selectedUserGroups == null) { 
      
      if(!process.name || process.name == "") this.openSnackBar("Prozess wurde nicht hinzugefügt! Name erforderlich!");
      else if(!process.description || process.description == "") this.openSnackBar("Prozess wurde nicht hinzugefügt! Beschreibung erforderlich!");
      else if(selectedUserGroups == null) this.openSnackBar("Prozess wurde nicht hinzugefügt! Wähle Sichtbarkeit aus!");
      else if(!process.bpmn || process.bpmn == "") this.openSnackBar("Prozess wurde nicht hinzugefügt! Wähle BPMN-Datei aus !");
      else this.openSnackBar("Prozess wurde nicht hinzugefügt !");
      return; 
    }
    this.openSnackBar("Prozess wurde erfolgreich hinzugefügt !");
    return this.http.post<Process>("http://localhost:9090/processAddWithUG", process, httpOptions).pipe(
      tap((process: Process) => this.log(`added process w/ id=${process.processID}`)),
      catchError(this.handleError<Process>('addProcess'))
    );
  }

  startProcess(process: Process): Observable<ProcessInstance> {
    const id = process.camunda_processID;
    const url = `http://ec2-18-185-50-159.eu-central-1.compute.amazonaws.com:8080/engine-rest/process-definition/${id}/start`;
    return this.http.post<ProcessInstance>(url, httpOptionsCamundaREST);
  }

  addInstance(processInstance: ProcessInstance): Observable<any> {
    return this.http.post<ProcessInstance>("http://localhost:9090/processInstanceAdd", processInstance, httpOptions)
  }

  /** DELETE: delete the Process from the server */
  deleteProcess(process: Process | number): Observable<Process> {
    const id = typeof process === 'number' ? process : process.processID;
    const url = `http://localhost:9090/processDelete/${id}`;

    this.openSnackBar("Prozess wurde erfolgreich gelöscht !");
    return this.http.delete<Process>(url, httpOptions).pipe(
      tap(_ => this.log(`deleted process id=${id}`)),
      catchError(this.handleError<Process>('deleteProcess'))
    );
  }


  /** DELETE: delete BPMN/WAR from the Fileserver */
  deleteProcessFilesFromFileServer(linkBPMN: string, linkWAR: string): Observable<Process> {
    const substringBPMN = linkBPMN.substring(linkBPMN.lastIndexOf("/") + 1);
    const substringWAR = linkWAR.substring(linkWAR.lastIndexOf("/") + 1);
    //console.log(substringBPMN + substringWAR);
    this.messageService.add('ProcessService: Deleted BPMN/WAR from FileServer');

    return this.http.delete<Process>("http://localhost:9090/deleteFiles?filenameBPMN=" + substringBPMN + "&filenameWAR=" + substringWAR, httpOptions).pipe(
      tap(_ => this.log(`deleted files from fileserver`)),
      catchError(this.handleError<Process>('deleteFiles'))
    );
  }

  deleteBPMNFromFileServer(linkBPMN: string): Observable<Process> {
    const substringBPMN = linkBPMN.substring(linkBPMN.lastIndexOf("/") + 1);
    //console.log(substringBPMN + substringWAR);
    this.messageService.add('ProcessService: Deleted BPMN/WAR from FileServer');

    return this.http.delete<Process>("http://localhost:9090/deleteBPMN?filenameBPMN=" + substringBPMN, httpOptions).pipe(
      tap(_ => this.log(`deleted files from fileserver`)),
      catchError(this.handleError<Process>('deleteFiles'))
    );
  }


  /* Original (not Observable)
    getProcesses(): Process[] {
      return PROCESSES;
    }*/

  private log(message: string) {
    this.messageService.add(`ProcessService: ${message}`);
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
}
