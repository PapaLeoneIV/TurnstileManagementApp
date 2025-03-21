--TODO insert them using a procedural function, procedure, or DO statement 
-- (https://stackoverflow.com/questions/19145761/postgres-for-loop)//


--CREATE SEQUENCE role_level_sequence INCREMENT BY 1;
DO $$
BEGIN
FOR r in 1..1000 loop
INSERT INTO ROLE(level, description) VALUES
(nextval('role_level_sequence'), get_random_string(10, 'abcdefghilmnopqrstuvz'));
end loop;
end;
$$;


--SELECT * FROM ROLE;