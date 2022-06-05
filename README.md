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
- [Forgot Password Api](#forgot-password-api)
- [Organization Api](#organization-api)
- [Organization Projects Api](#organization-projects-api)
- [Organization Users Api](#organization-users-api)
- [User project Api](#user-project-api)
- [User Work Api](#user-work-api)

### Auth Api

- [Login User](#-login-user)
- [Register User / SignUp User](#-register-user)
- [Logout user](#-logout-user)
- [Change Password](#-change-password)
- [Get User](#-get-user)
- [Update User](#-update-user)
- [Fcm Token](#-fcm-token)
- [Refresh Token](#-refresh-token)

--------------
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

--------------
### Register User
--------------

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

| param     | type                  | Description                                                    |
|:----------|:----------------------|:---------------------------------------------------------------|
| email     | `String`              | Users official email id.                                       |
| password  | `String`              | Password, which user set while sign up.                        |
| firstName | `String`              | First Name of the user Signing Up                              |
| lastName  | `String`              | Last Name of the user Signing Up                               |
| orgId     | `String`              | Auto Generated Unique ID assigned to the Organization          |
| role      | `String`              | Role of user in the organization like Admin or normal employee |

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
         "name": "mm",
         "website": "mm.com",
         "id": "151d11a3-780d-4f03-bbad-889bd3707b02",
         "identifier": "com.mm.org"
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

--------------
### Logout User
--------------

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
   BearerToken: String
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

--------------
### Change Password
--------------

```HTTP
POST BASE_URL/api/v1/changePassword
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
   BearerToken: String
```

| param        | type     | Description                                                                           |
|:-------------|:---------|:--------------------------------------------------------------------------------------|
| password     | `String` | New Password that we want to set for the account Logged in                            |
| oldPassword  | `String` | Existing Password of the account Logged in                                            |
| Bearer Token | `String` | JWT Token for security purpose. This get generated once users Logged In Successfully. |

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

--------------
### Get User
--------------

```HTTP
GET BASE_URL/api/v1/user
```

**Request Body**

```ts
   No Body
```

**Authorization**

```ts
   BearerToken: String
```

| param        | type                  | Description                                                                           |
|:-------------|:----------------------|:--------------------------------------------------------------------------------------|
| Bearer Token | `String`              | JWT Token for security purpose. This get generated once users Logged In Successfully. |

**Response**

1. When -> 200 OK : Password Changed

```ts
{
   "message": String,
   "data":{
      "email": String,
      "firstName": String,
      "id": String,
      "lastName": String,
      "modifiedTime": String,
      "orgId": String,
      "role": String
   }   
}
```

| param        | type                  | Description                                           |
|:-------------|:----------------------|:------------------------------------------------------|
| message      | `String`              | Returns info for every type of request.               |
| data         | `Data`                | Details of the User Just Signed Up                    |
| id           | `String`              | Auto Generated Unique ID assigned to a User           |
| firstName    | `String`              | First Name of the User Just Signed Up                 |
| lastName     | `String`              | Last Name of the User Just Signed Up                  |
| email        | `String`              | Email of the User Just Signed Up                      |
| modifiedTime | `String`              | Time of successful Signup                             |
| orgId        | `String`              | Auto Generated Unique ID assigned to the Organization |
| role         | `String`              | Role of the user in the Organization                  |

2. When -> 400 BAD REQUEST

```ts
{
  "message": "ERROR"
}
```

| param        | type     | Description                                                                           |
|:-------------|:---------|:--------------------------------------------------------------------------------------|
| message      | `String` | Returns info for every type of request.                                               |

--------------
### Update User
--------------

```HTTP
PUT BASE_URL/api/v1/user
```

**Request Body**

```ts
{
   "id": "f8296e81-6d5b-4a5e-9de4-9d60459a1997",
   "firstName": "Yugesh",
   "lastName": "Jain",
   "email": "yugesh@mutualmobile.com",
   "orgId": "151d11a3-780d-4f03-bbad-889bd3707b02",
   "role": String,
   "pushToken": String,
   "profilePic": String,
   "harvestOrganization": {
      "name": "mm",
      "website": "mm.com",
      "id": "151d11a3-780d-4f03-bbad-889bd3707b02",
      "identifier": "com.mm.org"
   }
}]
```

**Authorization**

```ts
   BearerToken: String
```

| param               | type                  | Description                                                                           |
|:--------------------|:----------------------|:--------------------------------------------------------------------------------------|
| id                  | `String`              | Auto Generated Unique ID assigned to a User                                           |
| firstName           | `String`              | First Name of the User Just Signed Up                                                 |
| lastName            | `String`              | Last Name of the User Just Signed Up                                                  |
| email               | `String`              | Email of the User Just Signed Up                                                      |
| orgId               | `String`              | Auto Generated Unique ID assigned to the Organization                                 |
| role                | `String`              | Role of user in the organization like Admin or normal employee                        |
| pushToken           | `String`              | Auto Generated Unique ID assigned to the User                                         |
| profilePic          | `String`              | Url to the Uploaded Profile Picture                                                   |
| harvestOrganization | `HarvestOrganization` | Organization Details                                                                  |
| name                | `String`              | User's Organization Name                                                              |
| website             | `String`              | Auto Generated Unique ID assigned to the Organization                                 |
| id                  | `String`              | Returns info for every type of request.                                               |
| identifier          | `String`              | Unique Identifier for the Organization                                                |
| BearerToken         | `String`              | JWT Token for security purpose. This get generated once users Logged In Successfully. |

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

--------------
### Fcm Token
--------------

```HTTP
POST BASE_URL/api/v1/fcmToken
```

**Request Body**

```ts
{
   "id": "f8296e81-6d5b-4a5e-9de4-9d60459a1997",
   "firstName": "Yugesh",
   "lastName": "Jain",
   "email": "yugesh@mutualmobile.com",
   "orgId": "151d11a3-780d-4f03-bbad-889bd3707b02",
   "role": String,
   "pushToken": String,
   "profilePic": String,
   "harvestOrganization": {
      "name": "mm",
      "website": "mm.com",
      "id": "151d11a3-780d-4f03-bbad-889bd3707b02",
      "identifier": "com.mm.org"
   }
}]
```

**Authorization**

```ts
   BearerToken: String
```

| param               | type                  | Description                                                                           |
|:--------------------|:----------------------|:--------------------------------------------------------------------------------------|
| id                  | `String`              | Auto Generated Unique ID assigned to a User                                           |
| firstName           | `String`              | First Name of the User Just Signed Up                                                 |
| lastName            | `String`              | Last Name of the User Just Signed Up                                                  |
| email               | `String`              | Email of the User Just Signed Up                                                      |
| orgId               | `String`              | Auto Generated Unique ID assigned to the Organization                                 |
| role                | `String`              | Role of user in the organization like Admin or normal employee                        |
| pushToken           | `String`              | Auto Generated Unique ID assigned to the User                                         |
| profilePic          | `String`              | Url to the Uploaded Profile Picture                                                   |
| harvestOrganization | `HarvestOrganization` | Organization Details                                                                  |
| name                | `String`              | User's Organization Name                                                              |
| website             | `String`              | Auto Generated Unique ID assigned to the Organization                                 |
| id                  | `String`              | Returns info for every type of request.                                               |
| identifier          | `String`              | Unique Identifier for the Organization                                                |
| BearerToken         | `String`              | JWT Token for security purpose. This get generated once users Logged In Successfully. |

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

--------------
### Refresh Token
--------------

```HTTP
POST BASE_URL/api/v1/refreshToken
```

**Request Body**

```ts
{
   "refreshToken": String,
}
```

| param        | type     | Description                |
|:-------------|:---------|:---------------------------|
| refreshToken | `String` | Unique Token for each user |

**Response**

1. When -> 200 OK

```ts
{
  "token": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhNTA1MjYwZi03YmZhLTRiZDUtODBjZS04MDBmOGE0M2IzZmMiLCJpYXQiOjE2NTM5Mzg2NzUsImV4cCI6MTY1Mzk0MjI3NX0.1CydFVIwoWq4gaqUhhEBy9XpQVaed-XW0s9qn0uFkUIg4h3WXiQkZrYUqULU0ZxeFMX1jfEMqO9FtTwt3zD5Zw",
  "message": String,
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

### Forgot Password Api

- [Forgot Password](#-forgot-password)
- [Reset Password](#-reset-password)

--------------
### Forgot Password
--------------

```HTTP
POST BASE_URL/api/v1/public/forgotPassword
```

**Parameters**

```ts
   email: String
```

| param | type     | Description                                                          |
|:------|:---------|:---------------------------------------------------------------------|
| email | `String` | Email Address of the account for the one who's password is forgotten |

**Response**

1. When -> 200 OK

```ts
{
  "message": "Email to reset the password sent to your Email"
}
```

| param        | type     | Description                                                                           |
|:-------------|:---------|:--------------------------------------------------------------------------------------|
| message      | `String` | Returns info for every type of request.                                               |

2. When -> 400 BAD REQUEST

```ts
{
  "message": "ERROR"
}
```

| param        | type     | Description                                                                           |
|:-------------|:---------|:--------------------------------------------------------------------------------------|
| message      | `String` | Returns info for every type of request.                                               |

--------------
### Reset Password
--------------

```HTTP
POST BASE_URL/api/v1/resetPassword
```

**Request Body**

```ts
{
   "token": String,
   "password": String
}
```

| param    | type     | Description                                                                |
|:---------|:---------|:---------------------------------------------------------------------------|
| token    | `String` | JWT Token for security purpose. We will get this from the email deep link. |
| password | `String` | New Password that we want to set for the account                           |

**Response**

1. When -> 200 OK

```ts
{
  "message": String
}
```

| param        | type     | Description                                                                           |
|:-------------|:---------|:--------------------------------------------------------------------------------------|
| message      | `String` | Returns info for every type of request.                                               |\

2. When -> 400 BAD REQUEST

```ts
{
  "message": "ERROR"
}
```

| param        | type     | Description                                                                           |
|:-------------|:---------|:--------------------------------------------------------------------------------------|
| message      | `String` | Returns info for every type of request.                                               |

### Organization Api

- [Find Organization By Identifier](#-find-organization-by-identifier)

--------------
### Find Organization By Identifier
--------------

```HTTP
GET BASE_URL/api/v1/public/organization
```

**Parameters**

```ts
   identifier: String
```

| param      | type     | Description                            |
|:-----------|:---------|:---------------------------------------|
| identifier | `String` | Unique Identifier for the Organization |

**Response**

1. When -> 200 OK

```ts
{
   "message": String
   "harvestOrganization": {
      "name": "mm",
      "website": "mm.com",
      "imgUrl": "www.someimg.png"
      "id": "151d11a3-780d-4f03-bbad-889bd3707b02",
      "identifier": "com.mm.org"
   }
}
```

| param               | type                  | Description                                           |
|:--------------------|:----------------------|:------------------------------------------------------|
| message             | `String`              | Returns info for every type of request.               |
| harvestOrganization | `HarvestOrganization` | Organization Details                                  |
| name                | `String`              | User's Organization Name                              |
| website             | `String`              | Auto Generated Unique ID assigned to the Organization |
| imgUrl              | `String`              | Url to the Organization uploaded Logo                 |
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

### Organization Projects Api

- [Create Project](#-create-project)
- [Update Project](#-update-project)
- [Delete Project](#-delete-project)
- [Find Projects In Organization](#-find-projects-in-organization)
- [List of Users in a Project](#-list-of-users-in-a-project)

--------------
### Create Project
--------------

```HTTP
GET BASE_URL/api/v1/organization/project
```

**Request Body**

```ts
{
   "name": String,
   "client": String,
   "isIndefinite": Boolean,
   "startDate": String,
   "endDate": String
}
```

**Authorization**

```ts
   BearerToken: String
```

| param        | type      | Description                                                                           |
|:-------------|:----------|:--------------------------------------------------------------------------------------|
| name         | `String`  | Name of the project to be created                                                     |
| client       | `String`  | Name of the client who's project is thisClient                                        |
| isIndefinite | `Boolean` | Is the project indefinite or not                                                      |
| startDate    | `String`  | Start date of the Project                                                             |
| endDate      | `String`  | End date of the Project                                                               |
| Bearer Token | `String`  | JWT Token for security purpose. This get generated once users Logged In Successfully. |

**Response**

1. When -> 200 OK

```ts
{
   "message": String
   "data": {
      "id": String,
      "name": String,
      "client": String,
      "isIndefinite": Boolean,
      "startDate": String,
      "endDate": String,
      "organizationId": String
   }
}
```

| param        | type      | Description                                                           |
|:-------------|:----------|:----------------------------------------------------------------------|
| message      | `String`  | Returns info for every type of request.                               |
| id           | `String`  | Unique Auto generated Id for the project                              |
| name         | `String`  | Name of the project to be created                                     |
| client       | `String`  | Name of the client who's project is thisClient                        |
| isIndefinite | `Boolean` | Is the project indefinite or not                                      |
| startDate    | `String`  | Start date of the Project                                             |
| endDate      | `String`  | End date of the Project                                               |
| orgId        | `String`  | Unique Auto generated Organization Id in which the project is created |

2. When -> 400 BAD REQUEST

```ts
{
  "message": "ERROR"
}
```

| param        | type     | Description                                                                           |
|:-------------|:---------|:--------------------------------------------------------------------------------------|
| message      | `String` | Returns info for every type of request.                                               |

--------------
### Update Project
--------------

```HTTP
PUT BASE_URL/api/v1/organization/project
```

**Request Body**

```ts
{
   "id": String,
   "name": String,
   "client": String,
   "isIndefinite": Boolean,
   "startDate": String,
   "endDate": String,
   "organizationId": String
}
```

**Authorization**

```ts
   BearerToken: String
```

| param          | type      | Description                                                                           |
|:---------------|:----------|:--------------------------------------------------------------------------------------|
| id             | `String`  | Unique Auto generated Id for the project                                              |
| name           | `String`  | Name of the project to be created                                                     |
| client         | `String`  | Name of the client who's project is thisClient                                        |
| isIndefinite   | `Boolean` | Is the project indefinite or not                                                      |
| startDate      | `String`  | Start date of the Project                                                             |
| endDate        | `String`  | End date of the Project                                                               |
| organizationId | `String`  | Unique Auto generated Organization Id in which the project is created                 |
| Bearer Token   | `String`  | JWT Token for security purpose. This get generated once users Logged In Successfully. |

**Response**

1. When -> 200 OK

```ts
{
   "message": String
}
```

| param        | type     | Description                             |
|:-------------|:---------|:----------------------------------------|
| message      | `String` | Returns info for every type of request. |

2. When -> 400 BAD REQUEST

```ts
{
  "message": "ERROR"
}
```

| param        | type     | Description                             |
|:-------------|:---------|:----------------------------------------|
| message      | `String` | Returns info for every type of request. |

--------------
### Delete Project
--------------

```HTTP
DELETE BASE_URL/api/v1/organization/project
```

**Parameters**

```ts
   projectId: String
```

**Authorization**

```ts
   BearerToken: String
```

| param        | type     | Description                                                                           |
|:-------------|:---------|:--------------------------------------------------------------------------------------|
| projectId    | `String` | Unique Auto generated Id for the project                                              |
| Bearer Token | `String` | JWT Token for security purpose. This get generated once users Logged In Successfully. |

**Response**

1. When -> 200 OK

```ts
{
   "message": String
}
```

| param        | type     | Description                             |
|:-------------|:---------|:----------------------------------------|
| message      | `String` | Returns info for every type of request. |

2. When -> 400 BAD REQUEST

```ts
{
  "message": "ERROR"
}
```

| param        | type     | Description                             |
|:-------------|:---------|:----------------------------------------|
| message      | `String` | Returns info for every type of request. |

--------------
### Find Projects In Organization
--------------

```HTTP
GET BASE_URL/api/v1/public/organization/project
```

**Parameters**

```ts
   orgId: String,
   offset: Int,
   limit: Int,
   search: String
```

**Authorization**

```ts
   BearerToken: String
```

| param        | type     | Description                                                                           |
|:-------------|:---------|:--------------------------------------------------------------------------------------|
| orgId        | `String` | Unique Auto generated Organization Id in which the project is created                 |
| offSet       | `Int`    | Offset                                                                                |
| limit        | `Int`    | Limit per Page                                                                        |
| search       | `String` | Name of the project you want to search in the searchbar                               |
| Bearer Token | `String` | JWT Token for security purpose. This get generated once users Logged In Successfully. |

**Response**

1. When -> 200 OK

```ts
{
   "message": String
   "data": [
      {
         "id": String,
         "name": String,
         "client": String,
         "isIndefinite": Boolean,
         "startDate": String,
         "endDate": String,
         "organizationId": String
      }
   ]
}
```

| param          | type      | Description                                                           |
|:---------------|:----------|:----------------------------------------------------------------------|
| message        | `String`  | Returns info for every type of request.                               |
| id             | `String`  | Unique Auto generated Id for the project                              |
| name           | `String`  | Name of the project to be created                                     |
| client         | `String`  | Name of the client who's project is thisClient                        |
| isIndefinite   | `Boolean` | Is the project indefinite or not                                      |
| startDate      | `String`  | Start date of the Project                                             |
| endDate        | `String`  | End date of the Project                                               |
| organizationId | `String`  | Unique Auto generated Organization Id in which the project is created |

2. When -> 400 BAD REQUEST

```ts
{
  "message": "ERROR"
}
```

| param        | type     | Description                                                                           |
|:-------------|:---------|:--------------------------------------------------------------------------------------|
| message      | `String` | Returns info for every type of request.                                               |

--------------
### List of users in a Project
--------------

```HTTP
GET BASE_URL/api/v1/organization/project/list-users
```

**Parameters**

```ts
   projectId: String
```

**Authorization**

```ts
   BearerToken: String
```

| param        | type     | Description                                                                           |
|:-------------|:---------|:--------------------------------------------------------------------------------------|
| projectId    | `String` | Unique Auto generated Id for the project                                              |
| Bearer Token | `String` | JWT Token for security purpose. This get generated once users Logged In Successfully. |

**Response**

1. When -> 200 OK

```ts
{
   "message": String
   "data": [
      {
         "email": String,
         "firstName": String,
         "id": String,
         "lastName": String,
         "modifiedTime": String,
         "orgId": String,
         "role": String
      }
   ]
}
```

| param        | type                  | Description                                           |
|:-------------|:----------------------|:------------------------------------------------------|
| message      | `String`              | Returns info for every type of request.               |
| data         | `Data`                | Details of the User Just Signed Up                    |
| id           | `String`              | Auto Generated Unique ID assigned to a User           |
| firstName    | `String`              | First Name of the User Just Signed Up                 |
| lastName     | `String`              | Last Name of the User Just Signed Up                  |
| email        | `String`              | Email of the User Just Signed Up                      |
| modifiedTime | `String`              | Time of successful Signup                             |
| orgId        | `String`              | Auto Generated Unique ID assigned to the Organization |
| role         | `String`              | Role of the user in the Organization                  |

2. When -> 400 BAD REQUEST

```ts
{
  "message": "ERROR"
}
```

| param    | type     | Description                             |
|:---------|:---------|:----------------------------------------|
| message  | `String` | Returns info for every type of request. |

### Organization Users Api

- [Find Users in Organization](#-find-users-in-organization)

--------------
### Find Users in Organization
--------------

```HTTP
GET BASE_URL/api/v1/organization/users
```

**Parameters**

```ts
   userType: Int,
   orgIdentifier: String,
   isUserDeleted: Boolean,
   offset: Int,
   limit: Int,
   search: String
```

**Authorization**

```ts
   BearerToken: String
```

| param         | type      | Description                                                                           |
|:--------------|:----------|:--------------------------------------------------------------------------------------|
| userType      | `Int`     | Role of the user                                                                      |
| orgIdentifier | `String`  | Unique identifier for an Organization                                                 |
| isUserDeleted | `Boolean` | Boolean to see if the user is Deleted or not                                          |
| offSet        | `Int`     | Offset                                                                                |
| limit         | `Int`     | Limit per Page                                                                        |
| search        | `String`  | Name of the user you want to search in the searchbar                                  |
| Bearer Token  | `String`  | JWT Token for security purpose. This get generated once users Logged In Successfully. |

**Response**

1. When -> 200 OK

```ts
{
   "message": String
   "data": [
      {
         "email": String,
         "firstName": String,
         "id": String,
         "lastName": String,
         "modifiedTime": String,
         "orgId": String
      }
   ]
}
```

| param        | type                  | Description                                           |
|:-------------|:----------------------|:------------------------------------------------------|
| message      | `String`              | Returns info for every type of request.               |
| data         | `Data`                | Details of the User Just Signed Up                    |
| id           | `String`              | Auto Generated Unique ID assigned to a User           |
| firstName    | `String`              | First Name of the User Just Signed Up                 |
| lastName     | `String`              | Last Name of the User Just Signed Up                  |
| email        | `String`              | Email of the User Just Signed Up                      |
| modifiedTime | `String`              | Time of successful Signup                             |
| orgId        | `String`              | Auto Generated Unique ID assigned to the Organization |

2. When -> 400 BAD REQUEST

```ts
{
  "message": "ERROR"
}
```

| param    | type     | Description                             |
|:---------|:---------|:----------------------------------------|
| message  | `String` | Returns info for every type of request. |

### User project Api

- [Assign Projects to User](#-assign-projects-to-user)
- [Log Work Time](#-log-work-time)
- [Get Projects Assigned to a user](#-get-projects-assigned-to-a-user)

--------------
### Assign Projects to User
--------------

```HTTP
POST BASE_URL/api/v1/org-admin/assign-user-project
```

**Request Body**

```ts
   {
      "projectMap": HashMap<projectId, List<userId>>
   }
```

**Authorization**

```ts
   BearerToken: String
```

| param        | type     | Description                                                                           |
|:-------------|:---------|:--------------------------------------------------------------------------------------|
| projectId    | `String` | Unique Auto Generated ID for Project                                                  |
| userId       | `String` | Unique Auto Generated ID for User                                                     |
| Bearer Token | `String` | JWT Token for security purpose. This get generated once users Logged In Successfully. |

**Response**

1. When -> 200 OK

```ts
{
   "message": String
}
```

| param    | type      | Description                             |
|:---------|:----------|:----------------------------------------|
| message  | `String`  | Returns info for every type of request. |

2. When -> 400 BAD REQUEST

```ts
{
  "message": "ERROR"
}
```

| param    | type     | Description                             |
|:---------|:---------|:----------------------------------------|
| message  | `String` | Returns info for every type of request. |

--------------
### Log Work Time
--------------

```HTTP
POST BASE_URL/api/v1/user/project/log-work
```

**Request Body**

```ts
   {
      "id": String,
      "projectId": String,
      "userId": String,
      "workDate": String,
      "workHours": Float,
      "note": String
   }
```

**Authorization**

```ts
   BearerToken: String
```

| param        | type     | Description                                                                           |
|:-------------|:---------|:--------------------------------------------------------------------------------------|
| id           | `String` | ID                                                                                    |
| projectId    | `String` | Unique Auto Generated ID for Project                                                  |
| userId       | `String` | Unique Auto Generated ID for User                                                     |
| workDate     | `String` | Date on which the work is done                                                        |
| workHours    | `String` | Number of hours dedicated to the particular work                                      |
| Bearer Token | `String` | JWT Token for security purpose. This get generated once users Logged In Successfully. |

**Response**

1. When -> 200 OK

```ts
{
   "message": String
}
```

| param    | type     | Description                             |
|:---------|:---------|:----------------------------------------|
| message  | `String` | Returns info for every type of request. |

2. When -> 400 BAD REQUEST

```ts
{
  "message": "ERROR"
}
```

| param    | type     | Description                             |
|:---------|:---------|:----------------------------------------|
| message  | `String` | Returns info for every type of request. |

--------------
### Get Projects Assigned to a user
--------------

```HTTP
GET BASE_URL/api/v1/user/assigned-projects
```

**Parameters**

```ts
   userId: String
```

**Authorization**

```ts
   BearerToken: String
```

| param  | type     | Description                         |
|:-------|:---------|:------------------------------------|
| userId | `String` | Unique Auto Generated Id for a User |

**Response**

1. When -> 200 OK

```ts
{
   "message": String
   "data": [
      {
         "id": String,
         "name": String,
         "client": String,
         "isIndefinite": Boolean,
         "startDate": String,
         "endDate": String,
         "organizationId": String
      }
   ]
}
```

| param          | type      | Description                                                           |
|:---------------|:----------|:----------------------------------------------------------------------|
| message        | `String`  | Returns info for every type of request.                               |
| id             | `String`  | Unique Auto generated Id for the project                              |
| name           | `String`  | Name of the project to be created                                     |
| client         | `String`  | Name of the client who's project is thisClient                        |
| isIndefinite   | `Boolean` | Is the project indefinite or not                                      |
| startDate      | `String`  | Start date of the Project                                             |
| endDate        | `String`  | End date of the Project                                               |
| organizationId | `String`  | Unique Auto generated Organization Id in which the project is created |

2. When -> 400 BAD REQUEST

```ts
{
  "message": "ERROR"
}
```

| param    | type     | Description                             |
|:---------|:---------|:----------------------------------------|
| message  | `String` | Returns info for every type of request. |

### User Work Api

- [Get Work Logs for a Date Range](#-get-work-logs-for-a-date-range)

--------------
### Get Work Logs for a Date Range
--------------

```HTTP
GET BASE_URL/api/v1/user/project/log-work
```

**Request Body**

```ts
   {
      "startDate": String,
      "endDate": String,
      "userIds": List<String> 
   }
```

**Authorization**

```ts
   BearerToken: String
```

| param     | type           | Description                          |
|:----------|:---------------|:-------------------------------------|
| startDate | `String`       | Date from which you want the logs    |
| endDate   | `String`       | Date till which you want the logs    |
| userIds   | `List<String>` | user Ids for those you want the logs |

**Response**

1. When -> 200 OK

```ts
{
   "message": String
   "data": [
      {
         "id": String,
         "projectId": String,
         "userId": String,
         "workDate": String,
         "workHours": Float,
         "note": String
      }
   ]
}
```

| param          | type     | Description                                        |
|:---------------|:---------|:---------------------------------------------------|
| message        | `String` | Returns info for every type of request.            |
| projectId      | `String` | Unique Auto generated Id for the project           |
| userId         | `String` | Unique Auto generated Id for the user              |
| workDate       | `String` | Date on which the work is done                     |
| workHours      | `Float`  | Number of hours dedicated to the work on that date |
| note           | `String` | Note                                               |

2. When -> 400 BAD REQUEST

```ts
{
  "message": "ERROR"
}
```

| param    | type     | Description                             |
|:---------|:---------|:----------------------------------------|
| message  | `String` | Returns info for every type of request. |