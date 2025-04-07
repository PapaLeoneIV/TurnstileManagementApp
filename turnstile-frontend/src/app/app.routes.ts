import { Routes } from '@angular/router';
import { UserInfoComponent } from '@feature/user-info/user-info.component';
import { HomepageComponent } from '@feature/homepage/homepage.component';
import { canActivateAuthRole } from '@utils/keycloak/guards/AuthGuard';
import { AddPageComponent } from '@feature/add-page/add-page.component';

export const routes: Routes = [
    { path: 'home-page', component: HomepageComponent },
    { path: 'user-info', component: UserInfoComponent, canActivate: [canActivateAuthRole], data: { role: 'admin' } },
    { path: 'add', component: AddPageComponent, canActivate: [canActivateAuthRole], data: { role: 'admin' } },
    { path: '**', redirectTo: "home-page" }
];
