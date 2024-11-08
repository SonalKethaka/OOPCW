import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { ConfigurationFormComponent } from "./configuration-form/configuration-form.component";
import { CommonModule } from '@angular/common';
import { TicketService } from './ticket.service';
import { ControlPanelComponent } from "./control-panel/control-panel.component";
import { TicketDisplayComponent } from "./ticket-display/ticket-display.component";
import { LogDisplayComponent } from "./log-display/log-display.component";

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, ConfigurationFormComponent, CommonModule, ControlPanelComponent, TicketDisplayComponent, LogDisplayComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  config: any;
  title = 'Front-End';

  constructor(private ticketService: TicketService) {}

  onConfigure(config: any) {
    console.log('Configuring system with:', config);
    this.ticketService.startSystem(config).subscribe(
      () => console.log('System started with new configuration.'),
      (error) => console.error('Error starting system:', error)
    );
  }



  onStart() {
    this.ticketService.startSystem(this.config).subscribe(
      () => console.log('System started.'),
      (error) => console.error('Error starting system:', error)
    );
  }

  onStop() {
    this.ticketService.stopSystem().subscribe(
      () => console.log('System stopped.'),
      (error) => console.error('Error stopping system:', error)
    );
  }
}
