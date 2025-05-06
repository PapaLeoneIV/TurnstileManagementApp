import { Component } from '@angular/core';
import { HeaderComponent } from '@shared/components/header/header.component';
import { FilterListComponent } from './components/filter-list/filter-list.component';
import { AdditionalFiltersComponent } from './components/additional-filters/additional-filters.component';
import { NavBarComponent } from '@app/shared/components/nav-bar/nav-bar.component';
import { TableComponent } from './components/table/table.component';


@Component({
  selector: 'app-user-info',
  imports: [NavBarComponent, HeaderComponent, FilterListComponent, AdditionalFiltersComponent, TableComponent ],
  templateUrl: './user-info.component.html',
  styleUrl: './user-info.component.css'
})
export class UserInfoComponent {

  isOpen = false;

  toggleSidebar() {
    this.isOpen = !this.isOpen;
  }

}
