--get employee by company name
SELECT
	U.ID,
	U.NAME,
	U.SURNAME,
	U.EMAIL,
	C.COMPANY_NAME
FROM
	USERS AS U
	JOIN EMPLOYEE AS E ON E.USER_ID = U.ID
	JOIN COMPANY AS C ON E.COMPANY_ID = C.ID
WHERE
	C.COMPANY_NAME = 'TDG Consulting'
