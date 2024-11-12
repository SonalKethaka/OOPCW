import { Component, OnInit } from '@angular/core';
import { TicketService } from '../ticket.service';
import { NgFor } from '@angular/common';
import { CommonModule } from '@angular/common';
import { WebsocketService } from '../websocket.service';



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

  constructor(private ticketService: TicketService, private webSocketService: WebsocketService) {}

  ngOnInit() {
    this.getTicketsLeft();
    this.getSystemStatus();
    this.webSocketService.connect();//temp
    this.webSocketService.getLogs().subscribe((log) => {//temp
      this.logMessages.push(log);//temp
    });
  }

  startSystem() {
    
    if (this.isRunning) {
      return;  // If the system is already running, do nothing
  }
  this.isRunning = true;
    
  this.logMessages.push("System Started kjadkajsdjk");
  this.systemStatus = "Started";



    this.ticketService.startSystem().subscribe(
      (response) => {
        console.log(response);
        this.getTicketsLeft();

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
        this.getTicketsLeft();

        this.logMessages.push(response);
      },
      (error) => console.error('Error stopping system', error)
    );
  }

  getTicketsLeft() {
    console.log("Get tickets Got called");
    this.ticketService.getTicketsLeft().subscribe(
      (data: number) => {this.ticketsLeft = data,
      console.log(data)},

      (error) => {
        console.error('Error fetching tickets left', error);
      this.isRunning = true;}

    );
  }

  // getSystemStatus() {
  //   // this.ticketService.getSystemStatus().subscribe(
  //   //   (status) => this.systemStatus = status,
  //   //   (error) => console.error('Error fetching system status', error)
  //   // );

  //   this.ticketService.getSystemStatus().subscribe(
  //   response => {
  //     this.systemStatus = response.status;
  //     console.log("System status:", this.systemStatus);
  //   },
  //   error => {
  //     console.error("Error fetching system status", error);
  //   }
  // );
  // }

  // getSystemStatus() {
  //   this.ticketService.getSystemStatus().subscribe(
  //     response => {
  //       console.log("Full Response Object:", response);
  //       this.systemStatus = response.status; // This assumes response has a 'status' field
  //     },
  //     error => {
  //       console.error("Error fetching system status", error);
  //     }
  //   );
  // }

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
