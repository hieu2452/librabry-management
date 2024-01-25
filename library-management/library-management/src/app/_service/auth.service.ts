import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, map } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private userSource = new BehaviorSubject(null);
  userSource$ = this.userSource.asObservable();

  baseUrl = 'http://localhost:8080/'

  constructor(private http: HttpClient) { }

  login(user: any) {
    return this.http.post(this.baseUrl + 'api/auth/login', user).pipe(
      map((response: any) => {
        if (response) {
          this.setCurrentUser(response);
          return response;
        }
      })
    )
  }

  setCurrentUser(user: any) {
    const strUser = JSON.stringify(user);
    const accessToken = user.accessToken;
    localStorage.setItem('user', strUser);
    localStorage.setItem('accessToken', accessToken);
    this.userSource.next(user)
  }
}
