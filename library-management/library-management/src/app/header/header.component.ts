import { Component } from '@angular/core';
import { MATERIAL_MODULDE } from '../material/material.module';
import { AuthService } from '../_service/auth.service';

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [MATERIAL_MODULDE],
  templateUrl: './header.component.html',
  styleUrl: './header.component.css'
})
export class HeaderComponent {
  constructor(public authService: AuthService) {

  }

  onLogout() {
    this.authService.logout();
  }
}
