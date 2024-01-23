import { Component } from '@angular/core';
import { MATERIAL_MODULDE } from '../material/material.module';
import { RouterOutlet } from '@angular/router';
import { LayoutComponent } from '../layout/layout.component';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [MATERIAL_MODULDE, RouterOutlet, LayoutComponent],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.css'
})
export class DashboardComponent {


  constructor() {

  }
}



