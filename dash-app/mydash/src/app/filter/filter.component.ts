import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { DocumentsComponent } from '../document/documents/documents.component';
import { Category } from '../category/category';
import { CategoryService } from '../category/category.service'

@Component({
  selector: 'app-filter',
  templateUrl: './filter.component.html',
  styleUrls: ['./filter.component.css']
})
export class FilterComponent implements OnInit {

  constructor(private dc:DocumentsComponent, private cs: CategoryService) { }

  categories: Category[];



  ngOnInit() {
    this.cs.getCategories()
      .subscribe(category => this.categories = category);
  }


  filter(name: string) {
    if (name === "All") this.dc.getDokumente();
    this.dc.filterDocuments(name);
  }

  form = new FormGroup({
    categoryControl: new FormControl()
  });

}
