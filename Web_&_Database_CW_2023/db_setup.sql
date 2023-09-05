CREATE DATABASE accomidation;
USE accomidation;

DROP TABLE IF EXISTS Student;
 
CREATE TABLE Student (
StudentID int UNIQUE NOT NULL,
FirstName varchar(255) NOT NULL,
LastName varchar(255) NOT NULL,
Username varchar(255) UNIQUE NOT NULL,
OtherAddress varchar(255)
);

INSERT INTO Student VALUES(6731, 'Paul', 'Smith', 'ps12347', NULL);
INSERT INTO Student VALUES(6732, 'Lucy', 'Jane', 'li75483', NULL);
INSERT INTO Student VALUES(6733, 'Jen', 'Mires', 'jm10936', '13 Borne Lane, Guildford');
INSERT INTO Student VALUES(6734, 'Ben', 'Lark', 'bl93756', NULL);
INSERT INTO Student VALUES(6735, 'Mathew', 'Craw', 'mc96874', NULL);
INSERT INTO Student VALUES(6736, 'Grace', 'Peers', 'gp12347', '06 Wolves Wood, Hurst Green');
INSERT INTO Student VALUES(6737, 'Raya', 'Laron', 'rl12347', NULL);


DROP TABLE IF EXISTS Email;

CREATE TABLE Email(
EmailID int UNIQUE NOT NULL,
StudentID int NOT NULL,
Email varchar(255) NOT NULL,
PRIMARY KEY(EmailID),
FOREIGN KEY(StudentID) REFERENCES Student(StudentID)
);

INSERT INTO Email VALUES(1,6731, 'ps12347@surrey.ac.uk');
INSERT INTO Email VALUES(2,6732, 'li75483@surrey.ac.uk');
INSERT INTO Email VALUES(3,6733, 'jm10936@surrey.ac.uk');
INSERT INTO Email VALUES(4,6733, 'moster@gmail.com');
INSERT INTO Email VALUES(5,6733, 'carrot@yahoo.com');
INSERT INTO Email VALUES(6,6734, 'bl93756@surrey.ac.uk');
INSERT INTO Email VALUES(7,6735, 'mc96874@surrey.ac.uk');
INSERT INTO Email VALUES(8,6736, 'gp12347@surrey.ac.uk');
INSERT INTO Email VALUES(9,6736, 'yellowstone@zoho.com');
INSERT INTO Email VALUES(10,6737, 'rl12347@surrey.ac.uk');


DROP TABLE IF EXISTS Employee;

CREATE TABLE Employee(
EmpID int UNIQUE NOT NULL,
Supervisor int NOT NULL,
FirstName varchar(255) NOT NULL,
LastName varchar(255) NOT NULL,
JobTitle varchar(255) NOT NULL,
PRIMARY KEY(EmpID),
FOREIGN KEY(Supervisor) REFERENCES Employee(EmpID)
);

INSERT INTO Employee VALUES(530,530, 'Daniel', 'Richards', 'Seniour Electrician');
INSERT INTO Employee VALUES(537,530, 'Catherine', 'Blacks', 'Electrician');
INSERT INTO Employee VALUES(533,533, 'Lucy', 'Doe', 'Seniour Plumber');
INSERT INTO Employee VALUES(531,533, 'Jack', 'Sunderland', 'Plumber');
INSERT INTO Employee VALUES(532,530, 'Sasha', 'ONeil', 'Electrician');
INSERT INTO Employee VALUES(534,530, 'Tim', 'Cracknel', 'Electrician');
INSERT INTO Employee VALUES(535,537, 'Sasha', 'Woods', 'Generalist');
INSERT INTO Employee VALUES(536,534, 'Charlie', 'Lane', 'Plumber');



DROP TABLE IF EXISTS Location;

CREATE TABLE Location(
LocationID int UNIQUE NOT NULL,
ManagedBy int UNIQUE NOT NULL,
Site ENUM('Manor Park', 'Hazel Farm'),
Road ENUM('AF','DJ','EC','JB','RR','HC','HD','HP','OW'),
PRIMARY KEY(LocationID),
FOREIGN KEY(ManagedBy) REFERENCES Employee(EmpID)
);

INSERT INTO Location VALUES(8950, 532, 'Manor Park', 'AF');
INSERT INTO Location VALUES(8951, 533, 'Manor Park', 'RR');
INSERT INTO Location VALUES(8952, 530, 'Manor Park', 'EC');
INSERT INTO Location VALUES(8953, 531, 'Hazel Farm', 'HC');
INSERT INTO Location VALUES(8954, 535, 'Hazel Farm', 'HD');
INSERT INTO Location VALUES(8955, 534, 'Hazel Farm', 'OW');

DROP TABLE IF EXISTS Flat;

CREATE TABLE Flat(
FlatID int UNIQUE NOT NULL,
LocationID int,
FlatNo int NOT NULL,
PRIMARY KEY(FlatID),
FOREIGN KEY(LocationID) REFERENCES Location(LocationID)
);

INSERT INTO Flat VALUES(1, 8950, 09);
INSERT INTO Flat VALUES(2, 8950, 10);
INSERT INTO Flat VALUES(3, 8950, 11);
INSERT INTO Flat VALUES(4, 8951, 09);
INSERT INTO Flat VALUES(5, 8951, 10);
INSERT INTO Flat VALUES(6, 8951, 11);
INSERT INTO Flat VALUES(7, 8952, 10);
INSERT INTO Flat VALUES(8, 8952, 11);
INSERT INTO Flat VALUES(9, 8953, 09);
INSERT INTO Flat VALUES(10, 8953, 10);
INSERT INTO Flat VALUES(11, 8953, 11);
INSERT INTO Flat VALUES(12, 8953, 12);
INSERT INTO Flat VALUES(13, 8954, 09);
INSERT INTO Flat VALUES(14, 8954, 10);
INSERT INTO Flat VALUES(15, 8955, 09);
INSERT INTO Flat VALUES(16, 8955, 10);

