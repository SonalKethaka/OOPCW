import { Component, EventEmitter, Output } from '@angular/core';

@Component({
  selector: 'app-control-panel',
  standalone: true,
  imports: [],
  templateUrl: './control-panel.component.html',
  styleUrl: './control-panel.component.css'
})
export class ControlPanelComponent {
  @Output() start = new EventEmitter<void>();
  @Output() stop = new EventEmitter<void>();

  startSystem() {
    this.start.emit();
  }

  stopSystem() {
    this.stop.emit();
  }
}