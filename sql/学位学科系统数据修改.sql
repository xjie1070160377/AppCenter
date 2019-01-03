
CREATE TABLE dm_cglxsl
(
	cglxsldm             int(5) NOT NULL,
	cglxslmc             VARCHAR(100) NULL
);



ALTER TABLE dm_cglxsl
ADD PRIMARY KEY (cglxsldm);



CREATE TABLE dm_dslx
(
	dslxdm               int(5) NOT NULL,
	dslxmc               VARCHAR(50) NULL
);



ALTER TABLE dm_dslx
ADD PRIMARY KEY (dslxdm);



CREATE TABLE dm_gj
(
	zwjc                 VARCHAR(50) NULL,
	lzfldzmm             VARCHAR(10) NULL,
	szfldzmdm            VARCHAR(10) NULL,
	albszdm              int(5) NOT NULL,
	zwqc                 VARCHAR(100) NULL,
	ywjc                 VARCHAR(100) NULL,
	ywqc                 VARCHAR(100) NULL
);



ALTER TABLE dm_gj
ADD PRIMARY KEY (albszdm);



CREATE TABLE dm_hjdj
(
	hjdjdm               int(5) NOT NULL,
	hjdjmc               VARCHAR(100) NULL
);



ALTER TABLE dm_hjdj
ADD PRIMARY KEY (hjdjdm);



CREATE TABLE dm_hjqk
(
	hjqkdm               int(5) NOT NULL,
	hjqkmc               VARCHAR(100) NULL
);



ALTER TABLE dm_hjqk
ADD PRIMARY KEY (hjqkdm);



CREATE TABLE dm_jglx
(
	jglxm                int(5) NOT NULL,
	jglxmc               VARCHAR(50) NULL
);



ALTER TABLE dm_jglx
ADD PRIMARY KEY (jglxm);



CREATE TABLE t_js_xsrz
(
	jslsh                int(11) NULL,
	rzjg                 VARCHAR(255) NULL,
	drzw                 VARCHAR(150) NULL,
	kssj                 DATE NULL,
	jssj                 DATE NULL,
	xsrzbh               int(11) NOT NULL,
	jglxm                int(5) NULL,
	albszdm              int(5) NULL
);



ALTER TABLE t_js_xsrz
ADD PRIMARY KEY (xsrzbh);



CREATE TABLE dm_jsjl
(
	jsjldm               int(5) NOT NULL,
	jsjlmc               VARCHAR(100) NULL
);



ALTER TABLE dm_jsjl
ADD PRIMARY KEY (jsjldm);



CREATE TABLE dm_lwlxdm
(
	lxdm                 int(5) NOT NULL,
	lxmc                 VARCHAR(100) NULL,
	pxdm                 int(5) NULL
);



ALTER TABLE dm_lwlxdm
ADD PRIMARY KEY (lxdm);



CREATE TABLE dm_mz
(
	szdm                 int(5) NOT NULL,
	mzmc                 VARCHAR(10) NULL,
	lmzmpxf              VARCHAR(100) NULL,
	zmdm                 VARCHAR(10) NULL
);



ALTER TABLE dm_mz
ADD PRIMARY KEY (szdm);



CREATE TABLE dm_pm
(
	pmdm                 int(5) NOT NULL,
	pmmc                 varchar(100) NULL
);



ALTER TABLE dm_pm
ADD PRIMARY KEY (pmdm);



CREATE TABLE dm_rych
(
	rychm                int(5) NOT NULL,
	rychmc               VARCHAR(200) NULL,
	pxdm                 int(5) NULL,
	sfxs                 int(1) NULL
);



ALTER TABLE dm_rych
ADD PRIMARY KEY (rychm);



CREATE TABLE dm_xb
(
	xbdm                 int(5) NOT NULL,
	xb                   VARCHAR(10) NULL
);



ALTER TABLE dm_xb
ADD PRIMARY KEY (xbdm);



CREATE TABLE dm_xklx
(
	id                   int(5) NOT NULL,
	lxmc                 VARCHAR(100) NULL
);



ALTER TABLE dm_xklx
ADD PRIMARY KEY (id);



CREATE TABLE dm_xl
(
	whcddm               int(5) NOT NULL,
	whcd                 VARCHAR(50) NULL
);



