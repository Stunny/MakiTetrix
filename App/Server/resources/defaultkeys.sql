-- phpMyAdmin SQL Dump
-- version 4.6.5.2
-- https://www.phpmyadmin.net/
--
-- Servidor: localhost:3306
-- Temps de generació: 20-07-2017 a les 20:12:55
-- Versió del servidor: 5.6.35
-- Versió de PHP: 7.1.1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";

--
-- Base de dades: `MakiTetris`
--

-- --------------------------------------------------------

--
-- Estructura de la taula `DefaultKeys`
--

CREATE TABLE `DefaultKeys` (
  `user` varchar(255) NOT NULL DEFAULT '',
  `derecha` int(255) NOT NULL,
  `izquierda` int(255) NOT NULL,
  `abajo` int(255) NOT NULL,
  `rderecha` int(255) NOT NULL,
  `rizquierda` int(255) NOT NULL,
  `pause` int(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Indexos per taules bolcades
--

--
-- Index de la taula `DefaultKeys`
--
ALTER TABLE `DefaultKeys`
  ADD PRIMARY KEY (`user`);