/* Re-create database SWINZDB */
DROP DATABASE IF EXISTS swinzdb;
CREATE DATABASE swinzdb;
USE swinzdb;

/* Create tables */
CREATE TABLE `User` (
  `username` VARCHAR(32) NOT NULL UNIQUE,
  `password` VARCHAR(256) NOT NULL,
  `avatar` BLOB,
  `alias` VARCHAR(32) NOT NULL UNIQUE,
  PRIMARY KEY (`username`)
);

CREATE TABLE `Answer` (
  `answerid` INT NOT NULL AUTO_INCREMENT,
  `text` TEXT(256) NOT NULL,
  `questionid` INT NOT NULL,
  truthfulness BIT NOT NULL,
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
  `datecreated` TIMESTAMP NOT NULL,
  `player_one` VARCHAR(32) NOT NULL,
  `player_two` VARCHAR(32) NOT NULL,
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

INSERT INTO User (username, password, alias) VALUES (''
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
INSERT INTO Answer(questionid, truthfulness, text) VALUES (
  1, 1, 'Death...'
);

INSERT INTO Answer(questionid, truthfulness, text) VALUES (
  1, 0, 'Death Metal!'
);

INSERT INTO Answer(questionid, truthfulness, text) VALUES (
  1, 0, 'Jazz'
);

INSERT INTO Answer(questionid, truthfulness, text) VALUES (
  1, 0, 'Flute?'
);

INSERT INTO Answer(questionid, truthfulness, text) VALUE (
  2, 1, 'Imperials?'
);

INSERT INTO Answer(questionid, truthfulness, text) VALUE (
  2, 0, 'Stormcloacks!'
);

INSERT INTO Answer(questionid, truthfulness, text) VALUE (
  2, 0, 'Forever Tamriel'
);

INSERT INTO Answer(questionid, truthfulness, text) VALUE (
  2, 0, 'Oblivion... is rising again!'
);

INSERT INTO Answer(questionid, truthfulness, text) VALUE (
  3, 1, 'The Queen of Death'
);

INSERT INTO Answer(questionid, truthfulness, text) VALUE (
  3, 0, 'Get lost, jerk'
);

INSERT INTO Answer(questionid, truthfulness, text) VALUE (
  3, 0, 'I have no moeny!'
);

INSERT INTO Answer(questionid, truthfulness, text) VALUE (
  3, 0, 'The Queen of Life'
);

INSERT INTO Answer(questionid, truthfulness, text) VALUE (
  4, 1, 'Yes'
);

INSERT INTO Answer(questionid, truthfulness, text) VALUE (
  4, 0, 'Nooooooooooo'
);

INSERT INTO Answer(questionid, truthfulness, text) VALUE (
  4, 0, 'Nooooooo'
);

INSERT INTO Answer(questionid, truthfulness, text) VALUE (
  4, 0, 'No'
);

INSERT INTO Answer(questionid, truthfulness, text) VALUE (
  5, 1, 'Definitely not'
);

INSERT INTO Answer(questionid, truthfulness, text) VALUE (
  5, 0, 'No'
);

INSERT INTO Answer(questionid, truthfulness, text) VALUE (
  5, 0, 'Maybe'
);

INSERT INTO Answer(questionid, truthfulness, text) VALUE (
  5, 0, 'Yeeeees'
);

/* Insert test games */
INSERT INTO Game(gameid, creator, player_one, player_two) VALUE (
  '47310824', 'David', 'David', 'Jachym'
);

INSERT INTO Game(gameid, creator, player_one, player_two) VALUE (
  '82549371', 'Jachym', 'David', 'Jachym'
);

/* Assign test questions to games */
INSERT INTO GameQuestions(gameid_fk, questionid_fk) VALUE (
  '47310824', 1
);

INSERT INTO GameQuestions(gameid_fk, questionid_fk) VALUE (
  '47310824', 2
);

INSERT INTO GameQuestions(gameid_fk, questionid_fk) VALUE (
  '47310824', 3
);

INSERT INTO GameQuestions(gameid_fk, questionid_fk) VALUE (
  '47310824', 4
);