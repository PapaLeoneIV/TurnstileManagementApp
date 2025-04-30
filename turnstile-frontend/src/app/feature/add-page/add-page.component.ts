import { Component, OnInit } from '@angular/core';
import { RouterLink } from '@angular/router';
import { HeaderComponent } from '@app/shared/components/header/header.component';
import { NAV_BUTTON_LIST } from '@app/shared/models/nav-button-list';
import { NgClass, NgFor } from '@angular/common';
import { ModelListComponent } from '@app/shared/components/model-list/model-list.component';
import { FormGroup, ReactiveFormsModule } from '@angular/forms';
import { DynamicFormService } from '@app/core/service/dynamic-form.service';
import { ModelDTOService } from '@app/core/service/model-dto.service';
import { CommonModule } from '@angular/common';
import { UserInsertDTO } from '@app/core/dto/add/user-insert-dto';
import { CompanyDTO } from '@app/core/dto/company-dto';
import { BadgeDTO } from '@app/core/dto/badge-dto';
import { TransactionEvent } from '@app/core/dto/trasaction-event.dto';
import { ErrorLog } from '@app/core/dto/error-log.dto';
import { InsertService } from '@app/core/service/insert.service';
import { Observable } from 'rxjs';
import { ApiResponse } from '@app/shared/models/api-response';
import { InsideOfficeInsertDTO } from '@app/core/dto/add/inside-office-insert.dto';
import { RoleInsertDTO } from '@app/core/dto/add/role-insert-dto';
import { TurnstileInsertDTO } from '@app/core/dto/add/turnstile-insert.dto';
import { TransactionInsertDTO } from '@app/core/dto/add/transaction-insert.dto';
@Component({
  selector: 'app-add-page',
  imports: [HeaderComponent, ModelListComponent, RouterLink, NgClass, ReactiveFormsModule, NgFor, CommonModule],
  templateUrl: './add-page.component.html',
  styleUrl: './add-page.component.css'
})
export class AddPageComponent implements OnInit {
  buttons = NAV_BUTTON_LIST;
  isOpen = false;
  form!: FormGroup;
  dto: RoleInsertDTO | UserInsertDTO | CompanyDTO | BadgeDTO | TransactionInsertDTO | TurnstileInsertDTO | InsideOfficeInsertDTO | TransactionEvent | ErrorLog;

  constructor(private formService: DynamicFormService,
    private mdtos: ModelDTOService,
    private insertService: InsertService) {
    this.dto = new UserInsertDTO();
  }

  ngOnInit() {
    this.form = new FormGroup({});
    this.mdtos.ee.subscribe((dto) => {
      this.dto = this.mdtos.getDTO(dto.toUpperCase());
      this.form = this.formService.generateForm(this.dto);
    });
  }

  toggleSidebar() {
    this.isOpen = !this.isOpen;
  }



  submitForm() {
    const handlers = new Map<Function, () => Observable<ApiResponse<any>>>([
      [RoleInsertDTO, () => this.insertService.addRole(this.form.value)],
      [UserInsertDTO, () => this.insertService.addUser(this.form.value)],
      [CompanyDTO, () => this.insertService.addCompany(this.form.value)],
      [BadgeDTO, () => this.insertService.addBadge(this.form.value)],
      [TransactionInsertDTO, () => this.insertService.addTransaction(this.form.value)],
      [TurnstileInsertDTO, () => this.insertService.addTurnstile(this.form.value)],
      [InsideOfficeInsertDTO, () => this.insertService.addInsideOffice(this.form.value)],
      [TransactionEvent, () => this.insertService.addTransactionEvent(this.form.value)],
      [ErrorLog, () => this.insertService.addErrorLog(this.form.value)]
    ]);

    const handler = Array.from(handlers.entries()).find(([type]) => this.dto instanceof type);
    if (!handler) {
      console.log("Unknown DTO:", this.form.value);
      return;
    }
  
    handler[1]().subscribe({
      next: (response) => {
        console.log('Success:', response);
      },
      error: (err) => {
        console.error('Error during submission:', err);
      }
    });
  }
}