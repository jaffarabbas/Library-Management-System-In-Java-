-- phpMyAdmin SQL Dump
-- version 4.9.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jan 09, 2021 at 08:50 PM
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
(37, '7', 'harry', 'j,k rowling', '1233333333', '2021-01-06 17:36:26', 1),
(38, '33', 'dsfsd', 'sdfsdf', 'sdfsdf', '2021-01-06 18:14:32', 1),
(39, '55', 'fdgdf', 'dfgdfg', 'ertertert', '2021-01-06 18:20:05', 1),
(40, '555', 'samin', '45345793457934', 'asdsd', '2021-01-06 19:36:48', 1);

-- --------------------------------------------------------

--
-- Table structure for table `defaulters`
--

CREATE TABLE `defaulters` (
  `id` int(11) NOT NULL,
  `bookId` varchar(200) NOT NULL,
  `memberId` varchar(200) DEFAULT NULL,
  `issueTime` timestamp NOT NULL DEFAULT current_timestamp(),
  `renew_count` int(11) DEFAULT 0,
  `expiry_date` varchar(200) DEFAULT NULL,
  `fine` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

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
(1, 'samin', '123');

-- --------------------------------------------------------

--
-- Table structure for table `member_collection`
--

CREATE TABLE `member_collection` (
  `name` varchar(100) NOT NULL,
  `number` varchar(100) NOT NULL,
  `address` varchar(300) NOT NULL,
  `card_number` varchar(100) NOT NULL,
  `insertion_date` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `member_collection`
--

INSERT INTO `member_collection` (`name`, `number`, `address`, `card_number`, `insertion_date`) VALUES
('saminsamin', '03015545689', 'kjasdhkjas', '2323', '2021-01-06 19:38:14'),
('Samin', '031417147714', 'pakisatan', '6969', '2020-12-12 19:00:00'),
('ooopp', 'bvngfhfgh', 'gjghjghj', 'dgfgdfgfg', '2021-01-05 08:29:35'),
('09', 'fdgdfg', 'dfgdfg', 'ertertert', '2021-01-06 18:14:52'),
('ttt', 'retert', 'ertert', '22', '2021-01-06 18:15:11');

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
-- Indexes for table `defaulters`
--
ALTER TABLE `defaulters`
  ADD PRIMARY KEY (`bookId`),
  ADD UNIQUE KEY `id` (`id`),
  ADD KEY `memberId` (`memberId`);

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
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=41;

--
-- AUTO_INCREMENT for table `defaulters`
--
ALTER TABLE `defaulters`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=40;

--
-- AUTO_INCREMENT for table `issued_books`
--
ALTER TABLE `issued_books`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=24;

--
-- AUTO_INCREMENT for table `login`
--
ALTER TABLE `login`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `defaulters`
--
ALTER TABLE `defaulters`
  ADD CONSTRAINT `defaulters_ibfk_1` FOREIGN KEY (`bookId`) REFERENCES `book_collection` (`sno`),
  ADD CONSTRAINT `defaulters_ibfk_2` FOREIGN KEY (`memberId`) REFERENCES `member_collection` (`card_number`);

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
