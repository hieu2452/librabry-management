import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BookParam } from '../_modal/BookParam';
import { map } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class BookService {
  baseUrl = 'http://localhost:8080/'

  constructor(private http: HttpClient) { }

  getBooks(bookParam: BookParam) {
    let params = new HttpParams();
    params = params.append('category', bookParam.category);
    params = params.append('publisher', bookParam.publisher);
    params = params.append('language', bookParam.language);
    params = params.append('pageNumber', bookParam.pageNumber);
    params = params.append('pageSize', bookParam.pageSize);
    return this.http.get(this.baseUrl + 'api/book', { observe: 'response', params }).pipe(
      map(response => {
        if (response.body)
          return response.body
        return
      })
    );
  }

  addBook(book: any) {
    return this.http.post(this.baseUrl + 'api/book/create', book);
  }

  addBookByExcel(file: any) {
    console.log(file)
    return this.http.post(this.baseUrl + 'api/book/create-excel', file);
  }

  updateBook(id:any,book: any) {
    return this.http.put(this.baseUrl + 'api/book/update/' + id, book)
  }

  search(bookParam: BookParam) {
    let params = new HttpParams();
    params = params.append('keyword', bookParam.keyword);
    return this.http.get(this.baseUrl + 'api/book/search', { observe: 'response', params }).pipe(
      map(response => {
        if (response.body)
          return response.body
        return
      })
    );
  }
}
