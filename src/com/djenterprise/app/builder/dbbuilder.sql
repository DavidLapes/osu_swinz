DROP DATABASE IF EXISTS swinzdb;
CREATE DATABASE swinzdb;
USE swinzdb;

CREATE TABLE `User` (
  `username` varchar(32) NOT NULL UNIQUE,
  `password` varchar(32) NOT NULL,
  `avatar` blob,
  `displayname` varchar(32) NOT NULL UNIQUE,
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
  `gameid` varchar(8) NOT NULL,
  PRIMARY KEY (`questionid`)
);

CREATE TABLE `Game` (
  `gameid` varchar(8) NOT NULL,
  `creator` varchar(32) NOT NULL,
  `datecreated` DATETIME NOT NULL,
  PRIMARY KEY (`gameid`)
);

ALTER TABLE `Answer` ADD CONSTRAINT `Answer_fk0` FOREIGN KEY (`questionid`) REFERENCES `Question`(`questionid`);

ALTER TABLE `Question` ADD CONSTRAINT `Question_fk0` FOREIGN KEY (`gameid`) REFERENCES `Game`(`gameid`);

ALTER TABLE `Game` ADD CONSTRAINT `Game_fk0` FOREIGN KEY (`creator`) REFERENCES `User`(`username`);

INSERT INTO User (username, password, displayname) VALUES (
  'David', '1234', 'David'
);

INSERT INTO User (username, password, displayname) VALUES (
    'Jachym', '4321', 'Chymja'
);