# Forum web application demo

<p align="center">
  <img src="src/main/resources/static/images/homepage.png" width="625" alt="front page" />
</p>

Application web pages built using thymeleaf templating and Undertow HTTP web server. Persistence provided by MariaDB and Hibernate ORM.

### Quick Start

```
make local
```

---

## To run the application

*NOTE: Make sure you have MariaDB up!*

```
$ gradle build run
```

## To generate the schema for MariaDB

*NOTE: Make sure you have MariaDB up!*

```
$ make init
```

## To generate the mockdata for MariaDB

*NOTE: Make sure you have MariaDB up!*

```
$ make mockdata`
```
