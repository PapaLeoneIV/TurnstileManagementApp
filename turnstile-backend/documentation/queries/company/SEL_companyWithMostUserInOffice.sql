SELECT
    C.COMPANY_NAME,
    COUNT(DISTINCT E.ID) AS EMPLOYEE_COUNT,
    COUNT(DISTINCT V.ID) AS VISITOR_COUNT,
    COUNT(DISTINCT U.ID) AS TOTAL_USER_COUNT
FROM
    COMPANY AS C
JOIN
    EMPLOYEE AS E ON E.COMPANY_ID = C.ID
JOIN
    VISITOR AS V ON V.COMPANY_ID = C.ID
JOIN
    USERS AS U ON U.ID = E.USER_ID OR U.ID = V.USER_ID
JOIN
    INSIDE_OFFICE AS IO ON IO.USER_ID = U.ID
GROUP BY
    C.COMPANY_NAME
ORDER BY
    TOTAL_USER_COUNT DESC
LIMIT 1000;
