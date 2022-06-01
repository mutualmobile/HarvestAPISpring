# Harvest API 
This repo showcase API development using Spring-Boot Technology stack using pure Kotlin.
Currently, the API's are in WIP and being used in this [HarvestKMM]("https://github.com/mutualmobile/HarvestTimeKMP) project. 

**Servers**

| Name       | URL                 | Description                                                                                                                                                                                                        |
| :--------- | :------------------ | :----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| Production | WIP    | The public API server                                                                                                                                                                                              |
| Staging    | https://harvestkmp.mmharvest.com/api/v1/public | The master branch automatically deploys to the staging server after every commit. |

# Contents 
- [Getting Started](#-getting-started)
- [Spring Framework](#-spring-frameworks-used)
- [Prerequisite](#-prerequisite)
- [TODO](#-todo)
- [API References](#api-references)

## Getting started
-----------
1. Clone this project
2. Run following command
   ```sh
   mvn install
   ```
3. Run project and Enjoy!
   ```sh
   mvn spring-boot:run
   ```
   
## Spring Frameworks used
----------
1. [Spring Boot](https://projects.spring.io/spring-boot/)
2. [Spring Boot Actuator](https://spring.io/guides/gs/actuator-service/)
3. [Spring Data JPA](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/)
4. [Spring Data REST](https://projects.spring.io/spring-data-rest/)
5. [Hibernate](http://hibernate.org/)
6. [Spring REST Docs](https://projects.spring.io/spring-restdocs/)


## Prerequisite
-------------
1. Kotlin
2. H2 DB
3. Maven

## TODO
--------------

# API References
--------------------- 
- [Login User](#-login-user)
- Get Organization 
- Sign In 
- Sign Up 
- Create Organization 
- Update Organization 
- Read Organization 
- Delete Organization 
- Assign Users 
- Assign Project to Users 
- Time Recording Start/Stop
- Delete User 

### Login User 
--------------
```HTTP
POST BASE_URL/login
```

200 OK : LogIn the user, Successfully. 

**Request Body**

| param     | type     | Description                                                                                                                                                                                                                                                                                                                          |
| :-------- | :------- | :----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| Email  | `String`    | Users official email id.                                                                                                                                                                                                                                                              |
| Password | `String`    | Password, which user set while sign up.                                                                                                                                                                                                                                                               |


** Response ** 

```ts
{
  "token": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhNTA1MjYwZi03YmZhLTRiZDUtODBjZS04MDBmOGE0M2IzZmMiLCJpYXQiOjE2NTM5Mzg2NzUsImV4cCI6MTY1Mzk0MjI3NX0.1CydFVIwoWq4gaqUhhEBy9XpQVaed-XW0s9qn0uFkUIg4h3WXiQkZrYUqULU0ZxeFMX1jfEMqO9FtTwt3zD5Zw",
  "message": "User logged in Successfully",
  "refreshToken": "958ede86-163c-4e80-8d8f-ed81ff1d7421"
}
```

| param     | type     | Description                                                                                                                                                                                                                                                                                                                          |
| :-------- | :------- | :----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| token | `String`    | JWT Token for security purpose. This get generated once users Logged In Successfully.                                                                                                                                                                                                                                                                 |
| message | `String`    | Returns info for every type of request.                                                                                                                                                                                                                                                        |
| refreshToken  | `String` | Unique Token for each user                                                                                                                                                                                                                                       |  |


## Generate Organization 
---------------------------
