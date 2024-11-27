import { Component, OnDestroy, OnInit } from '@angular/core';
import { TicketService } from '../ticket.service';
import { NgFor } from '@angular/common';
import { CommonModule } from '@angular/common';
import { WebsocketService } from '../websocket.service';
// import { NgZone } from '@angular/core';




@Component({
  selector: 'app-system-control',
  standalone: true,
  imports: [NgFor],
  templateUrl: './system-control.component.html',
  styleUrl: './system-control.component.css'
})
export class SystemControlComponent implements OnInit {
  ticketsLeft: number | undefined;
  systemStatus: string = 'Stopped';
  logMessages: string[] = [];
  isRunning: boolean = false;
  intervalId: any;

  constructor(private ticketService: TicketService, private webSocketService: WebsocketService) {}

  ngOnInit() {
    this.getTicketsLeft();
    // this.startTicketFetching();
    this.getSystemStatus();
    this.webSocketService.connect();//temp
    this.webSocketService.getLogs().subscribe((log) => {//temp
      this.logMessages.push(log);//temp
    });
  }

  ngOnDestroy() {
    // Clean up interval when component is destroyed
    if (this.intervalId) {
      clearInterval(this.intervalId);
    }
  }

  startTicketUpdating() {
    // Clear any existing intervals to avoid duplicate updates
    if (this.intervalId) {
      clearInterval(this.intervalId);
    }

    // Update tickets left every second
    this.intervalId = setInterval(() => {
      this.getTicketsLeft();
    }, 1000);
  }

  stopTicketUpdating() {
    // Clear the interval to stop updates
    if (this.intervalId) {
      clearInterval(this.intervalId);
      this.intervalId = null;
    }
  }

  startSystem() {
    
    if (this.isRunning) {
      return;  // If the system is already running, do nothing
    }
    this.isRunning = true;
    
    this.logMessages.push("System Started Succesfully.");
    this.systemStatus = "Started";


    this.ticketService.startSystem().subscribe(
      (response) => {
        console.log(response);
        // this.getTicketsLeft();
        this.startTicketUpdating();

        // this.getTicketsLeft();
      },
      (error) => {
        console.error('Error starting system', error);
        this.isRunning = false;
      }
      
    );
  }

  stopSystem() {

    if (!this.isRunning) {
      return;  // If the system is already running, do nothing
  }
  this.isRunning = false;
  this.systemStatus = 'Stopped';



    this.ticketService.stopSystem().subscribe(
      (response) => {
        console.log(response);
        // this.getTicketsLeft();
        this.stopTicketUpdating();
        this.logMessages.push(response);
      },
      (error) => console.error('Error stopping system', error)
    );
  }



  getTicketsLeft() {
    console.log("Get tickets Got called");
    this.ticketService.getTicketsLeft().subscribe(
      (data: number) => {
        this.ticketsLeft = data,
        console.log(this.ticketsLeft);
      console.log(data)
    },

      (error) => {
        console.error('Error fetching tickets left', error);
      this.isRunning = true;}

    );
  }

  

  getSystemStatus() {
    console.log("getSystemStatus() called");
  
    this.ticketService.getSystemStatus().subscribe(
      response => {
        console.log("Full Response Object:", response);
        this.systemStatus = response.status;
      },
      error => {
        console.error("Error fetching system status", error);
      }
    );
  
    console.log("getSystemStatus() finished");
  }

  
  
}
