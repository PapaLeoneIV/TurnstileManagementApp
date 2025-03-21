--get all the transaction of a specific user(employee/visitor)
SELECT
    U.NAME,
    U.SURNAME,
    U.EMAIL,
    T.DATE,
    T.ENTER_TIME,
    T.EXIT_TIME,
    T.TURNSTILE_ID
FROM
    USERS AS U
JOIN TRANSACTIONS AS T ON T.USER_ID = U.ID
WHERE
    U.ID = 32;
