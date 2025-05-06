import { Component, Input} from '@angular/core';
import { CommonModule } from '@angular/common';
import { UserDTO } from '@core/dto/user-dto';
import { UserService } from '@app/core/service/user.service';
import { ButtonFilterService } from '@app/core/service/button-filter.service';
import { mapUserDTO } from '@app/utils/Mapper';
@Component({
  selector: 'app-table',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './table.component.html',
  styleUrl: './table.component.css'
})

export class TableComponent  {
 constructor(private dataService: UserService,
    private buttonFilterService: ButtonFilterService) { }

  ngOnInit() {
    this.dataService.getByEmployee()
      .subscribe({
        next: (response) => this.userDTOArray = response.data.map(mapUserDTO),
        error: (response) => console.error(response)
      })
    this.buttonFilterService.ee.subscribe({
      next: (response: UserDTO[]) => this.userDTOArray = response})
  }
  userDTOArray: UserDTO[] = [];

}
