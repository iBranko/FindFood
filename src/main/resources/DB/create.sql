SET MODE PostgreSQL;

DROP TABLE restaurants;
DROP table foods;
drop table couriers;
drop table customers;
drop table reviews;
drop table tickets;
drop table restaurants_foods;

CREATE TABLE IF NOT EXISTS restaurants (
 id int PRIMARY KEY auto_increment,
 name VARCHAR,
 address VARCHAR,
 zipcode VARCHAR,
 phone VARCHAR,
 website VARCHAR,
 email VARCHAR
);

CREATE TABLE IF NOT EXISTS foods (
 id int PRIMARY KEY auto_increment,
 name VARCHAR
);

CREATE TABLE IF NOT EXISTS couriers (
 id int PRIMARY KEY auto_increment,
 name VARCHAR,
 email VARCHAR
);

CREATE TABLE IF NOT EXISTS customers (
 id int PRIMARY KEY auto_increment,
 name VARCHAR,
 phone VARCHAR,
 address VARCHAR,
 zipcode VARCHAR,
 email VARCHAR
);

CREATE TABLE IF NOT EXISTS reviews (
 id int PRIMARY KEY auto_increment,
 content VARCHAR,
 rating VARCHAR,
 ticketId INTEGER
);

CREATE TABLE IF NOT EXISTS tickets (
 id int PRIMARY KEY auto_increment,
 restaurantId INTEGER,
 customerId INTEGER,
 foodId INTEGER,
 courierId INTEGER,
 creationTime TIMESTAMP
);

CREATE TABLE IF NOT EXISTS restaurants_foods (
 id int PRIMARY KEY auto_increment,
 foodId INTEGER,
 restaurantId INTEGER
);