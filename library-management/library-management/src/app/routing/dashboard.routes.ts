import { Routes } from "@angular/router";
import { ManageBookComponent } from "../manage-book/manage-book.component";
import { DashboardComponent } from "../dashboard/dashboard.component";
import { LayoutComponent } from "../layout/layout.component";


export const DASHBOARD_ROUTES: Routes = [
    // {
    //     path: '',
    //     pathMatch: 'full',
    //     component: LayoutComponent
    // },
    {
        path: 'book',
        loadComponent: () => import('../manage-book/manage-book.component').then(a => a.ManageBookComponent),
    }
];