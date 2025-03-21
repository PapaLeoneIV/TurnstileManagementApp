INSERT INTO ROLE (LEVEL, DESCRIPTION) VALUES
(1, 'Admin'),
(2, 'Manager'),
(3, 'Senior'),
(4, 'Mid'),
(5, 'Junior'),
(6, 'Stage'),
(7, 'Lead'),
(8, 'Assistant'),
(9, 'Intern'),
(10, 'Supervisor'),
(11, 'Coordinator'),
(12, 'Director'),
(13, 'Executive'),
(14, 'Chief'),
(15, 'President');


-- Insert entries into COMPANY
INSERT INTO COMPANY (COMPANY_NAME, ADDRESS) VALUES
('TDG Consulting', 'Via Matteotti, 129'),
('Engineering', 'Piazza della Liberta, 12');

-- Insert entries into BADGE
INSERT INTO BADGE (RFID) VALUES
('RFID-001'),
('RFID-002'),
('RFID-003'),
('RFID-004'),
('RFID-005'),
('RFID-006'),
('RFID-007'),
('RFID-008'),
('RFID-009'),
('RFID-010');

INSERT INTO USERS (NAME, SURNAME, EMAIL) VALUES
('John', 'Doe', 'john.doe@company.com'),
('Jane', 'Smith', 'jane.smith@visitor.com'),
('Alice', 'Johnson', 'alice.johnson@company.com'),
('Foo', 'Doe', 'foo.doe@company.com'),
('Bar', 'Smith', 'bar.smith@visitor.com'),
('Baz', 'Johnson', 'baz.johnson@company.com'),
('Michael', 'Williams', 'michael.williams@visitor.com'),
('Emily', 'Davis', 'emily.davis@company.com'),
('Olivia', 'Garcia', 'olivia.garcia@visitor.com'),
('James', 'Brown', 'james.brown@company.com'),
('Liam', 'Miller', 'liam.miller@visitor.com');

-- Insert entries into PERMISSIONS
INSERT INTO PERMISSIONS (ALLOWED_ENTER_TIME, ALLOWED_EXIT_TIME, END_OF_PERMISSION, ROLE_ID) VALUES
('08:00', '17:00', '2025-12-31', 1),
('09:00', '18:00', '2025-12-31', 2),
('10:00', '19:00', '2025-12-31', 3),
('07:00', '16:00', '2025-12-31', 4),
('08:00', '17:00', '2025-12-31', 5),
('09:00', '18:00', '2025-12-31', 6),
('06:00', '15:00', '2025-12-31', 7),
('08:30', '17:30', '2025-12-31', 8),
('10:00', '19:00', '2025-12-31', 9),
('11:00', '20:00', '2025-12-31', 10),
('07:30', '16:30', '2025-12-31', 11),
('09:30', '18:30', '2025-12-31', 12),
('06:30', '15:30', '2025-12-31', 13),
('08:00', '17:00', '2025-12-31', 14),
('10:30', '19:30', '2025-12-31', 15);

-- Insert entries into EMPLOYEE
INSERT INTO EMPLOYEE (USER_ID, ROLE_ID, COMPANY_ID, PERMISSION_ID, BADGE_ID) VALUES
(1, 2, 2, 3, 1),
(5, 5, 1, 5, 5),
(6, 3, 2, 15, 6),
(7, 4, 2, 12, 7),
(10, 4, 2, 12, 10);

-- Insert entries into VISITOR
INSERT INTO VISITOR (USER_ID, ROLE_ID, COMPANY_ID, PERMISSION_ID, BADGE_ID) VALUES
(2, 4, 1, 3, 2),
(3, 3, 2, 3, 3),
(4, 1, 1, 4, 4),
(8, 5, 1, 13, 8),
(9, 4, 2, 9, 9);

-- Insert entries into TURNSTILE
INSERT INTO TURNSTILE (AVAILABLE) VALUES
(TRUE);

-- Insert entries into INSIDE_OFFICE
INSERT INTO INSIDE_OFFICE (USER_ID) VALUES
(1),
(3),
(2),
(5),
(4),
(6),
(8),
(7),
(9);

-- Insert entries into TRANSACTIONS
INSERT INTO TRANSACTIONS (DATE, ENTER_TIME, CURRENT_STATE, EXIT_TIME, USER_ID, TURNSTILE_ID) VALUES
('2025-01-01', '08:00:00', 'ENTERED', '17:00:00', 1, 1),
('2025-01-02', '09:00:00', 'ENTERED', '18:00:00', 2, 1),
('2025-01-02', '09:00:00', 'ENTERED', '18:00:00', 3, 1),
('2025-01-02', '09:00:00', 'ENTERED', '18:00:00', 4, 1),
('2025-01-02', '09:00:00', 'ENTERED', '18:00:00', 5, 1),
('2025-01-02', '09:00:00', 'ENTERED', '18:00:00', 6, 1),
('2025-01-02', '09:00:00', 'ENTERED', '18:00:00', 7, 1),
('2025-01-02', '09:00:00', 'ENTERED', '18:00:00', 8, 1),
('2025-01-03', '08:30:00', 'ENTERED', '17:30:00', 9, 1);

-- Insert entries into TRANSACTION_EVENT
INSERT INTO TRANSACTION_EVENT (STATE, TRANSACTION_ID) VALUES
('Authorizing', 1),
('Approved', 1),
('Entered', 1);
