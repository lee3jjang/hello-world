/*
SELECT SUBSTR(기준일자, 1, 4) as 기준년도,
	   공항,
	   COUNT(*) AS 건수,
	   ROUND(COUNT(*)/3.65,1) AS 진행률
  FROM (SELECT DISTINCT 기준일자, 공항 FROM AIRPORT
         UNION
        SELECT * FROM EXCEPTION)
 GROUP BY substr(기준일자, 1, 4), 공항;
 */
 
 
 SELECT COUNT(*) AS 작업건수,
	    (365*4*14+366*1*14)-COUNT(*) AS 잔여건수,
		ROUND(COUNT(*)/(3.65*4*14+3.66*1*14),1) AS 진행률	
   FROM (SELECT DISTINCT 기준일자, 공항 FROM AIRPORT
          UNION
         SELECT * FROM EXCEPTION);
 