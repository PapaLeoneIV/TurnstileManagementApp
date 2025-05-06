import { NgClass, NgFor } from '@angular/common';
import { Component, model } from '@angular/core';
import { OnInit } from '@angular/core';
import { RouterModule } from '@angular/router';
import { ModelDTOService } from '@app/core/service/model-dto.service';
import { ApiResponse } from '@app/shared/models/api-response';
import { ModelService } from '@core/service/model.service';
import { Observable, Subject, takeUntil } from 'rxjs';

@Component({
  selector: 'app-model-list',
  imports: [NgFor, RouterModule, NgClass],
  templateUrl: './model-list.component.html',
  styleUrl: './model-list.component.css'
})
export class ModelListComponent implements OnInit {
  models: string[] = [];
  private destroy$ = new Subject<void>();

  constructor(private modelService: ModelService,
    private dtoService: ModelDTOService
  ) { }

  ngOnInit() {
    let observable: Observable<ApiResponse<string>> | null = null;
    observable = this.modelService.getModels();


    if (observable) {
      observable.pipe(takeUntil(this.destroy$)).subscribe((response) => {
        this.models = response.data.map((model) => model.toUpperCase());
      });
    }
   

  }
  selectedItem: string | null = null;

  updateData(item: string) {
    this.dtoService.ee.emit(item);
    this.selectedItem = item;
  }

  ngOnDestroy() {
    this.destroy$.next();
    this.destroy$.complete();
  }
}
