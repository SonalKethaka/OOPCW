import { Injectable } from '@angular/core';
import { Observable, Subject } from 'rxjs';
import SockJS from 'sockjs-client';
import { Client, Stomp } from '@stomp/stompjs';

@Injectable({
  providedIn: 'root'
})
export class WebsocketService {

  private stompClient: any;
  private logSubject = new Subject<string>();

  // connect(): void {
  //   const socket = new SockJS('http://localhost:8080/ws');
  //   this.stompClient = Stomp.over(socket);
  //   this.stompClient.connect({}, () => {
  //     this.stompClient.subscribe('/topic/logs', (message: { body: string; }) => {
  //       this.logSubject.next(message.body);
  //     });
  //   });
  // }

  // getLogs(): Observable<string> {
  //   return this.logSubject.asObservable();
  // }

  constructor() {
    this.initializeWebSocket();
  }

  private initializeWebSocket() {
    // Use Stomp.over with a factory function to create a new SockJS instance
    this.stompClient = new Client({
      webSocketFactory: () => new SockJS('http://localhost:8080/ws'),
      reconnectDelay: 5000,  // Reconnect every 5 seconds if the connection is lost
    });

    this.stompClient.onConnect = () => {
      // Subscribe to the logs topic upon connection
      this.stompClient.subscribe('/topic/logs', (message: { body: string; }) => {
        this.logSubject.next(message.body);
      });
    };

    this.stompClient.onStompError = (frame: { headers: { [x: string]: string; }; body: string; }) => {
      console.error('Broker reported error: ' + frame.headers['message']);
      console.error('Additional details: ' + frame.body);
    };
  }

  connect(): void {
    if (!this.stompClient.active) {
      this.stompClient.activate();
    }
  }

  disconnect(): void {
    if (this.stompClient && this.stompClient.active) {
      this.stompClient.deactivate();
    }
  }

  getLogs(): Observable<string> {
    return this.logSubject.asObservable();
  }
}