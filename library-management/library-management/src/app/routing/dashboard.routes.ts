import { Routes } from "@angular/router";
import { ManageBookComponent } from "../manage-book/manage-book.component";
import { DashboardComponent } from "../dashboard/dashboard.component";
import { LayoutComponent } from "../layout/layout.component";
import { routeGuard } from "../_guard/route.guard";


export const DASHBOARD_ROUTES: Routes = [
    {
        path: '',
        pathMatch: 'full',
        redirectTo:'book'
    },
    {
        path: 'manage-book',
        canActivate: [routeGuard],
        loadComponent: () => import('../manage-book/manage-book.component').then(a => a.ManageBookComponent),
        data: {
            accessRole: ['ADMIN', 'LIBRARIAN']
        }
    },
    {
        path: 'manage-member',
        canActivate: [routeGuard],
        data: {
            accessRole: ['ADMIN', 'LIBRARIAN']
        },
        loadComponent: () => import('../manage-member/manage-member.component').then(a => a.ManageMemberComponent),
    },
    {
        path: 'manage-borrow',
        canActivate: [routeGuard],
        data: {
            accessRole: ['ADMIN', 'LIBRARIAN']
        },
        loadComponent: () => import('../manage-borrow/manage-borrow.component').then(a => a.ManageBorrowComponent),
    },
    {
        path: 'borrow',
        canActivate: [routeGuard],
        data: {
            accessRole: ['ADMIN', 'LIBRARIAN']
        },
        loadComponent: () => import('../borrow/borrow.component').then(a => a.BorrowComponent),
    }
];