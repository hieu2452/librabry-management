import { Component } from '@angular/core';
import { MATERIAL_MODULDE } from '../material/material.module';
import { RouterModule, RouterOutlet } from '@angular/router';
import { HasRoleDirective } from '../_directive/has-role.directive';
import { NgFor } from '@angular/common';
import { HeaderComponent } from '../header/header.component';

@Component({
  selector: 'app-layout',
  standalone: true,
  imports: [MATERIAL_MODULDE, RouterModule, HasRoleDirective, NgFor, HeaderComponent],
  templateUrl: './layout.component.html',
  styleUrl: './layout.component.css'
})
export class LayoutComponent {

  menuItems = [
    { state: 'dashboard', name: 'Dashboard', type: 'link', icon: 'dashboard', role: ['librarian', 'admin'] },
    { state: 'manage-member', name: 'Manage  Member', type: 'link', icon: 'people', role: ['librarian', 'admin'] },
    { state: 'manage-staff', name: 'Manage Staff', type: 'link', icon: 'people', role: ['admin'] },
    { state: 'manage-book', name: 'View Book', type: 'link', icon: 'backup_table', role: ['admin', 'librarian'] },
    { state: 'manage-category', name: 'Manage Category', type: 'link', icon: 'category', role: ['librarian', 'admin'] },
    { state: 'manage-borrow', name: 'Manage Borrow', type: 'link', icon: 'people', role: ['admin', 'librarian'] },
    { state: 'borrow', name: 'Borrow', type: 'link', icon: 'people', role: ['admin', 'librarian'] },
  ]
}
