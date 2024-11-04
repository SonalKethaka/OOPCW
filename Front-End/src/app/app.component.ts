import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { ConfigurationFormComponent } from "./configuration-form/configuration-form.component";
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, ConfigurationFormComponent, CommonModule],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'Front-End';
}
