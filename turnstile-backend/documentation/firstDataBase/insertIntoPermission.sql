--CREATE TABLE PERMISSIONS (
--    ID SERIAL PRIMARY KEY,
--    ALLOWED_ENTER_TIME TIME,
--    ALLOWED_EXIT_TIME TIME,
--    END_OF_PERMISSION DATE NOT NULL,
--    ROLE_ID INT NOT NULL,
--    FOREIGN KEY (ROLE_ID) REFERENCES ROLE (ID)
--);
DO $$
DECLARE 
    enter_time TIME := '07:30:00';
    exit_time TIME := '16:30:00';
    end_date DATE := '2025-02-27';
    role_id INT;
BEGIN
    FOR r IN 1..1000 LOOP
        role_id := (SELECT id FROM ROLE ORDER BY RANDOM() LIMIT 1); 

        INSERT INTO PERMISSIONS(ALLOWED_ENTER_TIME, ALLOWED_EXIT_TIME, END_OF_PERMISSION, ROLE_ID) 
        VALUES (enter_time, exit_time, end_date, role_id);

        enter_time := enter_time + INTERVAL '10 minutes';
        exit_time := exit_time + INTERVAL '10 minutes';

        IF r % 10 = 0 THEN
            end_date := end_date + INTERVAL '10 days';
        END IF;
    END LOOP;
END;
$$;


--SELECT * FROM PERMISSIONS;
