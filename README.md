## Parser App

#### Synopsis

This project has been implemented as part of a small task and it is a parser in Java that parses web server access log file, 
loads the log to MySQL and checks if a given IP makes more than a certain number of requests for the given duration. 
  
Technology Details  
- This projects is based on Spring Boot that makes it easy to create stand-alone applications that you can "just run".
- It includes Spring Shell which is used for creating an interactive command line. 
- It includes an embedded H2 database (file based - MySQL mode).  
The console of H2 can be accessed by url: [http://localhost:8080/h2](http://localhost:8080/h2)  
where you can use the following to entry the interface of it  
username: sa  
password:  
jdbcUrl:jdbc:h2:~/parser  
The above properties can be changed via the [application.properties](src/main/resources/application.properties).   
- It integrates with flyway for data migration. This is used only for running the initial scripts to create the db schema for our project. 

#### Build the project

The following command will compile java and create the executable jar file [parser-1.0.0.jar](build/libs/parser-1.0.0.jar).

    ./gradlew clean build  
    
#### Run the application using the executable jar file

The following command will run the application:

    java -jar ./build/libs/parser-1.0.0.jar  
    
#### Spring Shell Commands

After you have run the above command the spring boot application has been started and at some point you will notice the welcome message  
of the Parser Shell (as I have named our CLI for this project).
- By just typing `help` you will see all the available commands. Many of them come along with the framework itself.  
Our custom commands are the following: 
- `parse`: 
 This command parses an access server log file in order to find which IP's existing on that file exceed the given threshold.
 When there are IP's, they will be shown in the command line and they will be stored in a db table named `blocked_ip`
 Available parameters that you can give along with this command are:  
    * `--accesslog` The path which indicates where the file is located.  
    * `--startDate` The start date of written logs the parser should check for IPs. It should be of format `yyyy-MM-dd.HH:mm:ss`.  
    * `--duration` The duration must be `daily` or `hourly`. In case parameter is not given, the default value will be used.    
    * `--threshold` Indicates how many request the IP's should have exceeded in order to be marked.    
    
- `load-file`
 This command parses the whole access.log file and store the related info to a db table name `access_log` in order then
 to run any queries against it so that you can find any IP's exceed a specific threshold within a date range.
 Available parameters that you can give along with this command are:  
    * `--accesslog` The path which indicates where the file is located.  
    
 In case some parameter is not given for the above commands, a default value will be used. You can modify the default values via the [environment.default.properties](src/main/resources/environment.default.properties). 

#### Examples

**1. parse command**

Assuming you have the `access.log` file at this path: `/tmp/access.log` and you want to check which IPs exceed the threshold of  
200 requests from `2017-01-01 19:00:00.000` for an hour (`duration` = `HOURLY`) then you can run the following command in the  
Parser Shell:

    parse --accesslog /tmp/access.log --threshold 200 --duration HOURLY --startDate 2017-01-01.19:00:00  
    
 The above will give you these IP's: `192.168.5.107, 192.168.246.75, 192.168.114.17`
    
**2. load-file command**

Assuming you have the `access.log` file at this path: `/tmp/access.log` and you want to store the related info to a db table name `access_log` in order then  
to run any queries against it so that you can find any IP's exceed a specific threshold within a date range then you can run the following command in the  
Parser Shell:

    load-file --accesslog /tmp/access.log 
    
 The above will result in storing 116484 rows in `access_log` table
 
 #### SQL Queries
 
 After having some data on `access.log` table (you can run the `load-file` command in order to add some from some file)  
 you can run the following queries in order to get the requested data from the requirements. Both of the queries can be found in the  
 these files: [01.find_ips_exceeding_threshold.sql](REQUESTED SQL QUERIES/01.find_ips_exceeding_threshold.sql) and [02.find_number_of_requests_of_an_ip.sql](REQUESTED SQL QUERIES/02.find_number_of_requests_of_an_ip.sql)
    
**1. MySQL query to find IPs that mode more than a certain number of requests for a given time period.**

    select * from (select ip_address, count(*) as number_of_requests from access_log
                   where log_date > '2017-01-01 19:00:00.000' and log_date < '2017-01-01 20:00:00.000'
                   group by ip_address
                   order by number_of_requests desc)
    where number_of_requests > 100;
    
**2. MySQL query to find requests made by a given IP.**

    select ip_address, count(*) as number_of_requests from access_log
    where ip_address = '192.168.114.17'
    group by ip_address;
    

 #### Authors
Nick Drakopoulos


  
