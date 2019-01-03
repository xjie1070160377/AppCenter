
CREATE TABLE t_currency_cul_node
(
	node_id            integer NULL,
	rule_id              INTEGER NULL,
	cul_id               INTEGER NOT NULL
);



ALTER TABLE t_currency_cul_node
ADD PRIMARY KEY (cul_id);



CREATE TABLE t_currency_log
(
	change_id            INTEGER NULL,
	rule_id              INTEGER NULL,
	detail_id            INTEGER NULL,
	createtime           DATE NULL,
	quantity             NUMERIC(8,2) NULL,
	remain               NUMERIC(8,2) NULL,
	log_id               INTEGER NOT NULL
);



ALTER TABLE t_currency_log
ADD PRIMARY KEY (log_id);



CREATE TABLE t_currency_rule
(
	rule_id              INTEGER NOT NULL,
	rule_name            VARCHAR(200) NULL,
	total                NUMERIC(8,2) NULL,
	max_total            NUMERIC(8,2) NULL,
	status               NUMERIC(1) NULL,
	caption              VARCHAR(500) NULL,
	isspecial            NUMERIC(1) NULL,
	rule_code            VARCHAR(20) NULL
);



ALTER TABLE t_currency_rule
ADD PRIMARY KEY (rule_id);



CREATE TABLE t_currency_special
(
	info_id            integer NULL,
	rule_id              INTEGER NULL,
	special_id           INTEGER NOT NULL
);



ALTER TABLE t_currency_special
ADD PRIMARY KEY (special_id);



CREATE TABLE t_draw_rule
(
	rule_id              INTEGER NOT NULL,
	title                VARCHAR(500) NULL,
	caption              TEXT  NULL,
	start_time           DATE NULL,
	end_time             DATE NULL,
	status               NUMERIC(1) NULL,
	day_limit            NUMERIC(2) NULL
);



ALTER TABLE t_draw_rule
ADD PRIMARY KEY (rule_id);



CREATE TABLE t_exchange_goods
(
	rule_id              INTEGER NULL,
	good_id              INTEGER NULL,
	detail_id            INTEGER NOT NULL,
	total                NUMERIC(2) NULL,
	sum_total            NUMERIC(5) NULL,
	remain_total         NUMERIC(5) NULL,
	day_limit            NUMERIC(5) NULL
);



ALTER TABLE t_exchange_goods
ADD PRIMARY KEY (detail_id);



CREATE TABLE t_exchange_rule
(
	rule_id              INTEGER NOT NULL,
	rule_name            VARCHAR(200) NULL,
	caption              VARCHAR(500) NULL,
	start_time           DATE NULL,
	end_time             DATE NULL,
	total                NUMERIC(8,2) NULL,
	day_limit            NUMERIC(5) NULL,
	status               NUMERIC(1) NULL,
	rule_type            NUMERIC(1) NULL
);



ALTER TABLE t_exchange_rule
ADD PRIMARY KEY (rule_id);



CREATE TABLE t_goods
(
	good_id              INTEGER NOT NULL,
	good_name            VARCHAR(200) NULL,
	caption              VARCHAR(500) NULL,
	price                NUMERIC(8,2) NULL,
	url                  VARCHAR(500) NULL
);



ALTER TABLE t_goods
ADD PRIMARY KEY (good_id);



CREATE TABLE t_goods_chance
(
	good_id              INTEGER NULL,
	rule_id              INTEGER NULL,
	chance_id            INTEGER NOT NULL,
	chance               NUMERIC(5,2) NULL,
	total                NUMERIC(5) NULL,
	remian_total         NUMERIC(5) NULL
);



ALTER TABLE t_goods_chance
ADD PRIMARY KEY (chance_id);



CREATE TABLE t_user_day_cur
(
	user_id            bigint(20) NULL,
	rule_id              INTEGER NULL,
	last_time            DATE NULL,
	total                NUMERIC(8,2) NULL,
	day_id               INTEGER NOT NULL
);



