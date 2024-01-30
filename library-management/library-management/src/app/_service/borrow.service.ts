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

  getBillDetail(billId: number) {
    return this.http.get(this.baseUrl + 'api/bill/detail/' + billId);
  }

  borrowBooks(request: any) {
    return this.http.post(this.baseUrl + 'api/bill/create', request);
  }
}
