import {Component, OnInit, OnDestroy} from '@angular/core';
import {ButtonFilterService, MainFilterService} from '../../../service/button-filter.service';
import {DataService} from '../../../service/data.service';
import {queryFilters as qf, userType} from '../../../models/enum';
import {UserDTO} from '../../../models/dto/user-dto';
import {Observable, Subject, takeUntil, take} from 'rxjs';
import {ApiResponse} from '../../../models/api-response';

@Component({
  selector: 'app-additional-filters',
  templateUrl: './additional-filters.component.html',
  styleUrl: './additional-filters.component.css'
})
export class AdditionalFiltersComponent implements OnInit, OnDestroy {
  data: UserDTO[] = [];
  additionFilters: string[] = [];
  mainFilter: string = "";
  private destroy$ = new Subject<void>();

  constructor(
    private buttonFilterService: ButtonFilterService,
    private mainFilterService: MainFilterService,
    private dataService: DataService) {}

  ngOnInit() {
    this.mainFilterService.ee.pipe(takeUntil(this.destroy$)).subscribe((mainFilter) => this.updateAdditionFilters(mainFilter));
  }

  applyFilter(filter: string) {
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
        observable = this.dataService.getRoleList();
        break;

      case qf.BY_COMPANY:
        observable = this.dataService.getCompanyList();
        break;
    }

    if (observable) {
      observable.subscribe((response) => {
        this.additionFilters = response;
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
