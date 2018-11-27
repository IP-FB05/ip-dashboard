import { Component, OnInit } from '@angular/core';

import { Category } from '../category';
import { CategoryService } from '../category.service'

@Component({
  selector: 'app-category-add',
  templateUrl: './category-add.component.html',
  styleUrls: ['./category-add.component.css']
})
export class CategoryAddComponent implements OnInit {

  constructor(private categoryService: CategoryService) {

  }

  ngOnInit() {
  }

  category: Category = new Category();
  
  createCategory(): void {
    this.categoryService.createCategory(this.category)
        .subscribe( data => {
          alert("Category created successfully.");
        });
  };
}
