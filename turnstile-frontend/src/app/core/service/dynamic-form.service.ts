import { Injectable } from '@angular/core';
import { FormGroup, FormControl } from '@angular/forms';


@Injectable({ providedIn: 'root' })
export class DynamicFormService {
    
  generateForm(dto: any): FormGroup {
    const formGroup = new FormGroup({});

    Object.keys(dto).forEach((key) => {
      formGroup.addControl(key, new FormControl('')); 
    });

    return formGroup;
  }
}