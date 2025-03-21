DO $$
DECLARE 
    user_ids INT;
    role_id INT;
    company_id INT;
    permission_id INT;
    badge_id INT;
    user_count INT := (SELECT COUNT(id) FROM USERS);
    employee_limit INT := user_count * 0.8;  -- 80% Employees
    visitor_limit INT := user_count * 0.2;   -- 20% Visitors
    employee_count INT := 0;
    visitor_count INT := 0;
BEGIN
    FOR r IN 1..user_count LOOP
        user_ids := (SELECT id FROM USERS ORDER BY RANDOM() LIMIT 1);

        IF NOT EXISTS (SELECT 1 FROM EMPLOYEE AS E WHERE E.USER_ID = user_ids) 
        AND NOT EXISTS (SELECT 1 FROM VISITOR AS V WHERE V.USER_ID = user_ids) THEN
            
            badge_id := (SELECT id FROM BADGE ORDER BY RANDOM() LIMIT 1);
            
            IF employee_count < employee_limit AND RANDOM() < 0.8 THEN
                role_id := (SELECT id FROM ROLE ORDER BY RANDOM() LIMIT 1);
                company_id := (SELECT id FROM COMPANY ORDER BY RANDOM() LIMIT 1);
                permission_id := (SELECT id FROM PERMISSIONS ORDER BY RANDOM() LIMIT 1);

                INSERT INTO EMPLOYEE(USER_ID, ROLE_ID, COMPANY_ID, PERMISSION_ID, BADGE_ID) 
                VALUES (user_ids, role_id, company_id, permission_id, badge_id);

                employee_count := employee_count + 1;
            ELSIF visitor_count < visitor_limit THEN
                role_id := (SELECT id FROM ROLE ORDER BY RANDOM() LIMIT 1);
                company_id := (SELECT id FROM COMPANY ORDER BY RANDOM() LIMIT 1);
                permission_id := (SELECT id FROM PERMISSIONS ORDER BY RANDOM() LIMIT 1);

                INSERT INTO VISITOR(USER_ID, ROLE_ID, COMPANY_ID, PERMISSION_ID, BADGE_ID) 
                VALUES (user_ids, role_id, company_id, permission_id, badge_id);

                visitor_count := visitor_count + 1;
            END IF;
        END IF;
    END LOOP;
END;
$$;

