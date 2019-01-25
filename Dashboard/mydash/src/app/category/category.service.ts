
import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

// Import Components
import { Category } from '../category/category';

const httpOptions = {
  //headers: new HttpHeaders({ 'Content-Type': 'application/json', 'Authorization': 'Basic ' +  btoa('dashboard:dashboardPW') })
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class CategoryService {

  constructor(private http:HttpClient) {}

	private categoryUrl = 'http://localhost:9090/category';

  public getCategories() {
    return this.http.get<Category[]>(this.categoryUrl+"/all", httpOptions);
  }

  public deleteCategory(category) {
    return this.http.delete(this.categoryUrl + "/"+ category.id, httpOptions);
  }

  public createCategory(category) {
    return this.http.post<Category>(this.categoryUrl+"/add", category, httpOptions);
  }

}
