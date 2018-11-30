import { Component, OnInit } from '@angular/core';
import { Category } from '../category';
import { CategoryService } from '../category.service'


@Component({
  selector: 'app-category',
  templateUrl: './category.component.html',
  styleUrls: ['./category.component.css']
})
export class CategoryComponent implements OnInit {

  categories: Category[];

  constructor(private categoryService: CategoryService) {

  }

  ngOnInit() {
    
    this.categoryService.getCategories()
      .subscribe( data => {
        this.categories = data;
      });
  };

  

}
