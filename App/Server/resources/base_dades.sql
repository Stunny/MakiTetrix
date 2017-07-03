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

--
-- Base de dades: `MakiTetris`
--

-- --------------------------------------------------------

--
-- Estructura de la taula `Login`
--
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

--
-- Bolcant dades de la taula `Login`
--

INSERT INTO Login (user, mail, password, connected, register_date, last_login, number_games, total_points) VALUES
('userName1', 'email1@mail.com', 'pass1', true, (SELECT DATE_FORMAT(CURDATE(), '%d/%m/%Y') AS your_date), '03/06/1996', 20, 1240),
('angel', 'angel@mail.com', 'pass2', false, (SELECT DATE_FORMAT(CURDATE(), '%d/%m/%Y') AS your_date), '20/11/2020', 12, 2131),
('test', 'test@mail.com', 'pass3', true, (SELECT DATE_FORMAT(CURDATE(), '%d/%m/%Y') AS your_date), '21/05/2011', 5, 2311);
--
-- Indexos per taules bolcades
--

--
-- Index de la taula `Login`
--
/*
ALTER TABLE `Login`
  ADD UNIQUE KEY `user` (`user`),
  ADD UNIQUE KEY `mail` (`mail`);
*/
  SELECT * FROM Login;
  