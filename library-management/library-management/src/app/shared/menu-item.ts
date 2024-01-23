import { Injectable } from "@angular/core";
export interface Menu {
    state: string;
    name: string;
    type: string;
    icon: string;
    role: string[];
}

const MENUITEMS = [
    { state: 'dashboard', name: 'Dashboard', type: 'link', icon: 'dashboard', role: ['user', 'admin'] },
    { state: 'member', name: 'Manage  Member', type: 'link', icon: 'inventory_2', role: ['admin'] },
    { state: 'staff', name: 'Manage Staff', type: 'link', icon: 'user', role: ['admin'] },
    { state: 'book', name: 'View Bok', type: 'link', icon: 'backup_table', role: ['admin', 'user'] },
    { state: 'category', name: 'Manage Category', type: 'link', icon: 'category', role: ['user', 'admin'] },
    { state: 'Borrow', name: 'Manager Borrow', type: 'link', icon: 'people', role: ['admin'] },
    // { state: 'order', name: 'Manager Order', type: 'link', icon: 'backup_table', role: ['admin','user'] },
]

@Injectable()
export class MenuItems {
    getMenuItem(): Menu[] {
        return MENUITEMS;
    }
}