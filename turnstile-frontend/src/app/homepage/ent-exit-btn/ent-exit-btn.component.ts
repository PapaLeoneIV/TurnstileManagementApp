import { Component, input } from '@angular/core';

@Component({
  selector: 'app-ent-exit-btn',
  imports: [],
  templateUrl: './ent-exit-btn.component.html',
  styleUrl: './ent-exit-btn.component.css'
})
export class EntExitBtnComponent {
  content = input<string>("Enter/Exit");
}
