# kemenu

Kemenu web application, for menu management written in Java and Angular.

## IMPORTANT:exclamation:: Use npm as follows

:warning: Before running the frontend you have to compile the frontend:

`mvn -U clean install -pl :kemenu-frontend`

1. Hot reloading to work with frontend:

`bash kemenu-frontend/npm --prefix kemenu-frontend run start`

2. For use ng command:

`bash kemenu-frontend/npm --prefix kemenu-frontend run ng <NG_COMMAND>`

3. For install a npm package:

`bash kemenu-frontend/npm --prefix kemenu-frontend install <PACKAGE>`

## Build project

1. Compile frontend

`mvn -U clean install -pl :kemenu-frontend`

2. Compile backend

`mvn -U clean test package -pl :kemenu-backend`

3. Launch acceptance tests

`mvn -U clean test -pl :kemenu-acceptance-tests`

4. How to launch docker

`docker-compose up`

## How to run backend in local with Intellij IDEA

:warning: Before running the backend, you need to have the docker running with mongodb.

1. Go to `kemenu-backend/src/main/java/com/kemenu/kemenu_backend/Application.java`
2. Right click and click on `Debug 'Application'`
3. Stop it
4. Go to `Run` > `Edit Configurations...`
5. In the new window select `Spring Boot` > `Application` > `Environment`
6. Put in `VM options` the following `-Dspring.profiles.active=dev`
7. Click on `Apply` and then in `OK`

# Stack

* Angular 9 + Bootstrap
* Java 14 + Spring Boot
* MongoDB
* Docker

# Branch naming convention

A branch must be named following this convention:

* Start with KEM-[ISSUE_NUMBER]_[SHORT_DESCRIPTION]
* For example: `KEM-9_login_page`

