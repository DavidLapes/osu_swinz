/* Re-create database SWINZDB */
DROP DATABASE IF EXISTS swinzdb;
CREATE DATABASE swinzdb;
USE swinzdb;

/* Create tables */
CREATE TABLE `User` (
  `username` VARCHAR(32) NOT NULL UNIQUE,
  `password` VARCHAR(32) NOT NULL,
  `avatar` BLOB,
  `alias` VARCHAR(32) NOT NULL UNIQUE,
  PRIMARY KEY (`username`)
);

CREATE TABLE `Answer` (
  `answerid` INT NOT NULL AUTO_INCREMENT,
  `text` TEXT(256) NOT NULL,
  `questionid` INT NOT NULL,
  `thruthfulness` BIT NOT NULL,
  PRIMARY KEY (`answerid`)
);

CREATE TABLE `Question` (
  `questionid` INT NOT NULL AUTO_INCREMENT,
  `text` TEXT(256) NOT NULL,
  PRIMARY KEY (`questionid`)
);

CREATE TABLE `Game` (
  `gameid` VARCHAR(8) NOT NULL,
  `creator` VARCHAR(32) NOT NULL,
  `datecreated` DATETIME NOT NULL,
  PRIMARY KEY (`gameid`)
);

CREATE TABLE `GameQuestions` (
  `gameid_fk` VARCHAR(8) NOT NULL,
  `questionid_fk` INT NOT NULL,
  PRIMARY KEY (`gameid_fk`, `questionid_fk`)
);

/* Add foreign keys to tables */
ALTER TABLE `Answer` ADD CONSTRAINT `Answer_fk0` FOREIGN KEY (`questionid`) REFERENCES `Question`(`questionid`);

ALTER TABLE `Game` ADD CONSTRAINT `Game_fk0` FOREIGN KEY (`creator`) REFERENCES `User`(`username`);

ALTER TABLE `GameQuestions` ADD CONSTRAINT `Question_fk1` FOREIGN KEY (`questionid_fk`) REFERENCES `Question`(`questionid`);

ALTER TABLE `GameQuestions` ADD CONSTRAINT `Game_fk1` FOREIGN KEY (`gameid_fk`) REFERENCES `Game`(`gameid`);

/* Insert test users */
INSERT INTO User (username, password, alias) VALUES (
  'David', '1234', 'David'
);

INSERT INTO User (username, password, alias) VALUES (
  'Jachym', '4321', 'Chymja'
);

/* Insert test questions */
INSERT INTO Question(text) VALUES (
  'What is the music of life?'
);

INSERT INTO Question(text) VALUES (
  'Stormcloacks, or Imperials?'
);

INSERT INTO Question(text) VALUES (
  'Who is Astrid?'
);

INSERT INTO Question(text) VALUES (
  'Shall we execute Cicero?'
);

INSERT INTO Question(text) VALUES (
  'Is Ulrfic the High King?'
);

/* Insert test answers and assign them to questions */