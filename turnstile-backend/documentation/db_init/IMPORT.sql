DROP TABLE IF EXISTS inside_office CASCADE;
DROP TABLE IF EXISTS turnstile CASCADE;
DROP TABLE IF EXISTS badge CASCADE;
DROP TABLE IF EXISTS transactions CASCADE;
DROP TABLE IF EXISTS transaction_event CASCADE;
DROP TABLE IF EXISTS error_log CASCADE;
DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS company CASCADE;
DROP TABLE IF EXISTS role CASCADE;

CREATE TABLE ROLE (
    ID SERIAL PRIMARY KEY,
    LEVEL INT NOT NULL,
    DESCRIPTION VARCHAR(100)
);

INSERT INTO ROLE(LEVEL, DESCRIPTION) VALUES (1, 'Manager');
INSERT INTO ROLE(LEVEL, DESCRIPTION) VALUES (2, 'DBA');
INSERT INTO ROLE(LEVEL, DESCRIPTION) VALUES (3, 'SeniorDev');
INSERT INTO ROLE(LEVEL, DESCRIPTION) VALUES (4, 'MidDev');
INSERT INTO ROLE(LEVEL, DESCRIPTION) VALUES (5, 'JuniorDev');
INSERT INTO ROLE(LEVEL, DESCRIPTION) VALUES (6, 'Stage');
INSERT INTO ROLE(LEVEL, DESCRIPTION) VALUES (7, 'Security');
INSERT INTO ROLE(LEVEL, DESCRIPTION) VALUES (8, 'Cleaning');
INSERT INTO ROLE(LEVEL, DESCRIPTION) VALUES (9, 'Co-Manager');





CREATE TABLE COMPANY (
    ID SERIAL PRIMARY KEY,
    NAME VARCHAR(100) NOT NULL,
    ADDRESS VARCHAR(100) NOT NULL
);

INSERT INTO COMPANY(NAME, ADDRESS) VALUES ('TDG Consulting', 'Via Giacomo Matteotti, 129, Sesto Fiorentino');
INSERT INTO COMPANY(NAME, ADDRESS) VALUES ('Engineering', 'Via Lorenzo Il Magnifico, 32');
INSERT INTO COMPANY(NAME, ADDRESS) VALUES ('Cleaning Services', 'Via Guelfa 36');
INSERT INTO COMPANY(NAME, ADDRESS) VALUES ('Security Service', 'Via Michelangelo, 1');

CREATE TABLE BADGE (
	ID SERIAL PRIMARY KEY,
	ALLOWED_ENTER_TIME TIME NOT NULL,
    ALLOWED_EXIT_TIME TIME NOT NULL,
    EXPIRY DATE NOT NULL,
	RFID VARCHAR(150) UNIQUE NOT NULL
);
INSERT INTO BADGE(ALLOWED_ENTER_TIME, ALLOWED_EXIT_TIME, EXPIRY, RFID) VALUES ('09:30:00', '16:30:00', '10-03-2026', 'A100000000');
INSERT INTO BADGE(ALLOWED_ENTER_TIME, ALLOWED_EXIT_TIME, EXPIRY, RFID) VALUES ('09:30:00', '18:30:00', '10-03-2026', 'B200000000');
INSERT INTO BADGE(ALLOWED_ENTER_TIME, ALLOWED_EXIT_TIME, EXPIRY, RFID) VALUES ('09:00:00', '18:00:00', '10-03-2026', 'C300000000');
INSERT INTO BADGE(ALLOWED_ENTER_TIME, ALLOWED_EXIT_TIME, EXPIRY, RFID) VALUES ('09:00:00', '18:00:00', '10-03-2026', 'D400000000');
INSERT INTO BADGE(ALLOWED_ENTER_TIME, ALLOWED_EXIT_TIME, EXPIRY, RFID) VALUES ('09:00:00', '18:00:00', '10-03-2026', 'E500000000');
INSERT INTO BADGE(ALLOWED_ENTER_TIME, ALLOWED_EXIT_TIME, EXPIRY, RFID) VALUES ('09:00:00', '18:00:00', '10-03-2026', 'F600000000');
INSERT INTO BADGE(ALLOWED_ENTER_TIME, ALLOWED_EXIT_TIME, EXPIRY, RFID) VALUES ('09:00:00', '18:00:00', '10-03-2026', 'G700000000');
INSERT INTO BADGE(ALLOWED_ENTER_TIME, ALLOWED_EXIT_TIME, EXPIRY, RFID) VALUES ('09:00:00', '18:00:00', '10-03-2026', 'H800000000');
INSERT INTO BADGE(ALLOWED_ENTER_TIME, ALLOWED_EXIT_TIME, EXPIRY, RFID) VALUES ('05:00:00', '12:00:00', '10-03-2026', 'I900000000');
INSERT INTO BADGE(ALLOWED_ENTER_TIME, ALLOWED_EXIT_TIME, EXPIRY, RFID) VALUES ('05:00:00', '18:00:00', '10-03-2026', 'L100000000');
INSERT INTO BADGE(ALLOWED_ENTER_TIME, ALLOWED_EXIT_TIME, EXPIRY, RFID) VALUES ('09:00:00', '18:01:00', '10-03-2026', 'M110000000');




