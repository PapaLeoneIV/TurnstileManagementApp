import { Routes } from '@angular/router';
import { UserInfoComponent } from './user-info/user-info.component';
import { HomepageComponent } from './homepage/homepage.component';

export const routes: Routes = [
    { path: 'home-page', component: HomepageComponent },
    { path: 'user-info', component: UserInfoComponent },
    {path: '**',  redirectTo:"home-page"}
];
