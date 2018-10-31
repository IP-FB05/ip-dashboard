import { Injectable } from '@angular/core';
import { Process } from './process';
import { PROCESSES } from './mock-processes';

@Injectable({
  providedIn: 'root'
})
export class ProcessService {

  constructor() { }

  getProcesses(): Process[] {
    return PROCESSES;
  }
}
