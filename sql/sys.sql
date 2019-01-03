-- --------------------------------------------------------
-- 主机:                           127.0.0.1
-- 服务器版本:                        5.6.26-log - MySQL Community Server (GPL)
-- 服务器操作系统:                      Win64
-- HeidiSQL 版本:                  8.3.0.4843
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- 导出 appcenter 的数据库结构
CREATE DATABASE IF NOT EXISTS `appcenter` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `appcenter`;


-- 导出  表 appcenter.t_sys_menu 结构
CREATE TABLE IF NOT EXISTS `t_sys_menu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `menuName` varchar(32) NOT NULL COMMENT '菜单名称',
  `parentId` bigint(20) NOT NULL DEFAULT '0' COMMENT '父菜单ID',
  `menuCode` varchar(32) DEFAULT NULL COMMENT '编号',
  `menuUrl` varchar(256) DEFAULT NULL COMMENT '菜单URL',
  `urlTarget` varchar(32) DEFAULT NULL COMMENT '页面打开位置',
  `navMenu` int(11) NOT NULL DEFAULT '0' COMMENT '0:不显示在导航菜单中,1:显示在导航菜单中',
  `menuArea` int(11) NOT NULL DEFAULT '0' COMMENT '菜单类型,0:普通菜单,1:后台菜单',
  `sort` int(11) NOT NULL DEFAULT '0' COMMENT '排序',
  `iconCls` varchar(32) DEFAULT NULL,
  `iconUrl` varchar(256) DEFAULT NULL,
  `remark` varchar(128) DEFAULT NULL COMMENT '备注',
  `createTime` datetime NOT NULL,
  `lastUpdate` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  appcenter.t_sys_menu 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `t_sys_menu` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_sys_menu` ENABLE KEYS */;


-- 导出  表 appcenter.t_sys_menu_perms 结构
CREATE TABLE IF NOT EXISTS `t_sys_menu_perms` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `menuId` bigint(20) NOT NULL COMMENT '菜单id',
  `permId` bigint(20) NOT NULL COMMENT '权限点ID',
  PRIMARY KEY (`id`),
  KEY `Index_1` (`menuId`),
  KEY `FK_Reference_s8` (`permId`),
  CONSTRAINT `FK_Reference_s7` FOREIGN KEY (`menuId`) REFERENCES `t_sys_menu` (`id`),
  CONSTRAINT `FK_Reference_s8` FOREIGN KEY (`permId`) REFERENCES `t_sys_permission` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  appcenter.t_sys_menu_perms 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `t_sys_menu_perms` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_sys_menu_perms` ENABLE KEYS */;


-- 导出  表 appcenter.t_sys_permission 结构
CREATE TABLE IF NOT EXISTS `t_sys_permission` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '权限点ID',
  `permName` varchar(32) NOT NULL COMMENT '权限点名称',
  `permCode` varchar(128) NOT NULL COMMENT '权限点标示',
  `sort` int(11) NOT NULL DEFAULT '0' COMMENT '排序',
  `createTime` datetime NOT NULL COMMENT '创建时间',
  `lastUpdate` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `Index_1` (`permCode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Perms权限点';

-- 正在导出表  appcenter.t_sys_permission 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `t_sys_permission` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_sys_permission` ENABLE KEYS */;


-- 导出  表 appcenter.t_sys_role 结构
CREATE TABLE IF NOT EXISTS `t_sys_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '管理角色标识',
  `roleName` varchar(32) NOT NULL COMMENT '角色名称',
  `status` int(11) NOT NULL COMMENT '0:禁用,1:启用',
  `createTime` datetime NOT NULL COMMENT '角色创建时间',
  `lastUpdate` datetime NOT NULL COMMENT '角色最近修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  appcenter.t_sys_role 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `t_sys_role` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_sys_role` ENABLE KEYS */;


-- 导出  表 appcenter.t_sys_role_menu 结构
CREATE TABLE IF NOT EXISTS `t_sys_role_menu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `roleId` bigint(20) NOT NULL COMMENT '角色id',
  `menuId` bigint(20) NOT NULL COMMENT '菜单ID',
  PRIMARY KEY (`id`),
  KEY `Index_1` (`roleId`),
  KEY `FK_Reference_s4` (`menuId`),
  CONSTRAINT `FK_Reference_s3` FOREIGN KEY (`roleId`) REFERENCES `t_sys_role` (`id`),
  CONSTRAINT `FK_Reference_s4` FOREIGN KEY (`menuId`) REFERENCES `t_sys_menu` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  appcenter.t_sys_role_menu 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `t_sys_role_menu` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_sys_role_menu` ENABLE KEYS */;