ALTER TABLE dm_xl
ADD PRIMARY KEY (whcddm);



CREATE TABLE dm_xmjb
(
	jbdm                 int(5) NOT NULL,
	jbmc                 VARCHAR(100) NULL
);



ALTER TABLE dm_xmjb
ADD PRIMARY KEY (jbdm);



CREATE TABLE dm_xmly
(
	lydm                 int(5) NOT NULL,
	lymc                 VARCHAR(100) NULL
);



ALTER TABLE dm_xmly
ADD PRIMARY KEY (lydm);



CREATE TABLE dm_xw
(
	xwdm                 int(5) NOT NULL,
	xw                   VARCHAR(50) NULL
);



ALTER TABLE dm_xw
ADD PRIMARY KEY (xwdm);



CREATE TABLE dm_xzdm
(
	xzdm                 int(5) NOT NULL,
	xzmc                 VARCHAR(10) NULL
);



ALTER TABLE dm_xzdm
ADD PRIMARY KEY (xzdm);



CREATE TABLE dm_zc
(
	zcdm                 int(5) NOT NULL,
	zcmc                 VARCHAR(100) NULL,
	gzcdm                VARCHAR(10) NULL
);



ALTER TABLE dm_zc
ADD PRIMARY KEY (zcdm);



CREATE TABLE dm_zllx
(
	zllxm                int(5) NOT NULL,
	zllx                 VARCHAR(100) NULL
);



ALTER TABLE dm_zllx
ADD PRIMARY KEY (zllxm);



CREATE TABLE dm_zzmm
(
	zzmmdm               int(5) NOT NULL,
	zzmm                 VARCHAR(100) NULL,
	jc                   VARCHAR(100) NULL
);



ALTER TABLE dm_zzmm
ADD PRIMARY KEY (zzmmdm);



CREATE TABLE t_ds_zy
(
	org_zyid             int(5) NULL,
	jslsh                int(11) NULL,
	sfkxk                int(1) NULL,
	id                   int(11) NOT NULL
);



ALTER TABLE t_ds_zy
ADD PRIMARY KEY (id);



CREATE TABLE t_js
(
	org_id               int(11) NULL,
	jslsh                int(11) NOT NULL,
	csrq                 DATE NULL,
	zcsj                 DATE NULL,
	rdsj                 DATE NULL,
	jg                   VARCHAR(150) NULL,
	yjfx                 VARCHAR(200) NULL,
	tdyy                 VARCHAR(4000) NULL,
	yjfx2                VARCHAR(200) NULL,
	tdyy2                VARCHAR(4000) NULL,
	yjfx3                VARCHAR(100) NULL,
	tdyy3                VARCHAR(4000) NULL,
	rxsj                 DATE NULL,
	status               int(1) NULL,
	lefttime             DATE NULL,
	photo_url            VARCHAR(100) NULL,
	zcdm                 int(5) NULL,
	xbdm                 int(5) NULL,
	szdm                 int(5) NULL,
	zzmmdm               int(5) NULL,
	dslxdm               int(5) NULL,
	sdrq                 DATE NULL,
	bdrq                 DATE NULL,
	oid									 int(5) NULL	
);



ALTER TABLE t_js
ADD PRIMARY KEY (jslsh);



CREATE TABLE t_js_cdxmqk
(
	jslsh                int(11) NULL,
	xmbh                 int(11) NOT NULL,
	xmxdbm               VARCHAR(100) NULL,
	xmhtbh               VARCHAR(100) NULL,
	xmmc                 VARCHAR(200) NULL,
	fzrxm                VARCHAR(100) NULL,
	kssj                 DATE NULL,
	jssj                 DATE NULL,
	jf                   NUMERIC(16,4) NULL,
	SBDWDDZJF            NUMERIC(16,4) NULL,
	lydm                 int(5) NULL,
	jbdm                 int(5) NULL,
	pmdm                 int(5) NULL
);



ALTER TABLE t_js_cdxmqk
ADD PRIMARY KEY (xmbh);



