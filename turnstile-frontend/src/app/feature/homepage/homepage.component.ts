import { Component, OnInit } from '@angular/core';
import { HeaderComponent } from '@shared/components/header/header.component';
import { NAV_BUTTON_LIST } from '@shared/models/nav-button-list';
import { Router } from '@angular/router';
import { FormBuilder, ReactiveFormsModule } from '@angular/forms';
import { DoorManService } from '@core/service/doorman-service';
import { ApiResponse } from '@shared/models/api-response';
import { TransactionDTO } from '@core/dto/transaction-dto';
import { NgStyle } from '@angular/common';
import { Validators } from '@angular/forms';
import { TransactionService } from '@core/service/transaction.service';

@Component({
  selector: 'app-homepage',
  imports: [HeaderComponent, ReactiveFormsModule, NgStyle],
  templateUrl: './homepage.component.html',
  styleUrl: './homepage.component.css'
})
export class HomepageComponent implements OnInit {
  protected readonly buttons = NAV_BUTTON_LIST;

  public msgStatus = "Waiting";
  public lastEntry = "N/A";


  checkoutForm;

  constructor(
    private doormanService: DoorManService,
    private transactionService: TransactionService,
    private router: Router,
    private formBuilder: FormBuilder) {
    this.checkoutForm = this.formBuilder.group({
      badgeId: ['', [Validators.required, Validators.pattern("^[0-9]*$"), Validators.min(1)]],
      turnstileId: ['', [Validators.required, Validators.pattern("^[0-9]*$"), Validators.min(1)]]
    });
  }

  ngOnInit() {
    this.transactionService.getLastTransaction().subscribe((response) => {
      if (response.message !== "OK" || !response.data) {
        this.lastEntry = "N/A";
        return
      }
      let transaction: TransactionDTO | null = null;
      if (!Array.isArray(response.data)) {
        transaction = response.data;
      }
      else if (response.data.length > 0) {
        transaction = response.data[0];
      }
      if (!transaction) {
        this.lastEntry = "No transactions found";
        return;
      }
      this.lastEntry = `${transaction.date} ${transaction.time}`;
    });
  }



  onSelect(route: string) {
    this.router.navigate([route]);
  }

  onSubmit(val: string) {
    const { badgeId, turnstileId } = this.checkoutForm.value;
    if (!badgeId || !turnstileId) {
      return;
    }
    if (val === "enter") {
      this.doormanService.Enter({ badgeId: parseInt(badgeId), turnstileId: parseInt(turnstileId) })
        .subscribe((data: ApiResponse<String>) => {
          if (data.message === "OK") {
            this.msgStatus = `Success: ${data.message}`;
            this.lastEntry = data.timestamp.toString();
          } else {
            this.msgStatus = `Error: ${data.message} `;
          }
          this.checkoutForm.reset();
        });

    } else if (val === "exit") {
      this.doormanService.Exit({ badgeId: parseInt(badgeId as string), turnstileId: parseInt(turnstileId as string) })
        .subscribe((data: ApiResponse<String>) => {
          if (data.message === "OK") {
            this.msgStatus = `Success: ${data.message}`;
            this.lastEntry = data.timestamp.toString();
            this.checkoutForm.reset();
          } else {
            this.msgStatus = `Error: ${data.message} `;
          }
          this.checkoutForm.reset();

        });

    } else {
      console.log("Invalid value");
    }

  }
}
