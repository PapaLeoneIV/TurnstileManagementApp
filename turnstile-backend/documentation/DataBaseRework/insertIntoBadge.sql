DO $$
BEGIN
FOR r in 1..1000 loop
INSERT INTO BADGE(rfid) VALUES
(get_random_string(20, 'ABCDEFGHILMNOPQRSTUVZabcdefghilmnopqrstuvz1234567890'));
end loop;
end;
$$;

--SELECT * FROM BADGE;