# Harvest API

This repo showcase API development using Spring-Boot Technology stack using pure Kotlin.
Currently, the APIs are in WIP and being used in this [HarvestKMM]("https://github.com/mutualmobile/HarvestTimeKMP)
project.

**Servers**

| Name       | URL                                            | Description                                                                       |
|:-----------|:-----------------------------------------------|:----------------------------------------------------------------------------------|
| Production | WIP                                            | The public API server                                                             |
| Staging    | https://harvestkmp.mmharvest.com/api/v1/public | The master branch automatically deploys to the staging server after every commit. |

# Contents

- [Getting Started](#-getting-started)
- [Spring Framework](#-spring-frameworks-used)
- [Prerequisite](#-prerequisite)
- [TODO](#-todo)
- [API References](#-api-references)

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

- [Auth Api](#auth-api)
- [Login User](#-login-user)
- [Get Organization]
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

### Auth Api
- [Login User](#-login-user)
- [Register User / SignUp User](#-register-user)
- [Logout user](#-logout-user)
- [Change Password](#-change-password)
- [Get User](#-get-user)
- [Update User](#-update-user)
- [Fcm Token](#-fcm-token)
- [Refresh Token](#-refresh-token)


### Login User
--------------

```HTTP
POST BASE_URL/api/v1/public/login
```
**Request Body**

| param    | type     | Description                             |
|:---------|:---------|:----------------------------------------|
| Email    | `String` | Users official email id.                |
| Password | `String` | Password, which user set while sign up. |

**Response**

1. When -> 200 OK
```ts
{
  "token": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhNTA1MjYwZi03YmZhLTRiZDUtODBjZS04MDBmOGE0M2IzZmMiLCJpYXQiOjE2NTM5Mzg2NzUsImV4cCI6MTY1Mzk0MjI3NX0.1CydFVIwoWq4gaqUhhEBy9XpQVaed-XW0s9qn0uFkUIg4h3WXiQkZrYUqULU0ZxeFMX1jfEMqO9FtTwt3zD5Zw",
  "message": "User logged in Successfully",
  "refreshToken": "958ede86-163c-4e80-8d8f-ed81ff1d7421"
}
```

| param        | type     | Description                                                                           |
|:-------------|:---------|:--------------------------------------------------------------------------------------|
| token        | `String` | JWT Token for security purpose. This get generated once users Logged In Successfully. |
| message      | `String` | Returns info for every type of request.                                               |
| refreshToken | `String` | Unique Token for each user                                                            |

2. When -> 400 BAD REQUEST
```ts
{
  "message": "ERROR"
}
```

| param        | type     | Description                                                                           |
|:-------------|:---------|:--------------------------------------------------------------------------------------|
| message      | `String` | Returns info for every type of request.                                               |


### Register User
---------------------------

```HTTP
POST BASE_URL/api/v1/public/signup
```

**Request Body**

1. For New Organization User SignUp ->
```ts
{
    "email":"yugesh@mutualmobile.com",
    "password":"password",
    "firstName":"Yugesh",
    "lastName":"Jain",
    "harvestOrganization":{
        "name":"mm",
        "website":"mm.com",
        "identifier":"com.mm.org"
    }
}
```
| param               | type                  | Description                             |
|:--------------------|:----------------------|:----------------------------------------|
| email               | `String`              | Users official email id.                |
| password            | `String`              | Password, which user set while sign up. |
| firstName           | `String`              | First Name of the user Signing Up       |
| lastName            | `String`              | Last Name of the user Signing Up        |
| harvestOrganization | `HarvestOrganization` | Organization Details                    |
| name                | `String`              | User's Organization Name                |
| website             | `String`              | Web Address for the Organization        |
| identifier          | `String`              | Unique Identifier for the Organization  |


2. For Existing Organization User SignUp ->
```ts
{
    "email":"yugesh@mutualmobile.com",
    "password":"password",
    "firstName":"Yugesh",
    "lastName":"Jain",
    "orgId": "151d11a3-780d-4f03-bbad-889bd3707b02",
    "role": String,
}
```

| param     | type                  | Description                                             |
|:----------|:----------------------|:--------------------------------------------------------|
| email     | `String`              | Users official email id.                                |
| password  | `String`              | Password, which user set while sign up.                 |
| firstName | `String`              | First Name of the user Signing Up                       |
| lastName  | `String`              | Last Name of the user Signing Up                        |
| orgId     | `String`              | Auto Generated Unique ID assigned to the Organization   |
| role      | `String`              | Role of user in the organization like Admin or employee |


**Response**

1. When -> 200 OK : Registration Successful! Please verify your email before getting started!
```ts
{
   "message": "Registration Successful! Please verify your email before getting started!",
   "data": {
      "id": "f8296e81-6d5b-4a5e-9de4-9d60459a1997",
      "firstName": "Yugesh",
      "lastName": "Jain",
      "email": "yugesh@mutualmobile.com",
      "modifiedTime": "Sat Jun 04 12:03:52 UTC 2022",
      "orgId": "151d11a3-780d-4f03-bbad-889bd3707b02",
      "harvestOrganization": {
         "name": "mmt",
         "website": "mmt.com",
         "id": "151d11a3-780d-4f03-bbad-889bd3707b02",
         "identifier": "com.mmt.org"
      }
   }
}
```
| param               | type                  | Description                                           |
|:--------------------|:----------------------|:------------------------------------------------------|
| message             | `String`              | Returns info for every type of request.               |
| data                | `Data`                | Details of the User Just Signed Up                    |
| id                  | `String`              | Auto Generated Unique ID assigned to a User           |
| firstName           | `String`              | First Name of the User Just Signed Up                 |
| lastName            | `String`              | Last Name of the User Just Signed Up                  |
| email               | `String`              | Email of the User Just Signed Up                      |
| modifiedTime        | `String`              | Time of successful Signup                             |
| orgId               | `String`              | Auto Generated Unique ID assigned to the Organization |
| harvestOrganization | `HarvestOrganization` | Organization Details                                  |
| name                | `String`              | User's Organization Name                              |
| website             | `String`              | Auto Generated Unique ID assigned to the Organization |
| id                  | `String`              | Returns info for every type of request.               |
| identifier          | `String`              | Unique Identifier for the Organization                |

2. When -> 400 BAD REQUEST
```ts
{
  "message": "ERROR"
}
```

| param        | type     | Description                                                                           |
|:-------------|:---------|:--------------------------------------------------------------------------------------|
| message      | `String` | Returns info for every type of request.                                               |


### Logout User
---------------------------
```HTTP
POST BASE_URL/api/v1/logout
```
**Request Body**
```ts
{
    "userId": String
}
```

**Authorization**
```ts
   BearerToekn: String
```

| param        | type                  | Description                                                                           |
|:-------------|:----------------------|:--------------------------------------------------------------------------------------|
| userId       | `String`              | Auto Generated Unique ID assigned to a User                                           |
| Bearer Token | `String`              | JWT Token for security purpose. This get generated once users Logged In Successfully. |


**Response**
1. When -> 200 OK : Registration Successful! Please verify your email before getting started!
```ts
{
   "message": "Logged Out Successfully!!"
}
```
| param               | type                  | Description                                           |
|:--------------------|:----------------------|:------------------------------------------------------|
| message             | `String`              | Returns info for every type of request.               |

2. When -> 400 BAD REQUEST
```ts
{
  "message": "ERROR"
}
```

| param        | type     | Description                                                                           |
|:-------------|:---------|:--------------------------------------------------------------------------------------|
| message      | `String` | Returns info for every type of request.                                               |


### Change Password
---------------------------
```HTTP
POST BASE_URL/api/v1/logout
```
**Request Body**
```ts
{
    "password": String,
    "oldPassword": String
}
```

**Authorization**
```ts
   BearerToekn: String
```

| param        | type                  | Description                                                                           |
|:-------------|:----------------------|:--------------------------------------------------------------------------------------|
| password     | `String`              | Existing Password of the account Logged in                                            |
| oldPassword  | `String`              | New Password that we want to set for the account Logged in                            |
| Bearer Token | `String`              | JWT Token for security purpose. This get generated once users Logged In Successfully. |


**Response**
1. When -> 200 OK : Password Changed
```ts
{
   "message": String
}
```
| param               | type                  | Description                                           |
|:--------------------|:----------------------|:------------------------------------------------------|
| message             | `String`              | Returns info for every type of request.               |

2. When -> 400 BAD REQUEST
```ts
{
  "message": "ERROR"
}
```

| param        | type     | Description                                                                           |
|:-------------|:---------|:--------------------------------------------------------------------------------------|
| message      | `String` | Returns info for every type of request.                                               |

### Get User
---------------------------

### Update User
---------------------------

### Fcm Token
---------------------------

### Refresh Token
---------------------------
