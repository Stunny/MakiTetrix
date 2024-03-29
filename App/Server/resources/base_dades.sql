-- phpMyAdmin SQL Dump
-- version 4.6.5.2
-- https://www.phpmyadmin.net/
--
-- Servidor: localhost:3306
-- Temps de generació: 24-04-2017 a les 10:27:34
-- Versió del servidor: 5.6.35
-- Versió de PHP: 7.1.1

CREATE DATABASE IF NOT EXISTS MakiTetris;
USE MakiTetris;

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";

DROP TABLE IF EXISTS Partida CASCADE;
DROP TABLE IF EXISTS Replay CASCADE;
DROP TABLE IF EXISTS DefaultKeys CASCADE;
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
  gaming BOOLEAN,
  startingGameTime VARCHAR(255),
  PRIMARY KEY (user, mail)
) ENGINE = InnoDB DEFAULT CHARSET = utf8;

CREATE TABLE Replay(
	user VARCHAR(255),
	ID INT DEFAULT 0,
	Order_ INT,
    move VARCHAR(255),
    path VARCHAR(255),
    FOREIGN KEY (user) REFERENCES Login (user)
);

CREATE TABLE Partida(
	user varchar(255) NOT NULL,
    score INT,
    time VARCHAR(255),
    game_date VARCHAR(255),
    max_espectators INT,
    replay_path VARCHAR(255),
    FOREIGN KEY (user) REFERENCES Login (user)
);

CREATE TABLE DefaultKeys (
  user varchar(255) NOT NULL DEFAULT '',
  derecha int(255) NOT NULL,
  izquierda int(255) NOT NULL,
  abajo int(255) NOT NULL,
  rderecha int(255) NOT NULL,
  rizquierda int(255) NOT NULL,
  pause int(255) NOT NULL,
  FOREIGN KEY (user) REFERENCES Login (user)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

  ALTER TABLE DefaultKeys
  ADD PRIMARY KEY (user);