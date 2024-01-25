import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class MemberService {
  baseUrl = 'http://localhost:8080/'

  constructor(private http: HttpClient) { }

  getMembers() {
    return this.http.get(this.baseUrl + 'api/member/get')
  }
}
