import { Injectable } from '@angular/core';
import { Process } from './process';
import { PROCESSES } from './mock-processes';
import { Observable, of } from 'rxjs';


@Injectable({
  providedIn: 'root'
})
export class ProcessService {

  constructor() { }

  getProcesses(): Observable<Process[]> {
    return of (PROCESSES);
  }
/* Original (not Observable)
  getProcesses(): Process[] {
    return PROCESSES;
  }*/
}
