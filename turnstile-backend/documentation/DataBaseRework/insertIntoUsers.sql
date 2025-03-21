DO $$
BEGIN
FOR r in 1..1000 loop
INSERT INTO USERS(name, surname, email) VALUES
(get_random_string(8, 'abcdefghilmnopqrstuvz'), get_random_string(10, 'abcdefghilmnopqrstuvz'), get_random_string(11, 'abcdefghilmnopqrstuvz') || '@gmail.com');
end loop;
end;
$$;

--SELECT * FROM USERS;