CREATE TABLE t_js_jl
(
	jslsh                int(11) NULL,
	jsjlbh               int(11) NOT NULL,
	hjxmmc               VARCHAR(100) NULL,
	hjmc                 VARCHAR(100) NULL,
	fssj                 DATE NULL,
	bz                   VARCHAR(200) NULL,
	zsbh                 VARCHAR(100) NULL,
	wcr                  VARCHAR(100) NULL,
	cydws                VARCHAR(50) NULL,
	jsjldm               int(5) NULL,
	hjqkdm               int(5) NULL,
	hjdjdm               int(5) NULL,
	pmdm                 int(5) NULL
);



ALTER TABLE t_js_jl
ADD PRIMARY KEY (jsjlbh);



CREATE TABLE t_js_jx
(
	jslsh                int(11) NULL,
	jxbh                 int(11) NOT NULL,
	jxdd                 VARCHAR(200) NULL,
	sfgn                 int(1) NULL,
	jxksrq               DATE NULL,
	jxjsrq               DATE NULL,
	jxnr                 VARCHAR(255) NULL
);



ALTER TABLE t_js_jx
ADD PRIMARY KEY (jxbh);



CREATE TABLE t_js_rych
(
	jsrych_id            int(11) NOT NULL,
	jslsh                int(11) NULL,
	rychm                int(5) NULL,
	hqsj                 DATE NULL
);



ALTER TABLE t_js_rych
ADD PRIMARY KEY (jsrych_id);



CREATE TABLE t_js_xslwqk
(
	jslsh                int(11) NULL,
	lwbh                 int(11) NOT NULL,
	xslwtm               VARCHAR(255) NULL,
	dyzzxm               VARCHAR(50) NULL,
	txzz                 VARCHAR(50) NULL,
	fbrq                 DATE NULL,
	fbkwmc               VARCHAR(200) NULL,
	jsh                  VARCHAR(50) NULL,
	tycs                 int(5) NULL,
	yxyz                 VARCHAR(50) NULL,
	sfdbxcg              int(1) NULL,
	lxdm                 int(5) NULL,
	cglxsldm             int(5) NULL,
	pmdm                 int(5) NULL
);



ALTER TABLE t_js_xslwqk
ADD PRIMARY KEY (lwbh);



CREATE TABLE t_js_xwxl
(
	jslsh                int(11) NOT NULL,
	xwdm                 int(5) NULL,
	whcddm               int(5) NULL,
	hzgxwsj              DATE NULL,
	hzgxwxs              VARCHAR(100) NULL,
	hzgxwzy              VARCHAR(100) NULL,
	hzgxlsj              DATE NULL,
	hzgxlxs              VARCHAR(100) NULL,
	hzgxlzy              VARCHAR(100) NULL
);



ALTER TABLE t_js_xwxl
ADD PRIMARY KEY (jslsh);



CREATE TABLE t_js_zlcgqk
(
	jslsh                int(11) NULL,
	zlcgbh               int(11) NOT NULL,
	zlcgmc               VARCHAR(200) NULL,
	dyfmr                VARCHAR(100) NULL,
	sqbh                 VARCHAR(50) NULL,
	pzrq                 DATE NULL,
	sfdbxcg              int(1) NULL,
	zlsrdw               VARCHAR(100) NULL,
	zllxm                int(5) NULL,
	pmdm                 int(5) NULL
);



ALTER TABLE t_js_zlcgqk
ADD PRIMARY KEY (zlcgbh);



CREATE TABLE t_js_zzjcqk
(
	jslsh                int(11) NULL,
	zzjcbh               int(11) NOT NULL,
	cbsj                 DATE NULL,
	cbdwmc               VARCHAR(50) NULL,
	sfdbxcg              int(1) NULL,
	xzdm                 int(5) NULL,
	pmdm                 int(5) NULL,
	zzjcmc               VARCHAR(200) NULL
);



ALTER TABLE t_js_zzjcqk
ADD PRIMARY KEY (zzjcbh);



CREATE TABLE t_org_xkzy
(
	org_id               int(11) NOT NULL,
	zyid                 int(5) NOT NULL,
	id                   int(5) NOT NULL
);



ALTER TABLE t_org_xkzy
ADD PRIMARY KEY (id);



CREATE TABLE t_syn_data
(
	data_id              int(11) NOT NULL,
	createtime           DATE NULL,
	syn_data             TEXT NULL,
	ora_data             TEXT NULL,
	opr_type             int(1) NULL,
	deal_type            int(1) NULL,
	table_name           VARCHAR(100) NULL,
	dealtime             DATE NULL,
	pkid                 int(11) NULL,
	ifpkid               int(11) NULL,
	user_id              int(11) NULL
);



