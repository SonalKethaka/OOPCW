# OOPCW
Coursework

Real-Time Event Ticketing System

Introduction

The Real-Time Event Ticketing System is a web-based application that facilitates efficient ticket management for events. It provides real-time updates on ticket operations, supports vendors in adding tickets, and allows customers (including VIPs) to purchase tickets dynamically. Built using Angular for the frontend, Spring Boot for the backend, and PostgreSQL as the database, the system also includes a live dashboard to monitor ticketing activities and manage the system’s state.

Setup Instructions

Prerequisites

Ensure the following software is installed on your system:
	•	Java: Version 17 or higher
	•	Spring Boot: Version 3.1 or higher
	•	PostgreSQL: Version 13 or higher
	•	Angular CLI: Version 15 or higher
	•	Maven: Version 3.6 or higher

How to Build and Run the Application

Backend Setup (Spring Boot)
	1.	Clone the repository:

    git clone <repository-url>
    cd <repository-folder>  

    2.	Configure the Database:

	•	Open the application.yml file located in the src/main/resources/ directory.
	•	Update the database connection details:

    spring:
    datasource:
        url: jdbc:postgresql://localhost:5432/ticketing_system
        username: your_username
        password: your_password
    jpa:
        hibernate:
        ddl-auto: update
        show-sql: true

    3.	Create the Database:

	•	Log in to your PostgreSQL server and create the ticketing_system database:

    CREATE DATABASE ticketing_system;

    4.	Build and Run the Backend:

	•	Compile the code and run the backend using Maven:

    mvn clean install
    mvn spring-boot:run

    5.	Verify:

	•	The backend server will be running at http://localhost:8080.


Frontend Setup (Angular)
	1.	Clone the Repository:

    git clone <repository-url>
    cd <repository-folder>  

    2.	Install Angular CLI (if not installed):

    npm install -g @angular/cli

    3.	Install Dependencies:

    npm install

    4.	Run the Angular Application:

    ng serve

	5.	Verify:

	•	The frontend application will be accessible at http://localhost:4200.


Usage Instructions

How to Configure and Start the System
	1.	Start the Backend:

	•	Run the backend using:

    mvn spring-boot:run

    2.	Start the Frontend:

	•	Launch the frontend using:

    ng serve

    3.	Access the Application:

	•	Open a browser and navigate to http://localhost:4200.


Explanation of UI Controls
	1.	System Control Panel:

	•	Start System: Press the green button to activate the ticketing system.
	•	Stop System: Press the red button to stop the ticketing system.
	•	System Status: Displays the current status (Started or Stopped).

	2.	Ticket Management:

	•	Customers: Use + or - buttons to add or remove customers.
	•	VIP Customers: Manage VIP customers using the same controls.
	•	Vendors: Use the + button to add tickets to the pool.

	3.	Configuration Form:

	•	Total Tickets: Input the total number of tickets available.
	•	Ticket Release Rate: Define the rate at which tickets will be added by vendors.
	•	Customer Retrieval Rate: Specify the number of tickets customers can retrieve at one time.
    •	Max Ticket Capacity: Input the maximum number of tickets available.

	4.	Logs:

	•	Displays real-time system activity, including ticket additions and purchases.

	5.	Analytics:

	•	Monitor ticket trends through dynamic charts displaying ticket additions and purchases.