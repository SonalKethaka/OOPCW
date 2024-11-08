import { Component, OnDestroy, OnInit } from '@angular/core';
import { interval, Subscription } from 'rxjs';
import { TicketService } from '../ticket.service';

@Component({
  selector: 'app-ticket-display',
  standalone: true,
  imports: [],
  templateUrl: './ticket-display.component.html',
  styleUrl: './ticket-display.component.css'
})
export class TicketDisplayComponent implements OnInit, OnDestroy {
  totalTickets: number = 0;
  subscription: Subscription | undefined;

  constructor(private ticketService: TicketService) {}

  ngOnInit() {
    // Polling every 2 seconds to update ticket count
    this.subscription = interval(2000).subscribe(() => {
      this.ticketService.getTotalTickets().subscribe(
        (tickets) => (this.totalTickets = tickets),
        (error) => console.error('Error fetching ticket count:', error)
      );
    });
  }

  ngOnDestroy() {
    if (this.subscription) {
      this.subscription.unsubscribe();
    }
  }
}