ALTER TABLE t_syn_data
ADD PRIMARY KEY (data_id);



CREATE TABLE t_syn_table
(
	id                   int(5) NOT NULL,
	table_name           VARCHAR(100) NULL,
	update_time          DATE NULL,
	isreset              int(1) NULL
);



ALTER TABLE t_syn_table
ADD PRIMARY KEY (id);



CREATE TABLE t_sys_lab
(
	org_xkzy_id          int(5) NULL,
	lab_name             VARCHAR(200) NULL,
	lab_id               int(11) NOT NULL
);



ALTER TABLE t_sys_lab
ADD PRIMARY KEY (lab_id);



CREATE TABLE t_xkzy
(
	id                   int(5) NOT NULL,
	parent_id            int(5) NULL,
	zydm                 varchar(18) NULL,
	zymc                 varchar(100) NULL,
	tree_number          varchar(18) NULL,
	tree_level           int(5) NULL,
	tree_max             varchar(18) NULL,
	lxid                 int(5) NULL
);



ALTER TABLE t_xkzy
ADD PRIMARY KEY (id);



ALTER TABLE t_js_xsrz
ADD FOREIGN KEY R_12 (jslsh) REFERENCES t_js (jslsh);



ALTER TABLE t_js_xsrz
ADD FOREIGN KEY R_238 (jglxm) REFERENCES dm_jglx (jglxm);



ALTER TABLE t_js_xsrz
ADD FOREIGN KEY R_239 (albszdm) REFERENCES dm_gj (albszdm);



ALTER TABLE t_ds_zy
ADD FOREIGN KEY R_210 (org_zyid) REFERENCES t_org_xkzy (id);



ALTER TABLE t_ds_zy
ADD FOREIGN KEY R_211 (jslsh) REFERENCES t_js (jslsh);



ALTER TABLE t_js
ADD FOREIGN KEY R_6 (org_id) REFERENCES t_sys_org (org_id);



ALTER TABLE t_js
ADD FOREIGN KEY R_215 (zcdm) REFERENCES dm_zc (zcdm);



ALTER TABLE t_js
ADD FOREIGN KEY R_216 (xbdm) REFERENCES dm_xb (xbdm);



ALTER TABLE t_js
ADD FOREIGN KEY R_217 (szdm) REFERENCES dm_mz (szdm);



ALTER TABLE t_js
ADD FOREIGN KEY R_218 (zzmmdm) REFERENCES dm_zzmm (zzmmdm);



ALTER TABLE t_js
ADD FOREIGN KEY R_219 (dslxdm) REFERENCES dm_dslx (dslxdm);



ALTER TABLE t_js_cdxmqk
ADD FOREIGN KEY R_14 (jslsh) REFERENCES t_js (jslsh);



ALTER TABLE t_js_cdxmqk
ADD FOREIGN KEY R_228 (lydm) REFERENCES dm_xmly (lydm);



ALTER TABLE t_js_cdxmqk
ADD FOREIGN KEY R_229 (jbdm) REFERENCES dm_xmjb (jbdm);



ALTER TABLE t_js_cdxmqk
ADD FOREIGN KEY R_230 (pmdm) REFERENCES dm_pm (pmdm);



ALTER TABLE t_js_jl
ADD FOREIGN KEY R_13 (jslsh) REFERENCES t_js (jslsh);



ALTER TABLE t_js_jl
ADD FOREIGN KEY R_223 (jsjldm) REFERENCES dm_jsjl (jsjldm);



ALTER TABLE t_js_jl
ADD FOREIGN KEY R_224 (hjqkdm) REFERENCES dm_hjqk (hjqkdm);



ALTER TABLE t_js_jl
ADD FOREIGN KEY R_225 (hjdjdm) REFERENCES dm_hjdj (hjdjdm);



ALTER TABLE t_js_jl
ADD FOREIGN KEY R_226 (pmdm) REFERENCES dm_pm (pmdm);



ALTER TABLE t_js_jx
ADD FOREIGN KEY R_18 (jslsh) REFERENCES t_js (jslsh);



ALTER TABLE t_js_rych
ADD FOREIGN KEY R_9 (jslsh) REFERENCES t_js (jslsh);



