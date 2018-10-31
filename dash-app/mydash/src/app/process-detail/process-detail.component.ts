import { Component, OnInit, Input } from '@angular/core';
import { Process } from '../process'; 

import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';

import { ProcessService }  from '../process.service';

@Component({
  selector: 'app-process-detail',
  templateUrl: './process-detail.component.html',
  styleUrls: ['./process-detail.component.css']
})
export class ProcessDetailComponent implements OnInit {

  @Input() process: Process;

  constructor(
    private route: ActivatedRoute,
    private processService: ProcessService,
    private location: Location
  ) {}

  ngOnInit() {
    this.getProcess();
  }

  getProcess(): void {
    const id = +this.route.snapshot.paramMap.get('id');
    this.processService.getProcess(id)
      .subscribe(process => this.process = process);
  }

  goBack(): void {
    this.location.back();
  }

}
