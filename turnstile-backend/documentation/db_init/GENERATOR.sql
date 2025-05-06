--Trigger to enforce uniqueness across badge-id

CREATE OR REPLACE FUNCTION enforce_one_to_one_badge() 
RETURNS TRIGGER AS $$
BEGIN
    IF EXISTS (SELECT 1 FROM EMPLOYEE WHERE BADGE_ID = NEW.BADGE_ID) THEN
        RAISE EXCEPTION 'Badge % is already assigned to an Employee', NEW.BADGE_ID;
    END IF;
    
    IF EXISTS (SELECT 1 FROM VISITOR WHERE BADGE_ID = NEW.BADGE_ID) THEN
        RAISE EXCEPTION 'Badge % is already assigned to a Visitor', NEW.BADGE_ID;
    END IF;
    
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS check_badge_employee ON EMPLOYEE;
CREATE TRIGGER check_badge_employee
BEFORE INSERT OR UPDATE ON EMPLOYEE
FOR EACH ROW EXECUTE FUNCTION enforce_one_to_one_badge();

DROP TRIGGER IF EXISTS check_badge_visitor ON VISITOR;
CREATE TRIGGER check_badge_visitor
BEFORE INSERT OR UPDATE ON VISITOR
FOR EACH ROW EXECUTE FUNCTION enforce_one_to_one_badge();


CREATE SEQUENCE IF NOT EXISTS role_level_sequence INCREMENT BY 1;

CREATE OR REPLACE FUNCTION random_string(INTEGER)
RETURNS TEXT AS
$BODY$
SELECT array_to_string(
    ARRAY (
        SELECT substring(
            '0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz'
            FROM (ceil(random()*62))::int FOR 1
        )
        FROM generate_series(1, $1)
    ), 
    ''
)
$BODY$
LANGUAGE sql VOLATILE;

DO $$
BEGIN
FOR r in 1..1000 loop
INSERT INTO ROLE(level, description) VALUES
(nextval('role_level_sequence'), random_string(10));
end loop;
end;
$$;

--COMPANY
DO $$
BEGIN
FOR r in 1..1000 loop
INSERT INTO COMPANY(name, address) VALUES
(random_string(10), random_string(10));
end loop;
end;
$$;

--BADGE
DO $$
BEGIN
FOR r in 1..1000 loop
INSERT INTO BADGE( rfid) VALUES
(random_string(20));
end loop;
end;
$$;

--USERS
DO $$
BEGIN
FOR r in 1..1000 loop
INSERT INTO USERS(name, surname, email) VALUES
(random_string(8), random_string(10), random_string(11) || '@gmail.com');
end loop;
end;
$$;

-- PERMISSION
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

--EMPLOYEE / VISITOR
DO $$
DECLARE 
    user_ids INT;
    role_id INT;
    company_id INT;
    permission_id INT;
    badge_ids INT;
    user_count INT := (SELECT COUNT(id) FROM USERS);
    employee_limit INT := user_count * 0.8;  
    visitor_limit INT := user_count * 0.2;
    employee_count INT := 0;
    visitor_count INT := 0;
