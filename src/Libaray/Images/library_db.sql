-- phpMyAdmin SQL Dump
-- version 4.9.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Dec 16, 2020 at 11:29 AM
-- Server version: 10.4.8-MariaDB
-- PHP Version: 7.3.11

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `library_db`
--

-- --------------------------------------------------------

--
-- Table structure for table `book_collection`
--

CREATE TABLE `book_collection` (
  `id` int(11) NOT NULL,
  `sno` varchar(100) NOT NULL,
  `name` varchar(100) NOT NULL,
  `isbn` varchar(250) NOT NULL,
  `auther` varchar(100) NOT NULL,
  `insertion_date` timestamp NOT NULL DEFAULT current_timestamp(),
  `availiblity` tinyint(1) NOT NULL DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `book_collection`
--

INSERT INTO `book_collection` (`id`, `sno`, `name`, `isbn`, `auther`, `insertion_date`, `availiblity`) VALUES
(5, '10', 'harry potter', 'j.k Rowling', '12361827313dfdf', '2020-12-12 19:00:00', 0),
(8, '3', 'djkshkjsdf', 'kjdhgkjdfg', 'lkerjtlkert', '2020-12-13 19:00:00', 0),
(9, '456', '46456', '54645', '645645', '2020-12-15 16:45:28', 1),
(15, '10', 'harry potter', '12361827313dfdf', 'j.k Rowling', '2020-12-16 07:15:21', 1),
(16, '10', 'harry potter', '1', 'j.k Rowling', '2020-12-16 07:15:42', 1);

-- --------------------------------------------------------

--
-- Table structure for table `issued_books`
--

CREATE TABLE `issued_books` (
  `id` int(11) NOT NULL,
  `bookId` varchar(200) NOT NULL,
  `memberId` varchar(200) DEFAULT NULL,
  `issueTime` timestamp NOT NULL DEFAULT current_timestamp(),
  `renew_count` int(11) DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `issued_books`
--

INSERT INTO `issued_books` (`id`, `bookId`, `memberId`, `issueTime`, `renew_count`) VALUES
(3, '10', '6969', '2020-12-14 11:00:09', 1),
(4, '3', '2', '2020-12-14 14:15:45', 1);

-- --------------------------------------------------------

--
-- Table structure for table `login`
--

CREATE TABLE `login` (
  `id` int(11) NOT NULL,
  `name` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `login`
--

INSERT INTO `login` (`id`, `name`, `password`) VALUES
(1, 'jaffar', '123');

-- --------------------------------------------------------

--
-- Table structure for table `member_collection`
--

CREATE TABLE `member_collection` (
  `id` int(11) NOT NULL,
  `name` varchar(100) NOT NULL,
  `number` varchar(100) NOT NULL,
  `address` varchar(300) NOT NULL,
  `card_number` varchar(100) NOT NULL,
  `insertion_date` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `member_collection`
--

INSERT INTO `member_collection` (`id`, `name`, `number`, `address`, `card_number`, `insertion_date`) VALUES
(1, 'jaffar', '345873485', 'kdfjglkdjg dkfjgkjdfg', '54689', '2020-12-09'),
(2, 'samin', '03343075507', 'jamsih rode wali galiii ', 'fa19-bsse-0010', '2020-12-09'),
(3, 'Samin dear', '87345893445', 'gjhdfghkjdfhgkjdfhgkjdfg', '100', '2020-12-11'),
(4, 'ahmed', '031417147714', 'pakisatan', '6969', '2020-12-13'),
(5, '2', 'gg', 'gg', '2', '2020-12-14');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `book_collection`
--
ALTER TABLE `book_collection`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `sno` (`sno`,`isbn`);

--
-- Indexes for table `issued_books`
--
ALTER TABLE `issued_books`
  ADD PRIMARY KEY (`bookId`),
  ADD UNIQUE KEY `id` (`id`),
  ADD KEY `memberId` (`memberId`);

--
-- Indexes for table `login`
--
ALTER TABLE `login`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `member_collection`
--
ALTER TABLE `member_collection`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `number` (`number`),
  ADD UNIQUE KEY `address` (`address`),
  ADD UNIQUE KEY `card_number` (`card_number`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `book_collection`
--
ALTER TABLE `book_collection`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=20;

--
-- AUTO_INCREMENT for table `issued_books`
--
ALTER TABLE `issued_books`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `login`
--
ALTER TABLE `login`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `member_collection`
--
ALTER TABLE `member_collection`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `issued_books`
--
ALTER TABLE `issued_books`
  ADD CONSTRAINT `issued_books_ibfk_1` FOREIGN KEY (`bookId`) REFERENCES `book_collection` (`sno`),
  ADD CONSTRAINT `issued_books_ibfk_2` FOREIGN KEY (`memberId`) REFERENCES `member_collection` (`card_number`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
