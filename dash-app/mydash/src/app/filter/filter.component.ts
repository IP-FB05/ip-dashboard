import { Component, OnInit } from '@angular/core';
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

  test() {
    //var myselect=document.getElementById("catselect").option
    //let myselect: HTMLSelectElement = <HTMLSelectElement>document.getElementById("catselect");
    //this.dc.test(myselect);
  }

  showSelectValue (mySelect) {
    console.log(mySelect);
  }

  test2() {
    this.dc.test('MCD');
  }

}
