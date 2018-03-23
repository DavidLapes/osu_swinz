DROP DATABASE IF EXISTS swinzdb;
CREATE DATABASE swinzdb;
USE swinzdb;

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
  PRIMARY KEY (`answerid`)
);

CREATE TABLE `Question` (
  `questionid` INT NOT NULL AUTO_INCREMENT,
  `text` TEXT(256) NOT NULL,
  `gameid` VARCHAR(8) NOT NULL,
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

ALTER TABLE `Answer` ADD CONSTRAINT `Answer_fk0` FOREIGN KEY (`questionid`) REFERENCES `Question`(`questionid`);

ALTER TABLE `Question` ADD CONSTRAINT `Question_fk0` FOREIGN KEY (`gameid`) REFERENCES `Game`(`gameid`);

ALTER TABLE `Game` ADD CONSTRAINT `Game_fk0` FOREIGN KEY (`creator`) REFERENCES `User`(`username`);

ALTER TABLE `GameQuestions` ADD CONSTRAINT `Question_fk1` FOREIGN KEY (`questionid_fk`) REFERENCES `Question`(`questionid`);

ALTER TABLE `GameQuestions` ADD CONSTRAINT `Game_fk1` FOREIGN KEY (`gameid_fk`) REFERENCES `Game`(`gameid`);

INSERT INTO User (username, password, alias) VALUES (
  'David', '1234', 'David'
);

INSERT INTO User (username, password, alias) VALUES (
    'Jachym', '4321', 'Chymja'
);