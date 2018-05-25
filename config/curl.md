### Test AdminRestController (application deployed in application context `votingforrestaurant`).
> For windows use `Git Bash`

#### get All Users
`curl -s http://localhost:8080/rest/admin/users`

#### get Users 100001
`curl -s http://localhost:8080/rest/admin/users/100001`

#### create User
`curl -s -X POST -d '{"name": "New2","password": "passwordNew","roles": ["ROLE_USER"]}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/rest/admin/users`

#### delete User 100000
`curl -s -X DELETE http://localhost:8080/rest/admin/users/100000`

#### update User 100001
`curl -s -X PUT -d '{"name": "UserUpdated","password": "passwordNew","roles": ["ROLE_USER"]}' -H 'Content-Type: application/json' http://localhost:8080/rest/admin/users/100001`

### Test ProfileRestController

#### get Profile
`curl -s http://localhost:8080/rest/profile`

#### delete Profile
`curl -s -X DELETE http://localhost:8080/rest/profile`

#### update Profile
`curl -s -X PUT -d '{"name": "UserUpdated","password": "passwordNew","roles": ["ROLE_USER"]}' -H 'Content-Type: application/json' http://localhost:8080/rest/profile`

### Test AdminRestaurantRestController

#### get Restaurant 100002
`curl -s http://localhost:8080/rest/admin/restaurants/100002`

#### get All Restaurants
`curl -s http://localhost:8080/rest/admin/restaurants`

#### create Restaurant
`curl -s -X POST -d '{"name": "New123"}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/rest/admin/restaurants`

#### update Restaurant 100002
`curl -s -X PUT -d '{"name": "New777"}' -H 'Content-Type: application/json' http://localhost:8080/rest/admin/restaurants/100002`

#### delete Restaurant 100002
`curl -s -X DELETE http://localhost:8080/rest/admin/restaurants/100002`

### Test ProfileRestaurantRestController

#### get Restaurant 100002
`curl -s http://localhost:8080/rest/profile/restaurants/100002`

#### get All Restaurants
`curl -s http://localhost:8080/rest/profile/restaurants`

### Test AdminMenuRestController

#### get Menu 100007
`curl -s http://localhost:8080/rest/admin/menus/100007?restId=100002`

#### get All Menus for Restaurant 100002
`curl -s http://localhost:8080/rest/admin/menus?restId=100002`

#### delete Menu 100007
`curl -s -X DELETE http://localhost:8080/rest/admin/menus/100007?restId=100002`

#### get todays Menu with meals for Restaurant 100002
`curl -s http://localhost:8080/rest/admin/menus/todays?restId=100002`

#### create Menu
`curl -s -X POST -d '{"addDate": "2015-06-01T10:00"}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/rest/admin/menus?restId=100002`

#### update Menu 100007
`curl -s -X PUT -d '{"addDate": "2017-06-01T10:00"}' -H 'Content-Type: application/json' http://localhost:8080/rest/admin/menus/100007?restId=100002`

### Test ProfileMenuRestController

#### get Menu 100007
`curl -s http://localhost:8080/rest/profile/menus/100007?restId=100002`

#### get All Menus for Restaurant 100002
`curl -s http://localhost:8080/rest/profile/menus?restId=100002`

#### get todays Menu with meals for Restaurant 100002
`curl -s http://localhost:8080/rest/profile/menus/todays?restId=100002`

### Test AdminMealRestController

#### get Meal 100014
`curl -s http://localhost:8080/rest/admin/meals/100014?menuId=100007`

#### get All Meals for Menu 100007
`curl -s http://localhost:8080/rest/admin/meals?menuId=100007`

#### create Meal
`curl -s -X POST -d '{"name": "New123","price":"123"}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/rest/admin/meals?menuId=100007`

#### update Meal 100014
`curl -s -X PUT -d '{"name": "New777","price":"777"}' -H 'Content-Type: application/json' http://localhost:8080/rest/admin/meals/100014?menuId=100007`

#### delete Menu 100007
`curl -s -X DELETE http://localhost:8080/rest/admin/meals/100014?menuId=100007`

### Test ProfileMealRestController

#### get Meal 100014
`curl -s http://localhost:8080/rest/profile/meals/100014?menuId=100007`

#### get All Meals for Menu 100007
`curl -s http://localhost:8080/rest/profile/meals?menuId=100007`

### Test AdminVoteRestController

#### get Vote 100021
`curl -s http://localhost:8080/rest/admin/votes/100021`

#### get All Votes for all
`curl -s http://localhost:8080/rest/admin/votes`

#### create Vote for Restaurant 100002 and ADMIN
`curl -s -X POST -d '{"user":{"id":100001,"name": "Admin","password": "password",
"registered":"2018-04-10T19:07:31.749+0000","roles": ["ROLE_ADMIN"],"votes": null},"date": "2018-04-10","restaurant": 
{"id": 100002,"name": "Russia","menus": null,"votes": null}}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/rest/admin/votes?restId=100002`

#### update Vote 100021
`curl -s -X PUT -d '{"user":{"id":100001,"name": "Admin","password": "password",
                    "registered":"2018-04-10T19:07:31.749+0000","roles": ["ROLE_ADMIN"],"votes": null},"date": "2017-04-10","restaurant": 
                    {"id": 100003,"name": "Ukraine","menus": null,"votes": null}}' -H 'Content-Type: application/json' http://localhost:8080/rest/admin/votes/100021?restId=100003`

#### update Vote 100021 - Exception
`curl -s -X PUT -d '{"user":{"id":100001,"name": "Admin","password": "password",
                    "registered":"2018-04-10T19:07:31.749+0000","roles": ["ROLE_ADMIN"],"votes": null},"date": "2018-04-10","restaurant": 
                    {"id": 100003,"name": "Ukraine","menus": null,"votes": null}}' -H 'Content-Type: application/json' http://localhost:8080/rest/admin/votes/100021?restId=100003`

#### delete Vote 100021
`curl -s -X DELETE http://localhost:8080/rest/admin/votes/100021`

### Test ProfileVoteRestController

#### create Vote for Restaurant 100002 and USER
`curl -s -X POST -d '{"user":{"id":100000,"name": "User","password": "password",
"registered":"2018-04-10T19:07:31.749+0000","roles": ["ROLE_USER"],"votes": null},"date": "2018-04-10","restaurant": 
{"id": 100002,"name": "Russia","menus": null,"votes": null}}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/rest/admin/votes?restId=100002`
