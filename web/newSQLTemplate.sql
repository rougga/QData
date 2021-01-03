SELECT
g1.BIZ_TYPE_ID,
G1.NAME,
G1.NB_T,
G1.NB_TT, 
G1.NB_A,
G1.NB_TL1,
G1.NB_SA,
CASE 
WHEN G1.NB_T::numeric = 0::numeric THEN 0::numeric 
ELSE CAST((G1.NB_A::numeric / G1.NB_T::numeric) * 100::numeric AS DECIMAL(10,2)) 
END AS PERAPT,
CASE
WHEN G1.NB_T::numeric = 0::numeric THEN 0::numeric
ELSE CAST((G1.NB_TL1::numeric / G1.NB_T::numeric) * 100::numeric AS DECIMAL(10,2)) 
END AS PERTL1pt,
CASE WHEN G1.NB_T::numeric = 0::numeric THEN 0::numeric 
ELSE CAST((G1.NB_SA::numeric / G1.NB_T::numeric) * 100::numeric AS DECIMAL(10,2)) 
END AS PERSAPT , 
G1.AVGSEC_A, G1.avgsec_T 
from 
( select 
t1.biz_type_id,
b.name, 
(SELECT COUNT(*) FROM T_TICKET T2 WHERE T2.BIZ_TYPE_ID = T1.BIZ_TYPE_ID  and to_date(to_char(t2.ticket_time,'YYYY-MM-DD'),'YYYY-MM-DD')  BETWEEN TO_DATE($P{date1},'YYYY-MM-DD') AND TO_DATE($P{date2},'YYYY-MM-DD') ) AS NB_T,
(SELECT COUNT(*) FROM T_TICKET T2 WHERE T2.BIZ_TYPE_ID = T1.BIZ_TYPE_ID AND T2.STATUS = 4  and to_date(to_char(t2.ticket_time,'YYYY-MM-DD'),'YYYY-MM-DD')  BETWEEN TO_DATE($P{date1},'YYYY-MM-DD') AND TO_DATE($P{date2},'YYYY-MM-DD') ) AS NB_TT,
(SELECT COUNT(*) FROM T_TICKET T2 WHERE T2.BIZ_TYPE_ID = T1.BIZ_TYPE_ID AND T2.STATUS = 2  and to_date(to_char(t2.ticket_time,'YYYY-MM-DD'),'YYYY-MM-DD')  BETWEEN TO_DATE($P{date1},'YYYY-MM-DD') AND TO_DATE($P{date2},'YYYY-MM-DD')) AS NB_A,
(SELECT COUNT(*) FROM T_TICKET T2 WHERE T2.BIZ_TYPE_ID = T1.BIZ_TYPE_ID AND DATE_PART('epoch'::text, T2.FINISH_TIME - T2.START_TIME)::numeric / 60::numeric <= 1 AND T2.STATUS = 4  and to_date(to_char(t2.ticket_time,'YYYY-MM-DD'),'YYYY-MM-DD')  BETWEEN TO_DATE($P{date1},'YYYY-MM-DD') AND TO_DATE($P{date2},'YYYY-MM-DD') ) AS NB_TL1,
(SELECT COUNT(*) FROM T_TICKET T2 WHERE T2.BIZ_TYPE_ID = T1.BIZ_TYPE_ID AND T2.STATUS = 0  and to_date(to_char(t2.ticket_time,'YYYY-MM-DD'),'YYYY-MM-DD')  BETWEEN TO_DATE($P{date1},'YYYY-MM-DD') AND TO_DATE($P{date2},'YYYY-MM-DD') ) AS NB_SA,
(SELECT AVG(DATE_PART('epoch'::text, T2.CALL_TIME - T2.TICKET_TIME)::numeric) FROM T_TICKET T2 WHERE T2.BIZ_TYPE_ID = T1.BIZ_TYPE_ID and T2.call_time is not null  and to_date(to_char(t2.ticket_time,'YYYY-MM-DD'),'YYYY-MM-DD')  BETWEEN TO_DATE($P{date1},'YYYY-MM-DD') AND TO_DATE($P{date2},'YYYY-MM-DD')) AS AVGSEC_A, 
(SELECT AVG(DATE_PART('epoch'::text, T2.FINISH_TIME - T2.START_TIME)::numeric) FROM T_TICKET T2 WHERE T2.BIZ_TYPE_ID = T1.BIZ_TYPE_ID AND T2.STATUS = 4  and to_date(to_char(t2.ticket_time,'YYYY-MM-DD'),'YYYY-MM-DD')  BETWEEN TO_DATE($P{date1},'YYYY-MM-DD') AND TO_DATE($P{date2},'YYYY-MM-DD') ) AS AVGSEC_T FROM T_TICKET T1, T_BIZ_TYPE B WHERE T1.BIZ_TYPE_ID = B.ID  AND TO_DATE(TO_CHAR(T1.TICKET_TIME,'YYYY-MM-DD'),'YYYY-MM-DD') BETWEEN TO_DATE($P{date1},'YYYY-MM-DD') AND TO_DATE($P{date2},'YYYY-MM-DD') GROUP BY T1.BIZ_TYPE_ID, B.NAME ) G1 ;
