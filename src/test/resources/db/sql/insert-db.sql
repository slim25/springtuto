/* USERS */
INSERT INTO users VALUES (1,'vitalia@ee.ee', 'Vitalik', '1990-01-08',1); 
INSERT INTO users VALUES (2,'admin@ee.ee', 'Admin', '1980-01-08',0); 

/* EVENTS */
INSERT INTO event VALUES (1,'Glasgou', 11.2, 'HIGH',3); 
INSERT INTO event VALUES (2,'Walk around', 120.50, 'MID',1); 

/* EVENTS, AUDITORIUM AND DATE */
INSERT INTO event_auditorium_date VALUES (1,1, '2016-02-7'); 
INSERT INTO event_auditorium_date VALUES (2,1, '2016-02-8'); 
INSERT INTO event_auditorium_date VALUES (3,1, '2016-02-9'); 

INSERT INTO event_auditorium_date VALUES (1,2, '2016-02-7'); 
INSERT INTO event_auditorium_date VALUES (3,2, '2016-02-8'); 

/* TICKETS */
INSERT INTO ticket VALUES (12,1, 112, 1,'2016-02-8'); 
INSERT INTO ticket VALUES (122,2, 13, 2,'2016-02-7'); 

