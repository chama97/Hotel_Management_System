DROP DATABASE IF EXISTS Hotel;
CREATE DATABASE IF NOT EXISTS Hotel;
SHOW DATABASES ;

DROP TABLE IF EXISTS User;
CREATE TABLE IF NOT EXISTS User(
    userName VARCHAR(45) NOT NULL DEFAULT 'Unknown',
    password VARCHAR(45) NOT NULL,
    role VARCHAR(45) NOT NULL
    );
SHOW TABLES ;
DESCRIBE User;

DROP TABLE IF EXISTS Employee;
CREATE TABLE IF NOT EXISTS Employee(
    id VARCHAR(15) NOT NULL,
    name VARCHAR(45) NOT NULL DEFAULT 'Unknown',
    address TEXT,
    status VARCHAR(15),
    contact INT ,
    email TEXT,
    salary DOUBLE,
    CONSTRAINT PRIMARY KEY (id)
    );
SHOW TABLES ;
DESCRIBE Employee;

DROP TABLE IF EXISTS Customer;
CREATE TABLE IF NOT EXISTS Customer(
    id VARCHAR(15) NOT NULL,
    name VARCHAR(45) NOT NULL DEFAULT 'Unknown',
    address TEXT,
    city TEXT,
    nationality TEXT,
    email TEXT,
    contact INT ,
    CONSTRAINT PRIMARY KEY (id)
    );
SHOW TABLES ;
DESCRIBE Customer;

DROP TABLE IF EXISTS Room;
CREATE TABLE IF NOT EXISTS Room(
    id VARCHAR(15),
    roomType VARCHAR(15),
    capacity VARCHAR(15),
    roomPrice DOUBLE DEFAULT 0.00,
    isAvailable TINYINT(1),
    CONSTRAINT PRIMARY KEY (id)
    );
SHOW TABLES ;
DESCRIBE Room;

DROP TABLE IF EXISTS Meal;
CREATE TABLE IF NOT EXISTS Meal(
    id VARCHAR(15),
    name VARCHAR(15),
    mealPrice DOUBLE DEFAULT 0.00,
    qty INT,
    CONSTRAINT PRIMARY KEY (id)
    );
SHOW TABLES ;
DESCRIBE Meal;


DROP TABLE IF EXISTS Vehicle;
CREATE TABLE IF NOT EXISTS Vehicle(
    id VARCHAR(15),
    type VARCHAR(15),
    rentCharge DOUBLE DEFAULT 0.00,
    isAvailable TINYINT(1),
    CONSTRAINT PRIMARY KEY (id)

    );
SHOW TABLES ;
DESCRIBE Vehicle;


DROP TABLE IF EXISTS `reserve Detail`;
CREATE TABLE IF NOT EXISTS `reserve Detail`(
    custId VARCHAR(15),
    roomId VARCHAR(15),
    checkInDate Date,
    checkOutDate Date,
    roomCharge DOUBLE,
    foodCharge DOUBLE,
    vehicleCharge DOUBLE,
    totalFee DOUBLE,
    CONSTRAINT PRIMARY KEY (custId),

    CONSTRAINT FOREIGN KEY (custId) REFERENCES Customer(id) ON DELETE CASCADE ON UPDATE CASCADE ,
    CONSTRAINT FOREIGN KEY (roomId) REFERENCES Room(id) ON DELETE CASCADE ON UPDATE CASCADE
    );
SHOW TABLES ;
DESCRIBE `reserve Detail`;

INSERT INTO user (`userName`,`password`, `role`)values
('chama', '123', 'Administrator');

INSERT INTO Room (`id`, `roomType`, `capacity`, `roomPrice`) VALUES
('R024', 'Deluxe', 'Single', '6500'),
('R025', 'Deluxe', 'Single', '6500'),
('R026', 'Deluxe', 'Double', '8000'),
('R027', 'Deluxe', 'Double', '8000'),
('R028', 'Deluxe', 'Double', '8000'),
('R029', 'Deluxe', 'Double', '8000'),
('R030', 'Deluxe', 'Double', '8000'),
('R031', 'Deluxe', 'Triple', '9000'),
('R032', 'Deluxe', 'Triple', '9000'),
('R033', 'Deluxe', 'Triple', '9000'),
('R034', 'Deluxe', 'Triple', '9000'),
('R035', 'Deluxe', 'Triple', '9000'),
('R036', 'Deluxe', 'Quad', '10000'),
('R037', 'Deluxe', 'Quad', '10000'),
('R038', 'Deluxe', 'Quad', '10000'),
('R039', 'Deluxe', 'Quad', '10000'),
('R040', 'Deluxe', 'Quad', '10000'),
('R041', 'Superior', 'Single', '12000'),
('R042', 'Superior', 'Single', '12000'),
('R043', 'Superior', 'Single', '12000'),
('R044', 'Superior', 'Double', '15000'),
('R045', 'Superior', 'Double', '15000'),
('R046', 'Superior', 'Double', '15000'),
('R047', 'Superior', 'Triple', '18000'),
('R048', 'Superior', 'Triple', '18000'),
('R049', 'Superior', 'Quad', '20000'),
('R050', 'Superior', 'Quad', '20000');


