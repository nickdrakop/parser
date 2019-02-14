-- MySQL query to find IPs that mode more than a certain number of requests for a given time period.
-- startDate: '2017-01-01 19:00:00.000'
-- startDate: '2017-01-01 20:00:00.000'
-- threshold: 100

select * from (select ip_address, count(*) as number_of_requests from access_log
               where log_date > '2017-01-01 19:00:00.000' and log_date < '2017-01-01 20:00:00.000'
               group by ip_address
               order by number_of_requests desc)
where number_of_requests > 100;