import { Component, Input} from '@angular/core';
import { CommonModule } from '@angular/common';
import {UserDTO} from '../../../models/dto/user-dto';
@Component({
  selector: 'app-table',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './table.component.html',
  styleUrl: './table.component.css'
})

export class TableComponent  {

  @Input() userDTOArray: UserDTO[] = [];

}
