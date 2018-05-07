/* Re-create database SWINZDB */
DROP DATABASE IF EXISTS swinzdb;
CREATE DATABASE swinzdb;
USE swinzdb;

/* Create tables */
CREATE TABLE `User` (
  `username` VARCHAR(32) NOT NULL UNIQUE,
  `password` VARCHAR(256) NOT NULL,
  `avatar` MEDIUMBLOB,
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

CREATE TABLE `GameState` (
  `gameid_fk` VARCHAR(8) NOT NULL UNIQUE,
  `number_of_questions` INT NOT NULL,
  `current_round` INT NOT NULL,
  `player_one_points` INT NOT NULL,
  `player_two_points` INT NOT NULL,
  `player_one_answered` INT,
  `player_two_answered` INT,
  `player_one_connected` BIT,
  `player_two_connected` BIT,
  `question_start` TIME,
  `gamestarted` BIT
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

ALTER TABLE `GameState` ADD CONSTRAINT `GameState_fk0` FOREIGN KEY (`gameid_fk`) REFERENCES `Game`(`gameid`);

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
  'Safer... Stormcloacks, or Imperials?'
);

INSERT INTO Question(text) VALUES (
  'Who is Astrid?'
);

INSERT INTO Question(text) VALUES (
  'Did Cicero betray Brotherhood?'
);

INSERT INTO Question(text) VALUES (
  'Is Ulrfic the High King?'
);

INSERT INTO Answer(questionid, truthfulness, text) VALUES (
  1, 1, 'Death.'
);

INSERT INTO Answer(questionid, truthfulness, text) VALUES (
  1, 0, 'Life.'
);

INSERT INTO Answer(questionid, truthfulness, text) VALUES (
  1, 0, 'Jazz'
);

INSERT INTO Answer(questionid, truthfulness, text) VALUES (
  1, 0, 'Flute.'
);

INSERT INTO Answer(questionid, truthfulness, text) VALUE (
  2, 1, 'Imperials'
);

INSERT INTO Answer(questionid, truthfulness, text) VALUE (
  2, 0, 'Stormcloacks will save Skyrim'
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
  3, 0, 'She is just hot'
);

INSERT INTO Answer(questionid, truthfulness, text) VALUE (
  3, 0, 'I have no moeny!'
);

INSERT INTO Answer(questionid, truthfulness, text) VALUE (
  3, 0, 'The Queen of Forgiveness'
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
  5, 0, 'One day maybe'
);

INSERT INTO Answer(questionid, truthfulness, text) VALUE (
  5, 0, 'Maybe'
);

INSERT INTO Answer(questionid, truthfulness, text) VALUE (
  5, 0, 'Yeeeees'
);

INSERT INTO Question(text) VALUE(
  'What is the newest version of Java?'
);

INSERT INTO Answer(questionid, truthfulness, text) VALUE(
    6, 0, 'Java 8'
);

INSERT INTO Answer(questionid, truthfulness, text) VALUE(
    6, 0, 'J2EE'
);

INSERT INTO Answer(questionid, truthfulness, text) VALUE(
    6, 1, 'Java 10'
);

INSERT INTO Answer(questionid, truthfulness, text) VALUE(
    6, 1, 'Java 9'
);

INSERT INTO Question(text) VALUE(
  'What is derivation?'
);

INSERT INTO Answer(questionid, truthfulness, text) VALUE(
    7, 0, 'Primitive function'
);

INSERT INTO Answer(questionid, truthfulness, text) VALUE(
    7, 1, 'Limit'
);

INSERT INTO Answer(questionid, truthfulness, text) VALUE(
    7, 0, 'Trigonometry equation'
);

INSERT INTO Answer(questionid, truthfulness, text) VALUE(
    7, 0, 'Opposite of integrals'
);

INSERT INTO Question(text) VALUE(
  'How can we survive Black Hole suck?'
);

INSERT INTO Answer(questionid, truthfulness, text) VALUE(
    8, 0, 'Blue Hole'
);

INSERT INTO Answer(questionid, truthfulness, text) VALUE(
    8, 0, 'Black Hole'
);

INSERT INTO Answer(questionid, truthfulness, text) VALUE(
    8, 0, 'Green Hole'
);

INSERT INTO Answer(questionid, truthfulness, text) VALUE(
    8, 1, 'White Hole'
);

INSERT INTO Question(text) VALUE(
    'What is Skyrim?'
);

INSERT INTO Answer(questionid, truthfulness, text) VALUE(
    9, 1, 'Land of Tamriel'
);

INSERT INTO Answer(questionid, truthfulness, text) VALUE(
    9, 0, 'Land of Oblivion'
);

INSERT INTO Answer(questionid, truthfulness, text) VALUE(
    9, 0, 'Land of Morrowind'
);

INSERT INTO Answer(questionid, truthfulness, text) VALUE(
    9, 0, 'Land of Imperical Region'
);

INSERT INTO Question(text) VALUE(
    'How do we pass variables in Java?'
);

INSERT INTO Answer(questionid, truthfulness, text) VALUE(
    10, 0, 'Pass-By-Reference'
);

INSERT INTO Answer(questionid, truthfulness, text) VALUE(
    10, 1,  'Pass-By-Value'
);

INSERT INTO Answer(questionid, truthfulness, text) VALUE(
    10, 0,  'Depends on our choice'
);

INSERT INTO Answer(questionid, truthfulness, text) VALUE(
    10, 0,  'Objects by reference, primitives by value'
);

INSERT INTO Question(text) VALUE(
    'What does J2EE stand for?'
);

INSERT INTO Answer(questionid, truthfulness, text) VALUE(
    11, 0,  'Java 2nd Enterprise Edition'
);

INSERT INTO Answer(questionid, truthfulness, text) VALUE(
    11, 0,  'Java 2 Enterprise Edition'
);

INSERT INTO Answer(questionid, truthfulness, text) VALUE(
    11, 1,  'Java Enterprise Edition'
);

INSERT INTO Answer(questionid, truthfulness, text) VALUE(
    11, 0,  'Java To Enterprise Edition'
);

INSERT INTO Question(text) VALUE(
  'What is the truth about JavaSE and JavaEE'
);

INSERT INTO Answer(questionid, truthfulness, text) VALUE(
  12, 0,  'JavaSE is based on JavaEE'
);

INSERT INTO Answer(questionid, truthfulness, text) VALUE(
  12, 0,  'JavaSE is same as JavaEE'
);

INSERT INTO Answer(questionid, truthfulness, text) VALUE(
  12, 0,  'JavaEE is platform-dependant'
);

INSERT INTO Answer(questionid, truthfulness, text) VALUE(
  12, 1,  'JavaEE stands for network applications'
);

INSERT INTO Question(text) VALUE(
  'What is Hackaton?'
);

INSERT INTO Answer(questionid, truthfulness, text) VALUE(
  13, 1,  'Group programming competition'
);

INSERT INTO Answer(questionid, truthfulness, text) VALUE(
  13, 0,  'Group hacking competition'
);

INSERT INTO Answer(questionid, truthfulness, text) VALUE(
  13, 0,  'Group eating competition'
);

INSERT INTO Answer(questionid, truthfulness, text) VALUE(
  13, 0,  'Group salty competition'
);

INSERT INTO Question(text) VALUE(
  'What is the truth about JavaSE and JavaEE'
);

INSERT INTO Answer(questionid, truthfulness, text) VALUE(
  14, 0,  'JavaSE is based on JavaEE'
);

INSERT INTO Answer(questionid, truthfulness, text) VALUE(
  14, 0,  'JavaSE is same as JavaEE'
);

INSERT INTO Answer(questionid, truthfulness, text) VALUE(
  14, 0,  'JavaEE is platform-dependant'
);

INSERT INTO Answer(questionid, truthfulness, text) VALUE(
  14, 1,  'JavaEE stands for network applications'
);

INSERT INTO Question(text) VALUE(
  'What university stands behind WWW invention?'
);

INSERT INTO Answer(questionid, truthfulness, text) VALUE(
  15, 1,  'Massachusetts Institute of Technology'
);

INSERT INTO Answer(questionid, truthfulness, text) VALUE(
  15, 0,  'California Institute of Technology'
);

INSERT INTO Answer(questionid, truthfulness, text) VALUE(
  15, 0,  'Stanford'
);

INSERT INTO Answer(questionid, truthfulness, text) VALUE(
  15, 0,  'Cambridge'
);

INSERT INTO Question(text) VALUE(
  'Which is the most marketable language in 2018'
);

INSERT INTO Answer(questionid, truthfulness, text) VALUE(
  16, 0,  'JavaScript'
);

INSERT INTO Answer(questionid, truthfulness, text) VALUE(
  16, 0,  'C-Sharp'
);

INSERT INTO Answer(questionid, truthfulness, text) VALUE(
  16, 1,  'Java'
);

INSERT INTO Answer(questionid, truthfulness, text) VALUE(
  16, 0,  'Objective-C'
);

INSERT INTO Question(text) VALUE(
  'What does VHDL stand for?'
);

INSERT INTO Answer(questionid, truthfulness, text) VALUE(
  17, 0,  'Velka Hardwarova Dobrodruzstvi LOL'
);

INSERT INTO Answer(questionid, truthfulness, text) VALUE(
  17, 1,  'VHSIC Hardware Description Language'
);

INSERT INTO Answer(questionid, truthfulness, text) VALUE(
  17, 0,  'Voltage Hardware Description Language'
);

INSERT INTO Answer(questionid, truthfulness, text) VALUE(
  17, 0,  'Verified Hardware Description Language'
);

INSERT INTO Question(text) VALUE(
  'Who is Odin?'
);

INSERT INTO Answer(questionid, truthfulness, text) VALUE(
    18, 0, 'Viking God of Thunder'
);

INSERT INTO Answer(questionid, truthfulness, text) VALUE(
    18, 0, 'Viking God of Sea'
);

INSERT INTO Answer(questionid, truthfulness, text) VALUE(
    18, 1, 'Viking God of War'
);

INSERT INTO Answer(questionid, truthfulness, text) VALUE(
    18, 0, 'Viking God of Death'
);

INSERT INTO Question(text) VALUE(
    'What is special about Odin appearance?'
);

INSERT INTO Answer(questionid, truthfulness, text) VALUE(
    19, 0, 'He has one leg'
);

INSERT INTO Answer(questionid, truthfulness, text) VALUE(
    19, 1, 'He has one eye'
);

INSERT INTO Answer(questionid, truthfulness, text) VALUE(
    19, 0, 'He has one arm'
);

INSERT INTO Answer(questionid, truthfulness, text) VALUE(
    19, 0, 'He has one ear'
);

INSERT INTO Question(text) VALUE(
    'What framework stand for dependency management?'
);

INSERT INTO Answer(questionid, truthfulness, text) VALUE(
    20, 1, 'Maven'
);

INSERT INTO Answer(questionid, truthfulness, text) VALUE(
    20, 0, 'Spring'
);

INSERT INTO Answer(questionid, truthfulness, text) VALUE(
    20, 0, 'EJB'
);

INSERT INTO Answer(questionid, truthfulness, text) VALUE(
    20, 0, 'WildFly'
);

INSERT INTO Question(text) VALUE(
    'What area could not Anastasia touch in Fifty Shades of Grey?'
);

INSERT INTO Answer(questionid, truthfulness, text) VALUE(
    21, 0, 'His shoulders'
);

INSERT INTO Answer(questionid, truthfulness, text) VALUE(
    21, 1, 'His chest'
);

INSERT INTO Answer(questionid, truthfulness, text) VALUE(
    21, 0, 'His bell'
);

INSERT INTO Answer(questionid, truthfulness, text) VALUE(
    21, 0, 'His hair'
);

INSERT INTO Question(text) VALUE(
    'Blizzard admitted their mistakes made during Cataclysm and Pandaria. When?'
);

INSERT INTO Answer(questionid, truthfulness, text) VALUE(
    22, 0, 'BlizzCon 2014'
);

INSERT INTO Answer(questionid, truthfulness, text) VALUE(
    22, 1, 'BlizzCon 2015'
);

INSERT INTO Answer(questionid, truthfulness, text) VALUE(
    22, 0, 'BlizzCon 2016'
);

INSERT INTO Answer(questionid, truthfulness, text) VALUE(
    22, 0, 'BlizzCon 2017'
);

INSERT INTO Question(text) VALUE(
    'What exploit was DisguisedToast banned for in 2017?'
);

INSERT INTO Answer(questionid, truthfulness, text) VALUE(
    23, 0, 'He showed Succubus and Auchenai infinite exploit'
);

INSERT INTO Answer(questionid, truthfulness, text) VALUE(
    23, 0, 'He had 8 minions on board at the one specific moment'
);

INSERT INTO Answer(questionid, truthfulness, text) VALUE(
    23, 1, 'He played Mirage Caller to create unkiillable minion'
);

INSERT INTO Answer(questionid, truthfulness, text) VALUE(
    23, 0, 'He played Shudderwock with Yogg-Saron'
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