BEGIN
    FOR r IN 1..user_count LOOP
        user_ids := (SELECT id FROM USERS ORDER BY RANDOM() LIMIT 1);

        IF NOT EXISTS (SELECT 1 FROM EMPLOYEE WHERE USER_ID = user_ids) 
        AND NOT EXISTS (SELECT 1 FROM VISITOR WHERE USER_ID = user_ids) THEN

            LOOP
                badge_ids := (SELECT id FROM BADGE ORDER BY RANDOM() LIMIT 1);
                EXIT WHEN NOT EXISTS (SELECT 1 FROM EMPLOYEE AS E WHERE E.BADGE_ID = badge_ids) 
                    AND NOT EXISTS (SELECT 1 FROM VISITOR AS V WHERE V.BADGE_ID = badge_ids);
            END LOOP;

            role_id := (SELECT id FROM ROLE ORDER BY RANDOM() LIMIT 1);
            company_id := (SELECT id FROM COMPANY ORDER BY RANDOM() LIMIT 1);
            permission_id := (SELECT id FROM PERMISSIONS ORDER BY RANDOM() LIMIT 1);

            IF employee_count < employee_limit AND RANDOM() < 0.8 THEN
                INSERT INTO EMPLOYEE(USER_ID, ROLE_ID, COMPANY_ID, PERMISSION_ID, BADGE_ID) 
                VALUES (user_ids, role_id, company_id, permission_id, badge_ids);

                employee_count := employee_count + 1;
            ELSIF visitor_count < visitor_limit THEN
                INSERT INTO VISITOR(USER_ID, ROLE_ID, COMPANY_ID, PERMISSION_ID, BADGE_ID) 
                VALUES (user_ids, role_id, company_id, permission_id, badge_ids);

                visitor_count := visitor_count + 1;
            END IF;
        END IF;
    END LOOP;
END;
$$;



--TURNSTILE
INSERT INTO
	TURNSTILE (AVAILABLE)
VALUES
	('TRUE');

--INSIDE OFFICE
DO $$
DECLARE 
    user_ids INT;
BEGIN
    FOR user_ids IN (SELECT id FROM USERS ORDER BY RANDOM() LIMIT 900) LOOP
        IF NOT EXISTS (SELECT 1 FROM INSIDE_OFFICE WHERE USER_ID = user_ids) THEN
            INSERT INTO INSIDE_OFFICE(USER_ID) 
            VALUES (user_ids);
        END IF;
    END LOOP;
END;
$$;

DO $$
DECLARE
    user_ids INT;
    turnstile_ids INT;
BEGIN
    FOR i IN 1..10000 LOOP
        user_ids := (SELECT ID FROM USERS ORDER BY RANDOM() LIMIT 1);
        turnstile_ids := (SELECT ID FROM TURNSTILE ORDER BY RANDOM() LIMIT 1);

        IF NOT EXISTS (
            SELECT 1 FROM TRANSACTIONS 
            WHERE USER_ID = user_ids AND TURNSTILE_ID = turnstile_ids
        ) THEN
            INSERT INTO TRANSACTIONS (DATE, ENTER_TIME, CURRENT_STATE, EXIT_TIME, USER_ID, TURNSTILE_ID)
            VALUES (
                NOW()::DATE - (random() * 10)::INT, 
                TIME '08:00:00' + (random() * INTERVAL '8 hours'),  
                'APPROVED',
                CASE WHEN random() > 0.05 THEN TIME '16:00:00' + (random() * INTERVAL '4 hours') ELSE NULL END, -- 5% have an exit time
                user_ids,
                turnstile_ids
            );
        ELSE
            CONTINUE;
        END IF;
    END LOOP;
END;
$$;

--TRANSACTION EVENTS
DO $$ 
DECLARE 
    trans RECORD;
    event_states TEXT[] := ARRAY['BADGE_PASSED', 'VALIDATING', 'OPEN_GATE', 'PASSING_THROUGH', 'CLOSING_GATE', 'COMPLETED'];
    event_state TEXT;
    event_time TIMESTAMP;
BEGIN
    FOR trans IN (SELECT ID FROM TRANSACTIONS) LOOP  -- Loop through all transactions
        event_time := NOW() - (random() * INTERVAL '10 days');  -- Start time of transaction

        FOREACH event_state IN ARRAY event_states LOOP  -- Loop through each state
            INSERT INTO TRANSACTION_EVENT (STATE, TRANSACTION_ID, CREATED_AT)
            VALUES (event_state, trans.ID, event_time);
            
            event_time := event_time + (random() * INTERVAL '1 minute'); -- Add a slight delay for each step
        END LOOP;
    END LOOP;
END $$;
