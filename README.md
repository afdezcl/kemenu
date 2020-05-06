# kemenu

Kemenu web app, for menu management written in Java and Angular.

## IMPORTANT!: Use npm as follows

1. `bash kemenu-frontend/npm run start`

## Build project

1. Compile frontend

`mvn -U clean install -pl :kemenu-frontend`

2. Compile backend

`mvn -U clean test package -pl :kemenu-backend`

3. Launch acceptance tests

`mvn -U clean test -pl :kemenu-acceptance-tests`

4. How to launch docker

`docker-compose up`

# Stack

* Angular 9 + Bootstrap
* Java 14 + Spring Boot
* MongoDB
* Docker

# Branch naming convention

A branch must be named following this convention:

* Start with KEM-[ISSUE_NUMBER]_[SHORT_DESCRIPTION]
* For example: `KEM-9_login_page`

