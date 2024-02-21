import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class BorrowService {
  baseUrl = 'http://server:8080/';

  constructor(private http: HttpClient) {

  }

  getBill() {
    return this.http.get(this.baseUrl + 'api/checkOut')
  }

  getBillDetail(billId: number) {
    return this.http.get(this.baseUrl + 'api/checkOut/detail/' + billId);
  }

  borrowBooks(request: any) {
    return this.http.post(this.baseUrl + 'api/checkOut/create', request);
  }
}
