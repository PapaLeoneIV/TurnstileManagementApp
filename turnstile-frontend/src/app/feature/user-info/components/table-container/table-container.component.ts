import { Component } from '@angular/core';
import { UserService } from '@core/service/user.service';
import { OnInit } from '@angular/core';
import { TableComponent } from "./table/table.component";
import { UserDTO } from '@core/dto/user-dto';
import { ButtonFilterService } from '@core/service/button-filter.service';
import { mapUserDTO } from '@utils/Mapper';


@Component({
  selector: 'app-table-container',
  standalone: true,
  templateUrl: './table-container.component.html',
  styleUrl: './table-container.component.css',
  imports: [TableComponent]
})
export class TableContainerComponent implements OnInit {

  data: UserDTO[] = [];

  constructor(private dataService: UserService,
    private buttonFilterService: ButtonFilterService) { }

  ngOnInit() {
    this.dataService.getByEmployee()
      .subscribe((response) => {
        this.data = response.data.map(mapUserDTO);
      })

    this.buttonFilterService.ee.subscribe((data) => this.data = data)
  }


}
