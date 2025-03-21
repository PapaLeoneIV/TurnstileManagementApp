import {Component, Input, SimpleChange} from '@angular/core';
import {DataService} from '../../service/data.service';
import {OnInit} from '@angular/core';
import {TableComponent} from "./table/table.component";
import {UserDTO} from '../../models/dto/user-dto';
import {ButtonFilterService} from "../../service/button-filter.service"

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
  selector: 'app-table-container',
  standalone: true,
  templateUrl: './table-container.component.html',
  styleUrl: './table-container.component.css',
  imports: [TableComponent]
})
export class TableContainerComponent implements OnInit {

  data: UserDTO[] = [];

  constructor(private dataService: DataService,
              private buttonFilterService: ButtonFilterService,
  ) {
  }

  ngOnInit() {
    this.dataService.getByEmployee()
      .subscribe((response) => {
        this.data = response.data.map(mapUserDTO);
      })

    this.buttonFilterService.ee.subscribe((data) => this.data = data)

  }


}
