import { Component, ViewChild } from '@angular/core';
import { RouterModule, RouterOutlet } from '@angular/router';
// import { ConfigurationFormComponent } from "./configuration-form/configuration-form.component";
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ConfigurationFormComponent } from "./configuration-form/configuration-form.component";
import { SystemControlComponent } from "./system-control/system-control.component";
import { HeaderControlComponentComponent } from './header-control-component/header-control-component.component';
import { CustomerControlComponentComponent } from './customer-control-component/customer-control-component.component';

// import { ConfigurationFormComponent } from "./configuration-form/configuration-form.component";




@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule, 
            FormsModule, 
            RouterModule, 
            ConfigurationFormComponent, 
            SystemControlComponent, 
            HeaderControlComponentComponent, 
            CustomerControlComponentComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})

export class AppComponent {
  @ViewChild(SystemControlComponent) systemControl!: SystemControlComponent;

  
  title = 'Front-End';

  constructor(){}
}
