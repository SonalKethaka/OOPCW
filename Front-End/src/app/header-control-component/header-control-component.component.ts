import { Component } from '@angular/core';
import { SystemControlComponent } from '../system-control/system-control.component';
import { AppComponent } from '../app.component';

@Component({
  selector: 'app-header-control-component',
  standalone: true,
  imports: [],
  templateUrl: './header-control-component.component.html',
  styleUrl: './header-control-component.component.css'
})
export class HeaderControlComponentComponent {

  constructor(private appComponent: AppComponent) {}

  startSystem() {
    this.appComponent.systemControl.startSystem();
  }

  stopSystem() {
    this.appComponent.systemControl.stopSystem();  
  }

}
