/*
SQLyog Ultimate v13.1.1 (64 bit)
MySQL - 8.0.33 : Database - order-service-so16
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`order-service-so16` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `order-service-so16`;

/*Table structure for table `t_order_line_item` */

DROP TABLE IF EXISTS `t_order_line_item`;

CREATE TABLE `t_order_line_item` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `item_name` varchar(255) DEFAULT NULL,
  `price` decimal(38,2) DEFAULT NULL,
  `quantity` int DEFAULT NULL,
  `sku_code` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `t_order_line_item_chk_1` CHECK ((`price` >= 0)),
  CONSTRAINT `t_order_line_item_chk_2` CHECK ((`quantity` >= 0))
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Table structure for table `t_orders` */

DROP TABLE IF EXISTS `t_orders`;

CREATE TABLE `t_orders` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `customer_id` bigint DEFAULT NULL,
  `order_name` varchar(255) DEFAULT NULL,
  `order_number` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Table structure for table `t_orders_order_line_items` */

DROP TABLE IF EXISTS `t_orders_order_line_items`;

CREATE TABLE `t_orders_order_line_items` (
  `order_id` bigint NOT NULL,
  `order_line_items_id` bigint NOT NULL,
  UNIQUE KEY `UK_dr0mag64ltmnuqo6c11iiln6o` (`order_line_items_id`),
  KEY `FKak6ywh7578tmaap0ru1vr85id` (`order_id`),
  CONSTRAINT `FK5awhgblq8yf6j6e8iu6u73jmg` FOREIGN KEY (`order_line_items_id`) REFERENCES `t_order_line_item` (`id`),
  CONSTRAINT `FKak6ywh7578tmaap0ru1vr85id` FOREIGN KEY (`order_id`) REFERENCES `t_orders` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Table structure for table `user_t` */

DROP TABLE IF EXISTS `user_t`;

CREATE TABLE `user_t` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `password` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
