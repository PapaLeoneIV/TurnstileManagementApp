import { Component } from '@angular/core';
import { LogoutService } from '@app/core/service/logout.service';


@Component({
  selector: 'app-logout',
  standalone: true,
  imports: [],
  templateUrl: './logout.component.html',
  styleUrl: './logout.component.css'
})
export class LogoutComponent {

    constructor(private logoutService: LogoutService) {}

    logout() {
      this.logoutService.performLogout().subscribe({
        next: () => {
          console.log('Logout successful');
        },
        error: (err) => {
          console.error('Logout failed', err);
        },
      });
    }
  }
