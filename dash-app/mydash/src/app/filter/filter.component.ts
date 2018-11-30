import { Component, OnInit } from '@angular/core';
import { Location } from '@angular/common';
import { Router } from '@angular/router';

import { FormControl, FormGroup } from '@angular/forms';

import { Category } from '../category/category';
import { CategoryService } from '../category/category.service'

import { DocumentsComponent } from '../document/documents/documents.component';
import { ProcessesComponent } from '../process/processes/processes.component';
import { SystemsComponent } from '../system/systems/systems.component';

@Component({
  selector: 'app-filter',
  templateUrl: './filter.component.html',
  styleUrls: ['./filter.component.css']
})
export class FilterComponent implements OnInit {

  constructor(private cs: CategoryService,
    private dc: DocumentsComponent,
    private pc: ProcessesComponent,
    private sc: SystemsComponent,
    location: Location, router: Router) {

    router.events.subscribe((val) => {
      if (location.path() != '') {
        this.route = location.path();
      } else {
        this.route = '/dashboard'
      }
    });

  }

  categories: Category[];
  route: string;

  ngOnInit() {
    this.cs.getCategories()
      .subscribe(category => this.categories = category);
  }


  filter(name: string) {
    switch (this.route) {
      case '/processes': {
        alert("Kategorien der Prozesse fehlen in der DB")
        if (name === "") {
          this.pc.getProcesses();
        } else {
        //this.pc.filterDocuments(name);
        } 
        break;
      }
      case '/documents': {
        if (name === "") {
          this.dc.getDokumente();
        } else {
          this.dc.filterDocuments(name);
        }
        break;
      }
      case '/systems': {
        alert("Kategorien der Systeme fehlen in der DB")
        if (name === "") {
          this.sc.getSystems();
        } else {
        //this.sc.filterDocuments(name);
        }
        break;
      }
      default: {
        console.log("This route is not supported by the filter!");
        break;
      }
    }
  }

  form = new FormGroup({
    categoryControl: new FormControl()
  });

}
