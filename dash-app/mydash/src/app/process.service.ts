import { Injectable } from '@angular/core';
import { Process } from './process';
import { PROCESSES } from './mock-processes';
import { Observable, of } from 'rxjs';
import { MessageService } from './message.service';


@Injectable({
  providedIn: 'root'
})
export class ProcessService {

  constructor(private messageService: MessageService) { }

  getProcesses(): Observable<Process[]> {
    // TODO: send the message _after_ fetching the processes
    this.messageService.add('ProcessService: fetched processes');
    return of (PROCESSES);
  }

/* Original (not Observable)
  getProcesses(): Process[] {
    return PROCESSES;
  }*/
}
