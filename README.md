1. Skapa network:
docker network create storenetwork

2. Skapa databas:
docker run --name storeapi-item-db -p 3306:3306 -d -e MYSQL_DATABASE=itemdb -e MYSQL_USER=itemuser -e MYSQL_PASSWORD=ThePassword -e MYSQL_ROOT_PASSWORD=TheRoot --mount type=bind,source="${PWD}"/sql,target=/docker-entrypoint-initdb.d --network=storenetwork mysql

3. $Env:DB_PASSWORD="ThePassword"
   .\gradlew bootRun
   java -jar build/libs/storeapi_item.jar

4. Skapa image:
docker build --no-cache --build-arg DB_PASSWORD=ThePassword -t storeapi-db-item .

5. Skapa applikation:
docker run -d --name storeapi-item-app -p 8082:8080 --network=storenetwork -e MYSQL_HOST=storeapi-item-db -e DB_DATABASE=itemdb -e DB_USER=itemuser -e DB_PASSWORD=ThePassword storeapi-db-item