DROP TABLE IF EXISTS Room;

CREATE TABLE Room(
RoomID int UNIQUE NOT NULL,
FlatID int NOT NULL,
RoomNum int NOT NULL,
Ensuite boolean NOT NULL,
TypeOfRoom ENUM('S','G'),
PRIMARY KEY(RoomID),
FOREIGN KEY(FlatID) REFERENCES Flat(FlatID)
);

INSERT INTO Room VALUES(230, 1, 1, False, 'S');
INSERT INTO Room VALUES(231, 2, 2, False, 'S');
INSERT INTO Room VALUES(232, 3, 3, False, 'S');
INSERT INTO Room VALUES(233, 4, 1, True, 'S');
INSERT INTO Room VALUES(234, 5, 2, True, 'S');
INSERT INTO Room VALUES(235, 6, 3, True, 'S');
INSERT INTO Room VALUES(236, 9, 1, False, 'G');
INSERT INTO Room VALUES(237, 10, 2, False, 'G');
INSERT INTO Room VALUES(238, 11, 3, True, 'G');
INSERT INTO Room VALUES(239, 12, 4, False, 'G');
INSERT INTO Room VALUES(240, 13, 1, True, 'S');
INSERT INTO Room VALUES(241, 14, 2, False, 'S');
INSERT INTO Room VALUES(242, 15, 1, True, 'G');
INSERT INTO Room VALUES(243, 16, 2, False, 'G');
INSERT INTO Room VALUES(244, 7, 1, True, 'G');
INSERT INTO Room VALUES(245, 8, 2, False, 'G');


DROP TABLE IF EXISTS StudentRoom;

CREATE TABLE StudentRoom(
RoomID int NOT NULL,
YPrice double NOT NULL,
PRIMARY KEY(RoomID),
FOREIGN KEY(RoomID) REFERENCES Room(RoomID)
);

INSERT INTO StudentRoom VALUES(230, 6230.23);
INSERT INTO StudentRoom VALUES(231, 6230.23);
INSERT INTO StudentRoom VALUES(232, 6230.23);
INSERT INTO StudentRoom VALUES(233, 7364.08);
INSERT INTO StudentRoom VALUES(234, 7364.08);
INSERT INTO StudentRoom VALUES(235, 6230.23);
INSERT INTO StudentRoom VALUES(240, 7364.08);
INSERT INTO StudentRoom VALUES(241, 6230.23);

DROP TABLE IF EXISTS GuestRoom;

CREATE TABLE GuestRoom(
RoomID int NOT NULL,
DPrice double NOT NULL,
GFirstName varchar(255),
GLastName varchar(255),
PRIMARY KEY(RoomID),
FOREIGN KEY(RoomID) REFERENCES Room(RoomID)
);

INSERT INTO GuestRoom VALUES(236, 50.12, NULL, NULL);
INSERT INTO GuestRoom VALUES(237, 50.12, 'Jane', 'Lofi');
INSERT INTO GuestRoom VALUES(238, 73.84, 'Kane', 'Biscoffee');
INSERT INTO GuestRoom VALUES(242, 73.84, NULL, NULL);
INSERT INTO GuestRoom VALUES(243, 50.12, 'Hannah', 'Lark');
INSERT INTO GuestRoom VALUES(244, 73.84, 'Jeff', 'Cracknel');
INSERT INTO GuestRoom VALUES(245, 73.84, NULL, NULL);



DROP TABLE IF EXISTS Rent;

CREATE TABLE Rent(
AccountID int UNIQUE NOT NULL,
StudentID int NOT NULL,
RoomID int NOT NULL,
StartDate date NOT NULL,
EndDate date NOT NULL,
ExtraDetails ENUM('Standard','International-Lease', 'Med-Lease', 'Custom'),
CHECK (StartDate <= EndDate),
PRIMARY KEY(AccountID),
FOREIGN KEY(StudentID) REFERENCES Student(StudentID),
FOREIGN KEY(RoomID) REFERENCES Room(RoomID)
);

INSERT INTO Rent VALUES(4670, 6731, 237, '2015-10-12', '2015-10-15', 'Custom');
INSERT INTO Rent VALUES(4671, 6731, 238, '2015-11-20', '2015-11-30', 'Custom');
INSERT INTO Rent VALUES(4672, 6732, 243, '2015-10-12', '2015-10-15', 'Custom');
INSERT INTO Rent VALUES(4673, 6737, 244, '2015-10-12', '2015-10-15', 'Custom');
INSERT INTO Rent VALUES(4674, 6731, 230, '2015-09-12', '2016-07-15', 'Standard');
INSERT INTO Rent VALUES(4675, 6732, 233, '2015-09-12', '2016-07-15', 'Standard');
INSERT INTO Rent VALUES(4676, 6734, 234, '2015-09-12', '2016-09-15', 'Med-Lease');
INSERT INTO Rent VALUES(4677, 6735, 240, '2015-09-12', '2016-07-15', 'Standard');
INSERT INTO Rent VALUES(4678, 6737, 241, '2015-08-12', '2015-08-15', 'International-Lease');


