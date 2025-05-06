import { NgFor } from '@angular/common';
import { Component } from '@angular/core';
import { RouterLink, RouterModule } from '@angular/router';
import { NAV_BUTTON_LIST } from '@app/shared/models/nav-button-list';

@Component({
  selector: 'app-nav-bar',
  imports: [RouterLink, NgFor, RouterModule],
  templateUrl: './nav-bar.component.html',
  styleUrl: './nav-bar.component.css'
})
export class NavBarComponent {
  
  protected readonly buttons = NAV_BUTTON_LIST;
}
