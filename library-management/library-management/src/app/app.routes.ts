import { Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { ManageBookComponent } from './manage-book/manage-book.component';
import { LayoutComponent } from './layout/layout.component';
import { authenticatedGuard } from './_guard/authenticated.guard';

export const routes: Routes = [
    {
        path: '',
        pathMatch: 'full',
        redirectTo: 'login'
    },
    {
        path: '',
        // redirectTo: 'dashboard',
        // pathMatch: 'full',
        canActivateChild: [authenticatedGuard],
        children: [

            {
                path: 'dashboard',
                loadComponent: () => import('./layout/layout.component').then(a => a.LayoutComponent),
                loadChildren: () =>
                    import('./routing/dashboard.routes')
                        .then(m => m.DASHBOARD_ROUTES)
            }
        ],
    },
    {
        path: 'login',
        component: LoginComponent
    },

];
