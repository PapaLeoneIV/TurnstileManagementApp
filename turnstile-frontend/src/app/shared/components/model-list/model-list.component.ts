import { Component, model } from '@angular/core';
import { OnInit } from '@angular/core';
import { ModelDTOService } from '@app/core/service/model-dto.service';
import { ApiResponse } from '@app/shared/models/api-response';
import { ModelService } from '@core/service/model.service';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-model-list',
  imports: [],
  templateUrl: './model-list.component.html',
  styleUrl: './model-list.component.css'
})
export class ModelListComponent implements OnInit {
  models: string[] = [];

  constructor(private modelService: ModelService,
    private dtoService: ModelDTOService
  ) { }

  ngOnInit() {
    let observable: Observable<ApiResponse<string>> | null = null;
    observable = this.modelService.getModels();


    if (observable) {
      observable.subscribe((response) => {
        this.models = response.data.map((model) => model.toUpperCase());
      });
    }

  }

  updateData(filter: string) {
    this.dtoService.ee.emit(filter);
  }
}
