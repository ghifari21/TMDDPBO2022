-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jun 18, 2022 at 10:29 AM
-- Server version: 10.4.22-MariaDB
-- PHP Version: 8.1.2

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `db_gamedpbo`
--

-- --------------------------------------------------------

--
-- Table structure for table `texperiences`
--

CREATE TABLE `texperiences` (
  `username` varchar(255) NOT NULL,
  `adapt` int(11) NOT NULL,
  `fall` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `texperiences`
--

INSERT INTO `texperiences` (`username`, `adapt`, `fall`) VALUES
('Bazo', 113, 228),
('Chad', 12, 2),
('Gaming', 24, 4),
('GG', 22, 7),
('Ghifari', 31, 4),
('GOsty', 10, 70),
('Jiden', 5, 1),
('Jojo', 13, 1),
('Kuch', 3, 2),
('Oki', 35, 3),
('Pojan', 15, 2),
('udin', 12, 42);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `texperiences`
--
ALTER TABLE `texperiences`
  ADD PRIMARY KEY (`username`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
