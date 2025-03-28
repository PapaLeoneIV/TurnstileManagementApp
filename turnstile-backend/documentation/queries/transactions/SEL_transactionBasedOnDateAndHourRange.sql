-- Get all transactions between a range of hours on a specific day
SELECT
    T.DATE,
	T.CURRENT_STATE,
	T.ENTER_TIME,
	T.EXIT_TIME,
	U.NAME,
	U.SURNAME,
	U.EMAIL
FROM
    TRANSACTIONS AS T
JOIN
	USERS AS U ON T.USER_ID = U.ID
WHERE
	T.DATE = '01-01-2025'
	AND T.ENTER_TIME BETWEEN '09:00:00' AND '09:30:00';

