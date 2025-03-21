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
