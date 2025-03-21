--get employee inside the office
--TODO add transaction info for when the employee entered/exited the office
SELECT
	U.NAME,
	U.SURNAME,
	U.EMAIL
FROM
	USERS AS U
JOIN INSIDE_OFFICE AS IO ON IO.USER_ID = U.ID