CREATE TABLE USERS (
   	ID SERIAL PRIMARY KEY,
	USERTYPE VARCHAR(100) CHECK (USERTYPE = 'Employee' OR USERTYPE = 'Visitor'),
   	NAME VARCHAR(50) NOT NULL,
	SURNAME VARCHAR(50) NOT NULL,
	EMAIL VARCHAR(100) NOT NULL UNIQUE,
	ROLE_ID INT NOT NULL,
	STATE VARCHAR(25) DEFAULT 'EXIST',
	BADGE_ID INT UNIQUE NOT NULL,
	COMPANY_ID INT NOT NULL,
	FOREIGN KEY (ROLE_ID) REFERENCES ROLE(ID),
	FOREIGN KEY (COMPANY_ID) REFERENCES COMPANY(ID),
	FOREIGN KEY (BADGE_ID) REFERENCES BADGE (ID)
);

INSERT INTO USERS (USERTYPE, NAME, SURNAME, EMAIL, ROLE_ID, BADGE_ID, COMPANY_ID) VALUES ('Employee','Matteo', 'Boss', 'MatteoBoss@gmail.com', 1, 1, 1);
INSERT INTO USERS (USERTYPE, NAME, SURNAME, EMAIL, ROLE_ID, BADGE_ID, COMPANY_ID) VALUES ('Employee','Massimiliano', 'Tutor', 'Massimigliano@gmail.com', 7, 2, 1);
INSERT INTO USERS (USERTYPE, NAME, SURNAME, EMAIL, ROLE_ID, BADGE_ID, COMPANY_ID) VALUES ('Employee','Bernardo', 'Bucelli', 'bernardoBucelli@gmail.com', 2, 3, 1);
INSERT INTO USERS (USERTYPE, NAME, SURNAME, EMAIL, ROLE_ID, BADGE_ID, COMPANY_ID) VALUES ('Employee','Daniele', '2Piano', 'Daniele2Piano@gmail.com', 3, 4, 1);
INSERT INTO USERS (USERTYPE, NAME, SURNAME, EMAIL, ROLE_ID, BADGE_ID, COMPANY_ID) VALUES ('Employee','Pietro', '2Piano', 'Pietro2Piano@gmail.com', 3, 5, 2);
INSERT INTO USERS (USERTYPE, NAME, SURNAME, EMAIL, ROLE_ID, BADGE_ID, COMPANY_ID) VALUES ('Employee','Emma', 'Vernonello', 'emmaveronelli@gmail.com', 5, 6, 1);
INSERT INTO USERS (USERTYPE, NAME, SURNAME, EMAIL, ROLE_ID, BADGE_ID, COMPANY_ID) VALUES ('Visitor','Mario', 'Rossi', 'mariorossi@gmail.com', 8, 7, 3);
INSERT INTO USERS (USERTYPE, NAME, SURNAME, EMAIL, ROLE_ID, BADGE_ID, COMPANY_ID) VALUES ('Visitor','Luigi', 'Blue', 'luigiBlue@gmail.com', 8, 8, 3);
INSERT INTO USERS (USERTYPE, NAME, SURNAME, EMAIL, ROLE_ID, BADGE_ID, COMPANY_ID) VALUES ('Visitor','Daniele', '1Piano', 'daniele1Piano@gmail.com', 3, 9, 2);
INSERT INTO USERS (USERTYPE, NAME, SURNAME, EMAIL, ROLE_ID, BADGE_ID, COMPANY_ID) VALUES ('Employee','Riccardo', 'Leone', 'riccardoLeone@gmail.com', 6, 10, 1);
INSERT INTO USERS (USERTYPE, NAME, SURNAME, EMAIL, ROLE_ID, BADGE_ID, COMPANY_ID) VALUES ('Employee','Admir', '1Piano', 'Admir1Piano@gmail.com', 6, 11, 1);

