import { Directive, Input, TemplateRef, ViewContainerRef } from '@angular/core';
import { AuthService } from '../_service/auth.service';
import { take } from 'rxjs';

@Directive({
  selector: '[appHasRole]',
  standalone: true
})
export class HasRoleDirective {

  user: any;
  
  @Input() set appHasRole(roles: string[]) {
    if (this.user.role.some((r: string) => roles.includes(r.toLowerCase()))) {
      this.viewContainer.createEmbeddedView(this.templateRef);
    } else {
      this.viewContainer.clear();
    }
  }


  constructor(private templateRef: TemplateRef<any>,
    private viewContainer: ViewContainerRef, private authService: AuthService) {

    this.authService.userSource$.pipe(take(1)).subscribe({
      next: (user: any) => {
        if (user) {
          this.user = user;
        }
      }
    })

  }


}
