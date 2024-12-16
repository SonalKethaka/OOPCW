import { Component, ViewChild } from '@angular/core';
import { RouterModule, RouterOutlet } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ConfigurationFormComponent } from "./configuration-form/configuration-form.component";
import { SystemControlComponent } from "./system-control/system-control.component";
import { HeaderControlComponentComponent } from './header-control-component/header-control-component.component';
import { CustomerVendorControlComponent } from './customer-vendor-control/customer-vendor-control.component';





@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule, 
            FormsModule, 
            RouterModule, 
            ConfigurationFormComponent, 
            SystemControlComponent, 
            HeaderControlComponentComponent, 
            CustomerVendorControlComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})

export class AppComponent {
  @ViewChild(SystemControlComponent) systemControl!: SystemControlComponent;

  
  title = 'Front-End';

  constructor(){}
}