CREATE TABLE TURNSTILE (
    ID SERIAL PRIMARY KEY,
    AVAILABLE BOOLEAN DEFAULT TRUE
);

INSERT INTO TURNSTILE(AVAILABLE) VALUES(true);


CREATE TABLE INSIDE_OFFICE (
    ID SERIAL PRIMARY KEY,
    USER_ID INT REFERENCES USERS(ID) UNIQUE
);

INSERT INTO INSIDE_OFFICE(USER_ID) VALUES(7);
INSERT INTO INSIDE_OFFICE(USER_ID) VALUES(8);
INSERT INTO INSIDE_OFFICE(USER_ID) VALUES(10);
INSERT INTO INSIDE_OFFICE(USER_ID) VALUES(11);

CREATE TABLE TRANSACTIONS (
    ID SERIAL PRIMARY KEY,
    DATE DATE NOT NULL,
    TIME TIME NOT NULL,
    CURRENT_STATE VARCHAR(50) NOT NULL, --POSSIBLE STATES(ENTER_APPROVED, EXIT_APPROVED, ERROR)
    USER_ID INT NOT NULL,
    TURNSTILE_ID INT NOT NULL,
    FOREIGN KEY (TURNSTILE_ID) REFERENCES TURNSTILE (ID),
    FOREIGN KEY (USER_ID) REFERENCES USERS(ID)
);

INSERT INTO TRANSACTIONS (DATE, TIME, CURRENT_STATE, USER_ID, TURNSTILE_ID) VALUES
('2025-03-10', '09:15:00', 'ENTER_APPROVED', 1, 1),
('2025-03-10', '09:10:00', 'ENTER_APPROVED', 2, 1),
('2025-03-10', '09:10:00', 'ENTER_APPROVED', 3, 1),
('2025-03-10', '09:10:00', 'ENTER_APPROVED', 4, 1),
('2025-03-10', '09:10:00', 'ENTER_APPROVED', 5, 1),
('2025-03-10', '09:09:00', 'ERROR', 7, 1),
('2025-03-10', '09:10:00', 'ENTER_APPROVED', 7, 1),
('2025-03-10', '09:23:00', 'ENTER_APPROVED', 8, 1),
('2025-03-10', '09:05:00', 'ENTER_APPROVED', 10, 1),
('2025-03-10', '09:06:00', 'ENTER_APPROVED', 11, 1),
('2025-03-10', '16:30:00', 'EXIT_DENIED', 1, 1),
('2025-03-10', '16:30:00', 'EXIT_APPROVED', 1, 1),
('2025-03-10', '18:30:00', 'EXIT_APPROVED', 2, 1),
('2025-03-10', '18:30:00', 'EXIT_APPROVED', 3, 1),
('2025-03-10', '18:30:00', 'EXIT_APPROVED', 4, 1),
('2025-03-10', '18:30:00', 'EXIT_APPROVED', 5, 1);


