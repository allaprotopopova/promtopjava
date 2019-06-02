### curl samples (application deployed in application context `promtopjava`).
> For windows use `Git Bash`

#### get All Users
`curl -s http://localhost:8080/promtopjava/rest/admin/users --user admin@gmail.com:admin`

#### get Users 100001
`curl -s http://localhost:8080/promtopjava/rest/admin/users/100001 --user admin@gmail.com:admin`

#### get Users alla@yandex.ru
`curl -s http://localhost:8080/promtopjava/rest/admin/users/by?email="alla@yandex.ru" --user admin@gmail.com:admin`

#### delete Users
`curl -s -X DELETE http://localhost:8080/promtopjava/rest/admin/users/100001 --user admin@gmail.com:admin`

#### create Users
`curl -s -X POST -d '{"name":"New User","email":"email@gmail.com","password":"password", "enabled":true, "roles":["ROLE_USER"]}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/promtopjava/rest/admin/users --user admin@gmail.com:admin`

#### update Users
`curl -s -X PUT -d '{"id":100001,"name":"Artemis1","email":"artemis@yandex.ru","password":"{noop}password","enabled":true,"roles":["ROLE_USER"]}' -H 'Content-Type: application/json' http://localhost:8080/promtopjava/rest/admin/users/100001 --user admin@gmail.com:admin`

#### get Alla Restaurants
`curl -s http://localhost:8080/promtopjava/rest/admin/restaurants --user admin@gmail.com:admin`

#### get Alla Restaurants 1
`curl -s http://localhost:8080/promtopjava/rest/admin/restaurants/1 --user admin@gmail.com:admin`

#### delete Restaurants
`curl -s -X DELETE http://localhost:8080/promtopjava/rest/admin/restaurants/3 --user admin@gmail.com:admin`

#### create Restaurants
`curl -s -X POST -d '{"name":"Галеон","address":"улица Арбат 22/3"}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/promtopjava/rest/admin/restaurants --user admin@gmail.com:admin`

#### create Menu
`curl -s -X POST -d '{"dishes":[{"id":6,"name":"Гренки","price":10.0},{"id":7,"name":"Суп-лапша куриный","price":65.0},{"id":8,"name":"Котлеты по-Киевски","price":135.5}]}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/promtopjava/rest/admin/menu?restaurantId=1 --user admin@gmail.com:admin`

#### get Menu by Date
`curl -s http://localhost:8080/promtopjava/rest/user/menu/date/2019-04-30 --user alla@yandex.ru:password`

#### get Menu by RestaurantId
`curl -s http://localhost:8080/promtopjava/rest/user/menu/restaurant/1 --user alla@yandex.ru:password`

#### get Menu by Date and RestaurantId
`curl -s http://localhost:8080/promtopjava/rest/user/menu/by?restaurantId=1&date=2019-04-30 --user alla@yandex.ru:password`

#### get Votes
`curl -s http://localhost:8080/promtopjava/rest/user/votes?date=2019-04-30&id=1 --user alla@yandex.ru:password`







