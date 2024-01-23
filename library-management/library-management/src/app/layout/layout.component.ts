import { Component } from '@angular/core';
import { MATERIAL_MODULDE } from '../material/material.module';
import { RouterModule, RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-layout',
  standalone: true,
  imports: [MATERIAL_MODULDE, RouterModule],
  templateUrl: './layout.component.html',
  styleUrl: './layout.component.css'
})
export class LayoutComponent {

  menuItems = [
    { state: 'dashboard', name: 'Dashboard', type: 'link', icon: 'dashboard', role: ['user', 'admin'] },
    { state: 'member', name: 'Manage  Member', type: 'link', icon: 'people', role: ['admin'] },
    { state: 'staff', name: 'Manage Staff', type: 'link', icon: 'people', role: ['admin'] },
    { state: 'book', name: 'View Book', type: 'link', icon: 'backup_table', role: ['admin', 'user'] },
    { state: 'category', name: 'Manager Category', type: 'link', icon: 'category', role: ['admin'] },
    { state: 'Borrow', name: 'Manager Borrow', type: 'link', icon: 'people', role: ['admin'] },
    // { state: 'order', name: 'Manager Order', type: 'link', icon: 'backup_table', role: ['admin','user'] },
  ]
}
