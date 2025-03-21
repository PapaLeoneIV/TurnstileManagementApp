-- get all the user(employee/visitor) who entered before a specific time on a specific date
SELECT
    T.DATE,
    T.ENTER_TIME,
    U.NAME,
    U.SURNAME
FROM 
    USERS AS U
JOIN TRANSACTIONS AS T ON T.USER_ID = U.ID
WHERE
    T.DATE = '2025-02-25'
    AND T.ENTER_TIME <= '09:15:00';

	