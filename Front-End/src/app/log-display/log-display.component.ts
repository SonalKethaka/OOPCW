import { Component } from '@angular/core';
import { CommonModule, NgFor } from '@angular/common';

@Component({
  selector: 'app-log-display',
  standalone: true,
  imports: [CommonModule, NgFor],
  templateUrl: './log-display.component.html',
  styleUrl: './log-display.component.css'
})
export class LogDisplayComponent {
  logs: string[] = []; // Replace with actual WebSocket data in a real app

  // Placeholder method for adding logs (in real app, populate with WebSocket or polling)
  addLog(log: string) {
    this.logs.unshift(log); // Add new log entry at the beginning
  }
}
