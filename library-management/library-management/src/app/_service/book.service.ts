import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class BookService {
  baseUrl = 'http://localhost:8080/'

  constructor(private http: HttpClient) { }

  getBooks() {
    return this.http.get(this.baseUrl + 'api/book/get-all')
  }

  addBook(book: any) {
    return this.http.post(this.baseUrl + 'api/book/create-book', book);
  }
}
