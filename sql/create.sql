-- phpMyAdmin SQL Dump
-- version 4.0.10deb1
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Mar 15, 2016 at 07:57 PM
-- Server version: 5.5.46-0ubuntu0.14.04.2
-- PHP Version: 5.5.9-1ubuntu4.14

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";

--
-- Database: `imagecrop`
--

-- --------------------------------------------------------

--
-- Table structure for table `Errors`
--

CREATE TABLE IF NOT EXISTS `Errors` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'record id',
  `timestamp` varchar(256) NOT NULL COMMENT 'timestamp of error',
  `loglevel` varchar(64) NOT NULL COMMENT 'Log Level: ERROR, WARN, INFO, DEBUG, TRACE',
  `errormessage` varchar(256) NOT NULL COMMENT 'High level error message',
  `stacktrace` text COMMENT 'Stacktrace containing the error message',
  `created` datetime DEFAULT NULL,
  `modified` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='Table holding the error messages coming from an application.' AUTO_INCREMENT=1 ;

--
-- Triggers `Errors`
--
DROP TRIGGER IF EXISTS `ERROR_CREATE_TIMESTAMP`;
DELIMITER //
CREATE TRIGGER `ERROR_CREATE_TIMESTAMP` BEFORE INSERT ON `Errors`
 FOR EACH ROW SET NEW.CREATED = NOW()
//
DELIMITER ;
DROP TRIGGER IF EXISTS `ERROR_UPDATE_TIMESTAMP`;
DELIMITER //
CREATE TRIGGER `ERROR_UPDATE_TIMESTAMP` BEFORE UPDATE ON `Errors`
 FOR EACH ROW SET NEW.MODIFIED = NOW()
//
DELIMITER ;