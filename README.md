# Bus Reservation System

This project is a simple Bus Reservation System implemented in Java using MariaDB for storing data. It allows users to add new buses, update bus timings, remove buses, book tickets, and cancel tickets. It also demonstrates the use of multithreading for handling multiple users concurrently.

## Features

- **Add New Bus**: Users can add a new bus with capacity, source, destination, timing, and price.
- **Update Bus Timings**: Users can update the timings of existing buses.
- **Remove Bus**: Users can remove a bus based on the Bus ID.
- **Book Ticket**: Users can book a ticket for a particular bus.
- **Cancel Ticket**: Users can cancel a booked ticket.

## Implementation Details

- **DB Schema**: The project uses a MariaDB database with two tables: Buses and Tickets.
- **Java Classes**:
  - **BusServiceManager.java**: Main class to handle user actions.
  - **DatabaseHelper.java**: Util class for database connection.
  - **buses.sql**: SQL script to create the database schema and insert sample data.
  - **demo.java**: Contains classes for representing Bus, Consumer threads, and main functionality.
- **Instructions**:
  - Compile: `javac demo.java`
  - Run: `java -cp ".;C:/Users/nimesh.thakur/Documents/Java/mariadb-java-client-3.3.2.jar" demo`

Feel free to explore and modify the code as needed for your own use case! If you encounter any issues or have suggestions for improvements, please feel free to raise them.

Thank you for checking out this Bus Reservation System! 🚌🎫✨