CREATE TABLE TRANSACTION_EVENT (
    ID SERIAL PRIMARY KEY,
    STATE VARCHAR(100) NOT NULL, 				--POSSIBLE STATES(BADGE_PASSED, VALIDATING, OPEN_GATE, PASSING_THROUGH, CLOSING_GATE, COMPLETED)
    CREATED_AT TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    TRANSACTION_ID INT NOT NULL,
    FOREIGN KEY (TRANSACTION_ID) REFERENCES TRANSACTIONS(ID)
);


INSERT INTO TRANSACTION_EVENT (STATE, TRANSACTION_ID) VALUES
('BADGE_PASSED', 1),
('VALIDATING', 1),
('OPEN_GATE', 1),
('PASSING_THROUGH', 1),
('CLOSING_GATE', 1),
('COMPLETED', 1),
('BADGE_PASSED', 2),
('VALIDATING', 2),
('OPEN_GATE', 2),
('PASSING_THROUGH', 2),
('CLOSING_GATE', 2),
('COMPLETED', 2),
('BADGE_PASSED', 3),
('VALIDATING', 3),
('OPEN_GATE', 3),
('PASSING_THROUGH', 3),
('CLOSING_GATE', 3),
('COMPLETED', 3),
('BADGE_PASSED', 4),
('VALIDATING', 4),
('OPEN_GATE', 4),
('PASSING_THROUGH', 4),
('CLOSING_GATE', 4),
('COMPLETED', 4),
('BADGE_PASSED', 5),
('VALIDATING', 5),
('OPEN_GATE', 5),
('PASSING_THROUGH', 5),
('CLOSING_GATE', 5),
('COMPLETED', 5),
('BADGE_PASSED', 6),
('VALIDATING', 6),
('ERROR_OCCURRED', 6),
('BADGE_PASSED', 7),
('VALIDATING', 7),
('OPEN_GATE', 7),
('PASSING_THROUGH', 7),
('CLOSING_GATE', 7),
('COMPLETED', 7),
('BADGE_PASSED', 8),
('VALIDATING', 8),
('OPEN_GATE', 8),
('PASSING_THROUGH', 8),
('CLOSING_GATE', 8),
('COMPLETED', 8),
('BADGE_PASSED', 9),
('VALIDATING', 9),
('OPEN_GATE', 9),
('PASSING_THROUGH', 9),
('CLOSING_GATE', 9),
('COMPLETED', 9),
('BADGE_PASSED', 10),
('VALIDATING', 10),
('OPEN_GATE', 10),
('PASSING_THROUGH', 10),
('CLOSING_GATE', 10),
('COMPLETED', 10),
('BADGE_PASSED', 11),
('VALIDATING', 11),
('EXIT_DENIED', 11),
('BADGE_PASSED', 12),
('VALIDATING', 12),
('OPEN_GATE', 12),
('PASSING_THROUGH', 12),
('CLOSING_GATE', 12),
('COMPLETED', 12),
('BADGE_PASSED', 13),
('VALIDATING', 13),
('OPEN_GATE', 13),
('PASSING_THROUGH', 13),
('CLOSING_GATE', 13),
('COMPLETED', 13),
('BADGE_PASSED', 14),
('VALIDATING', 14),
('OPEN_GATE', 14),
('PASSING_THROUGH', 14),
('CLOSING_GATE', 14),
('COMPLETED', 14),
('BADGE_PASSED', 15),
('VALIDATING', 15),
('OPEN_GATE', 15),
('PASSING_THROUGH', 15),
('CLOSING_GATE', 15),
('COMPLETED', 15);



CREATE TABLE ERROR_LOG (
    ID SERIAL PRIMARY KEY,
    CREATED_AT TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    ERROR_MESSAGE VARCHAR(255) NOT NULL,
    USER_ID INT,
    TURNSTILE_ID INT,
    FOREIGN KEY (TURNSTILE_ID) REFERENCES TURNSTILE (ID),
    FOREIGN KEY (USER_ID) REFERENCES USERS(ID)
);
INSERT INTO ERROR_LOG (CREATED_AT, ERROR_MESSAGE, USER_ID, TURNSTILE_ID) VALUES
('2025-03-10 09:09:00', 'UNABLE TO READ RFID', 7, 1),
('2025-03-10 16:30:00', 'Turnstile Malfunction', 1, 1);

