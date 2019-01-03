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
CREATE DATABASE IF NOT EXISTS `appcenter` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `appcenter`;


-- 导出  表 appcenter.t_cms_ad 结构
CREATE TABLE IF NOT EXISTS `t_cms_ad` (
  `ad_id` int(11) NOT NULL,
  `site_id` int(11) NOT NULL DEFAULT '0',
  `adslot_id` int(11) NOT NULL DEFAULT '0',
  `name` varchar(150) NOT NULL DEFAULT '',
  `begin_date` datetime DEFAULT NULL,
  `end_date` datetime DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  `text` varchar(255) DEFAULT NULL,
  `script` text,
  `image` varchar(255) DEFAULT NULL,
  `flash` varchar(255) DEFAULT NULL,
  `seq` int(11) NOT NULL DEFAULT '1',
  `linkType` int(1) DEFAULT NULL,
  PRIMARY KEY (`ad_id`),
  KEY `fk_cms_ad_adslot` (`adslot_id`),
  KEY `fk_cms_ad_site` (`site_id`),
  CONSTRAINT `t_cms_ad_ibfk_1` FOREIGN KEY (`adslot_id`) REFERENCES `t_cms_ad_slot` (`adslot_id`),
  CONSTRAINT `t_cms_ad_ibfk_2` FOREIGN KEY (`site_id`) REFERENCES `t_cms_site` (`site_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。


-- 导出  表 appcenter.t_cms_ad_slot 结构
CREATE TABLE IF NOT EXISTS `t_cms_ad_slot` (
  `adslot_id` int(11) NOT NULL DEFAULT '0',
  `site_id` int(11) NOT NULL DEFAULT '0',
  `name` varchar(100) NOT NULL DEFAULT '',
  `number` varchar(100) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `type` int(11) NOT NULL DEFAULT '0',
  `template` varchar(255) DEFAULT '',
  `width` int(11) NOT NULL DEFAULT '0',
  `height` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`adslot_id`),
  KEY `fk_cms_adslot_site` (`site_id`),
  CONSTRAINT `t_cms_ad_slot_ibfk_1` FOREIGN KEY (`site_id`) REFERENCES `t_cms_site` (`site_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。


-- 导出  表 appcenter.t_cms_app 结构
CREATE TABLE IF NOT EXISTS `t_cms_app` (
  `id` tinyint(4) NOT NULL DEFAULT '0',
  `site_id` int(11) DEFAULT NULL,
  `version` varchar(50) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  `content` mediumtext,
  `publish_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_cms_app_site` (`site_id`),
  CONSTRAINT `t_cms_app_ibfk_1` FOREIGN KEY (`site_id`) REFERENCES `t_cms_site` (`site_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。


-- 导出  表 appcenter.t_cms_attachment 结构
CREATE TABLE IF NOT EXISTS `t_cms_attachment` (
  `attachment_id` int(11) NOT NULL DEFAULT '0',
  `site_id` int(11) NOT NULL DEFAULT '0',
  `name` varchar(150) NOT NULL DEFAULT '',
  `length` bigint(20) DEFAULT NULL,
  `ip` varchar(100) DEFAULT NULL,
  `time` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`attachment_id`),
  KEY `fk_cms_attachement_site` (`site_id`),
  CONSTRAINT `t_cms_attachment_ibfk_1` FOREIGN KEY (`site_id`) REFERENCES `t_cms_site` (`site_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。


-- 导出  表 appcenter.t_cms_attachment_ref 结构
CREATE TABLE IF NOT EXISTS `t_cms_attachment_ref` (
  `attachementref_id` int(11) NOT NULL DEFAULT '0',
  `attachment_id` int(11) NOT NULL DEFAULT '0',
  `ftype` varchar(100) NOT NULL DEFAULT '',
  `fid` int(11) NOT NULL DEFAULT '0',
  `site_id` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`attachementref_id`),
  KEY `fk_cms_attachmentref_attachment` (`attachment_id`),
  KEY `fk_cms_attachmentref_site` (`site_id`),
  CONSTRAINT `t_cms_attachment_ref_ibfk_1` FOREIGN KEY (`attachment_id`) REFERENCES `t_cms_attachment` (`attachment_id`),
  CONSTRAINT `t_cms_attachment_ref_ibfk_2` FOREIGN KEY (`site_id`) REFERENCES `t_cms_site` (`site_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。


-- 导出  表 appcenter.t_cms_attention 结构
CREATE TABLE IF NOT EXISTS `t_cms_attention` (
  `id` int(11) NOT NULL DEFAULT '0',
  `site_id` int(11) DEFAULT NULL,
  `type` tinyint(4) DEFAULT NULL,
  `fid` int(11) DEFAULT NULL,
  `attention_date` datetime DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_cms_attention_site` (`site_id`),
  CONSTRAINT `t_cms_attention_ibfk_1` FOREIGN KEY (`site_id`) REFERENCES `t_cms_site` (`site_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。


-- 导出  表 appcenter.t_cms_attribute 结构
CREATE TABLE IF NOT EXISTS `t_cms_attribute` (
  `attribute_id` int(11) NOT NULL DEFAULT '0',
  `site_id` int(11) DEFAULT '0',
  `number` varchar(20) DEFAULT '',
  `name` varchar(50) NOT NULL DEFAULT '',
  `seq` int(11) NOT NULL DEFAULT '2147483647',
  `is_with_image` char(1) DEFAULT '0',
  `is_scale` char(1) NOT NULL DEFAULT '0',
  `is_exact` char(1) NOT NULL DEFAULT '0',
  `is_watermark` char(1) NOT NULL DEFAULT '0',
  `image_width` int(11) DEFAULT NULL,
  `image_height` int(11) DEFAULT NULL,
  PRIMARY KEY (`attribute_id`),
  KEY `fk_cms_attribute_site` (`site_id`),
  CONSTRAINT `t_cms_attribute_ibfk_1` FOREIGN KEY (`site_id`) REFERENCES `t_cms_site` (`site_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。


-- 导出  表 appcenter.t_cms_collect 结构
CREATE TABLE IF NOT EXISTS `t_cms_collect` (
  `collect_id` int(11) NOT NULL DEFAULT '0',
  `node_id` int(11) NOT NULL DEFAULT '0',
  `site_id` int(11) NOT NULL DEFAULT '0',
  `name` varchar(100) NOT NULL DEFAULT '',
  `charset` varchar(100) NOT NULL DEFAULT 'UTF-8',
  `user_agent` varchar(255) NOT NULL DEFAULT 'Mozilla/5.0',
  `interval_min` int(11) NOT NULL DEFAULT '0',
  `interval_max` int(11) NOT NULL DEFAULT '0',
  `list_pattern` varchar(2000) NOT NULL DEFAULT '',
  `list_next_pattern` varchar(255) DEFAULT NULL,
  `is_list_next_reg` char(1) NOT NULL DEFAULT '0',
  `item_area_pattern` varchar(255) DEFAULT NULL,
  `is_item_area_reg` char(1) NOT NULL DEFAULT '0',
  `item_pattern` varchar(255) NOT NULL DEFAULT '',
  `is_item_reg` char(1) NOT NULL DEFAULT '0',
  `block_area_pattern` varchar(255) DEFAULT NULL,
  `is_block_area_reg` char(1) NOT NULL DEFAULT '0',
  `block_pattern` varchar(255) DEFAULT NULL,
  `is_block_reg` char(1) NOT NULL DEFAULT '0',
  `page_begin` int(11) NOT NULL DEFAULT '2',
  `page_end` int(11) NOT NULL DEFAULT '10',
  `is_desc` char(1) NOT NULL DEFAULT '1',
  `is_submit` char(1) NOT NULL DEFAULT '0',
  `status` int(11) NOT NULL DEFAULT '0',
  `user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`collect_id`),
  KEY `fk_cms_collect_node` (`node_id`),
  KEY `fk_cms_collect_site` (`site_id`),
  CONSTRAINT `t_cms_collect_ibfk_1` FOREIGN KEY (`node_id`) REFERENCES `t_cms_node` (`node_id`),
  CONSTRAINT `t_cms_collect_ibfk_2` FOREIGN KEY (`site_id`) REFERENCES `t_cms_site` (`site_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。


-- 导出  表 appcenter.t_cms_collect_field 结构
CREATE TABLE IF NOT EXISTS `t_cms_collect_field` (
  `collectfield_id` int(11) NOT NULL DEFAULT '0',
  `collect_id` int(11) NOT NULL DEFAULT '0',
  `site_id` int(11) NOT NULL DEFAULT '0',
  `name` varchar(100) NOT NULL DEFAULT '',
  `code` varchar(100) NOT NULL DEFAULT '',
  `type` int(11) NOT NULL DEFAULT '1',
  `source_type` int(11) NOT NULL DEFAULT '1',
  `source_url` varchar(255) DEFAULT NULL,
  `source_text` varchar(255) DEFAULT NULL,
  `data_area_pattern` varchar(255) DEFAULT NULL,
  `is_data_area_reg` char(1) NOT NULL DEFAULT '0',
  `data_pattern` varchar(255) DEFAULT NULL,
  `is_data_reg` char(1) NOT NULL DEFAULT '0',
  `date_format` varchar(255) DEFAULT NULL,
  `download_type` varchar(20) DEFAULT NULL,
  `image_param` varchar(255) DEFAULT NULL,
  `filter` varchar(2000) DEFAULT NULL,
  `seq` int(11) NOT NULL DEFAULT '2147483647',
  PRIMARY KEY (`collectfield_id`),
  KEY `fk_cms_collectfield_collect` (`collect_id`),
  KEY `fk_cms_collectfield_site` (`site_id`),
  CONSTRAINT `t_cms_collect_field_ibfk_1` FOREIGN KEY (`collect_id`) REFERENCES `t_cms_collect` (`collect_id`),
  CONSTRAINT `t_cms_collect_field_ibfk_2` FOREIGN KEY (`site_id`) REFERENCES `t_cms_site` (`site_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。


-- 导出  表 appcenter.t_cms_collect_log 结构
CREATE TABLE IF NOT EXISTS `t_cms_collect_log` (
  `collectlog_id` int(11) NOT NULL DEFAULT '0',
  `site_id` int(11) NOT NULL DEFAULT '0',
  `url` varchar(255) NOT NULL DEFAULT '',
  `title` varchar(255) DEFAULT NULL,
  `message` varchar(255) DEFAULT NULL,
  `time` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `status` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`collectlog_id`),
  KEY `fk_cms_collectlog_site` (`site_id`),
  CONSTRAINT `t_cms_collect_log_ibfk_1` FOREIGN KEY (`site_id`) REFERENCES `t_cms_site` (`site_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。


-- 导出  表 appcenter.t_cms_comment 结构
CREATE TABLE IF NOT EXISTS `t_cms_comment` (
  `comment_id` int(11) NOT NULL DEFAULT '0',
  `site_id` int(11) NOT NULL DEFAULT '0',
  `auditor_id` int(11) DEFAULT NULL,
  `ftype` varchar(50) NOT NULL DEFAULT '',
  `fid` int(11) NOT NULL DEFAULT '0',
  `creation_date` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `audit_date` datetime DEFAULT NULL,
  `ip` varchar(100) NOT NULL DEFAULT '127.0.0.1',
  `score` int(11) NOT NULL DEFAULT '0',
  `status` int(11) NOT NULL DEFAULT '0',
  `text` text,
  `creator_id` int(11) DEFAULT NULL,
  `sourcetype` varchar(50) DEFAULT NULL,
  `sourceid` int(11) DEFAULT NULL,
  PRIMARY KEY (`comment_id`),
  KEY `fk_cms_comment_site` (`site_id`),
  CONSTRAINT `t_cms_comment_ibfk_1` FOREIGN KEY (`site_id`) REFERENCES `t_cms_site` (`site_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。


-- 导出  表 appcenter.t_cms_feedback 结构
CREATE TABLE IF NOT EXISTS `t_cms_feedback` (
  `id` int(11) NOT NULL DEFAULT '0',
  `site_id` int(11) DEFAULT NULL,
  `username` varchar(100) DEFAULT NULL,
  `mobile` varchar(20) DEFAULT NULL,
  `title` varchar(60) DEFAULT NULL,
  `content` varchar(200) DEFAULT NULL,
  `creation_time` datetime DEFAULT NULL,
  `creation_ip` varchar(50) DEFAULT NULL,
  `reply_content` varchar(200) DEFAULT NULL,
  `reply_time` datetime DEFAULT NULL,
  `reply_ip` varchar(50) DEFAULT NULL,
  `is_reply` tinyint(4) DEFAULT NULL,
  `creator_id` int(11) DEFAULT NULL,
  `replyer_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_cms_feedback_site` (`site_id`),
  CONSTRAINT `t_cms_feedback_ibfk_1` FOREIGN KEY (`site_id`) REFERENCES `t_cms_site` (`site_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。


-- 导出  表 appcenter.t_cms_global 结构
CREATE TABLE IF NOT EXISTS `t_cms_global` (
  `global_id` int(11) NOT NULL DEFAULT '0',
  `protocol` varchar(50) NOT NULL DEFAULT 'http',
  `port` int(11) DEFAULT NULL,
  `context_path` varchar(255) DEFAULT NULL,
  `is_with_domain` char(1) NOT NULL DEFAULT '0',
  `uploads_publishpoint_id` int(11) DEFAULT NULL,
  `version` varchar(50) NOT NULL DEFAULT '',
  `redis_timeout` int(11) DEFAULT NULL,
  PRIMARY KEY (`global_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。


-- 导出  表 appcenter.t_cms_global_clob 结构
CREATE TABLE IF NOT EXISTS `t_cms_global_clob` (
  `global_id` int(11) NOT NULL DEFAULT '0',
  `key` varchar(50) NOT NULL DEFAULT '',
  `value` text,
  KEY `fk_cms_globalclob_global` (`global_id`),
  CONSTRAINT `t_cms_global_clob_ibfk_1` FOREIGN KEY (`global_id`) REFERENCES `t_cms_global` (`global_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。


-- 导出  表 appcenter.t_cms_global_custom 结构
CREATE TABLE IF NOT EXISTS `t_cms_global_custom` (
  `global_id` int(11) NOT NULL DEFAULT '0',
  `key` varchar(50) NOT NULL DEFAULT '',
  `value` varchar(2000) DEFAULT NULL,
  KEY `fk_cms_globalcustom_global` (`global_id`),
  CONSTRAINT `t_cms_global_custom_ibfk_1` FOREIGN KEY (`global_id`) REFERENCES `t_cms_global` (`global_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。


-- 导出  表 appcenter.t_cms_info 结构
CREATE TABLE IF NOT EXISTS `t_cms_info` (
  `info_id` int(11) NOT NULL DEFAULT '0',
  `site_id` int(11) NOT NULL DEFAULT '0',
  `node_id` int(11) NOT NULL DEFAULT '0',
  `publish_date` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `off_date` datetime DEFAULT NULL,
  `priority` tinyint(4) NOT NULL DEFAULT '0',
  `is_with_image` char(1) NOT NULL DEFAULT '0',
  `is_with_video` tinyint(4) DEFAULT NULL,
  `views` int(11) NOT NULL DEFAULT '0',
  `downloads` int(11) NOT NULL DEFAULT '0',
  `comments` int(11) NOT NULL DEFAULT '0',
  `diggs` int(11) NOT NULL DEFAULT '0',
  `score` int(11) NOT NULL DEFAULT '0',
  `status` char(1) NOT NULL DEFAULT 'A',
  `p1` tinyint(4) DEFAULT NULL,
  `p2` tinyint(4) DEFAULT NULL,
  `p3` tinyint(4) DEFAULT NULL,
  `p4` tinyint(4) DEFAULT NULL,
  `p5` tinyint(4) DEFAULT NULL,
  `p6` tinyint(4) DEFAULT NULL,
  `html_status` char(1) NOT NULL DEFAULT '0',
  `push` tinyint(4) DEFAULT NULL,
  `column_sort` double DEFAULT NULL,
  `org_id` int(11) DEFAULT NULL,
  `creator_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`info_id`),
  KEY `fk_cms_info_node` (`node_id`),
  KEY `fk_cms_info_site` (`site_id`),
  CONSTRAINT `t_cms_info_ibfk_1` FOREIGN KEY (`node_id`) REFERENCES `t_cms_node` (`node_id`),
  CONSTRAINT `t_cms_info_ibfk_2` FOREIGN KEY (`site_id`) REFERENCES `t_cms_site` (`site_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。


-- 导出  表 appcenter.t_cms_info_attribute 结构
CREATE TABLE IF NOT EXISTS `t_cms_info_attribute` (
  `infoattr_id` int(11) NOT NULL DEFAULT '0',
  `info_id` int(11) NOT NULL DEFAULT '0',
  `attribute_id` int(11) NOT NULL DEFAULT '0',
  `image` varchar(255) DEFAULT NULL,
  `seq` int(11) DEFAULT NULL,
  PRIMARY KEY (`infoattr_id`),
  KEY `fk_cms_infoattr_attribute` (`attribute_id`),
  KEY `fk_cms_infoattr_info` (`info_id`),
  CONSTRAINT `t_cms_info_attribute_ibfk_1` FOREIGN KEY (`attribute_id`) REFERENCES `t_cms_attribute` (`attribute_id`),
  CONSTRAINT `t_cms_info_attribute_ibfk_2` FOREIGN KEY (`info_id`) REFERENCES `t_cms_info` (`info_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。


-- 导出  表 appcenter.t_cms_info_buffer 结构
CREATE TABLE IF NOT EXISTS `t_cms_info_buffer` (
  `info_id` int(11) NOT NULL DEFAULT '0',
  `views` int(11) NOT NULL DEFAULT '0',
  `downloads` int(11) NOT NULL DEFAULT '0',
  `comments` int(11) NOT NULL DEFAULT '0',
  `involveds` int(11) NOT NULL DEFAULT '0',
  `diggs` int(11) NOT NULL DEFAULT '0',
  `burys` int(11) NOT NULL DEFAULT '0',
  `score` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`info_id`),
  CONSTRAINT `t_cms_info_buffer_ibfk_1` FOREIGN KEY (`info_id`) REFERENCES `t_cms_info` (`info_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。


-- 导出  表 appcenter.t_cms_info_clob 结构
CREATE TABLE IF NOT EXISTS `t_cms_info_clob` (
  `info_id` int(11) NOT NULL DEFAULT '0',
  `key_` varchar(50) NOT NULL DEFAULT '',
  `value_` text,
  KEY `fk_cms_infoclob_info` (`info_id`),
  CONSTRAINT `t_cms_info_clob_ibfk_1` FOREIGN KEY (`info_id`) REFERENCES `t_cms_info` (`info_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。


-- 导出  表 appcenter.t_cms_info_collect 结构
CREATE TABLE IF NOT EXISTS `t_cms_info_collect` (
  `id` int(11) NOT NULL DEFAULT '0',
  `site_id` int(11) DEFAULT NULL,
  `info_id` int(11) DEFAULT NULL,
  `title` varchar(150) DEFAULT NULL,
  `collect_date` datetime DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_info_collect2info` (`info_id`),
  KEY `fk_cms_info_collect_site` (`site_id`),
  CONSTRAINT `t_cms_info_collect_ibfk_1` FOREIGN KEY (`info_id`) REFERENCES `t_cms_info` (`info_id`) ON DELETE CASCADE,
  CONSTRAINT `t_cms_info_collect_ibfk_2` FOREIGN KEY (`site_id`) REFERENCES `t_cms_site` (`site_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。


-- 导出  表 appcenter.t_cms_info_custom 结构
CREATE TABLE IF NOT EXISTS `t_cms_info_custom` (
  `info_id` int(11) NOT NULL DEFAULT '0',
  `key_` varchar(50) NOT NULL DEFAULT '',
  `value_` varchar(2000) DEFAULT NULL,
  KEY `fk_cms_infocustom_info` (`info_id`),
  CONSTRAINT `t_cms_info_custom_ibfk_1` FOREIGN KEY (`info_id`) REFERENCES `t_cms_info` (`info_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。


-- 导出  表 appcenter.t_cms_info_detail 结构
CREATE TABLE IF NOT EXISTS `t_cms_info_detail` (
  `info_id` int(11) NOT NULL DEFAULT '0',
  `title` varchar(150) NOT NULL DEFAULT '',
  `html` varchar(255) DEFAULT NULL,
  `subtitle` varchar(150) DEFAULT NULL,
  `full_title` varchar(150) DEFAULT NULL,
  `link` varchar(255) DEFAULT NULL,
  `is_new_window` char(1) DEFAULT NULL,
  `color` varchar(50) DEFAULT NULL,
  `is_strong` char(1) NOT NULL DEFAULT '0',
  `is_em` char(1) NOT NULL DEFAULT '0',
  `info_path` varchar(255) DEFAULT NULL,
  `info_template` varchar(255) DEFAULT NULL,
  `meta_description` varchar(255) DEFAULT NULL,
  `source` varchar(50) DEFAULT NULL,
  `source_url` varchar(255) DEFAULT NULL,
  `author` varchar(50) DEFAULT NULL,
  `small_image` varchar(255) DEFAULT NULL,
  `large_image` varchar(255) DEFAULT NULL,
  `video` varchar(255) DEFAULT NULL,
  `video_name` varchar(255) DEFAULT NULL,
  `video_length` bigint(20) DEFAULT NULL,
  `video_time` varchar(100) DEFAULT NULL,
  `file` varchar(255) DEFAULT NULL,
  `file_name` varchar(255) DEFAULT NULL,
  `file_length` bigint(20) DEFAULT NULL,
  `doc` varchar(255) DEFAULT NULL,
  `doc_name` varchar(255) DEFAULT NULL,
  `doc_length` varchar(255) DEFAULT NULL,
  `doc_pdf` varchar(255) DEFAULT NULL,
  `doc_swf` varchar(255) DEFAULT NULL,
  `is_allow_comment` char(1) DEFAULT NULL,
  `step_name` varchar(100) DEFAULT NULL,
  `source_id` tinyint(4) DEFAULT NULL,
  `source_flag` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`info_id`),
  CONSTRAINT `t_cms_info_detail_ibfk_1` FOREIGN KEY (`info_id`) REFERENCES `t_cms_info` (`info_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。


-- 导出  表 appcenter.t_cms_info_file 结构
CREATE TABLE IF NOT EXISTS `t_cms_info_file` (
  `info_id` int(11) NOT NULL DEFAULT '0',
  `name` varchar(150) NOT NULL DEFAULT '',
  `length` bigint(20) NOT NULL DEFAULT '0',
  `file` varchar(255) NOT NULL DEFAULT '',
  `index_` int(11) NOT NULL DEFAULT '0',
  `downloads` int(11) NOT NULL DEFAULT '0',
  KEY `fk_cms_infofile_info` (`info_id`),
  CONSTRAINT `t_cms_info_file_ibfk_1` FOREIGN KEY (`info_id`) REFERENCES `t_cms_info` (`info_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。


-- 导出  表 appcenter.t_cms_info_image 结构
CREATE TABLE IF NOT EXISTS `t_cms_info_image` (
  `info_id` int(11) NOT NULL DEFAULT '0',
  `name` varchar(150) DEFAULT NULL,
  `image` varchar(255) DEFAULT NULL,
  `index_` int(11) NOT NULL DEFAULT '0',
  `text` text,
  KEY `fk_cms_infoimage_info` (`info_id`),
  CONSTRAINT `t_cms_info_image_ibfk_1` FOREIGN KEY (`info_id`) REFERENCES `t_cms_info` (`info_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。


-- 导出  表 appcenter.t_cms_info_membergroup 结构
CREATE TABLE IF NOT EXISTS `t_cms_info_membergroup` (
  `infomgroup_id` int(11) NOT NULL DEFAULT '0',
  `info_id` int(11) NOT NULL DEFAULT '0',
  `is_view_perm` char(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`infomgroup_id`),
  KEY `fk_cms_infomgroup_info` (`info_id`),
  CONSTRAINT `t_cms_info_membergroup_ibfk_1` FOREIGN KEY (`info_id`) REFERENCES `t_cms_info` (`info_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。


-- 导出  表 appcenter.t_cms_info_node 结构
CREATE TABLE IF NOT EXISTS `t_cms_info_node` (
  `infonode_id` int(11) NOT NULL DEFAULT '0',
  `info_id` int(11) NOT NULL DEFAULT '0',
  `node_id` int(11) NOT NULL DEFAULT '0',
  `node_index` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`infonode_id`),
  KEY `fk_cms_infonode_info` (`info_id`),
  KEY `fk_cms_infonode_node` (`node_id`),
  CONSTRAINT `t_cms_info_node_ibfk_1` FOREIGN KEY (`info_id`) REFERENCES `t_cms_info` (`info_id`),
  CONSTRAINT `t_cms_info_node_ibfk_2` FOREIGN KEY (`node_id`) REFERENCES `t_cms_node` (`node_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。


-- 导出  表 appcenter.t_cms_info_source 结构
CREATE TABLE IF NOT EXISTS `t_cms_info_source` (
  `source_id` tinyint(4) NOT NULL DEFAULT '0',
  `source_name` varchar(100) DEFAULT NULL,
  `source_image` varchar(100) DEFAULT NULL,
  `source_url` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`source_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。


-- 导出  表 appcenter.t_cms_info_special 结构
CREATE TABLE IF NOT EXISTS `t_cms_info_special` (
  `infospecial_id` int(11) NOT NULL DEFAULT '0',
  `info_id` int(11) NOT NULL DEFAULT '0',
  `special_id` int(11) NOT NULL DEFAULT '0',
  `special_index` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`infospecial_id`),
  KEY `fk_cms_infospecial_info` (`info_id`),
  KEY `fk_cms_infospecial_special` (`special_id`),
  CONSTRAINT `t_cms_info_special_ibfk_1` FOREIGN KEY (`info_id`) REFERENCES `t_cms_info` (`info_id`),
  CONSTRAINT `t_cms_info_special_ibfk_2` FOREIGN KEY (`special_id`) REFERENCES `t_cms_special` (`special_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。


-- 导出  表 appcenter.t_cms_info_tag 结构
CREATE TABLE IF NOT EXISTS `t_cms_info_tag` (
  `infotag_id` int(11) NOT NULL DEFAULT '0',
  `info_id` int(11) NOT NULL DEFAULT '0',
  `tag_index` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`infotag_id`),
  KEY `fk_cms_infotag_info` (`info_id`),
  CONSTRAINT `t_cms_info_tag_ibfk_1` FOREIGN KEY (`info_id`) REFERENCES `t_cms_info` (`info_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。


-- 导出  表 appcenter.t_cms_info_visitor 结构
CREATE TABLE IF NOT EXISTS `t_cms_info_visitor` (
  `id` int(11) NOT NULL DEFAULT '0',
  `site_id` int(11) DEFAULT NULL,
  `info_id` int(11) DEFAULT NULL,
  `ip` varchar(50) DEFAULT NULL,
  `time` datetime DEFAULT NULL,
  `title` varchar(200) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_info_visitor_info` (`info_id`),
  KEY `fk_info_visitor_site` (`site_id`),
  CONSTRAINT `t_cms_info_visitor_ibfk_1` FOREIGN KEY (`info_id`) REFERENCES `t_cms_info` (`info_id`) ON DELETE CASCADE,
  CONSTRAINT `t_cms_info_visitor_ibfk_2` FOREIGN KEY (`site_id`) REFERENCES `t_cms_site` (`site_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。


-- 导出  表 appcenter.t_cms_ios_message_error 结构
CREATE TABLE IF NOT EXISTS `t_cms_ios_message_error` (
  `id` int(11) NOT NULL DEFAULT '0',
  `token` varchar(100) DEFAULT NULL,
  `title` varchar(50) DEFAULT NULL,
  `message` varchar(200) DEFAULT NULL,
  `error_msg` varchar(2000) DEFAULT NULL,
  `createtime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。


-- 导出  表 appcenter.t_cms_ios_message_pedding 结构
CREATE TABLE IF NOT EXISTS `t_cms_ios_message_pedding` (
  `id` int(11) NOT NULL DEFAULT '0',
  `token` varchar(100) DEFAULT NULL,
  `title` varchar(50) DEFAULT NULL,
  `message` varchar(200) DEFAULT NULL,
  `travel` int(11) DEFAULT NULL,
  `createtime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。


-- 导出  表 appcenter.t_cms_ios_token 结构
CREATE TABLE IF NOT EXISTS `t_cms_ios_token` (
  `token` varchar(100) NOT NULL DEFAULT '',
  `siteId` int(11) NOT NULL DEFAULT '0',
  `id` int(11) NOT NULL DEFAULT '0',
  `failCount` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。


-- 导出  表 appcenter.t_cms_model 结构
CREATE TABLE IF NOT EXISTS `t_cms_model` (
  `model_id` int(11) NOT NULL DEFAULT '0',
  `site_id` int(11) NOT NULL DEFAULT '0',
  `type` varchar(100) NOT NULL DEFAULT '',
  `name` varchar(50) NOT NULL DEFAULT '',
  `number` varchar(100) DEFAULT NULL,
  `seq` int(11) NOT NULL DEFAULT '10',
  PRIMARY KEY (`model_id`),
  KEY `fk_cms_model_site` (`site_id`),
  CONSTRAINT `t_cms_model_ibfk_1` FOREIGN KEY (`site_id`) REFERENCES `t_cms_site` (`site_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。


-- 导出  表 appcenter.t_cms_model_custom 结构
CREATE TABLE IF NOT EXISTS `t_cms_model_custom` (
  `model_id` int(11) NOT NULL DEFAULT '0',
  `key_` varchar(50) NOT NULL DEFAULT '',
  `value_` varchar(2000) DEFAULT NULL,
  KEY `fk_cms_modelcustom_model` (`model_id`),
  CONSTRAINT `t_cms_model_custom_ibfk_1` FOREIGN KEY (`model_id`) REFERENCES `t_cms_model` (`model_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。


-- 导出  表 appcenter.t_cms_model_field 结构
CREATE TABLE IF NOT EXISTS `t_cms_model_field` (
  `modefiel_id` int(11) NOT NULL DEFAULT '0',
  `model_id` int(11) NOT NULL DEFAULT '0',
  `type` int(11) NOT NULL DEFAULT '0',
  `inner_type` int(11) NOT NULL DEFAULT '0',
  `label` varchar(50) NOT NULL DEFAULT '',
  `name` varchar(50) NOT NULL DEFAULT '',
  `prompt` varchar(255) DEFAULT NULL,
  `def_value` varchar(255) DEFAULT NULL,
  `is_required` char(1) NOT NULL DEFAULT '0',
  `seq` int(11) NOT NULL DEFAULT '10',
  `is_dbl_column` char(1) NOT NULL DEFAULT '0',
  `is_disabled` char(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`modefiel_id`),
  KEY `fk_cms_modefiel_model` (`model_id`),
  CONSTRAINT `t_cms_model_field_ibfk_1` FOREIGN KEY (`model_id`) REFERENCES `t_cms_model` (`model_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。


-- 导出  表 appcenter.t_cms_model_field_custom 结构
CREATE TABLE IF NOT EXISTS `t_cms_model_field_custom` (
  `modefiel_id` int(11) NOT NULL DEFAULT '0',
  `key_` varchar(50) NOT NULL DEFAULT '',
  `value_` varchar(2000) DEFAULT NULL,
  KEY `fk_cms_modfiecus_modefiel` (`modefiel_id`),
  CONSTRAINT `t_cms_model_field_custom_ibfk_1` FOREIGN KEY (`modefiel_id`) REFERENCES `t_cms_model_field` (`modefiel_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。


-- 导出  表 appcenter.t_cms_node 结构
CREATE TABLE IF NOT EXISTS `t_cms_node` (
  `node_id` int(11) NOT NULL DEFAULT '0',
  `site_id` int(11) DEFAULT '0',
  `parent_id` int(11) DEFAULT NULL,
  `node_model_id` int(11) DEFAULT '0',
  `workflow_id` int(11) DEFAULT NULL,
  `info_model_id` int(11) DEFAULT NULL,
  `number` varchar(100) DEFAULT NULL,
  `name` varchar(150) DEFAULT '',
  `tree_number` varchar(100) DEFAULT '0000',
  `tree_level` int(11) DEFAULT '0',
  `tree_max` varchar(10) DEFAULT '0000',
  `creation_date` datetime DEFAULT '0000-00-00 00:00:00',
  `refers` int(11) DEFAULT '0',
  `views` int(11) DEFAULT '0',
  `is_real_node` char(1) DEFAULT '1',
  `is_hidden` char(1) DEFAULT '0',
  `html_status` char(1) DEFAULT '0',
  `p1` tinyint(4) DEFAULT NULL,
  `p2` tinyint(4) DEFAULT NULL,
  `p3` tinyint(4) DEFAULT NULL,
  `p4` tinyint(4) DEFAULT NULL,
  `p5` tinyint(4) DEFAULT NULL,
  `p6` tinyint(4) DEFAULT NULL,
  `attentions` int(11) DEFAULT NULL,
  `creator_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`node_id`),
  KEY `fk_cms_node_model_info` (`info_model_id`),
  KEY `fk_cms_node_model_node` (`node_model_id`),
  KEY `fk_cms_node_parent` (`parent_id`),
  KEY `fk_cms_node_site` (`site_id`),
  KEY `fk_cms_node_workflow` (`workflow_id`),
  CONSTRAINT `t_cms_node_ibfk_1` FOREIGN KEY (`info_model_id`) REFERENCES `t_cms_model` (`model_id`),
  CONSTRAINT `t_cms_node_ibfk_2` FOREIGN KEY (`node_model_id`) REFERENCES `t_cms_model` (`model_id`),
  CONSTRAINT `t_cms_node_ibfk_3` FOREIGN KEY (`parent_id`) REFERENCES `t_cms_node` (`node_id`),
  CONSTRAINT `t_cms_node_ibfk_4` FOREIGN KEY (`site_id`) REFERENCES `t_cms_site` (`site_id`),
  CONSTRAINT `t_cms_node_ibfk_5` FOREIGN KEY (`workflow_id`) REFERENCES `t_cms_workflow` (`workflow_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。


-- 导出  表 appcenter.t_cms_node_buffer 结构
CREATE TABLE IF NOT EXISTS `t_cms_node_buffer` (
  `node_id` int(11) NOT NULL DEFAULT '0',
  `views` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`node_id`),
  CONSTRAINT `t_cms_node_buffer_ibfk_1` FOREIGN KEY (`node_id`) REFERENCES `t_cms_node` (`node_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。


-- 导出  表 appcenter.t_cms_node_clob 结构
CREATE TABLE IF NOT EXISTS `t_cms_node_clob` (
  `node_id` int(11) NOT NULL DEFAULT '0',
  `key` varchar(50) NOT NULL DEFAULT '',
  `value` text,
  KEY `fk_cms_nodeclob_node` (`node_id`),
  CONSTRAINT `t_cms_node_clob_ibfk_1` FOREIGN KEY (`node_id`) REFERENCES `t_cms_node` (`node_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。


-- 导出  表 appcenter.t_cms_node_custom 结构
CREATE TABLE IF NOT EXISTS `t_cms_node_custom` (
  `node_id` int(11) NOT NULL DEFAULT '0',
  `key` varchar(50) NOT NULL DEFAULT '',
  `value` varchar(2000) DEFAULT NULL,
  KEY `fk_cms_nodecustom_node` (`node_id`),
  CONSTRAINT `t_cms_node_custom_ibfk_1` FOREIGN KEY (`node_id`) REFERENCES `t_cms_node` (`node_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。


-- 导出  表 appcenter.t_cms_node_detail 结构
CREATE TABLE IF NOT EXISTS `t_cms_node_detail` (
  `node_id` int(11) NOT NULL DEFAULT '0',
  `link` varchar(255) DEFAULT NULL,
  `html` varchar(255) DEFAULT NULL,
  `meta_keywords` varchar(150) DEFAULT NULL,
  `meta_description` varchar(255) DEFAULT NULL,
  `is_new_window` char(1) DEFAULT NULL,
  `node_template` varchar(255) DEFAULT NULL,
  `info_template` varchar(255) DEFAULT NULL,
  `is_generate_node` char(1) DEFAULT NULL,
  `is_generate_info` char(1) DEFAULT NULL,
  `node_extension` varchar(10) DEFAULT NULL,
  `info_extension` varchar(10) DEFAULT NULL,
  `node_path` varchar(100) DEFAULT NULL,
  `info_path` varchar(100) DEFAULT NULL,
  `is_def_page` char(1) DEFAULT NULL,
  `static_method` int(11) DEFAULT NULL,
  `static_page` int(11) DEFAULT NULL,
  `small_image` varchar(255) DEFAULT NULL,
  `large_image` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`node_id`),
  CONSTRAINT `t_cms_node_detail_ibfk_1` FOREIGN KEY (`node_id`) REFERENCES `t_cms_node` (`node_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。


-- 导出  表 appcenter.t_cms_node_membergroup 结构
CREATE TABLE IF NOT EXISTS `t_cms_node_membergroup` (
  `nodemgroup_id` int(11) NOT NULL DEFAULT '0',
  `node_id` int(11) NOT NULL DEFAULT '0',
  `is_view_perm` char(1) NOT NULL DEFAULT '1',
  `is_contri_perm` char(1) NOT NULL DEFAULT '1',
  `is_comment_perm` char(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`nodemgroup_id`),
  KEY `fk_cms_nodemgroup_node` (`node_id`),
  CONSTRAINT `t_cms_node_membergroup_ibfk_1` FOREIGN KEY (`node_id`) REFERENCES `t_cms_node` (`node_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。


-- 导出  表 appcenter.t_cms_node_role 结构
CREATE TABLE IF NOT EXISTS `t_cms_node_role` (
  `noderole_id` int(11) NOT NULL DEFAULT '0',
  `node_id` int(11) NOT NULL DEFAULT '0',
  `is_node_perm` char(1) NOT NULL DEFAULT '1',
  `is_info_perm` char(1) NOT NULL DEFAULT '1',
  `role_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`noderole_id`),
  KEY `fk_cms_noderole_node` (`node_id`),
  CONSTRAINT `t_cms_node_role_ibfk_1` FOREIGN KEY (`node_id`) REFERENCES `t_cms_node` (`node_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。


-- 导出  表 appcenter.t_cms_operation_log 结构
CREATE TABLE IF NOT EXISTS `t_cms_operation_log` (
  `operation_id` int(11) NOT NULL DEFAULT '0',
  `site_id` int(11) NOT NULL DEFAULT '0',
  `name` varchar(150) NOT NULL DEFAULT '',
  `data_id` int(11) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `text` text,
  `ip` varchar(100) NOT NULL DEFAULT '',
  `time` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `type` int(11) NOT NULL DEFAULT '1',
  `user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`operation_id`),
  KEY `fk_cms_operationlog_site` (`site_id`),
  CONSTRAINT `t_cms_operation_log_ibfk_1` FOREIGN KEY (`site_id`) REFERENCES `t_cms_site` (`site_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。


-- 导出  表 appcenter.t_cms_publish_point 结构
CREATE TABLE IF NOT EXISTS `t_cms_publish_point` (
  `publishpoint_id` int(11) NOT NULL DEFAULT '0',
  `global_id` int(11) NOT NULL DEFAULT '0',
  `name` varchar(100) NOT NULL DEFAULT '',
  `description` varchar(255) DEFAULT NULL,
  `store_path` varchar(255) DEFAULT NULL,
  `display_path` varchar(255) DEFAULT NULL,
  `ftp_hostname` varchar(100) DEFAULT NULL,
  `ftp_port` int(11) DEFAULT NULL,
  `ftp_username` varchar(100) DEFAULT NULL,
  `ftp_password` varchar(100) DEFAULT NULL,
  `seq` int(11) NOT NULL DEFAULT '2147483647',
  `method` int(11) NOT NULL DEFAULT '1',
  `type` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`publishpoint_id`),
  KEY `fk_cms_publishpoint_global` (`global_id`),
  CONSTRAINT `t_cms_publish_point_ibfk_1` FOREIGN KEY (`global_id`) REFERENCES `t_cms_global` (`global_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。


-- 导出  表 appcenter.t_cms_qrtz_blob_triggers 结构
CREATE TABLE IF NOT EXISTS `t_cms_qrtz_blob_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL DEFAULT '',
  `TRIGGER_NAME` varchar(200) NOT NULL DEFAULT '',
  `TRIGGER_GROUP` varchar(200) NOT NULL DEFAULT '',
  `BLOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。


-- 导出  表 appcenter.t_cms_qrtz_calendars 结构
CREATE TABLE IF NOT EXISTS `t_cms_qrtz_calendars` (
  `SCHED_NAME` varchar(120) NOT NULL DEFAULT '',
  `CALENDAR_NAME` varchar(200) NOT NULL DEFAULT '',
  `CALENDAR` blob,
  PRIMARY KEY (`SCHED_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。


-- 导出  表 appcenter.t_cms_qrtz_cron_triggers 结构
CREATE TABLE IF NOT EXISTS `t_cms_qrtz_cron_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL DEFAULT '',
  `TRIGGER_NAME` varchar(200) NOT NULL DEFAULT '',
  `TRIGGER_GROUP` varchar(200) NOT NULL DEFAULT '',
  `CRON_EXPRESSION` varchar(120) NOT NULL DEFAULT '',
  `TIME_ZONE_ID` varchar(80) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。


-- 导出  表 appcenter.t_cms_qrtz_fired_triggers 结构
CREATE TABLE IF NOT EXISTS `t_cms_qrtz_fired_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL DEFAULT '',
  `ENTRY_ID` varchar(95) NOT NULL DEFAULT '',
  `TRIGGER_NAME` varchar(200) NOT NULL DEFAULT '',
  `TRIGGER_GROUP` varchar(200) NOT NULL DEFAULT '',
  `INSTANCE_NAME` varchar(200) NOT NULL DEFAULT '',
  `FIRED_TIME` bigint(20) NOT NULL DEFAULT '0',
  `PRIORITY` int(11) NOT NULL DEFAULT '0',
  `STATE` varchar(16) NOT NULL DEFAULT '',
  `JOB_NAME` varchar(200) DEFAULT NULL,
  `JOB_GROUP` varchar(200) DEFAULT NULL,
  `IS_NONCONCURRENT` varchar(1) DEFAULT NULL,
  `REQUESTS_RECOVERY` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。


-- 导出  表 appcenter.t_cms_qrtz_job_details 结构
CREATE TABLE IF NOT EXISTS `t_cms_qrtz_job_details` (
  `SCHED_NAME` varchar(120) NOT NULL DEFAULT '',
  `JOB_NAME` varchar(200) NOT NULL DEFAULT '',
  `JOB_GROUP` varchar(200) NOT NULL DEFAULT '',
  `DESCRIPTION` varchar(250) DEFAULT NULL,
  `JOB_CLASS_NAME` varchar(250) NOT NULL DEFAULT '',
  `IS_DURABLE` varchar(1) NOT NULL DEFAULT '',
  `IS_NONCONCURRENT` varchar(1) NOT NULL DEFAULT '',
  `IS_UPDATE_DATA` varchar(1) NOT NULL DEFAULT '',
  `REQUESTS_RECOVERY` varchar(1) NOT NULL DEFAULT '',
  `JOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。


-- 导出  表 appcenter.t_cms_qrtz_locks 结构
CREATE TABLE IF NOT EXISTS `t_cms_qrtz_locks` (
  `SCHED_NAME` varchar(120) NOT NULL DEFAULT '',
  `LOCK_NAME` varchar(40) NOT NULL DEFAULT '',
  PRIMARY KEY (`SCHED_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。


-- 导出  表 appcenter.t_cms_qrtz_paused_trigger_grps 结构
CREATE TABLE IF NOT EXISTS `t_cms_qrtz_paused_trigger_grps` (
  `SCHED_NAME` varchar(120) NOT NULL DEFAULT '',
  `TRIGGER_GROUP` varchar(200) NOT NULL DEFAULT '',
  PRIMARY KEY (`SCHED_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。


-- 导出  表 appcenter.t_cms_qrtz_scheduler_state 结构
CREATE TABLE IF NOT EXISTS `t_cms_qrtz_scheduler_state` (
  `SCHED_NAME` varchar(120) NOT NULL DEFAULT '',
  `INSTANCE_NAME` varchar(200) NOT NULL DEFAULT '',
  `LAST_CHECKIN_TIME` bigint(20) NOT NULL DEFAULT '0',
  `CHECKIN_INTERVAL` bigint(20) NOT NULL DEFAULT '0',
  PRIMARY KEY (`SCHED_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。


-- 导出  表 appcenter.t_cms_qrtz_simple_triggers 结构
CREATE TABLE IF NOT EXISTS `t_cms_qrtz_simple_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL DEFAULT '',
  `TRIGGER_NAME` varchar(200) NOT NULL DEFAULT '',
  `TRIGGER_GROUP` varchar(200) NOT NULL DEFAULT '',
  `REPEAT_COUNT` bigint(20) NOT NULL DEFAULT '0',
  `REPEAT_INTERVAL` bigint(20) NOT NULL DEFAULT '0',
  `TIMES_TRIGGERED` bigint(20) NOT NULL DEFAULT '0',
  PRIMARY KEY (`SCHED_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。


-- 导出  表 appcenter.t_cms_qrtz_simprop_triggers 结构
CREATE TABLE IF NOT EXISTS `t_cms_qrtz_simprop_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL DEFAULT '',
  `TRIGGER_NAME` varchar(200) NOT NULL DEFAULT '',
  `TRIGGER_GROUP` varchar(200) NOT NULL DEFAULT '',
  `STR_PROP_1` varchar(512) DEFAULT NULL,
  `STR_PROP_2` varchar(512) DEFAULT NULL,
  `STR_PROP_3` varchar(512) DEFAULT NULL,
  `INT_PROP_1` int(11) DEFAULT NULL,
  `INT_PROP_2` int(11) DEFAULT NULL,
  `LONG_PROP_1` bigint(20) DEFAULT NULL,
  `LONG_PROP_2` bigint(20) DEFAULT NULL,
  `DEC_PROP_1` decimal(13,4) DEFAULT NULL,
  `DEC_PROP_2` decimal(13,4) DEFAULT NULL,
  `BOOL_PROP_1` varchar(1) DEFAULT NULL,
  `BOOL_PROP_2` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。


-- 导出  表 appcenter.t_cms_qrtz_triggers 结构
CREATE TABLE IF NOT EXISTS `t_cms_qrtz_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL DEFAULT '',
  `TRIGGER_NAME` varchar(200) NOT NULL DEFAULT '',
  `TRIGGER_GROUP` varchar(200) NOT NULL DEFAULT '',
  `JOB_NAME` varchar(200) NOT NULL DEFAULT '',
  `JOB_GROUP` varchar(200) NOT NULL DEFAULT '',
  `DESCRIPTION` varchar(250) DEFAULT NULL,
  `NEXT_FIRE_TIME` bigint(20) DEFAULT NULL,
  `PREV_FIRE_TIME` bigint(20) DEFAULT NULL,
  `PRIORITY` int(11) DEFAULT NULL,
  `TRIGGER_STATE` varchar(16) NOT NULL DEFAULT '',
  `TRIGGER_TYPE` varchar(8) NOT NULL DEFAULT '',
  `START_TIME` bigint(20) NOT NULL DEFAULT '0',
  `END_TIME` bigint(20) DEFAULT NULL,
  `CALENDAR_NAME` varchar(200) DEFAULT NULL,
  `MISFIRE_INSTR` smallint(6) DEFAULT NULL,
  `JOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。


-- 导出  表 appcenter.t_cms_schedule_job 结构
CREATE TABLE IF NOT EXISTS `t_cms_schedule_job` (
  `schedulejob_id` int(11) NOT NULL DEFAULT '0',
  `site_id` int(11) NOT NULL DEFAULT '0',
  `name` varchar(100) NOT NULL DEFAULT '',
  `group` varchar(100) DEFAULT NULL,
  `code` varchar(100) NOT NULL DEFAULT '',
  `data` text,
  `description` varchar(255) DEFAULT NULL,
  `cron_expression` varchar(100) DEFAULT NULL,
  `start_time` datetime DEFAULT NULL,
  `end_time` datetime DEFAULT NULL,
  `start_delay` bigint(20) DEFAULT NULL,
  `repeat_interval` bigint(20) DEFAULT NULL,
  `unit` int(11) DEFAULT NULL,
  `cycle` int(11) NOT NULL DEFAULT '1',
  `status` int(11) NOT NULL DEFAULT '0',
  `user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`schedulejob_id`),
  KEY `fk_cms_schedulejob_site` (`site_id`),
  CONSTRAINT `t_cms_schedule_job_ibfk_1` FOREIGN KEY (`site_id`) REFERENCES `t_cms_site` (`site_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。


-- 导出  表 appcenter.t_cms_site 结构
CREATE TABLE IF NOT EXISTS `t_cms_site` (
  `site_id` int(11) NOT NULL DEFAULT '0',
  `global_id` int(11) NOT NULL DEFAULT '0',
  `html_publishpoint_id` int(11) NOT NULL DEFAULT '0',
  `parent_id` int(11) DEFAULT NULL,
  `name` varchar(100) NOT NULL DEFAULT '',
  `number` varchar(100) NOT NULL DEFAULT '',
  `full_name` varchar(100) DEFAULT NULL,
  `no_picture` varchar(255) NOT NULL DEFAULT '/img/no_picture.jpg',
  `template_theme` varchar(100) NOT NULL DEFAULT 'default',
  `domain` varchar(100) NOT NULL DEFAULT 'localhost',
  `is_identify_domain` char(1) NOT NULL DEFAULT '0',
  `is_static_home` char(1) NOT NULL DEFAULT '0',
  `is_def` char(1) NOT NULL DEFAULT '0',
  `status` int(11) NOT NULL DEFAULT '0',
  `tree_number` varchar(100) NOT NULL DEFAULT '0000',
  `tree_level` int(11) NOT NULL DEFAULT '0',
  `tree_max` varchar(10) NOT NULL DEFAULT '0000',
  `show_index` tinyint(4) DEFAULT '1',
  `need_login` tinyint(4) DEFAULT '0',
  `point_step` tinyint(4) DEFAULT '1',
  `org_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`site_id`),
  KEY `fk_cms_site_global` (`global_id`),
  KEY `fk_cms_site_publishpint` (`html_publishpoint_id`),
  KEY `fk_cms_site_parent` (`parent_id`),
  CONSTRAINT `t_cms_site_ibfk_1` FOREIGN KEY (`global_id`) REFERENCES `t_cms_global` (`global_id`),
  CONSTRAINT `t_cms_site_ibfk_2` FOREIGN KEY (`html_publishpoint_id`) REFERENCES `t_cms_publish_point` (`publishpoint_id`),
  CONSTRAINT `t_cms_site_ibfk_3` FOREIGN KEY (`parent_id`) REFERENCES `t_cms_site` (`site_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。


-- 导出  表 appcenter.t_cms_site_clob 结构
CREATE TABLE IF NOT EXISTS `t_cms_site_clob` (
  `site_id` int(11) NOT NULL DEFAULT '0',
  `key` varchar(50) NOT NULL DEFAULT '',
  `value` text,
  KEY `fk_cms_siteclob_site` (`site_id`),
  CONSTRAINT `t_cms_site_clob_ibfk_1` FOREIGN KEY (`site_id`) REFERENCES `t_cms_site` (`site_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。


-- 导出  表 appcenter.t_cms_site_custom 结构
CREATE TABLE IF NOT EXISTS `t_cms_site_custom` (
  `site_id` int(11) NOT NULL DEFAULT '0',
  `key` varchar(50) NOT NULL DEFAULT '',
  `value` varchar(2000) DEFAULT NULL,
  KEY `fk_cms_sitecustom_site` (`site_id`),
  CONSTRAINT `t_cms_site_custom_ibfk_1` FOREIGN KEY (`site_id`) REFERENCES `t_cms_site` (`site_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。


-- 导出  表 appcenter.t_cms_sns_op_log 结构
CREATE TABLE IF NOT EXISTS `t_cms_sns_op_log` (
  `id` bigint(20) NOT NULL DEFAULT '0',
  `site_id` int(11) DEFAULT NULL,
  `ftype` tinyint(4) NOT NULL DEFAULT '0',
  `fid` int(11) NOT NULL DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  `title` varchar(150) DEFAULT NULL,
  `author` varchar(20) DEFAULT NULL,
  `py_code` varchar(20) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_cms_sns_op_log_site` (`site_id`),
  CONSTRAINT `t_cms_sns_op_log_ibfk_1` FOREIGN KEY (`site_id`) REFERENCES `t_cms_site` (`site_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。


-- 导出  表 appcenter.t_cms_special 结构
CREATE TABLE IF NOT EXISTS `t_cms_special` (
  `special_id` int(11) NOT NULL DEFAULT '0',
  `model_id` int(11) NOT NULL DEFAULT '0',
  `site_id` int(11) DEFAULT '0',
  `speccate_id` int(11) NOT NULL DEFAULT '0',
  `creation_date` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `title` varchar(150) NOT NULL DEFAULT '',
  `meta_keywords` varchar(150) DEFAULT NULL,
  `meta_description` varchar(255) DEFAULT NULL,
  `special_template` varchar(255) DEFAULT NULL,
  `small_image` varchar(255) DEFAULT NULL,
  `large_image` varchar(255) DEFAULT NULL,
  `video` varchar(255) DEFAULT NULL,
  `video_name` varchar(255) DEFAULT NULL,
  `refers` int(11) NOT NULL DEFAULT '0',
  `views` int(11) NOT NULL DEFAULT '0',
  `is_with_image` char(1) NOT NULL DEFAULT '0',
  `is_recommend` char(1) NOT NULL DEFAULT '0',
  `video_length` bigint(20) DEFAULT NULL,
  `video_time` varchar(100) DEFAULT NULL,
  `priority` tinyint(4) DEFAULT NULL,
  `creator_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`special_id`),
  KEY `fk_cms_special_model` (`model_id`),
  KEY `fk_cms_special_site` (`site_id`),
  KEY `fk_cms_special_speccate` (`speccate_id`),
  CONSTRAINT `t_cms_special_ibfk_1` FOREIGN KEY (`model_id`) REFERENCES `t_cms_model` (`model_id`),
  CONSTRAINT `t_cms_special_ibfk_2` FOREIGN KEY (`site_id`) REFERENCES `t_cms_site` (`site_id`),
  CONSTRAINT `t_cms_special_ibfk_3` FOREIGN KEY (`speccate_id`) REFERENCES `t_cms_special_category` (`speccate_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。


-- 导出  表 appcenter.t_cms_special_category 结构
CREATE TABLE IF NOT EXISTS `t_cms_special_category` (
  `speccate_id` int(11) NOT NULL DEFAULT '0',
  `site_id` int(11) DEFAULT '0',
  `name` varchar(50) NOT NULL DEFAULT '',
  `seq` int(11) DEFAULT '2147483647',
  `views` int(11) DEFAULT '0',
  `meta_keywords` varchar(150) DEFAULT NULL,
  `meta_description` varchar(255) DEFAULT NULL,
  `creation_date` datetime DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`speccate_id`),
  KEY `fk_cms_speccategory_site` (`site_id`),
  CONSTRAINT `t_cms_special_category_ibfk_1` FOREIGN KEY (`site_id`) REFERENCES `t_cms_site` (`site_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。


-- 导出  表 appcenter.t_cms_special_clob 结构
CREATE TABLE IF NOT EXISTS `t_cms_special_clob` (
  `special_id` int(11) NOT NULL DEFAULT '0',
  `key` varchar(50) NOT NULL DEFAULT '',
  `value` text,
  KEY `fk_cms_specialclob_special` (`special_id`),
  CONSTRAINT `t_cms_special_clob_ibfk_1` FOREIGN KEY (`special_id`) REFERENCES `t_cms_special` (`special_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。


-- 导出  表 appcenter.t_cms_special_custom 结构
CREATE TABLE IF NOT EXISTS `t_cms_special_custom` (
  `special_id` int(11) NOT NULL DEFAULT '0',
  `key` varchar(50) DEFAULT NULL,
  `value` varchar(2000) DEFAULT NULL,
  KEY `fk_cms_specialcustom_special` (`special_id`),
  CONSTRAINT `t_cms_special_custom_ibfk_1` FOREIGN KEY (`special_id`) REFERENCES `t_cms_special` (`special_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。


-- 导出  表 appcenter.t_cms_special_file 结构
CREATE TABLE IF NOT EXISTS `t_cms_special_file` (
  `special_id` int(11) NOT NULL DEFAULT '0',
  `name` varchar(150) NOT NULL DEFAULT '',
  `length` bigint(20) NOT NULL DEFAULT '0',
  `file` varchar(255) NOT NULL DEFAULT '',
  `index` int(11) NOT NULL DEFAULT '0',
  `downloads` int(11) NOT NULL DEFAULT '0',
  KEY `fk_cms_specialfile_special` (`special_id`),
  CONSTRAINT `t_cms_special_file_ibfk_1` FOREIGN KEY (`special_id`) REFERENCES `t_cms_special` (`special_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。


-- 导出  表 appcenter.t_cms_special_image 结构
CREATE TABLE IF NOT EXISTS `t_cms_special_image` (
  `special_id` int(11) NOT NULL DEFAULT '0',
  `name` varchar(150) DEFAULT NULL,
  `image` varchar(255) DEFAULT NULL,
  `index` int(11) NOT NULL DEFAULT '0',
  `text` text,
  KEY `fk_cms_specialimage_special` (`special_id`),
  CONSTRAINT `t_cms_special_image_ibfk_1` FOREIGN KEY (`special_id`) REFERENCES `t_cms_special` (`special_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。


-- 导出  表 appcenter.t_cms_task 结构
CREATE TABLE IF NOT EXISTS `t_cms_task` (
  `task_id` int(11) NOT NULL DEFAULT '0',
  `site_id` int(11) NOT NULL DEFAULT '0',
  `name` varchar(150) NOT NULL DEFAULT '',
  `description` text,
  `begin_time` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `end_time` datetime DEFAULT NULL,
  `total` int(11) NOT NULL DEFAULT '0',
  `type` int(11) NOT NULL DEFAULT '1',
  `status` int(11) NOT NULL DEFAULT '0',
  `user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`task_id`),
  KEY `fk_cms_task_site` (`site_id`),
  CONSTRAINT `t_cms_task_ibfk_1` FOREIGN KEY (`site_id`) REFERENCES `t_cms_site` (`site_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。


-- 导出  表 appcenter.t_cms_workflow 结构
CREATE TABLE IF NOT EXISTS `t_cms_workflow` (
  `workflow_id` int(11) NOT NULL DEFAULT '0',
  `workflowgroup_id` int(11) NOT NULL DEFAULT '0',
  `site_id` int(11) NOT NULL DEFAULT '0',
  `name` varchar(100) NOT NULL DEFAULT '',
  `description` varchar(255) DEFAULT NULL,
  `seq` int(11) NOT NULL DEFAULT '2147483647',
  `status` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`workflow_id`),
  KEY `fk_cms_workflow_site` (`site_id`),
  KEY `fk_cms_workflow_workflowgroup` (`workflowgroup_id`),
  CONSTRAINT `t_cms_workflow_ibfk_1` FOREIGN KEY (`site_id`) REFERENCES `t_cms_site` (`site_id`),
  CONSTRAINT `t_cms_workflow_ibfk_2` FOREIGN KEY (`workflowgroup_id`) REFERENCES `t_cms_workflow_group` (`workflowgroup_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。


-- 导出  表 appcenter.t_cms_workflowstep_role 结构
CREATE TABLE IF NOT EXISTS `t_cms_workflowstep_role` (
  `wfsteprole_id` int(11) NOT NULL DEFAULT '0',
  `workflowstep_id` int(11) NOT NULL DEFAULT '0',
  `role_index` int(11) NOT NULL DEFAULT '0',
  `role_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`wfsteprole_id`),
  KEY `fk_cms_wfsteprole_wfstep` (`workflowstep_id`),
  CONSTRAINT `t_cms_workflowstep_role_ibfk_1` FOREIGN KEY (`workflowstep_id`) REFERENCES `t_cms_workflow_step` (`workflowstep_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。


-- 导出  表 appcenter.t_cms_workflow_group 结构
CREATE TABLE IF NOT EXISTS `t_cms_workflow_group` (
  `workflowgroup_id` int(11) NOT NULL DEFAULT '0',
  `site_id` int(11) NOT NULL DEFAULT '0',
  `name` varchar(100) NOT NULL DEFAULT '',
  `description` varchar(255) DEFAULT NULL,
  `seq` int(11) NOT NULL DEFAULT '2147483647',
  PRIMARY KEY (`workflowgroup_id`),
  KEY `fk_cms_workflowgroup_site` (`site_id`),
  CONSTRAINT `t_cms_workflow_group_ibfk_1` FOREIGN KEY (`site_id`) REFERENCES `t_cms_site` (`site_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。


-- 导出  表 appcenter.t_cms_workflow_log 结构
CREATE TABLE IF NOT EXISTS `t_cms_workflow_log` (
  `workflowlog_id` int(11) NOT NULL DEFAULT '0',
  `site_id` int(11) NOT NULL DEFAULT '0',
  `workflowprocess_id` int(11) NOT NULL DEFAULT '0',
  `from` varchar(100) NOT NULL DEFAULT '',
  `to` varchar(100) NOT NULL DEFAULT '',
  `creation_date` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `opinion` varchar(255) DEFAULT NULL,
  `type` int(11) NOT NULL DEFAULT '0',
  `user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`workflowlog_id`),
  KEY `fk_cms_workflowlog_site` (`site_id`),
  KEY `fk_cms_workflowlog_wfprocess` (`workflowprocess_id`),
  CONSTRAINT `t_cms_workflow_log_ibfk_1` FOREIGN KEY (`site_id`) REFERENCES `t_cms_site` (`site_id`),
  CONSTRAINT `t_cms_workflow_log_ibfk_2` FOREIGN KEY (`workflowprocess_id`) REFERENCES `t_cms_workflow_process` (`workflowprocess_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。


-- 导出  表 appcenter.t_cms_workflow_process 结构
CREATE TABLE IF NOT EXISTS `t_cms_workflow_process` (
  `workflowprocess_id` int(11) NOT NULL DEFAULT '0',
  `workflowstep_id` int(11) DEFAULT NULL,
  `site_id` int(11) NOT NULL DEFAULT '0',
  `workflow_id` int(11) NOT NULL DEFAULT '0',
  `data_id` int(11) NOT NULL DEFAULT '0',
  `data_type` int(11) NOT NULL DEFAULT '0',
  `begin_date` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `end_date` datetime DEFAULT NULL,
  `is_rejection` char(1) NOT NULL DEFAULT '0',
  `is_end` char(1) NOT NULL DEFAULT '0',
  `user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`workflowprocess_id`),
  KEY `fk_cms_workflowproc_site` (`site_id`),
  KEY `fk_cms_workflowproc_workflow` (`workflow_id`),
  KEY `fk_cms_wfprocess_wfstep` (`workflowstep_id`),
  CONSTRAINT `t_cms_workflow_process_ibfk_1` FOREIGN KEY (`site_id`) REFERENCES `t_cms_site` (`site_id`),
  CONSTRAINT `t_cms_workflow_process_ibfk_2` FOREIGN KEY (`workflow_id`) REFERENCES `t_cms_workflow` (`workflow_id`),
  CONSTRAINT `t_cms_workflow_process_ibfk_3` FOREIGN KEY (`workflowstep_id`) REFERENCES `t_cms_workflow_step` (`workflowstep_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。


-- 导出  表 appcenter.t_cms_workflow_step 结构
CREATE TABLE IF NOT EXISTS `t_cms_workflow_step` (
  `workflowstep_id` int(11) NOT NULL DEFAULT '0',
  `workflow_id` int(11) NOT NULL DEFAULT '0',
  `name` varchar(100) NOT NULL DEFAULT '',
  `seq` int(11) NOT NULL DEFAULT '2147483647',
  PRIMARY KEY (`workflowstep_id`),
  KEY `fk_cms_workflowstep_workflow` (`workflow_id`),
  CONSTRAINT `t_cms_workflow_step_ibfk_1` FOREIGN KEY (`workflow_id`) REFERENCES `t_cms_workflow` (`workflow_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。


-- 导出  表 appcenter.t_id_table 结构
CREATE TABLE IF NOT EXISTS `t_id_table` (
  `table_name` varchar(100) NOT NULL DEFAULT '',
  `id_value` bigint(20) NOT NULL DEFAULT '0',
  PRIMARY KEY (`table_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。


-- 导出  表 appcenter.t_sys_config 结构
CREATE TABLE IF NOT EXISTS `t_sys_config` (
  `keyName` varchar(64) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `keyValue` varchar(128) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `dataType` int(11) NOT NULL DEFAULT '0',
  `createTime` datetime NOT NULL,
  `lastUpdate` datetime DEFAULT NULL,
  `note` varchar(64) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`keyName`),
  KEY `Index_1` (`keyName`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。


-- 导出  表 appcenter.t_sys_menu 结构
CREATE TABLE IF NOT EXISTS `t_sys_menu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `menuName` varchar(32) NOT NULL COMMENT '菜单名称',
  `parentId` bigint(20) NOT NULL DEFAULT '0' COMMENT '父菜单ID',
  `menuCode` varchar(32) DEFAULT NULL COMMENT '编号',
  `menuUrl` varchar(256) DEFAULT NULL COMMENT '菜单URL',
  `urlTarget` varchar(32) DEFAULT NULL COMMENT '页面打开位置',
  `navMenu` int(11) NOT NULL DEFAULT '0' COMMENT '0:不显示在导航菜单中,1:显示在导航菜单中',
  `menuArea` int(11) NOT NULL DEFAULT '0' COMMENT '菜单类型,0:会员菜单,1:后台菜单',
  `sort` int(11) NOT NULL DEFAULT '0' COMMENT '排序',
  `iconCls` varchar(32) DEFAULT NULL,
  `iconUrl` varchar(256) DEFAULT NULL,
  `remark` varchar(128) DEFAULT NULL COMMENT '备注',
  `createTime` datetime NOT NULL,
  `lastUpdate` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。


-- 导出  表 appcenter.t_sys_menu_perms 结构
CREATE TABLE IF NOT EXISTS `t_sys_menu_perms` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `menuId` bigint(20) NOT NULL COMMENT '菜单id',
  `permId` bigint(20) NOT NULL COMMENT '权限点ID',
  PRIMARY KEY (`id`),
  KEY `Index_1` (`menuId`),
  KEY `FK_Reference_s8` (`permId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。


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

-- 数据导出被取消选择。


-- 导出  表 appcenter.t_sys_role 结构
CREATE TABLE IF NOT EXISTS `t_sys_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '管理角色标识',
  `roleName` varchar(32) NOT NULL COMMENT '角色名称',
  `status` int(11) NOT NULL COMMENT '0:禁用,1:启用',
  `createTime` datetime NOT NULL COMMENT '角色创建时间',
  `lastUpdate` datetime NOT NULL COMMENT '角色最近修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。


-- 导出  表 appcenter.t_sys_role_menu 结构
CREATE TABLE IF NOT EXISTS `t_sys_role_menu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `roleId` bigint(20) NOT NULL COMMENT '角色id',
  `menuId` bigint(20) NOT NULL COMMENT '菜单ID',
  PRIMARY KEY (`id`),
  KEY `Index_1` (`roleId`),
  KEY `FK_Reference_s4` (`menuId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。


-- 导出  表 appcenter.t_sys_role_perms 结构
CREATE TABLE IF NOT EXISTS `t_sys_role_perms` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `roleId` bigint(20) NOT NULL COMMENT '角色id',
  `permId` bigint(20) NOT NULL COMMENT '权限点ID',
  PRIMARY KEY (`id`),
  KEY `Index_1` (`roleId`),
  KEY `FK_Reference_s5` (`permId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色与Perms';

-- 数据导出被取消选择。


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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。


-- 导出  表 appcenter.t_sys_user_role 结构
CREATE TABLE IF NOT EXISTS `t_sys_user_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `userId` bigint(20) NOT NULL COMMENT '用户id',
  `roleId` bigint(20) NOT NULL COMMENT '角色id',
  PRIMARY KEY (`id`),
  KEY `Index_1` (`userId`),
  KEY `FK_Reference_s2` (`roleId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。


-- 导出  表 appcenter.t_sys_user_vcoin 结构
CREATE TABLE IF NOT EXISTS `t_sys_user_vcoin` (
  `userId` bigint(20) NOT NULL COMMENT '用户积分',
  `userName` varchar(128) NOT NULL,
  `historyTotal` bigint(20) NOT NULL DEFAULT '0',
  `availableTotal` bigint(20) NOT NULL DEFAULT '0',
  PRIMARY KEY (`userId`),
  KEY `f_userId_idx` (`userId`,`userName`),
  CONSTRAINT `userId` FOREIGN KEY (`userId`) REFERENCES `t_sys_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
