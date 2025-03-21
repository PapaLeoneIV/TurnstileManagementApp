import {Component, OnInit} from '@angular/core';
import {USER_INFO_FILTERS} from "../../models/user-info-filter";
import {ButtonFilterService, MainFilterService} from '../../service/button-filter.service';
import {DataService} from '../../service/data.service';
import {ApiResponse} from '../../models/api-response';
import {UserDTO} from '../../models/dto/user-dto';
import {queryFilters} from '../../models/enum';
import {Observable, takeUntil} from 'rxjs';

function mapUserDTO(user: UserDTO): UserDTO {
  return {
    usertype: user.usertype,
    name: user.name,
    surname: user.surname,
    email: user.email,
    role: {
      description: user.role.description,
      level: user.role.level
    },
    company: {
      name: user.company.name,
      address: user.company.address
    },
    badge: {
      allowed_enter_time: user.badge.allowed_enter_time,
      allowed_exit_time: user.badge.allowed_exit_time,
      expiry: user.badge.expiry,
      rfid: user.badge.rfid
    }
  };
}

@Component({
  selector: 'app-filter-list',
  imports: [],
  templateUrl: './filter-list.component.html',
  styleUrl: './filter-list.component.css'
})
export class FilterListComponent implements OnInit {
  protected data: UserDTO[] = [];

  constructor(private dataService: DataService,
              private buttonFilterService: ButtonFilterService,
              private mainFilterService: MainFilterService
  ) {
  }

  ngOnInit() {
    this.dataService.getUserInsideOffice("User").subscribe((response) => this.data = response.data.map(mapUserDTO));
  }

    //fetch data
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
        observable.subscribe((response) => {
          this.data = response.data.map(mapUserDTO);
          this.buttonFilterService.ee.emit(this.data);
        });
      }

      this.mainFilterService.ee.emit(filter);
    }


  filters = USER_INFO_FILTERS;
}
