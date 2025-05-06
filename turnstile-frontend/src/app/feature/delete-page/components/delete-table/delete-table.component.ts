import { CommonModule, NgFor, NgIf } from '@angular/common';
import { Component } from '@angular/core';
import { DeleteService } from '@app/core/service/delete.service';
import {flatten} from 'flat'

@Component({
  selector: 'app-delete-table',
  imports: [NgFor, CommonModule, NgIf],
  templateUrl: './delete-table.component.html',
  styleUrl: './delete-table.component.css'
})
export class DeleteTableComponent {
  constructor(private deleteService: DeleteService){
  }
  objectKeys = Object.keys
  DTO : any;

  ngOnInit(){
    this.deleteService.delete_entry_ee.subscribe({
      next: (response: any) => { this.DTO = flatten(response);},
      error: (response: any)=> {console.error("Error: ", response)}
    })
  }
}