ALTER TABLE t_js_rych
ADD FOREIGN KEY R_10 (rychm) REFERENCES dm_rych (rychm);



ALTER TABLE t_js_xslwqk
ADD FOREIGN KEY R_16 (jslsh) REFERENCES t_js (jslsh);



ALTER TABLE t_js_xslwqk
ADD FOREIGN KEY R_233 (lxdm) REFERENCES dm_lwlxdm (lxdm);



ALTER TABLE t_js_xslwqk
ADD FOREIGN KEY R_234 (cglxsldm) REFERENCES dm_cglxsl (cglxsldm);



ALTER TABLE t_js_xslwqk
ADD FOREIGN KEY R_235 (pmdm) REFERENCES dm_pm (pmdm);



ALTER TABLE t_js_xwxl
ADD FOREIGN KEY R_220 (jslsh) REFERENCES t_js (jslsh);



ALTER TABLE t_js_xwxl
ADD FOREIGN KEY R_221 (xwdm) REFERENCES dm_xw (xwdm);



ALTER TABLE t_js_xwxl
ADD FOREIGN KEY R_222 (whcddm) REFERENCES dm_xl (whcddm);



ALTER TABLE t_js_zlcgqk
ADD FOREIGN KEY R_17 (jslsh) REFERENCES t_js (jslsh);



ALTER TABLE t_js_zlcgqk
ADD FOREIGN KEY R_236 (zllxm) REFERENCES dm_zllx (zllxm);



ALTER TABLE t_js_zlcgqk
ADD FOREIGN KEY R_237 (pmdm) REFERENCES dm_pm (pmdm);



ALTER TABLE t_js_zzjcqk
ADD FOREIGN KEY R_15 (jslsh) REFERENCES t_js (jslsh);



ALTER TABLE t_js_zzjcqk
ADD FOREIGN KEY R_231 (xzdm) REFERENCES dm_xzdm (xzdm);



ALTER TABLE t_js_zzjcqk
ADD FOREIGN KEY R_232 (pmdm) REFERENCES dm_pm (pmdm);



ALTER TABLE t_org_xkzy
ADD FOREIGN KEY R_2 (org_id) REFERENCES t_sys_org (org_id);



ALTER TABLE t_org_xkzy
ADD FOREIGN KEY R_3 (zyid) REFERENCES t_xkzy (id);



ALTER TABLE t_sys_lab
ADD FOREIGN KEY R_213 (org_xkzy_id) REFERENCES t_org_xkzy (id);



ALTER TABLE t_xkzy
ADD FOREIGN KEY R_214 (lxid) REFERENCES dm_xklx (id);



ALTER TABLE t_xkzy
ADD FOREIGN KEY R_241 (parent_id) REFERENCES t_xkzy (id);



CREATE TABLE t_js_cjhdqk
(
	hdmc                 VARCHAR(100) NULL,
	hddd                 VARCHAR(100) NULL,
	sfgn                 int(1) NULL,
	kssj                 DATE NULL,
	jssj                 DATE NULL,
	hdnr                 VARCHAR(500) NULL,
	jlbh                 int(11) NOT NULL,
	jslsh                int(11) NULL
);



ALTER TABLE t_js_cjhdqk
ADD PRIMARY KEY (jlbh);



ALTER TABLE t_js_cjhdqk
ADD FOREIGN KEY R_247 (jslsh) REFERENCES t_js (jslsh);


ALTER TABLE `t_js_cdxmqk`
MODIFY COLUMN `xmbh`  int(11) NOT NULL AUTO_INCREMENT AFTER `jslsh`;

ALTER TABLE `t_js_zzjcqk`
MODIFY COLUMN `zzjcbh`  int(11) NOT NULL AUTO_INCREMENT AFTER `jslsh`;

ALTER TABLE `t_js_xslwqk`
MODIFY COLUMN `lwbh`  int(11) NOT NULL AUTO_INCREMENT AFTER `jslsh`;

ALTER TABLE `t_js_jl`
ADD COLUMN `sfdbxcg`  int(1) NULL AFTER `pmdm`;

ALTER TABLE `t_js_jl`
MODIFY COLUMN `jsjlbh`  int(11) NOT NULL AUTO_INCREMENT AFTER `jslsh`;
