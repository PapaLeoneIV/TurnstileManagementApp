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

