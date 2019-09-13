/*
SQLyog v10.2
MySQL - 5.5.5-10.3.9-MariaDB : Database - demo
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`domain` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `domain`;

/*Table structure for table `todo` */

-- MySQL dump 10.13  Distrib 5.5.62, for Win64 (AMD64)
--
-- Host: localhost    Database: domain
-- ------------------------------------------------------
-- Server version	5.5.5-10.4.7-MariaDB

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `domain_history_table`
--

DROP TABLE IF EXISTS `domain_history_table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `domain_history_table` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '域名ID',
  `domain_name` varchar(100) NOT NULL COMMENT '域名标示',
  `record` varchar(20) DEFAULT NULL COMMENT '备案情况',
  `resolution` varchar(20) DEFAULT NULL COMMENT '解析情况',
  `sys_service` varchar(255) DEFAULT NULL COMMENT '系统服务',
  `port` varchar(20) DEFAULT NULL COMMENT '端口',
  `if_certificate` tinyint(1) DEFAULT NULL COMMENT '是否需要证书',
  `owner` varchar(50) DEFAULT NULL COMMENT '使用单位',
  `linkman` varchar(50) DEFAULT NULL COMMENT '联系人',
  `email` varchar(50) DEFAULT NULL COMMENT '邮箱',
  `phone` varchar(20) DEFAULT NULL COMMENT '电话',
  `inter_access` tinyint(1) DEFAULT NULL COMMENT '互联网渠道访问',
  `inter_ip` varchar(100) DEFAULT NULL COMMENT 'Service IP1',
  `inter_if_analysis` tinyint(1) DEFAULT NULL COMMENT '解析现状1',
  `everest_access` tinyint(1) DEFAULT NULL COMMENT '建行内网访问（办公网）',
  `everest_ip` varchar(100) DEFAULT NULL COMMENT 'Service IP2',
  `everest_if_analysis` tinyint(1) DEFAULT NULL COMMENT '解析现状2',
  `ecc_access` tinyint(1) DEFAULT NULL COMMENT 'ECC终端访问',
  `ecc_ip` varchar(100) DEFAULT NULL COMMENT 'Service IP3',
  `ecc_if_analysis` tinyint(1) DEFAULT NULL COMMENT '解析现状3',
  `vpc_access` tinyint(1) DEFAULT NULL COMMENT '租户VPC内访问',
  `vpc_ip` varchar(100) DEFAULT NULL COMMENT 'Service IP4',
  `vpc_if_analysis` tinyint(1) DEFAULT NULL COMMENT '解析现状4',
  `ccb_other_access` tinyint(1) DEFAULT NULL COMMENT '建行云其它组件访问',
  `ccb_other_ip` varchar(100) DEFAULT NULL COMMENT 'Service IP5',
  `ccb_other_if_analysis` tinyint(1) DEFAULT NULL COMMENT '解析现状5',
  `property` varchar(50) DEFAULT NULL COMMENT '域名性质',
  `application_date` date DEFAULT NULL COMMENT '申请备案日期',
  `es_commission_time` date DEFAULT NULL COMMENT '预计投产时间',
  `update_type` varchar(20) DEFAULT NULL COMMENT '修改类型',
  `update_time` date DEFAULT NULL COMMENT '修改时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `attachment` varchar(255) DEFAULT NULL COMMENT '附件',
  `creator` varchar(20) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '记录创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='历史域名信息表';
/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Table structure for table `domain_info_table`
--

DROP TABLE IF EXISTS `domain_info_table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `domain_info_table` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '域名ID',
  `domain_name` varchar(100) NOT NULL COMMENT '域名标示',
  `record` varchar(20) DEFAULT NULL COMMENT '备案情况',
  `resolution` varchar(20) DEFAULT NULL COMMENT '解析情况',
  `sys_service` varchar(255) DEFAULT NULL COMMENT '系统服务',
  `port` varchar(20) DEFAULT NULL COMMENT '端口',
  `if_certificate` tinyint(1) DEFAULT NULL COMMENT '是否需要证书',
  `owner` varchar(50) DEFAULT NULL COMMENT '使用单位',
  `linkman` varchar(50) DEFAULT NULL COMMENT '联系人',
  `email` varchar(50) DEFAULT NULL COMMENT '邮箱',
  `phone` varchar(20) DEFAULT NULL COMMENT '电话',
  `inter_access` tinyint(1) DEFAULT NULL COMMENT '互联网渠道访问',
  `inter_ip` varchar(100) DEFAULT NULL COMMENT 'Service IP1',
  `inter_if_analysis` tinyint(1) DEFAULT NULL COMMENT '解析现状1',
  `everest_access` tinyint(1) DEFAULT NULL COMMENT '建行内网访问（办公网）',
  `everest_ip` varchar(100) DEFAULT NULL COMMENT 'Service IP2',
  `everest_if_analysis` tinyint(1) DEFAULT NULL COMMENT '解析现状2',
  `ecc_access` tinyint(1) DEFAULT NULL COMMENT 'ECC终端访问',
  `ecc_ip` varchar(100) DEFAULT NULL COMMENT 'Service IP3',
  `ecc_if_analysis` tinyint(1) DEFAULT NULL COMMENT '解析现状3',
  `vpc_access` tinyint(1) DEFAULT NULL COMMENT '租户VPC内访问',
  `vpc_ip` varchar(100) DEFAULT NULL COMMENT 'Service IP4',
  `vpc_if_analysis` tinyint(1) DEFAULT NULL COMMENT '解析现状4',
  `ccb_other_access` tinyint(1) DEFAULT NULL COMMENT '建行云其它组件访问',
  `ccb_other_ip` varchar(100) DEFAULT NULL COMMENT 'Service IP5',
  `ccb_other_if_analysis` tinyint(1) DEFAULT NULL COMMENT '解析现状5',
  `property` varchar(50) DEFAULT NULL COMMENT '域名性质',
  `application_date` date DEFAULT NULL COMMENT '申请备案日期',
  `es_commission_time` date DEFAULT NULL COMMENT '预计投产时间',
  `update_time` date DEFAULT NULL COMMENT '修改时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `attachment` varchar(255) DEFAULT NULL COMMENT '附件',
  `creator` varchar(20) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '记录创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `domain_name` (`domain_name`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='域名信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `relation_table`
--

-- DROP TABLE IF EXISTS `relation_table`;
-- /*!40101 SET @saved_cs_client     = @@character_set_client */;
-- /*!40101 SET character_set_client = utf8 */;
-- CREATE TABLE `relation_table` (
--   `id` int(11) NOT NULL AUTO_INCREMENT,
--   `domain_id` int(11) NOT NULL,
--   `ticket_id` varchar(60) NOT NULL COMMENT '工单号',
--   `ticket_creator` varchar(20) NOT NULL COMMENT '工单创建人',
--   `create_time` datetime NOT NULL COMMENT '工单创建时间',
--   `update_time` datetime DEFAULT NULL COMMENT '工单修改时间',
--   `ticket_state` varchar(10) NOT NULL COMMENT '工单状态',
--
--   PRIMARY KEY (`id`),
--   KEY `domain_id` (`domain_id`),
--   CONSTRAINT `domain_id` FOREIGN KEY (`domain_id`) REFERENCES `domain_info_table` (`id`)
-- ) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='工单关联表';
/*!40101 SET character_set_client = @saved_cs_client */;


/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;