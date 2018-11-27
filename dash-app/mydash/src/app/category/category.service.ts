import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

import { Category } from '../category/category';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class CategoryService {

  constructor(private http:HttpClient) {}

  //private categoryUrl = 'http://localhost:8080/category-portal/category';
	private categoryUrl = 'http://localhost:8080/category';

  public getCategories() {
    return this.http.get<Category[]>(this.categoryUrl+"/all");
  }

  public deleteCategory(category) {
    return this.http.delete(this.categoryUrl + "/"+ category.id);
  }

  public createCategory(category) {
    return this.http.post<Category>(this.categoryUrl+"/add", category);
  }

}
