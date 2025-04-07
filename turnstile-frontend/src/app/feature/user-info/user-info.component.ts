import { Component } from '@angular/core';
import { HeaderComponent } from '@shared/components/header/header.component';
import { FilterListComponent } from './components/filter-list/filter-list.component';
import { TableContainerComponent } from './components/table-container/table-container.component';
import { NgClass, NgIf } from '@angular/common';
import { NAV_BUTTON_LIST } from '@shared/models/nav-button-list';
import { AdditionalFiltersComponent } from './components/additional-filters/additional-filters.component';
import { RouterLink } from '@angular/router';


@Component({
  selector: 'app-user-info',
  imports: [HeaderComponent, FilterListComponent, AdditionalFiltersComponent, TableContainerComponent, NgClass, NgIf, RouterLink],
  templateUrl: './user-info.component.html',
  styleUrl: './user-info.component.css'
})
export class UserInfoComponent {

  constructor() { }

  isOpen = false;

  toggleSidebar() {
    this.isOpen = !this.isOpen;
  }

  protected readonly buttons = NAV_BUTTON_LIST;
}
