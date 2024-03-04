import { HttpErrorResponse, HttpInterceptorFn, HttpStatusCode } from '@angular/common/http';
import { inject } from '@angular/core';
import { AuthService } from '../_service/auth.service';
import { EMPTY, catchError, concatMap, finalize, map, switchMap, take, throwError } from 'rxjs';
import { Router } from '@angular/router';
import { error, log } from 'console';
import { Token } from '@angular/compiler';

export const jwtInterceptor: HttpInterceptorFn = (req, next) => {
  const authService = inject(AuthService);
  const router = inject(Router);
  if (req.url.includes("/login")) {
    return next(req);
  }
  const user = authService.userSource$.pipe(take(1)).subscribe({
    next: (data: any) => {
      if (data) {
        req = req.clone({
          setHeaders: {
            Authorization: `Bearer   ${data.accessToken}`
          }
        })
      }
    }
  })

  return next(req).pipe(
    catchError((error: HttpErrorResponse) => {
      if (error.status === HttpStatusCode.Unauthorized) {
        authService.isRefreshing = true;
        let user = authService.getUser()
        return authService.refreshToken(user.refreshToken).pipe(
          finalize(() => (authService.isRefreshing = false)),
          concatMap((response: any) => {
            authService.updateToken(response);
            const requestClone = authService.addTokenHeader(req, response.accessToken);
            return next(requestClone);
          }),
          catchError((error: HttpErrorResponse) => {
            if (error.status === HttpStatusCode.Forbidden) {
              router.navigateByUrl('/login');
            }
            return EMPTY;
          })
        )
      }
      return throwError(() => error);
    })
  );

};




