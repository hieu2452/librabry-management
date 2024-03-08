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
    return this.http.get(this.baseUrl + 'api/checkout')
  }

  getBillDetail(billId: number) {
    return this.http.get(this.baseUrl + 'api/checkout/detail/' + billId);
  }

  borrowBooks(request: any) {
    return this.http.post(this.baseUrl + 'api/checkout/create', request);
  }

  returnBooks(bookIds:any, billId:any) {
    return this.http.patch(this.baseUrl + 'api/checkout/return/' + billId, bookIds);
  }
}
