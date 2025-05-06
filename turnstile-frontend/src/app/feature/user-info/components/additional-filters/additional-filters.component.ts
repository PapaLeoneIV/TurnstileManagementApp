import {Component, OnInit, OnDestroy} from '@angular/core';
import {ButtonFilterService, MainFilterService} from '@core/service/button-filter.service';
import {UserService} from '@core/service/user.service';
import {queryFilters as qf, userType} from '@shared/models/enum';
import {UserDTO} from '@core/dto/user-dto';
import {Observable, Subject, takeUntil, take} from 'rxjs';
import {ApiResponse} from '@shared/models/api-response';
import { CompanyService } from '@core/service/company.service';
import { RoleService } from '@core/service/role.service'
import { NgClass, NgFor } from '@angular/common';

@Component({
  selector: 'app-additional-filters',
  imports: [NgFor, NgClass],
  templateUrl: './additional-filters.component.html',
  styleUrl: './additional-filters.component.css'
})
export class AdditionalFiltersComponent implements OnInit, OnDestroy {
  mainFilter: string = "";
  additionFilters: string[] = [];
  selectedItem: string | null = null;

  private destroy$ = new Subject<void>();
  constructor(
    private buttonFilterService: ButtonFilterService,
    private mainFilterService: MainFilterService,
    private dataService: UserService,
    private companyService: CompanyService,
    private roleService: RoleService) {}

  ngOnInit() {
    this.mainFilterService.ee.pipe(takeUntil(this.destroy$)).subscribe((mainFilter) => this.updateAdditionFilters(mainFilter));
  }

  applyFilter(filter: string) {
    this.selectedItem = filter;
    let observable: Observable<ApiResponse<UserDTO>> | null = null;
    switch (this.mainFilter) {
      case qf.BY_COMPANY:
        observable = this.dataService.getUsersWithCompanyName(filter);
        break;
      case qf.BY_ROLE:
        observable = this.dataService.getUserByRole(filter);
        break;
      case qf.BY_INSIDE_OFFICE:
        observable = this.dataService.getUserInsideOffice(filter);
        break;
    }

    if (observable) {
      observable.subscribe((response) => {
        this.buttonFilterService.ee.emit(response.data)
      });
    }

  }

  updateAdditionFilters(mainFilter: string) {
    this.mainFilter = mainFilter;
    let observable: Observable<string[]> | null = null;

    switch (mainFilter) {
      case qf.BY_INSIDE_OFFICE:
        this.additionFilters = [userType.ALL_USERS, userType.EMPLOYEE, userType.VISITOR];
        return;

      case qf.BY_ROLE:
        observable = this.roleService.getRoleList();
        break;

      case qf.BY_COMPANY:
        observable = this.companyService.getCompanyList();
        break;
    }

    if (observable) {
      observable.subscribe({
        next: (response) => this.additionFilters = response,
        error: (response) => console.error(response),
      });
    } else {
      this.additionFilters = [];
    }
  }

  ngOnDestroy() {
    this.destroy$.next();
    this.destroy$.complete();
  }
}