-- 导出  表 appcenter.t_sys_role_perms 结构
CREATE TABLE IF NOT EXISTS `t_sys_role_perms` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `roleId` bigint(20) NOT NULL COMMENT '角色id',
  `permId` bigint(20) NOT NULL COMMENT '权限点ID',
  PRIMARY KEY (`id`),
  KEY `Index_1` (`roleId`),
  KEY `FK_Reference_s5` (`permId`),
  CONSTRAINT `FK_Reference_s5` FOREIGN KEY (`permId`) REFERENCES `t_sys_permission` (`id`),
  CONSTRAINT `FK_Reference_s6` FOREIGN KEY (`roleId`) REFERENCES `t_sys_role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色与Perms';

-- 正在导出表  appcenter.t_sys_role_perms 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `t_sys_role_perms` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_sys_role_perms` ENABLE KEYS */;


-- 导出  表 appcenter.t_sys_user 结构
CREATE TABLE IF NOT EXISTS `t_sys_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `userName` varchar(128) NOT NULL,
  `passWord` varchar(128) NOT NULL,
  `pwdSalt` varchar(128) DEFAULT NULL,
  `status` int(11) NOT NULL DEFAULT '1' COMMENT '0:禁用,1:启用',
  `superUser` tinyint(1) DEFAULT NULL COMMENT '是否为超级用户',
  `createTime` datetime NOT NULL,
  `lastUpdate` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

-- 正在导出表  appcenter.t_sys_user 的数据：~9 rows (大约)
/*!40000 ALTER TABLE `t_sys_user` DISABLE KEYS */;
INSERT INTO `t_sys_user` (`id`, `userName`, `passWord`, `pwdSalt`, `status`, `superUser`, `createTime`, `lastUpdate`) VALUES
	(1, 'admin', '17e3a1e15091a6633e5a7ad25fc891dd', 'cc7820730a133172', 1, 1, '2016-01-19 17:33:47', '2016-02-05 14:28:49'),
	(2, 'test', '63f523ef45b9e1ca89160ef62b46279d', 'cc7820730a133172', 1, 1, '2016-01-19 17:33:47', '2016-01-25 14:38:42'),
	(3, 'test2', 'cb90fdf41d2e69de9d98df628700d447', 'cc7820730a133172', 0, 1, '2016-01-19 17:33:47', '2016-01-25 18:19:37'),
	(4, 'test311', '17e3a1e15091a6633e5a7ad25fc891dd', 'cc7820730a133172', 0, 0, '2016-01-19 17:33:47', '2016-05-02 23:06:12'),
	(8, 'test8', '17e3a1e15091a6633e5a7ad25fc891dd', 'cc7820730a133172', 1, 0, '2016-01-19 17:33:47', '2016-01-25 18:09:39'),
	(10, 'taven', '403f33bf08793350c5c7db6bf9c20fe7', 'e45a5e10c54de7dd', 0, 0, '2016-01-25 19:41:59', '2016-01-25 19:41:59'),
	(11, 'dddddddddddddddd', 'cce7dd478f4000aeb69b5e1fde200f12', '7e9cc107d37290ea', 1, 0, '2016-01-25 20:40:41', '2016-01-25 20:40:41'),
	(12, 'ttt', 'b193d693264c9d8840d2f14fa1334aff', '83dfcc9e23845928', 1, 0, '2016-01-25 20:40:56', '2016-01-25 20:40:56'),
	(13, 'ssssssssss', '3a9fdade4e5da44134cae13aa5257335', '23a269afb44f1344', 1, 0, '2016-05-02 22:21:54', '2016-05-02 22:21:54');
/*!40000 ALTER TABLE `t_sys_user` ENABLE KEYS */;


-- 导出  表 appcenter.t_sys_user_role 结构
CREATE TABLE IF NOT EXISTS `t_sys_user_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `userId` bigint(20) NOT NULL COMMENT '用户id',
  `roleId` bigint(20) NOT NULL COMMENT '角色id',
  PRIMARY KEY (`id`),
  KEY `Index_1` (`userId`),
  KEY `FK_Reference_s2` (`roleId`),
  CONSTRAINT `FK_Reference_s1` FOREIGN KEY (`userId`) REFERENCES `t_sys_user` (`id`),
  CONSTRAINT `FK_Reference_s2` FOREIGN KEY (`roleId`) REFERENCES `t_sys_role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  appcenter.t_sys_user_role 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `t_sys_user_role` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_sys_user_role` ENABLE KEYS */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
