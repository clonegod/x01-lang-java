CREATE DATABASE `library`;

USE `library`;


drop table if exists book_to_book_store;
drop table if exists book_store;
drop table if exists book;
drop table if exists language;
drop table if exists author;


CREATE TABLE language (
  id              integer(7)     NOT NULL auto_increment PRIMARY KEY,
  cd              CHAR(2)       NOT NULL,
  description     varchar(50)
);


CREATE TABLE author (
  id              integer(7)     NOT NULL auto_increment PRIMARY KEY,
  first_name      varchar(50),
  last_name       varchar(50)  NOT NULL,
  date_of_birth   DATE,
  year_of_birth   integer(7),
  distinguished   integer(1)
);


CREATE TABLE book (
  id              integer(7)     NOT NULL auto_increment PRIMARY KEY,
  author_id       integer(7)     NOT NULL,
  title           varchar(400) NOT NULL,
  published_in    integer(7)     NOT NULL,
  language_id     integer(7)     NOT NULL,
  
  CONSTRAINT fk_book_author     FOREIGN KEY (author_id)   REFERENCES author(id),
  CONSTRAINT fk_book_language   FOREIGN KEY (language_id) REFERENCES language(id)
);


CREATE TABLE book_store (
  name            varchar(200)  NOT NULL UNIQUE
);


CREATE TABLE book_to_book_store (
  name            varchar(200) NOT NULL,
  book_id         integer       NOT NULL,
  stock           integer,
  
  PRIMARY KEY(name, book_id),
  CONSTRAINT fk_b2bs_book_store FOREIGN KEY (name)        REFERENCES book_store (name) ON DELETE CASCADE,
  CONSTRAINT fk_b2bs_book       FOREIGN KEY (book_id)     REFERENCES book (id)         ON DELETE CASCADE
);