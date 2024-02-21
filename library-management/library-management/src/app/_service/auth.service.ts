import { isPlatformBrowser } from '@angular/common';
import { HttpClient, HttpRequest } from '@angular/common/http';
import { Inject, Injectable, PLATFORM_ID } from '@angular/core';
import { Router } from '@angular/router';
import { BehaviorSubject, map, take } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private userSource = new BehaviorSubject(null);
  userSource$ = this.userSource.asObservable();
  isLoggedIn = false;
  baseUrl = 'http://server:8080/'
  private _isRefreshing = false;

  constructor(private http: HttpClient, @Inject(PLATFORM_ID) private platformId: any, private router: Router) { }

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

  get isRefreshing() {
    return this._isRefreshing;
  }
  set isRefreshing(value) {
    this._isRefreshing = value;
  }

  setCurrentUser(user: any) {
    const accessToken = user.accessToken;
    const jwt = this.getDecodedToken(accessToken);
    user.role = jwt.role;
    user.permission = jwt.permission;
    const strUser = JSON.stringify(user);
    localStorage.setItem('user', strUser);
    this.isLoggedIn = true;
    this.userSource.next(user)
  }

  getDecodedToken(token: string) {
    return JSON.parse(atob(token.split('.')[1]));
  }

  getUser() {
    if (isPlatformBrowser(this.platformId)) {
      let strUser = localStorage.getItem('user');

      if (strUser) {
        const user = JSON.parse(strUser);
        return user;
      }
    }
    return;
  }

  addTokenHeader(request: HttpRequest<unknown>, accessToken: string) {
    return request.clone({ headers: request.headers.set('Authorization', `Bearer ${accessToken}`) });
  }

  updateToken(refreshTokenResposne: any) {
    const user = this.getUser();
    user.accessToken = refreshTokenResposne.accessToken;
    user.refreshToken = refreshTokenResposne.refreshToken;
    this.setCurrentUser(user);
  }

  refreshToken(token: string) {
    return this.http.get(this.baseUrl + 'api/auth/refreshtoken/' + token);
  }

  logout() {
    this.userSource.next(null);
    this.isLoggedIn = false;
    localStorage.clear();
    this.router.navigateByUrl("/login")
  }
}
