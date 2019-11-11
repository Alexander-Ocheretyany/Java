# RESTAPI

A web based database browser (similar to desktop application DBeaver) with basic functionality and for single database vendor only. Browser is able to register multiple database connections and browse their data and structure.

The program is a RESTful service with its own database. Built using Spring-boot, Maven, Hibernate and Tomcat.

Database for persistence is MySQL. Before its usage one must put the details of their database into "src/main/resources/application.properties" file (hostname, port, database name, username, password).


**REST API:**

1. Path "/connection":

      /connection (GET) - get the whole "connections" table.
      /connection/{id} (GET) -  specify a certain ID to get a sole record from "connections" table.
      /connection (POST) -  here, in the request body, a user must specify all columns to create a record - name, hostname, port, databasename,   username and password.
      /connection/{id} (PUT) - specify the ID of a record to be changed and in the request body define a new record.
      /connection/{id} (DELETE) - specify the ID of a record you want to delete and it will be deleted.

2. Path "/schemas/{id}" - gets all schemas from the database that is stored in "connections"table under id given.
3. Path "/tables/{id}" - gets all tables from the database that is stored in "connections"table under id given.
4. Path "/columns/{id}/{tablename}" - shows info about all columns in the table with a given tablename that is stored in the database from "connections" table under id given.
5.  Path "/show/{id}/{tablename}" - prints out the whole table with a given tablename from the database with a given id defined in "connections" table.
