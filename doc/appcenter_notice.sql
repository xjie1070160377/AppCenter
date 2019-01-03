
CREATE TABLE t_notice_info
(
	id                   INTEGER AUTO_INCREMENT,
	nodeId               INTEGER NULL,
	title                VARCHAR(60) NULL,
	description          VARCHAR(200) NULL,
	createTime           DATETIME NULL,
	creatorId            BIGINT NULL,
	smallImage           VARCHAR(200) NULL,
	PRIMARY KEY (id)
);



CREATE TABLE t_notice_infoContent
(
	id                   INTEGER NOT NULL,
	content              LONGTEXT NULL,
	PRIMARY KEY (id)
);



CREATE TABLE t_notice_node
(
	id                   INTEGER AUTO_INCREMENT,
	parent               INTEGER NULL,
	number               VARCHAR(20) NULL,
	description          VARCHAR(200) NULL,
	treeNumber           VARCHAR(100) NULL,
	treeLevel            TINYINT NULL,
	treeMax              VARCHAR(10) NULL,
	PRIMARY KEY (id)
);



ALTER TABLE t_notice_info
ADD FOREIGN KEY R_202 (nodeId) REFERENCES t_notice_node (id);



ALTER TABLE t_notice_infoContent
ADD FOREIGN KEY R_203 (id) REFERENCES t_notice_info (id);



ALTER TABLE t_notice_node
ADD FOREIGN KEY R_201 (parent) REFERENCES t_notice_node (id);



alter TABLE t_notice_info COMMENT = '֪ͨ����' ENGINE=InnoDB DEFAULT CHARSET=utf8;
      ALTER TABLE t_notice_info CHANGE COLUMN nodeId nodeId INTEGER NULL COMMENT '��Ŀ���';
      ALTER TABLE t_notice_info CHANGE COLUMN title title VARCHAR(60) NULL COMMENT '����';
      ALTER TABLE t_notice_info CHANGE COLUMN description description VARCHAR(200) NULL COMMENT 'ժҪ';
      ALTER TABLE t_notice_info CHANGE COLUMN createTime createTime DATETIME NULL COMMENT '����ʱ��';
      ALTER TABLE t_notice_info CHANGE COLUMN creatorId creatorId BIGINT NULL COMMENT '�����˱��';
      ALTER TABLE t_notice_info CHANGE COLUMN smallImage smallImage VARCHAR(200) NULL COMMENT '����ͼ';
      
alter TABLE t_notice_infoContent COMMENT = '֪ͨ��������' ENGINE=InnoDB DEFAULT CHARSET=utf8;
      ALTER TABLE t_notice_infoContent CHANGE COLUMN content content LONGTEXT NULL COMMENT '����';
      
alter TABLE t_notice_node COMMENT = '֪ͨ������Ŀ' ENGINE=InnoDB DEFAULT CHARSET=utf8;
      ALTER TABLE t_notice_node CHANGE COLUMN parent parent INTEGER NULL COMMENT '����Ŀ���';
      ALTER TABLE t_notice_node CHANGE COLUMN number number VARCHAR(20) NULL COMMENT '����';
      ALTER TABLE t_notice_node CHANGE COLUMN description description VARCHAR(200) NULL COMMENT '��ע';
      ALTER TABLE t_notice_node CHANGE COLUMN treeNumber treeNumber VARCHAR(100) NULL COMMENT '������';
      ALTER TABLE t_notice_node CHANGE COLUMN treeLevel treeLevel TINYINT NULL COMMENT '���㼶';
      ALTER TABLE t_notice_node CHANGE COLUMN treeMax treeMax VARCHAR(10) NULL COMMENT '���ӽڵ�������';

ALTER TABLE `t_notice_node`
ADD COLUMN `name`  varchar(20) NULL COMMENT '����' AFTER `treeMax`;

      