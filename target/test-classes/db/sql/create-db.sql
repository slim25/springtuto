
CREATE TABLE auditorium (
  id  INT NOT NULL AUTO_INCREMENT ,
  name VARCHAR(45) NOT NULL ,
  numberOfSeats  INT NOT NULL ,
  
);

CREATE TABLE vip_seats (
  auditorium_id INT NOT NULL ,
  vip_seat_number INT NOT NULL 
);

CREATE TABLE event (
	id INT NOT NULL AUTO_INCREMENT,
	name VARCHAR(45) NOT NULL ,
	price FLOAT NOT NULL ,
	rating VARCHAR(45) NOT NULL ,
	timesPerDay INT NOT NULL ,

);

CREATE TABLE event_auditorium_date (
	auditorium INT NOT NULL ,
	event_id INT NOT NULL ,
	event_date TIMESTAMP NOT NULL 
);

CREATE TABLE purchasedTickets (
	event_id INT NOT NULL ,
	ticket_id INT NOT NULL
);

CREATE TABLE ticket (
  id INT NOT NULL AUTO_INCREMENT,
  event_id INT NOT NULL ,
  seatNumber INT NOT NULL ,
  user_id INT NOT NULL ,
  event_date TIMESTAMP NOT NULL ,
  
);

CREATE TABLE users (
  id INT NOT NULL AUTO_INCREMENT,
  email VARCHAR(45) NOT NULL ,
  name VARCHAR(45) NOT NULL ,
  birthday TIMESTAMP NOT NULL ,
  registered TINYINT NOT NULL ,

);



