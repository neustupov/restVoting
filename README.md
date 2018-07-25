[![Build Status](https://travis-ci.org/neustupov/restVoting.svg?branch=master)](https://travis-ci.org/neustupov/restVoting)

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
       
### get User 100001    
#### Request

`GET /rest/admin/users/100001`

       curl -s http://localhost:8080/rest/admin/users/100001 --user admin@yandex.ru:admin

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
       Date: Sun, 03 Jun 2018 12:57:35 GMT
       
       {"id":100000,"name":"User","email":"user@yandex.ru","registered":"2018-06-03T12:51:32.915+0000","enabled":true,"roles":["ROLE_USER"],"votes":null}
       
### create User
#### Request

`POST /rest/admin/users`

       curl -s -X POST -d '{"name": "New2","email": "newuser@yandex.ru","password": "passwordNew","roles": ["ROLE_USER"]}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/rest/admin/users --user admin@yandex.ru:admin

#### Response
       HTTP/1.1 201 Created
       Server: Apache-Coyote/1.1
       Cache-Control: no-cache, no-store, max-age=0, must-revalidate
       Pragma: no-cache
       Expires: 0
       X-XSS-Protection: 1; mode=block
       X-Frame-Options: DENY
       X-Content-Type-Options: nosniff
       Location: http://localhost:8080/rest/admin/users/100028
       Content-Type: application/json;charset=UTF-8
       Transfer-Encoding: chunked
       Date: Sun, 03 Jun 2018 13:00:00 GMT
       
       {"id":100028,"name":"New2","email":"adf@yand1e1x.ru","registered":"2018-06-03T13:00:00.616+0000","enabled":true,"roles":["ROLE_USER"]}
       
### update User 100001
#### Request

`PUT /rest/admin/users/100001`

       curl -s -X PUT -d '{"name": "UserUpdated","email": "newuser1@yandex.ru","password": "passwordNew","roles": ["ROLE_USER"]}' -H 'Content-Type: application/json' http://localhost:8080/rest/admin/users/100001 --user admin@yandex.ru:admin

#### Response
       HTTP/1.1 200 OK
       Server: Apache-Coyote/1.1
       Cache-Control: no-cache, no-store, max-age=0, must-revalidate
       Pragma: no-cache
       Expires: 0
       X-XSS-Protection: 1; mode=block
       X-Frame-Options: DENY
       X-Content-Type-Options: nosniff
       Content-Length: 0
       Date: Sun, 03 Jun 2018 13:01:54 GMT

### validate with Error
#### Request

`POST /rest/admin/users`

       curl -s -X POST -d '{"name": "","email": "yandex.ru","password": "passwordNew","roles": ["ROLE_USER"]}' -H 'Content-Type: application/json' http://localhost:8080/rest/admin/users --user admin@yandex.ru:admin

#### Response
       HTTP/1.1 422 Unprocessable Entity
       Server: Apache-Coyote/1.1
       Cache-Control: no-cache, no-store, max-age=0, must-revalidate
       Pragma: no-cache
       Expires: 0
       X-XSS-Protection: 1; mode=block
       X-Frame-Options: DENY
       X-Content-Type-Options: nosniff
       Content-Type: application/json;charset=UTF-8
       Transfer-Encoding: chunked
       Date: Sun, 03 Jun 2018 13:05:22 GMT
       
       {"url":"http://localhost:8080/rest/admin/users","type":"VALIDATION_ERROR","details":["name размер должен быть между 2 и 100","email определен в неверном формате","name не может быть пусто"]}

### delete User 100000
#### Request

`DELETE /rest/admin/users/100000`

       curl -s -X DELETE http://localhost:8080/rest/admin/users/100000 --user admin@yandex.ru:admin
       
#### Response
       HTTP/1.1 204 No Content
       Server: Apache-Coyote/1.1
       Cache-Control: no-cache, no-store, max-age=0, must-revalidate
       Pragma: no-cache
       Expires: 0
       X-XSS-Protection: 1; mode=block
       X-Frame-Options: DENY
       X-Content-Type-Options: nosniff
       Date: Sun, 03 Jun 2018 13:08:55 GMT

### get Restaurant 100002
#### Request

`GET /rest/admin/restaurants/100002`

       curl -s http://localhost:8080/rest/admin/restaurants/100002 --user admin@yandex.ru:admin
       
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
       Date: Sun, 03 Jun 2018 13:11:14 GMT
       
       {"id":100002,"name":"Russia","menus":null,"votes":null}
       
### get All Restaurants
#### Request

`GET /rest/admin/restaurants`

       curl -s http://localhost:8080/rest/admin/restaurants --user admin@yandex.ru:admin
       
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
       Date: Sun, 03 Jun 2018 13:12:40 GMT
       
       [{"id":100002,"name":"Russia","numberOfVotes":1},{"id":100003,"name":"Ukraine","numberOfVotes":0},{"id":100004,"name":"U Kolyana","numberOfVotes":0},{"id":100005,"name":"Almaz","numberOfVotes":0},{"id":100006,"name":"Fart","numberOfVotes":0}]
       
### create Restaurant
#### Request

`POST /rest/admin/restaurants`

       curl -s -X POST -d '{"name": "New123"}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/rest/admin/restaurants --user admin@yandex.ru:admin
       
#### Response
       HTTP/1.1 201 Created
       Server: Apache-Coyote/1.1
       Cache-Control: no-cache, no-store, max-age=0, must-revalidate
       Pragma: no-cache
       Expires: 0
       X-XSS-Protection: 1; mode=block
       X-Frame-Options: DENY
       X-Content-Type-Options: nosniff
       Location: http://localhost:8080/rest/admin/restaurants/100029
       Content-Type: application/json;charset=UTF-8
       Transfer-Encoding: chunked
       Date: Sun, 03 Jun 2018 13:14:14 GMT
       
       {"id":100029,"name":"New123"}
       
### update Restaurant 100002
#### Request

`PUT /rest/admin/restaurants/100002`

       curl -s -X PUT -d '{"name": "New777"}' -H 'Content-Type: application/json' http://localhost:8080/rest/admin/restaurants/100002 --user admin@yandex.ru:admin
       
#### Response
       HTTP/1.1 200 OK
       Server: Apache-Coyote/1.1
       Cache-Control: no-cache, no-store, max-age=0, must-revalidate
       Pragma: no-cache
       Expires: 0
       X-XSS-Protection: 1; mode=block
       X-Frame-Options: DENY
       X-Content-Type-Options: nosniff
       Content-Length: 0
       Date: Sun, 03 Jun 2018 13:15:38 GMT

### delete Restaurant 100002
#### Request

`DELETE /rest/admin/restaurants/100002`

       curl -s -X DELETE http://localhost:8080/rest/admin/restaurants/100002 --user admin@yandex.ru:admin
       
#### Response
       HTTP/1.1 204 No Content
       Server: Apache-Coyote/1.1
       Cache-Control: no-cache, no-store, max-age=0, must-revalidate
       Pragma: no-cache
       Expires: 0
       X-XSS-Protection: 1; mode=block
       X-Frame-Options: DENY
       X-Content-Type-Options: nosniff
       Date: Sun, 03 Jun 2018 13:16:47 GMT

### get Menu 100007
#### Request

`GET /rest/admin/menus/100007`

       curl -s http://localhost:8080/rest/admin/menus/100007 --user admin@yandex.ru:admin
       
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
       Date: Sun, 03 Jun 2018 13:25:41 GMT
       
       {"id":100007,"addDate":"2015-05-01","meals":null,"restaurant":null}
       
### get All Menus for Restaurant 100002
#### Request

`GET /rest/admin/menus?restId=100002`

       curl -s http://localhost:8080/rest/admin/menus?restId=100002 --user admin@yandex.ru:admin
       
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
       Date: Sun, 03 Jun 2018 13:27:04 GMT
       
       [{"id":100007,"addDate":"2015-05-01","meals":null,"restaurant":null},{"id":100012,"addDate":"2015-05-02","meals":null,"restaurant":null},{"id":100013,"addDate":"2018-06-03","meals":null,"restaurant":null}]
       
### get todays Menu with meals for Restaurant 100002
#### Request

`GET /rest/admin/menus/todays?restId=100002`

       curl -s http://localhost:8080/rest/admin/menus/todays?restId=100002 --user admin@yandex.ru:admin
       
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
       Date: Sun, 03 Jun 2018 13:28:52 GMT
       
       {"id":100013,"addDate":"2018-06-03","meals":[{"id":100020,"name":"Mango","price":100}],"restaurant":null}
       
### create Menu
#### Request

`POST /rest/admin/menus?restId=100002`

       curl -s -X POST -d '{"addDate": "2015-06-01T10:00"}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/rest/admin/menus?restId=100002 --user admin@yandex.ru:admin
       
#### Response
       HTTP/1.1 201 Created
       Server: Apache-Coyote/1.1
       Cache-Control: no-cache, no-store, max-age=0, must-revalidate
       Pragma: no-cache
       Expires: 0
       X-XSS-Protection: 1; mode=block
       X-Frame-Options: DENY
       X-Content-Type-Options: nosniff
       Location: http://localhost:8080/rest/admin/menus/100028
       Content-Type: application/json;charset=UTF-8
       Transfer-Encoding: chunked
       Date: Sun, 03 Jun 2018 13:32:42 GMT
       
       {"id":100028,"addDate":"2015-06-01","restaurant":null}
       
### update Menu 100007
#### Request

`PUT /rest/admin/menus/100007?restId=100002`

       curl -s -X PUT -d '{"addDate": "2017-06-01T10:00"}' -H 'Content-Type: application/json' http://localhost:8080/rest/admin/menus/100007?restId=100002 --user admin@yandex.ru:admin
       
#### Response
       HTTP/1.1 200 OK
       Server: Apache-Coyote/1.1
       Cache-Control: no-cache, no-store, max-age=0, must-revalidate
       Pragma: no-cache
       Expires: 0
       X-XSS-Protection: 1; mode=block
       X-Frame-Options: DENY
       X-Content-Type-Options: nosniff
       Content-Length: 0
       Date: Sun, 03 Jun 2018 13:34:32 GMT

### delete Menu 100007
#### Request

`DELETE /rest/admin/menus/100007`

       curl -s -X DELETE http://localhost:8080/rest/admin/menus/100007 --user admin@yandex.ru:admin --user admin@yandex.ru:admin
       
#### Response
       HTTP/1.1 204 No Content
       Server: Apache-Coyote/1.1
       Cache-Control: no-cache, no-store, max-age=0, must-revalidate
       Pragma: no-cache
       Expires: 0
       X-XSS-Protection: 1; mode=block
       X-Frame-Options: DENY
       X-Content-Type-Options: nosniff
       Date: Sun, 03 Jun 2018 13:35:46 GMT

### get Menus between Dates for Restaurant 100002
#### Request

`GET /rest/admin/menus/filter?startDate=2015-05-01&endDate=2015-05-03&restId=100002`

       curl -s http://localhost:8080/rest/admin/menus/filter?startDate=2015-05-01&endDate=2015-05-03&restId=100002 --user admin@yandex.ru:admin
       
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
       Date: Sun, 03 Jun 2018 15:28:53 GMT
       
       [{"id":100012,"addDate":"2015-05-02","meals":[],"restaurant":null},{"id":100007,"addDate":"2015-05-01","meals":[{"id":100019,"name":"Bottle of water","price":50},{"id":100014,"name":"Apple","price":5}],"restaurant":null}]
       
### get Meal 100014
#### Request

`GET /rest/admin/meals/100014`

       curl -s http://localhost:8080/rest/admin/meals/100014 --user admin@yandex.ru:admin
              
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
       Date: Sun, 03 Jun 2018 15:33:46 GMT
       
       {"id":100014,"name":"Apple","price":5,"menu":null}
       
### get All Meals for Menu 100007
#### Request

`GET /rest/admin/meals?menuId=100007`

       curl -s http://localhost:8080/rest/admin/meals?menuId=100007 --user admin@yandex.ru:admin
              
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
       Date: Sun, 03 Jun 2018 15:35:35 GMT
       
       [{"id":100014,"name":"Apple","price":5,"menu":null},{"id":100019,"name":"Bottle of water","price":50,"menu":null}]
       
### create Meal
#### Request

`POST /rest/admin/meals?menuId=100007`

       curl -s -X POST -d '{"name": "New123","price":"123"}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/rest/admin/meals?menuId=100007 --user admin@yandex.ru:admin
              
#### Response
       HTTP/1.1 201 Created
       Server: Apache-Coyote/1.1
       Cache-Control: no-cache, no-store, max-age=0, must-revalidate
       Pragma: no-cache
       Expires: 0
       X-XSS-Protection: 1; mode=block
       X-Frame-Options: DENY
       X-Content-Type-Options: nosniff
       Location: http://localhost:8080/rest/admin/meals/100028
       Content-Type: application/json;charset=UTF-8
       Transfer-Encoding: chunked
       Date: Sun, 03 Jun 2018 15:37:04 GMT
       
       {"id":100028,"name":"New123","price":123,"menu":null}
       
### update Meal 100014
#### Request

`PUT /rest/admin/meals/100014?menuId=100007`

      curl -s -X PUT -d '{"name": "New777","price":"777"}' -H 'Content-Type: application/json' http://localhost:8080/rest/admin/meals/100014?menuId=100007 --user admin@yandex.ru:admin
              
#### Response
       HTTP/1.1 200 OK
       Server: Apache-Coyote/1.1
       Cache-Control: no-cache, no-store, max-age=0, must-revalidate
       Pragma: no-cache
       Expires: 0
       X-XSS-Protection: 1; mode=block
       X-Frame-Options: DENY
       X-Content-Type-Options: nosniff
       Content-Length: 0
       Date: Sun, 03 Jun 2018 15:39:50 GMT

### delete Menu 100007
#### Request

`DELETE /rest/admin/meals/100014?menuId=100007`

      curl -s -X DELETE http://localhost:8080/rest/admin/meals/100014?menuId=100007 --user admin@yandex.ru:admin
              
#### Response
       HTTP/1.1 204 No Content
       Server: Apache-Coyote/1.1
       Cache-Control: no-cache, no-store, max-age=0, must-revalidate
       Pragma: no-cache
       Expires: 0
       X-XSS-Protection: 1; mode=block
       X-Frame-Options: DENY
       X-Content-Type-Options: nosniff
       Date: Sun, 03 Jun 2018 15:41:10 GMT

### validate with Error
#### Request

`PUT /rest/admin/meals/100014?menuId=100007`

      curl -s -X PUT -d '{"name": "","price":""}' -H 'Content-Type: application/json' http://localhost:8080/rest/admin/meals/100014?menuId=100007 --user admin@yandex.ru:admin
              
#### Response
       HTTP/1.1 422 Unprocessable Entity
       Server: Apache-Coyote/1.1
       Cache-Control: no-cache, no-store, max-age=0, must-revalidate
       Pragma: no-cache
       Expires: 0
       X-XSS-Protection: 1; mode=block
       X-Frame-Options: DENY
       X-Content-Type-Options: nosniff
       Content-Type: application/json;charset=UTF-8
       Transfer-Encoding: chunked
       Date: Sun, 03 Jun 2018 15:43:23 GMT

       {"url":"http://localhost:8080/rest/admin/meals/100014","type":"VALIDATION_ERROR","details":["name размер должен быть между 2 и 100","name не может быть пусто","price должно быть задано"]}
       
### get Vote 100021
#### Request

`GET /rest/admin/votes/100024`

      curl -s http://localhost:8080/rest/admin/votes/100024 --user admin@yandex.ru:admin
              
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
       Date: Sun, 03 Jun 2018 15:46:25 GMT
       
       {"id":100024,"user":{"id":100001,"name":"Admin","email":"admin@yandex.ru","registered":"2018-06-03T15:18:45.099+0000","enabled":true,"roles":["ROLE_USER","ROLE_ADMIN"],"votes":null},"date":"2015-04-30T21:00:00.000+0000","restaurant":{"id":100005,"name":"Almaz","menus":null,"votes":null}}
       
### get All Votes for all
#### Request

`GET /rest/admin/votes`

      curl -s http://localhost:8080/rest/admin/votes --user admin@yandex.ru:admin
              
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
       Date: Sun, 03 Jun 2018 15:47:57 GMT
       
       [{"id":100021,"user":null,"date":"2015-04-30T21:00:00.000+0000","restaurant":{"id":100002,"name":"Russia","menus":null,"votes":null}},{"id":100022,"user":null,"date":"2015-05-01T21:00:00.000+0000","restaurant":{"id":100003,"name":"Ukraine","menus":null,"votes":null}},{"id":100023,"user":null,"date":"2015-05-02T21:00:00.000+0000","restaurant":{"id":100004,"name":"U Kolyana","menus":null,"votes":null}},{"id":100024,"user":null,"date":"2015-04-30T21:00:00.000+0000","restaurant":{"id":100005,"name":"Almaz","menus":null,"votes":null}},{"id":100025,"user":null,"date":"2015-05-01T21:00:00.000+0000","restaurant":{"id":100006,"name":"Fart","menus":null,"votes":null}},{"id":100026,"user":null,"date":"2015-05-02T21:00:00.000+0000","restaurant":{"id":100002,"name":"Russia","menus":null,"votes":null}},{"id":100027,"user":null,"date":"2018-06-02T21:00:00.000+0000","restaurant":{"id":100002,"name":"Russia","menus":null,"votes":null}}]
       
### create Vote for Restaurant 100002 and ADMIN
#### Request

`POST /rest/admin/votes?restId=100002`

      curl -s -X POST -d '{"date": "2018-04-10"}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/rest/admin/votes?restId=100002 --user admin@yandex.ru:admin
              
#### Response
       HTTP/1.1 201 Created
       Server: Apache-Coyote/1.1
       Cache-Control: no-cache, no-store, max-age=0, must-revalidate
       Pragma: no-cache
       Expires: 0
       X-XSS-Protection: 1; mode=block
       X-Frame-Options: DENY
       X-Content-Type-Options: nosniff
       Location: http://localhost:8080/rest/admin/votes/100029
       Content-Type: application/json;charset=UTF-8
       Transfer-Encoding: chunked
       Date: Sun, 03 Jun 2018 15:53:48 GMT
       
       {"id":100029,"user":null,"date":"2018-04-10T00:00:00.000+0000","restaurant":null}
       
### create Vote for Restaurant 100003 and USER
#### Request
       
`POST /rest/profile/votes?restId=100003`
       
        curl -s -X POST -d '{"date": "2018-05-10"}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/rest/profile/votes?restId=100003 --user user@yandex.ru:password
                     
#### Response
        HTTP/1.1 201 Created
              Server: Apache-Coyote/1.1
              Cache-Control: no-cache, no-store, max-age=0, must-revalidate
              Pragma: no-cache
              Expires: 0
              X-XSS-Protection: 1; mode=block
              X-Frame-Options: DENY
              X-Content-Type-Options: nosniff
              Location: http://localhost:8080/rest/admin/votes/100029
              Content-Type: application/json;charset=UTF-8
              Transfer-Encoding: chunked
              Date: Sun, 03 Jun 2018 15:53:48 GMT
              
              {"id":100029,"user":null,"date":"2018-04-10T00:00:00.000+0000","restaurant":null}
              
       
### update Vote 100021 - Exception (update is after StopTime for voting)
#### Request

`PUT /rest/admin/votes/100021?restId=100003`

      curl -s -X PUT -d '{"date": "2018-04-10"}' -H 'Content-Type: application/json' http://localhost:8080/rest/admin/votes/100021?restId=100003 --user admin@yandex.ru:admin
                          
#### Response
       HTTP/1.1 500 Internal Server Error
       Server: Apache-Coyote/1.1
       Cache-Control: no-cache, no-store, max-age=0, must-revalidate
       Pragma: no-cache
       Expires: 0
       X-XSS-Protection: 1; mode=block
       X-Frame-Options: DENY
       X-Content-Type-Options: nosniff
       Content-Type: application/json;charset=UTF-8
       Transfer-Encoding: chunked
       Date: Sun, 03 Jun 2018 15:58:53 GMT
       Connection: close

       {"url":"http://localhost:8080/rest/admin/votes/100021","type":"APP_ERROR","details":["java.time.DateTimeException: time is after Stop Time"]}

### delete Vote 100021
#### Request

`DELETE /rest/admin/votes/100024`

      curl -s -X DELETE http://localhost:8080/rest/admin/votes/100024 --user admin@yandex.ru:admin
              
#### Response
       HTTP/1.1 204 No Content
       Server: Apache-Coyote/1.1
       Cache-Control: no-cache, no-store, max-age=0, must-revalidate
       Pragma: no-cache
       Expires: 0
       X-XSS-Protection: 1; mode=block
       X-Frame-Options: DENY
       X-Content-Type-Options: nosniff
       Date: Sun, 03 Jun 2018 16:11:01 GMT

