import { Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { ManageBookComponent } from './manage-book/manage-book.component';
import { LayoutComponent } from './layout/layout.component';

export const routes: Routes = [
    {
        path:'',
        redirectTo: 'dashboard',
        pathMatch : 'full'
    },
    {
        path: 'login',
        component: LoginComponent
    },
    // {
    //     path: 'dashboard',
    //     component: LayoutComponent
    // },
    {
        path: 'dashboard',
        loadComponent: () => import('./layout/layout.component').then(a => a.LayoutComponent),
        loadChildren: () =>
            import('./routing/dashboard.routes')
                .then(m => m.DASHBOARD_ROUTES)
    }
];
