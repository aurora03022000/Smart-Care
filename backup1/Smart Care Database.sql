-- phpMyAdmin SQL Dump
-- version 5.1.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Dec 02, 2022 at 05:47 PM
-- Server version: 10.4.19-MariaDB
-- PHP Version: 7.3.28

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `smartcare`
--

-- --------------------------------------------------------

--
-- Table structure for table `current_ids_admin`
--

CREATE TABLE `current_ids_admin` (
  `current_id` int(10) NOT NULL,
  `current_user_clicked_id` int(10) NOT NULL,
  `current_patient_clicked_id` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `current_ids_admin`
--

INSERT INTO `current_ids_admin` (`current_id`, `current_user_clicked_id`, `current_patient_clicked_id`) VALUES
(1, 36, 116);

-- --------------------------------------------------------

--
-- Table structure for table `current_log_open`
--

CREATE TABLE `current_log_open` (
  `id` int(100) NOT NULL,
  `log_id` int(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `current_log_open`
--

INSERT INTO `current_log_open` (`id`, `log_id`) VALUES
(1, 48);

-- --------------------------------------------------------

--
-- Table structure for table `current_patient`
--

CREATE TABLE `current_patient` (
  `id` int(100) NOT NULL,
  `current_patient_id` int(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `current_patient`
--

INSERT INTO `current_patient` (`id`, `current_patient_id`) VALUES
(1, 94);

-- --------------------------------------------------------

--
-- Table structure for table `current_patient_monitored`
--

CREATE TABLE `current_patient_monitored` (
  `id` int(100) NOT NULL,
  `patient_id` int(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `current_patient_monitored`
--

INSERT INTO `current_patient_monitored` (`id`, `patient_id`) VALUES
(1, 97);

-- --------------------------------------------------------

--
-- Table structure for table `current_shared_patient`
--

CREATE TABLE `current_shared_patient` (
  `id` int(100) NOT NULL,
  `current_shared_id` int(100) NOT NULL,
  `shared_option` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `current_shared_patient`
--

INSERT INTO `current_shared_patient` (`id`, `current_shared_id`, `shared_option`) VALUES
(1, 97, 'One');

-- --------------------------------------------------------

--
-- Table structure for table `current_user_id`
--

CREATE TABLE `current_user_id` (
  `id` int(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `current_user_id`
--

INSERT INTO `current_user_id` (`id`) VALUES
(0);

-- --------------------------------------------------------

--
-- Table structure for table `deviceid`
--

CREATE TABLE `deviceid` (
  `device_id` int(100) NOT NULL,
  `device_status` varchar(100) NOT NULL,
  `ipAddress` varchar(255) NOT NULL,
  `isExist` int(5) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `deviceid`
--

INSERT INTO `deviceid` (`device_id`, `device_status`, `ipAddress`, `isExist`) VALUES
(202201, 'Not Available', '192.168.254.115\r', 1),
(2019590, 'Not Available', '192.168.2.1', 1);

-- --------------------------------------------------------

--
-- Table structure for table `history_log`
--

CREATE TABLE `history_log` (
  `history_id` int(100) NOT NULL,
  `activity` varchar(255) NOT NULL,
  `dateStamp` varchar(255) NOT NULL,
  `timeStamp` varchar(255) NOT NULL,
  `user` int(100) NOT NULL,
  `isExist` int(5) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `history_log`
--

INSERT INTO `history_log` (`history_id`, `activity`, `dateStamp`, `timeStamp`, `user`, `isExist`) VALUES
(180, 'Shared a patient named Juan Dela Cruz', 'December 02, 2022', '12:48 am', 0, 1),
(181, 'Shared a patient named Juan Dela Cruz', 'December 02, 2022', '12:51 am', 0, 1),
(182, 'Shared a patient named Juan Dela Cruz', 'December 02, 2022', '12:53 am', 0, 1),
(183, 'Shared a patient named Juan Dela Cruz', 'December 02, 2022', '12:56 am', 0, 1),
(184, 'Shared a patient named Juan Dela Cruz', 'December 02, 2022', '12:57 am', 0, 1),
(185, 'Shared a patient named Juan Dela Cruz', 'December 02, 2022', '12:57 am', 0, 1),
(186, 'Shared a patient named Juan Dela Cruz', 'December 02, 2022', '12:58 am', 0, 1),
(187, 'Shared a patient named Juan Dela Cruz', 'December 02, 2022', '12:58 am', 0, 1),
(188, 'Shared a patient named Juan Dela Cruz', 'December 02, 2022', '12:59 am', 0, 1),
(189, 'Shared a patient named Juan Dela Cruz', 'December 02, 2022', '01:00 am', 0, 1),
(190, 'Shared a patient named Juan Dela Cruz', 'December 02, 2022', '01:00 am', 0, 1),
(191, 'Shared a patient named Juan Dela Cruz', 'December 02, 2022', '01:02 am', 0, 1),
(192, 'Shared a patient named Juan Dela Cruz', 'December 02, 2022', '01:03 am', 0, 1),
(193, 'Shared a patient named Juan Dela Cruz', 'December 02, 2022', '01:03 am', 0, 1),
(194, 'Shared a patient named Juan Dela Cruz', 'December 02, 2022', '01:05 am', 0, 1),
(195, 'Stopped patient sharing to medical staff named Mea Gwen Gulle', 'December 02, 2022', '01:05 am', 0, 1),
(196, 'Shared a patient named Juan Dela Cruz', 'December 02, 2022', '01:05 am', 0, 1),
(197, 'Stopped patient sharing to medical staff named Mea Gwen Gulle', 'December 02, 2022', '01:05 am', 0, 1),
(198, 'Shared a patient named Juan Dela Cruz', 'December 02, 2022', '01:07 am', 0, 1),
(199, 'Stopped patient sharing to medical staff named Mea Gwen Gulle', 'December 02, 2022', '01:07 am', 0, 1),
(200, 'Deleted Staff named Mea Gwen Gulle', 'December 02, 2022', '01:08 am', 0, 1),
(201, 'Updated patient health records named Juan Dela Cruz', 'December 02, 2022', '04:06 am', 0, 1),
(202, 'Updated patient health records named Juan Dela Cruz', 'December 02, 2022', '04:09 am', 0, 1),
(203, 'Updated patient health records named Juan Dela Cruz', 'December 02, 2022', '04:09 am', 0, 1),
(204, 'Admitted a patient named Brenda', 'December 02, 2022', '04:32 am', 0, 1),
(205, 'Dismissed patient named Brenda', 'December 02, 2022', '04:32 am', 0, 1),
(206, 'Admitted a patient named dasd', 'December 02, 2022', '04:33 am', 0, 1),
(207, 'Dismissed patient named dasd', 'December 02, 2022', '04:33 am', 0, 1),
(208, 'Admitted a patient named das', 'December 02, 2022', '04:35 am', 0, 1),
(209, 'Dismissed patient named das', 'December 02, 2022', '04:39 am', 0, 1),
(210, 'Admitted a patient named DSADAS', 'December 02, 2022', '04:46 am', 0, 1),
(211, 'Logged in', 'December 02, 2022', '04:22 pm', 0, 1);

-- --------------------------------------------------------

--
-- Table structure for table `hospital_information`
--

CREATE TABLE `hospital_information` (
  `hospital_information_id` int(10) NOT NULL,
  `hospital_name` varchar(255) NOT NULL,
  `hospital_address` varchar(255) NOT NULL,
  `hospital_email` varchar(255) NOT NULL,
  `hospital_date_acquired` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `hospital_information`
--

INSERT INTO `hospital_information` (`hospital_information_id`, `hospital_name`, `hospital_address`, `hospital_email`, `hospital_date_acquired`) VALUES
(1, 'Notre Dame of Marbel University', 'Purok Bayanihan Barangay Santa Cruzdddddddddddddddd', 'Allahvalley@gmail.com', 'December 15, 1999');

-- --------------------------------------------------------

--
-- Table structure for table `patient`
--

CREATE TABLE `patient` (
  `patient_id` int(100) NOT NULL,
  `patient_name` varchar(255) NOT NULL,
  `gender` varchar(10) NOT NULL,
  `number` bigint(100) NOT NULL,
  `birthdate` varchar(255) NOT NULL,
  `address` varchar(255) NOT NULL,
  `disease` varchar(255) NOT NULL,
  `deviceID` bigint(100) NOT NULL,
  `room` bigint(100) NOT NULL,
  `encoder` int(255) NOT NULL,
  `share_id` varchar(100) NOT NULL,
  `date_admit` varchar(255) NOT NULL,
  `date_dismiss` varchar(255) NOT NULL,
  `isExist` int(5) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `patient`
--

INSERT INTO `patient` (`patient_id`, `patient_name`, `gender`, `number`, `birthdate`, `address`, `disease`, `deviceID`, `room`, `encoder`, `share_id`, `date_admit`, `date_dismiss`, `isExist`) VALUES
(111, 'Ybonie C. Somogod', 'Male', 909090909, '01/03/2001', 'Brgy. Morales Centro 1', 'tb', 2019590, 1, 0, '', 'November 30, 2022', 'November 30, 2022', 0),
(112, 'Juan Dela Cruz', 'Female', 9755462273, 'February 18, 2000', 'Purok Bayanihan Barangay Santa Cruz Koronadal City', 'Stroke', 202201, 2, 33, '', 'November 31, 2022', '', 1),
(113, 'Brenda', 'Male', 213, 'December 05, 2022', 'das', 'das', 2019590, 1, 0, '', 'December 02, 2022', 'December 02, 2022', 0),
(114, 'dasd', 'Female', 213, 'December 22, 2022', 'das', 'das', 2019590, 1, 0, '', 'December 02, 2022', 'December 02, 2022', 0),
(115, 'das', 'Female', 213, 'December 12, 2022', 'das', 'dsa', 2019590, 1, 0, '', 'December 02, 2022', 'December 02, 2022', 0),
(116, 'DSADAS', 'Male', 312, 'December 06, 2022', 'DASD', 'DAS', 2019590, 1, 0, '', 'December 02, 2022', '', 1);

-- --------------------------------------------------------

--
-- Table structure for table `patient_health_log`
--

CREATE TABLE `patient_health_log` (
  `log_id` int(100) NOT NULL,
  `patient_id` int(100) NOT NULL,
  `pulse_rate` varchar(255) NOT NULL,
  `oxygen_saturation` varchar(255) NOT NULL,
  `body_temperature` varchar(255) NOT NULL,
  `room_temperature` varchar(255) NOT NULL,
  `humidity` varchar(255) NOT NULL,
  `air_quality` varchar(255) NOT NULL,
  `blood_pressure` varchar(255) NOT NULL,
  `respiratory_rate` varchar(255) NOT NULL,
  `emergency` int(10) NOT NULL,
  `isExist` int(5) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `patient_health_log`
--

INSERT INTO `patient_health_log` (`log_id`, `patient_id`, `pulse_rate`, `oxygen_saturation`, `body_temperature`, `room_temperature`, `humidity`, `air_quality`, `blood_pressure`, `respiratory_rate`, `emergency`, `isExist`) VALUES
(38, 111, '0', '0', '0', '0', '0', '0', '0', '0', 0, 0),
(39, 112, '2', '5', '3', '3', '2', '22', '2', '1', 0, 1),
(40, 113, '0', '0', '0', '0', '0', '0', '0', '0', 0, 0),
(41, 114, '0', '0', '0', '0', '0', '0', '0', '0', 0, 0),
(42, 115, '0', '0', '0', '0', '0', '0', '0', '0', 0, 0),
(43, 116, '0', '0', '0', '0', '0', '0', '0', '0', 0, 1);

-- --------------------------------------------------------

--
-- Table structure for table `patient_logs`
--

CREATE TABLE `patient_logs` (
  `id` int(100) NOT NULL,
  `patient_id` int(100) NOT NULL,
  `date` varchar(100) NOT NULL,
  `time` varchar(100) NOT NULL,
  `pulse_rate` int(100) NOT NULL,
  `oxygen_saturation` int(100) NOT NULL,
  `body_temperature` int(100) NOT NULL,
  `blood_pressure` varchar(255) NOT NULL,
  `respiratory_rate` int(100) NOT NULL,
  `room_temperature` int(100) NOT NULL,
  `humidity` int(100) NOT NULL,
  `air_quality` int(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `patient_logs`
--

INSERT INTO `patient_logs` (`id`, `patient_id`, `date`, `time`, `pulse_rate`, `oxygen_saturation`, `body_temperature`, `blood_pressure`, `respiratory_rate`, `room_temperature`, `humidity`, `air_quality`) VALUES
(49, 112, 'November 30, 2022', '10:30 am', 0, 0, 0, '', 0, 0, 0, 0),
(50, 111, 'November 30, 2022', '01:00 am', 0, 0, 0, '', 0, 0, 0, 0);

-- --------------------------------------------------------

--
-- Table structure for table `previous_logs`
--

CREATE TABLE `previous_logs` (
  `id` int(100) NOT NULL,
  `previous_log_id` int(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `previous_logs`
--

INSERT INTO `previous_logs` (`id`, `previous_log_id`) VALUES
(1, 97);

-- --------------------------------------------------------

--
-- Table structure for table `rawdata`
--

CREATE TABLE `rawdata` (
  `raw_data_id` int(11) NOT NULL,
  `raw_value` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `rawdata`
--

INSERT INTO `rawdata` (`raw_data_id`, `raw_value`) VALUES
(1, 'air:41humidity: NANtemperature: NANdevice:202201ip:192.168.254.115\r');

-- --------------------------------------------------------

--
-- Table structure for table `request`
--

CREATE TABLE `request` (
  `id` int(100) NOT NULL,
  `user_id` int(100) NOT NULL,
  `message` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `request`
--

INSERT INTO `request` (`id`, `user_id`, `message`) VALUES
(1, 6, 'law ay');

-- --------------------------------------------------------

--
-- Table structure for table `room`
--

CREATE TABLE `room` (
  `room_number` int(100) NOT NULL,
  `room_status` varchar(255) NOT NULL,
  `isExist` int(5) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `room`
--

INSERT INTO `room` (`room_number`, `room_status`, `isExist`) VALUES
(1, 'Not Available', 1),
(2, 'Not Available', 1),
(4, 'Available', 1);

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `user_id` int(100) NOT NULL,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) CHARACTER SET latin1 COLLATE latin1_general_cs NOT NULL,
  `number` bigint(15) NOT NULL,
  `staff_name` varchar(255) NOT NULL,
  `birthdate` varchar(255) NOT NULL,
  `gender` varchar(10) NOT NULL,
  `address` varchar(255) NOT NULL,
  `date_registered` varchar(255) NOT NULL,
  `date_deleted` varchar(255) NOT NULL,
  `profile` varchar(100) NOT NULL,
  `isExist` int(5) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`user_id`, `username`, `password`, `number`, `staff_name`, `birthdate`, `gender`, `address`, `date_registered`, `date_deleted`, `profile`, `isExist`) VALUES
(0, 'admin', '3208Marlou123', 0, 'Administrator', '', '', '', '', '', '', 1),
(33, 'tekerj', 'jamjamteker2', 9638186993, 'James Lloyd G. Teker', 'April 01, 2001', 'Male', 'Brgy. Morales Centro', 'November 30, 2022', '', '/profile/1483293165_1663404013.jpeg', 1),
(35, 'newStaff', '3208NewStaff', 111, 'Staff', '12/7/2022', 'Male', 'dd', 'December 01, 2022', 'December 01, 2022', '/profile/default.png', 0),
(36, 'mea3208', '3208Mea123', 1, 'Mea Gwen Gulle', '12/22/2022', 'Male', 'A', 'December 01, 2022', 'December 02, 2022', '/profile/default.png', 0);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `current_ids_admin`
--
ALTER TABLE `current_ids_admin`
  ADD PRIMARY KEY (`current_id`);

--
-- Indexes for table `current_log_open`
--
ALTER TABLE `current_log_open`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `current_patient`
--
ALTER TABLE `current_patient`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `current_patient_monitored`
--
ALTER TABLE `current_patient_monitored`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `current_shared_patient`
--
ALTER TABLE `current_shared_patient`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `deviceid`
--
ALTER TABLE `deviceid`
  ADD PRIMARY KEY (`device_id`);

--
-- Indexes for table `history_log`
--
ALTER TABLE `history_log`
  ADD PRIMARY KEY (`history_id`);

--
-- Indexes for table `hospital_information`
--
ALTER TABLE `hospital_information`
  ADD PRIMARY KEY (`hospital_information_id`);

--
-- Indexes for table `patient`
--
ALTER TABLE `patient`
  ADD PRIMARY KEY (`patient_id`);

--
-- Indexes for table `patient_health_log`
--
ALTER TABLE `patient_health_log`
  ADD PRIMARY KEY (`log_id`),
  ADD KEY `user_id` (`patient_id`);

--
-- Indexes for table `patient_logs`
--
ALTER TABLE `patient_logs`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `previous_logs`
--
ALTER TABLE `previous_logs`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `rawdata`
--
ALTER TABLE `rawdata`
  ADD PRIMARY KEY (`raw_data_id`);

--
-- Indexes for table `request`
--
ALTER TABLE `request`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `room`
--
ALTER TABLE `room`
  ADD PRIMARY KEY (`room_number`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`user_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `current_ids_admin`
--
ALTER TABLE `current_ids_admin`
  MODIFY `current_id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `current_log_open`
--
ALTER TABLE `current_log_open`
  MODIFY `id` int(100) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `current_patient`
--
ALTER TABLE `current_patient`
  MODIFY `id` int(100) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `current_patient_monitored`
--
ALTER TABLE `current_patient_monitored`
  MODIFY `id` int(100) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `current_shared_patient`
--
ALTER TABLE `current_shared_patient`
  MODIFY `id` int(100) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `history_log`
--
ALTER TABLE `history_log`
  MODIFY `history_id` int(100) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=212;

--
-- AUTO_INCREMENT for table `hospital_information`
--
ALTER TABLE `hospital_information`
  MODIFY `hospital_information_id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `patient`
--
ALTER TABLE `patient`
  MODIFY `patient_id` int(100) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=117;

--
-- AUTO_INCREMENT for table `patient_health_log`
--
ALTER TABLE `patient_health_log`
  MODIFY `log_id` int(100) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=44;

--
-- AUTO_INCREMENT for table `patient_logs`
--
ALTER TABLE `patient_logs`
  MODIFY `id` int(100) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=51;

--
-- AUTO_INCREMENT for table `previous_logs`
--
ALTER TABLE `previous_logs`
  MODIFY `id` int(100) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `rawdata`
--
ALTER TABLE `rawdata`
  MODIFY `raw_data_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `request`
--
ALTER TABLE `request`
  MODIFY `id` int(100) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `user_id` int(100) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=37;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `patient_health_log`
--
ALTER TABLE `patient_health_log`
  ADD CONSTRAINT `patient_health_log_ibfk_1` FOREIGN KEY (`patient_id`) REFERENCES `patient` (`patient_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