INSERT INTO Vehicle (`id`, `type`, `rentCharge`, `isAvailable`) VALUES
('V001', 'Bicycle', '200',0),
('V002', 'Bicycle', '200', 0),
('V003', 'Bicycle', '200', 0),
('V004', 'Bicycle', '200', 0),
('V005', 'Bicycle', '200', 0),
('V006', 'Bicycle', '200', 0),
('V007', 'Bicycle', '200', 0),
('V008', 'Bicycle', '200', 0),
('V009', 'Bike', '500', 0),
('V010', 'Bike', '500', 0),
('V011', 'Bike', '500', 0),
('V012', 'Bike', '500', 0),
('V013', 'Bike', '500', 0),
('V014', 'Car', '3000', 0),
('V015', 'Car', '3000', 0),
('V016', 'Car', '3000', 0),
('V017', 'Car', '3000', 0),
('V018', 'Car', '3000', 0),
('V019', 'Cab', '4000', 0),
('V020', 'Cab', '4000', 0),
('V021', 'Cab', '4000', 0),
('V022', 'Jeep','4000', 0),
('V023', 'Jeep', '3000', 0),
('V024', 'Jeep','4000', 0),
('V025', 'Jeep', '4000', 0);

INSERT INTO Employee (`id`, `name`, `address`, `status`, `contact`, `email`, `salary`) VALUES
('E001', 'Saman', 'Kandy', 'SecurityGuard', '0762304050', 'saman@mail.com', '25000'),
('E002', 'Wasantha', 'Panadura', 'SecurityGuard', '0762347890', 'wasa@mail.com', '25000'),
('E003', 'Kumara', 'Galle', 'Housekeeper', '0762334050', 'kuma@mail.com', '23000'),
('E004', 'Mahesh', 'Kandy', 'Housekeeper', '0762304050', 'saman@mail.com', '25000'),
('E005', 'Kamal', 'Horana', 'Housekeeper', '0762334754', 'kamal@mail.com', '25000'),
('E006', 'Nimal', 'Galle', 'Housekeeper', '0762334050', 'nima@mail.com', '23000'),
('E007', 'Sujeewa', 'Matara', 'Housekeeper', '0762304050', 'suja@mail.com', '25000'),
('E008', 'Amal', 'Colombo', 'Housekeeper', '0762347890', 'ama@mail.com', '25000'),
('E009', 'Chanaka', 'Negambo', 'Housekeeper', '0345675450', 'chana@mail.com', '23000'),
('E010', 'Silva', 'Gampaha', 'Housekeeper', '0762304050', 'silva@mail.com', '25000'),
('E011', 'Isuru', 'Kalutara', 'Receptionist', '0762347890', 'wasa@mail.com', '28000'),
('E012', 'Sunumal', 'Galle', 'Receptionist', '0762334050', 'kuma@mail.com', '28000'),
('E013', 'Kalum', 'Kandy', 'Receptionist', '0762304050', 'kalu@mail.com', '28000'),
('E014', 'Minod', 'Wellawatte', 'Receptionist', '0762347890', 'mino@mail.com', '45000'),
('E015', 'Janaka', 'Ratmalana', 'Chef', '0762334050', 'jana@mail.com', '43000'),
('E016', 'Lakmal', 'Ingiriya', 'Chef', '0762304050', 'laka@mail.com', '55000'),
('E017', 'Yasitha', 'Ratnapura', 'Chef', '0762347890', 'yasa@mail.com', '55000'),
('E018', 'Chameera', 'Moratuwa', 'Manager', '0762334050', 'chama@mail.com', '73000'),
('E019', 'Wanidu', 'JaEla', 'Manager', '0762304050', 'wani@mail.com', '95000'),
('E020', 'Kosala', 'Kollupitiya', 'SecurityGuard', '0762347890', 'ko@mail.com', '25000'),
('E021', 'Perera', 'Banadaragama', 'Cleaner', '0762334050', 'pere@mail.com', '23000');


INSERT INTO Meal (`id`, `name`, `mealPrice`, `qty`) VALUES
('M001', 'FriedRice', '250', '100'),
('M002', 'VegetableRice', '200', '100'),
('M003', 'Biriyani', '300', '100'),
('M004', 'Egg Kottu', '200', '30'),
('M005', 'Cheese Kottu', '250', '50'),
('M006', 'Chicken Kottu', '250', '50'),
('M007', 'Pizza', '600', '100'),
('M008', 'Berger', '270', '40'),
('M009', 'Egg roti', '200', '100'),
('M010', 'Roti', '100', '100'),
('M011', 'Shrimp cake', '350', '100'),
('M012', 'Prawn Rolls', '200', '100'),
('M013', 'Crab tikka', '450', '100'),
('M014', 'Cuttlefish', '700', '30'),
('M015', 'Iced Coffee', '100', '50'),
('M016', 'Iced Milo', '250', '50'),
('M017', 'Papaya Juice', '120', '100'),
('M018', 'Orange Juice', '130', '40'),
('M019', 'Mango Cream', '170', '100'),
('M020', 'Falooda', '90', '100');





