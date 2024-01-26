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
    return this.http.get(this.baseUrl + 'api/book',{ observe: 'response', params }).pipe(
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

  updateBook(book: any) {
    return this.http.put(this.baseUrl + 'api/book/update', book)
  }
}
