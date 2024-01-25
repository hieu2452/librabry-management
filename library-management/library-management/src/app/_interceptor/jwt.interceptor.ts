import { HttpErrorResponse, HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { AuthService } from '../_service/auth.service';
import { catchError, take } from 'rxjs';
import { Router } from '@angular/router';

export const jwtInterceptor: HttpInterceptorFn = (req, next) => {
  const authService = inject(AuthService);
  const router = inject(Router);
  const user = authService.userSource$.pipe(take(1)).subscribe({
    next: (data: any) => {
      console.log(data)
      if (data) {
        req = req.clone({
          setHeaders: {
            Authorization: `Bearer ${data.accessToken}`
          }
        })
        console.log(req)
      }
    }
  })
  return next(req).pipe(
    catchError((error: HttpErrorResponse) => {
      if (error) {
        router.navigateByUrl('/login');
      }
      throw error;
    })
  );
};
