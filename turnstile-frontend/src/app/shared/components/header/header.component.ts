import { Component } from '@angular/core';
import { LogoutComponent } from '../logout/logout.component';
@Component({
  selector: 'app-header',
  imports: [LogoutComponent],
  templateUrl: './header.component.html',
  styleUrl: './header.component.css'
})
export class HeaderComponent {

}
