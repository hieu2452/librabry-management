import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { AuthService } from '../_service/auth.service';

export const routeGuard: CanActivateFn = (route, state) => {
  const router = inject(Router);
  const authService = inject(AuthService);
  const expectedrole = route.data['accessRole'];
  console.log(expectedrole)
  let isAuthorized = false;

  authService.getUser().role.some((r: any) => {
    if (expectedrole.includes(r)) {
      isAuthorized = true;
    }
  })

  if (!isAuthorized) {
    router.navigateByUrl(router.url)
  }

  return isAuthorized;
};
