import { Component, Inject, OnInit, PLATFORM_ID } from '@angular/core';
import { CommonModule, isPlatformBrowser } from '@angular/common';
import { RouterOutlet } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { MATERIAL_MODULDE } from './material/material.module';
import { AuthService } from './_service/auth.service';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule, RouterOutlet, LoginComponent, MATERIAL_MODULDE],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent implements OnInit {
  title = 'library-management';
  constructor(private authService: AuthService, @Inject(PLATFORM_ID) private platformId: any) {

  }
  ngOnInit(): void {
    this.setCurrentUser();
  }
  setCurrentUser() {
    if (isPlatformBrowser(this.platformId)) {
      let strUser = localStorage.getItem('user');
      
      if (strUser) {
        const user = JSON.parse(strUser);
        this.authService.setCurrentUser(user);
      }
    }
    return;
  }
}
