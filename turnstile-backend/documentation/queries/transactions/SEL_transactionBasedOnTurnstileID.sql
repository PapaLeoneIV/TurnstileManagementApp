-- get all transactions of a specific day
SELECT
    *
FROM
    TRANSACTIONS AS T
JOIN
	TURNSTILE AS TURNS ON T.TURNSTILE_ID = TURNS.ID
WHERE
	TURNS.ID = 1;
