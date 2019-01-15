import { Component, OnInit } from '@angular/core';

// Import Components
import { Category } from '../category';

// Import Services
import { CategoryService } from '../category.service'


@Component({
  selector: 'app-category',
  templateUrl: './category.component.html',
  styleUrls: ['./category.component.css']
})
export class CategoryComponent implements OnInit {

  categories: Category[];

  constructor(private categoryService: CategoryService) { }

  ngOnInit() {
    this.categoryService.getCategories()
      .subscribe( data => {
        this.categories = data;
      });
  };
}