ALTER TABLE t_user_day_cur
ADD PRIMARY KEY (day_id);



ALTER TABLE t_currency_cul_node
ADD FOREIGN KEY FK_CURRENCY_NODE (node_id) REFERENCES t_cms_node (node_id);



ALTER TABLE t_currency_cul_node
ADD FOREIGN KEY FK_CURRENCY_CUR_NODE (rule_id) REFERENCES t_currency_rule (rule_id);



ALTER TABLE t_currency_log
ADD FOREIGN KEY FK_EXCHANGE_LOG (change_id) REFERENCES t_exchange_rule (rule_id);



ALTER TABLE t_currency_log
ADD FOREIGN KEY FK_DRAW_LOG (rule_id) REFERENCES t_draw_rule (rule_id);



ALTER TABLE t_currency_log
ADD FOREIGN KEY FK_EXGOODS_LOG (detail_id) REFERENCES t_exchange_goods (detail_id);



ALTER TABLE t_currency_special
ADD FOREIGN KEY FK_CURRENCY_INFO (info_id) REFERENCES t_cms_info (info_id);



ALTER TABLE t_currency_special
ADD FOREIGN KEY FK_CURRENCY_SPECIAL (rule_id) REFERENCES t_currency_rule (rule_id);



ALTER TABLE t_exchange_goods
ADD FOREIGN KEY FK_EXCHANGE_RULE (rule_id) REFERENCES t_exchange_rule (rule_id);



ALTER TABLE t_exchange_goods
ADD FOREIGN KEY FK_EXCHANGE_GOODS (good_id) REFERENCES t_goods (good_id);



ALTER TABLE t_goods_chance
ADD FOREIGN KEY FK_GOODS_CHANCE (good_id) REFERENCES t_goods (good_id);



ALTER TABLE t_goods_chance
ADD FOREIGN KEY FK_DRAW_RULE_CHANCE (rule_id) REFERENCES t_draw_rule (rule_id);



ALTER TABLE t_user_day_cur
ADD FOREIGN KEY FK_USER_DAY_CUR (user_id) REFERENCES t_sys_user (id);



ALTER TABLE t_user_day_cur
ADD FOREIGN KEY FK_CURRENCY_RULE (rule_id) REFERENCES t_currency_rule (rule_id);


ALTER TABLE `t_currency_log`
ADD COLUMN `cur_id`  int(11) NULL AFTER `log_id`,
ADD COLUMN `caption`  varchar(500) NULL AFTER `cur_id`;

ALTER TABLE `t_currency_log` ADD CONSTRAINT `t_currency_log_ibfk_4` FOREIGN KEY (`cur_id`) REFERENCES `t_currency_rule` (`rule_id`);


insert into t_id_table (table_name,id_value) values ('t_currency_cul_node',1);
insert into t_id_table (table_name,id_value) values ('t_currency_log',1);
insert into t_id_table (table_name,id_value) values ('t_currency_rule',1);
insert into t_id_table (table_name,id_value) values ('t_currency_special',1);
insert into t_id_table (table_name,id_value) values ('t_draw_rule',1);
insert into t_id_table (table_name,id_value) values ('t_exchange_goods',1);
insert into t_id_table (table_name,id_value) values ('t_exchange_rule',1);
insert into t_id_table (table_name,id_value) values ('t_goods',1);
insert into t_id_table (table_name,id_value) values ('t_goods_chance',1);
insert into t_id_table (table_name,id_value) values ('t_user_day_cur',1);

ALTER TABLE `t_currency_log` 
ADD COLUMN `userId` INT(11) NOT NULL COMMENT '' AFTER `caption`;

ALTER TABLE `t_user_points`
ADD COLUMN `currency`  decimal(8,2) NULL AFTER `points`;
ALTER TABLE `t_currency_log`
ADD COLUMN `dataId`  int(11) NULL AFTER `userId`;

ALTER TABLE `t_currency_log`
ADD COLUMN `ruleCode`  varchar(20) NULL AFTER `dataId`;

