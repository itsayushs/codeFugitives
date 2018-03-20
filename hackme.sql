-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Mar 20, 2018 at 09:18 PM
-- Server version: 10.1.19-MariaDB
-- PHP Version: 5.6.28

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `hackme`
--

-- --------------------------------------------------------

--
-- Table structure for table `bands`
--

CREATE TABLE `bands` (
  `s_id` int(4) NOT NULL,
  `f_id` int(4) NOT NULL,
  `params` int(4) NOT NULL,
  `band_id` int(4) NOT NULL,
  `lats` varchar(15) NOT NULL,
  `longd` varchar(15) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `bands`
--

INSERT INTO `bands` (`s_id`, `f_id`, `params`, `band_id`, `lats`, `longd`) VALUES
(2234, 199, 2, 69, '26.88168554', '75.81342851'),
(1234, 1900, 4, 12, '26.88171232', '75.81335392');


-- --------------------------------------------------------

--
-- Table structure for table `station`
--

CREATE TABLE `station` (
  `station_id` int(4) NOT NULL,
  `station_loc` varchar(30) NOT NULL,
  `passwd` varchar(50) NOT NULL,
  `insp_name` varchar(25) NOT NULL,
  `latitude` varchar(10) NOT NULL,
  `longitude` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `station`
--

INSERT INTO `station` (`station_id`, `station_loc`, `passwd`, `insp_name`, `latitude`, `longitude`) VALUES
(1234, 'jaipur', 'e19d5cd5af0378da05f63f891c7467af', 'abcdaaa', '26.8776173', '75.7829254'),
(2234, 'jaipur', '5f4dcc3b5aa765d61d8327deb882cf99', 'abcdaaa', '26.8775429', '75.7829255');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
