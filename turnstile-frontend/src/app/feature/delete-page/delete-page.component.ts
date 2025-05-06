import { CommonModule, NgFor } from '@angular/common';
import { Component } from '@angular/core';
import { FormControl, FormGroup, NonNullableFormBuilder, ReactiveFormsModule } from '@angular/forms';
import { InsideOffice } from '@app/core/dto/inside-office.dto';
import { RoleDTO } from '@app/core/dto/role-dto';
import { TransactionDTO } from '@app/core/dto/transaction-dto';
import { TurnstileDTO } from '@app/core/dto/turnstile-dto';
import { UserDTO } from '@app/core/dto/user-dto';
import { BadgeDTO } from '@app/core/dto/badge-dto';
import { CompanyDTO } from '@app/core/dto/company-dto';
import { ErrorLog } from '@app/core/dto/error-log.dto';
import { TransactionEvent } from '@app/core/dto/trasaction-event.dto';
import { DeleteService } from '@app/core/service/delete.service';
import { ModelDTOService } from '@app/core/service/model-dto.service';
import { HeaderComponent } from '@app/shared/components/header/header.component';
import { NavBarComponent } from '@app/shared/components/nav-bar/nav-bar.component';
import { ModelListComponent } from '@app/shared/model-list/model-list.component';
import { ApiResponse } from '@app/shared/models/api-response';
import { Observable } from 'rxjs';
import { DeleteTableComponent } from '@app/feature/delete-page/components/delete-table/delete-table.component';
import { SearchService } from '@app/core/service/search.service';

@Component({
  selector: 'app-delete-page',
  imports: [NavBarComponent, HeaderComponent, ModelListComponent, ReactiveFormsModule, CommonModule, DeleteTableComponent],
  templateUrl: './delete-page.component.html',
  styleUrl: './delete-page.component.css'
})
export class DeletePageComponent {
  form!: FormGroup;
  dto: RoleDTO | UserDTO | CompanyDTO | BadgeDTO | TransactionDTO | TurnstileDTO | InsideOffice | TransactionEvent | ErrorLog;

  constructor(
    private mdtos: ModelDTOService,
    private deleteService: DeleteService,
    private searchService: SearchService)
  {
    this.form = new FormGroup({
      id: new FormControl(0, { nonNullable: true})
    });
    this.dto = new UserDTO();
  }

  ngOnInit() {
    this.mdtos.ee.subscribe((dto) => {
      this.dto = this.mdtos.getRegularDTO(dto.toUpperCase());
    });
  }

  submitDeleteById( ) {
    const handlers = new Map<Function, () => Observable<ApiResponse<any>>>([
      [RoleDTO, () => this.deleteService.deleteRole(this.form.value.id)],
      [UserDTO, () => this.deleteService.deleteUser(this.form.value.id)],
      [CompanyDTO, () => this.deleteService.deleteCompany(this.form.value.id)],
      [BadgeDTO, () => this.deleteService.deleteBadge(this.form.value.id)],
      [TransactionDTO, () => this.deleteService.deleteTransaction(this.form.value.id)],
      [TurnstileDTO, () => this.deleteService.deleteTurnstile(this.form.value.id)],
      [InsideOffice, () => this.deleteService.deleteInsideOffice(this.form.value.id)],
      [TransactionEvent, () => this.deleteService.deleteTransactionEvent(this.form.value.id)],
      [ErrorLog, () => this.deleteService.deleteErrorLog(this.form.value.id)]
    ]);

    const handler = Array.from(handlers.entries()).find(([type]) => this.dto instanceof type);
    if (!handler) {
      console.log("Unknown DTO:", this.form.value);
      return;
    }

    handler[1]().subscribe({
      next: (response) => {
        //se la delete è andata bene
        console.log('Success:', response);
      },
      error: (err) => {
        console.error('Error during submission:', err);
      }
    });
  }
  formValue = 0;
  submitSearchById(){
    this.formValue = this.form.value.id;
    const handlers = new Map<Function, () => Observable<ApiResponse<any>>>([
      [RoleDTO, () => this.searchService.searchRoleByID(this.form.value.id)],
      [UserDTO, () => this.searchService.searchUserByID(this.form.value.id)],
      [CompanyDTO, () => this.searchService.searchCompanyByID(this.form.value.id)],
      [BadgeDTO, () => this.searchService.searchBadgeByID(this.form.value.id)],
      [TransactionDTO, () => this.searchService.searchTransactionByID(this.form.value.id)],
      [TurnstileDTO, () => this.searchService.searchTurnstileByID(this.form.value.id)],
      [InsideOffice, () => this.searchService.searchInsideOfficeByID(this.form.value.id)],
      [TransactionEvent, () => this.searchService.searchTransactionEventByID(this.form.value.id)],
      [ErrorLog, () => this.searchService.searchErrorLogByID(this.form.value.id)]
    ]);
    const handler = Array.from(handlers.entries()).find(([type]) => this.dto instanceof type);
    if (!handler) {
      console.log("Unknown DTO found with id:", this.form.value, "and with handler:", handler);
      return;
    }

    handler[1]().subscribe({
      next: (response) => {
        this.deleteService.delete_entry_ee.emit(response.data);
        console.log('Success:', response.data);
      },
      error: (err) => {
        console.error('Error during submission:', err);
      }
    });
  }
}
