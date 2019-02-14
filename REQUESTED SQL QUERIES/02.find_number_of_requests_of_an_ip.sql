-- MySQL query to find requests made by a given IP.
-- givenIP: '192.168.114.17'

select ip_address, count(*) as number_of_requests from access_log
where ip_address = '192.168.114.17'
group by ip_address;