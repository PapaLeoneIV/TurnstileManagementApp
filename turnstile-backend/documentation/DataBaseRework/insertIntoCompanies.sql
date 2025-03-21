DO $$
BEGIN
FOR r in 1..1000 loop
INSERT INTO COMPANY(company_name, address) VALUES
(get_random_string(10, 'ABCDEFGHILMNOPQRSTUVZabcdefghilmnopqrstuvz'), get_random_string(10, 'abcdefghilmnopqrstuvz123456789'));
end loop;
end;
$$;

--SELECT * FROM COMPANY;