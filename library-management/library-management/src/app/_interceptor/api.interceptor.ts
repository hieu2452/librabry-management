import { HttpInterceptorFn } from '@angular/common/http';
import { AuthService } from '../_service/auth.service';
import { inject } from '@angular/core';
import { EMPTY } from 'rxjs';
import { Router } from '@angular/router';

export const apiInterceptor: HttpInterceptorFn = (req, next) => {
  if (req.url.includes('/login')) {
    return next(req);
  }

  const authService = inject(AuthService);


  if (authService.isRefreshing) {
    return EMPTY;
  }

  const dataUser = authService.getUser();

  if (!dataUser || !dataUser.accessToken) {
    inject(Router).navigateByUrl('/login');
    return EMPTY;
  }

  const requestClone = authService.addTokenHeader(req, dataUser.accessToken);

  return next(requestClone);
};
