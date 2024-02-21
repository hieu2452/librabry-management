import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class CategoryService {

  baseUrl = 'http://server:8080/'

  constructor(private http: HttpClient) { }

  getCategories() {
    return this.http.get(this.baseUrl + 'api/category/get')
  }

  getpublisher() {
    return this.http.get(this.baseUrl + 'api/category/get-publisher')
  }

}
