import { Component, input } from '@angular/core';

@Component({
  selector: 'app-nav-button',
  imports: [],
  templateUrl: './nav-button.component.html',
  styleUrl: './nav-button.component.css'
})
export class NavButtonComponent {
  btn = input<{ name: string; link: string; }>({name: "home", link: "/home"});
}
