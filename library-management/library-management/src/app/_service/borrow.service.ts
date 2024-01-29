import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class BorrowService {
  baseUrl = 'http://localhost:8080/';

  constructor(private http: HttpClient) {

  }

  getBill() {
    return this.http.get(this.baseUrl + 'api/bill')
  }
}
