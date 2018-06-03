## Тестовое задание на оплачиваемую стажировку

Design and implement a REST API using Hibernate/Spring/SpringMVC (or Spring-Boot) **without frontend**.

The task is:

Build a voting system for deciding where to have lunch.

 * 2 types of users: admin and regular users
 * Admin can input a restaurant and it's lunch menu of the day (2-5 items usually, just a dish name and price)
 * Menu changes each day (admins do the updates)
 * Users can vote on which restaurant they want to have lunch at
 * Only one vote counted per user
 * If user votes again the same day:
    - If it is before 11:00 we asume that he changed his mind.
    - If it is after 11:00 then it is too late, vote can't be changed

Each restaurant provides new menu each day.

As a result, provide a link to github repository.

It should contain the code and **README.md with API documentation and curl commands to get data for voting and vote.**

-----------------------------
P.S.: Make sure everything works with latest version that is on github :)

P.P.S.: Asume that your API will be used by a frontend developer to build frontend on top of that.

-----------------------------

# REST API

-----------------------------

## The REST API to the example app is described below.

-----------------------------

### New User Registration
#### Request
`POST /rest/register`

       curl -s -X POST -d '{"name": "registered","email": "reguser@yandex.ru","password": "passwordNew","roles": ["ROLE_USER"]}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/rest/register

#### Response
       HTTP/1.1 201 Created
       Server: Apache-Coyote/1.1
       Cache-Control: no-cache, no-store, max-age=0, must-revalidate
       Pragma: no-cache
       Expires: 0
       X-XSS-Protection: 1; mode=block
       X-Frame-Options: DENY
       X-Content-Type-Options: nosniff
       Location: http://localhost:8080/rest/100028
       Content-Type: application/json;charset=UTF-8
       Transfer-Encoding: chunked
       Date: Sun, 03 Jun 2018 12:25:23 GMT
       
       {"id":100028,"name":"registered","email":"reguser@yandex.ru","registered":"2018-06-03T12:25:22.404+0000","enabled":true,"roles":["ROLE_USER"]}

### get All Users
#### Request

`GET /rest/admin/users`

       curl -s http://localhost:8080/rest/admin/users --user admin@yandex.ru:admin

#### Response
       HTTP/1.1 200 OK
       Server: Apache-Coyote/1.1
       Cache-Control: no-cache, no-store, max-age=0, must-revalidate
       Pragma: no-cache
       Expires: 0
       X-XSS-Protection: 1; mode=block
       X-Frame-Options: DENY
       X-Content-Type-Options: nosniff
       Content-Type: application/json;charset=UTF-8
       Transfer-Encoding: chunked
       Date: Sun, 03 Jun 2018 12:42:51 GMT
       
       [{"id":100001,"name":"Admin","email":"admin@yandex.ru","registered":"2018-06-03T12:24:50.080+0000","enabled":true,"roles":["ROLE_ADMIN","ROLE_USER"],"votes":null},{"id":100000,"name":"User","email":"user@yandex.ru","registered":"2018-06-03T12:24:50.080+0000","enabled":true,"roles":["ROLE_USER"],"votes":null},{"id":100028,"name":"registered","email":"reguser@yandex.ru","registered":"2018-06-03T12:25:22.404+0000","enabled":true,"roles":["ROLE_USER"],"votes":null}]
