CREATE DATABASE BusReservation;

USE BusReservation;

CREATE TABLE Buses (
    id INT AUTO_INCREMENT PRIMARY KEY,
    capacity INT NOT NULL,
    src VARCHAR(100),
    dest VARCHAR(100),
    timing VARCHAR(50)
);

CREATE TABLE Tickets (
    ticket_id INT AUTO_INCREMENT PRIMARY KEY,
    bus_id INT,
    status ENUM('booked', 'cancelled'),
    FOREIGN KEY (bus_id) REFERENCES Buses(id)
);

ALTER TABLE Buses ADD COLUMN price DECIMAL(10, 2) NOT NULL;

ALTER TABLE Tickets ADD COLUMN amount_paid DECIMAL(10, 2) NOT NULL;

INSERT INTO Buses (capacity, src, dest, timing, price)
VALUES (50, 'Indore', 'Mumbai', '09:30AM-12:30PM', 1500.00);

INSERT INTO Buses (capacity, src, dest, timing, price)
VALUES (100, 'Mumbai', 'Delhi', '09:30AM-12:30PM', 3000.00);
