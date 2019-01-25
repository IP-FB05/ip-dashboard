import { Component, OnInit, Input } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';

// Import Models
import { Process } from '../process'; 

// Import Components

// Import Services
import { ProcessService }  from '../process.service';
import { AuthService } from '../../login/auth/auth.service';

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
    private location: Location,
    public authService: AuthService
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

  save(): void {
    this.processService.updateProcess(this.process)
      .subscribe(() => this.goBack());
  }

}
