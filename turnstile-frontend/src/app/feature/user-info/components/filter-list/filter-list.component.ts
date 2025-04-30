import { Component } from '@angular/core';
import { Observable } from 'rxjs';
import { USER_INFO_FILTERS } from '@shared/models/user-info-filter';
import { ButtonFilterService, MainFilterService } from '@core/service/button-filter.service';
import { UserService } from '@core/service/user.service';
import { ApiResponse } from '@shared/models/api-response';
import { UserDTO } from '@core/dto/user-dto';
import { queryFilters } from '@shared/models/enum';
import { mapUserDTO } from '@utils/Mapper';

@Component({
  selector: 'app-filter-list',
  imports: [],
  templateUrl: './filter-list.component.html',
  styleUrl: './filter-list.component.css'
})
export class FilterListComponent {
  protected data: UserDTO[] = [];

  constructor(private dataService: UserService,
    private buttonFilterService: ButtonFilterService,
    private mainFilterService: MainFilterService
  ) {
  }


  updateData(filter: string) {
    let observable: Observable<ApiResponse<UserDTO>> | null = null;

    switch (filter) {
      case queryFilters.ALL_USERS:
        observable = this.dataService.getAllUsers();
        break;
      case queryFilters.BY_EMPLOYEE:
        observable = this.dataService.getByEmployee();
        break;
      case queryFilters.BY_VISITOR:
        observable = this.dataService.getByVisitor();
        break;
      
    }

    if (observable) {
      observable.subscribe({
        next: (response) =>  this.buttonFilterService.ee.emit(response.data.map(mapUserDTO)),
        error: (response) => console.error(response),
        complete: undefined,        
      }
      );
    }

    this.mainFilterService.ee.emit(filter);
  }


  filters = USER_INFO_FILTERS;
}
