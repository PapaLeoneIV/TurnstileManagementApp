DO $$
DECLARE 
    user_id INT;
	user_id_length INT:= (SELECT COUNT(id) FROM USERS);
BEGIN
    FOR r IN 1..user_id_length LOOP
        user_id := (SELECT id FROM USERS ORDER BY RANDOM() LIMIT 1); 

        INSERT INTO INSIDE_OFFICE(USER_ID) 
        VALUES (user_id);
    END LOOP;
END;
$$;

