# Cake Manager version 0.0.1

# Improvements made :

1. Grade Build Introduced
2. Application upgraded to spring-boot
3. Swagger page add to test operations
4. Application split to layers & code clean up(controller,service,repo)
5. Unit tests added at every layer level
6. [Circle CI](https://app.circleci.com/pipelines/github/santoshreddy17/cake-manager) - Build system added to github
   repo
7. Docker Image implemented for containerisation

NOTE : Cirlce CI might not be accessible due to security. Happy to show it during discussions.

## Further Possible Enhancements

1. Authentication & Authorization
2. Validation of inputs
3. More feedback to user for validation exceptions

# Running Instructions :

1. From IDE : Run CakeManagerApplication
2. From Command line :
   _run gradle build_
   _java -jar build/libs/cake-manager-0.0.1_
3. Using Docker :
   Prerequisite : Docker installed locally
   on root of the project run:
   _docker build -f Dockerfile -t cake-manager ._
   _docker run -p 8080:8080 cake-manager_

# Test application

1. Using swagger : http://localhost:8080/swagger-ui/index.html
2. Using curl

## GET

`
curl -X 'GET' \
'http://localhost:8080/cakes' \
-H 'accept: */*
`

## PUT

`
curl -X 'PUT' \
'http://localhost:8080/cakes/new%20cake' \
-H 'accept: */*' \
-H 'Content-Type: application/json' \
-d '{
"description": "new cake desc1",
"imageLink": "newcakeimage1"
}'
`

## POST

`
curl -X 'POST' \
'http://localhost:8080/cakes' \
-H 'accept: */*' \
-H 'Content-Type: application/json' \
-d '{
"title": "new cake",
"desc": "new cake",
"image": "url"
}'
`

## DELETE

`
curl -X 'DELETE' \
'http://localhost:8080/cakes' \
-H 'accept: */*' \
-H 'Content-Type: application/json' \
-d '{
"title": "new cake2",
"desc": "new cake2",
"image": "newcake2url"
}'
`





