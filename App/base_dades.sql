-- phpMyAdmin SQL Dump
-- version 4.6.5.2
-- https://www.phpmyadmin.net/
--
-- Servidor: localhost:3306
-- Temps de generació: 24-04-2017 a les 10:27:34
-- Versió del servidor: 5.6.35
-- Versió de PHP: 7.1.1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";

--
-- Base de dades: `MakiTetris`
--

-- --------------------------------------------------------

--
-- Estructura de la taula `Login`
--

CREATE TABLE `Login` (
  `user` varchar(255) NOT NULL,
  `mail` varchar(255) NOT NULL,
  `password` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Bolcant dades de la taula `Login`
--

INSERT INTO `Login` (`user`, `mail`, `password`) VALUES
('aapppa', 'll', 'wed'),
('bullshit', 'megabullshit', 'ultrabullshit'),
('miquelator', 'miquelet-sans@hotmail.com', 'Goddammit96'),
('miquelet', 'sansnosanz@gmail.com', 'Goddammit96'),
('moltbondia', 'ssss', 'wed');

--
-- Indexos per taules bolcades
--

--
-- Index de la taula `Login`
--
ALTER TABLE `Login`
  ADD UNIQUE KEY `user` (`user`),
  ADD UNIQUE KEY `mail` (`mail`);
