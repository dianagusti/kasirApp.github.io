/*
SQLyog Ultimate v12.5.1 (32 bit)
MySQL - 10.4.8-MariaDB : Database - skripsi
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`skripsi` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;

USE `skripsi`;

/*Table structure for table `kategori` */

DROP TABLE IF EXISTS `kategori`;

CREATE TABLE `kategori` (
  `kat_id` int(11) NOT NULL AUTO_INCREMENT,
  `kat_nama` varchar(255) DEFAULT NULL,
  `kat_created` datetime DEFAULT current_timestamp(),
  `kat_updated` datetime DEFAULT NULL,
  PRIMARY KEY (`kat_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `kategori` */

/*Table structure for table `produk` */

DROP TABLE IF EXISTS `produk`;

CREATE TABLE `produk` (
  `produk_id` int(11) NOT NULL AUTO_INCREMENT,
  `produk_kat_id` int(11) DEFAULT NULL,
  `produk_kode` varchar(25) DEFAULT NULL,
  `produk_nama` varchar(255) DEFAULT NULL,
  `produk_gambar` varchar(255) DEFAULT NULL,
  `produk_satuan` varchar(10) DEFAULT NULL COMMENT 'Kilogram/Unit/Buah',
  `produk_harga` float DEFAULT NULL,
  `produk_created` datetime DEFAULT current_timestamp(),
  `produk_updated` datetime DEFAULT NULL,
  PRIMARY KEY (`produk_id`),
  KEY `produk_kat_id` (`produk_kat_id`),
  CONSTRAINT `produk_ibfk_1` FOREIGN KEY (`produk_kat_id`) REFERENCES `kategori` (`kat_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `produk` */

/*Table structure for table `produk_history` */

DROP TABLE IF EXISTS `produk_history`;

CREATE TABLE `produk_history` (
  `his_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `his_prod_id` int(11) DEFAULT NULL,
  `his_qty` int(11) DEFAULT NULL,
  `his_type` enum('in','out') DEFAULT NULL,
  `his_date` datetime DEFAULT current_timestamp(),
  PRIMARY KEY (`his_id`),
  KEY `his_prod_id` (`his_prod_id`),
  CONSTRAINT `produk_history_ibfk_1` FOREIGN KEY (`his_prod_id`) REFERENCES `produk` (`produk_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `produk_history` */

/*Table structure for table `transaksi` */

DROP TABLE IF EXISTS `transaksi`;

CREATE TABLE `transaksi` (
  `trans_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `trans_tanggal` datetime DEFAULT NULL,
  `trans_total` float DEFAULT NULL,
  PRIMARY KEY (`trans_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `transaksi` */

/*Table structure for table `transaksi_detail` */

DROP TABLE IF EXISTS `transaksi_detail`;

CREATE TABLE `transaksi_detail` (
  `det_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `det_trans_id` bigint(20) DEFAULT NULL,
  `det_prod_id` int(11) DEFAULT NULL,
  `det_qty` float DEFAULT NULL,
  `det_harga_satuan` float DEFAULT NULL,
  PRIMARY KEY (`det_id`),
  KEY `det_prod_id` (`det_prod_id`),
  KEY `det_trans_id` (`det_trans_id`),
  CONSTRAINT `transaksi_detail_ibfk_1` FOREIGN KEY (`det_prod_id`) REFERENCES `produk` (`produk_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `transaksi_detail_ibfk_2` FOREIGN KEY (`det_trans_id`) REFERENCES `transaksi` (`trans_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `transaksi_detail` */

/*Table structure for table `users` */

DROP TABLE IF EXISTS `users`;

CREATE TABLE `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nama` varchar(100) DEFAULT NULL,
  `email` varchar(200) DEFAULT NULL,
  `password` varchar(200) DEFAULT NULL,
  `user_created` datetime DEFAULT NULL,
  `user_updated` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `users` */

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
