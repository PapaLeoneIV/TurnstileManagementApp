import { Component } from '@angular/core';
import { HeaderComponent } from '@shared/components/header/header.component';
import { FilterListComponent } from './components/filter-list/filter-list.component';
import { TableContainerComponent } from './components/table-container/table-container.component';
import { NgClass, NgIf } from '@angular/common';
import { Router } from '@angular/router';
import { NAV_BUTTON_LIST } from '@shared/models/nav-button-list';
import { AdditionalFiltersComponent } from './components/additional-filters/additional-filters.component';
@Component({
  selector: 'app-user-info',
  imports: [HeaderComponent, FilterListComponent, AdditionalFiltersComponent, TableContainerComponent, NgClass, NgIf],
  templateUrl: './user-info.component.html',
  styleUrl: './user-info.component.css'
})
export class UserInfoComponent {

  constructor(private router: Router) { }

  isOpen = false;

  onSelect(route: string) {
    this.router.navigate([route]);
  }

  toggleSidebar() {
    this.isOpen = !this.isOpen;
  }

  protected readonly buttons = NAV_BUTTON_LIST;
}
