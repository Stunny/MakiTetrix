-- phpMyAdmin SQL Dump
-- version 4.6.5.2
-- https://www.phpmyadmin.net/
--
-- Servidor: localhost:3306
-- Temps de generació: 24-04-2017 a les 10:27:34
-- Versió del servidor: 5.6.35
-- Versió de PHP: 7.1.1

-- CREATE DATABASE MakiTetris;
USE MakiTetris;

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";

DROP TABLE IF EXISTS Login CASCADE;
CREATE TABLE Login (
  user varchar(255) NOT NULL,
  mail varchar(255) NOT NULL,
  password varchar(50) NOT NULL,
  connected BOOLEAN,
  register_date VARCHAR(255),
  last_login VARCHAR(255),
  number_games INT,
  total_points INT,
  PRIMARY KEY (user, mail)
) ENGINE = InnoDB DEFAULT CHARSET = utf8;

INSERT INTO Login (user, mail, password, connected, register_date, last_login, number_games, total_points) VALUES
('userName1', 'email1@mail.com', 'pass1', true, (SELECT DATE_FORMAT(CURDATE(), '%d/%m/%Y') AS your_date), '03/06/1996', 20, 1240),
('angel', 'angel@mail.com', 'pass2', false, (SELECT DATE_FORMAT(CURDATE(), '%d/%m/%Y') AS your_date), '20/11/2020', 12, 2131),
('test', 'test@mail.com', 'pass3', true, (SELECT DATE_FORMAT(CURDATE(), '%d/%m/%Y') AS your_date), '21/05/2011', 5, 2311);

DROP TABLE IF EXISTS Partida CASCADE;
CREATE TABLE Partida(
	user varchar(255) NOT NULL,
    score INT,
    time INT,
    game_date VARCHAR(255),
    max_espectators INT,
    replay_path VARCHAR(255),
    FOREIGN KEY (user) REFERENCES Login (user)
);

INSERT INTO Partida(user, score, time, game_date, max_espectators, replay_path) VALUES
('angel', 500, 300, '03/06/2016 20:39', 10, 'C/'),
('angel', 1000, 450, '18/10/2017 15:39', 13, 'C/'),
('angel2', 1000, 450, '30/04/2015 10:39', 20, 'C/'),
('angel2', 100, 450, '30/04/2015 10:39', 20, 'C/');

  SELECT * FROM Login;
  SELECT * FROM Partida;
  