##数据导入,请先替换旧数据库名
update t_cms_site set status = 1, template_theme = 'html5' where site_id = 1;

ALTER TABLE `t_sys_user` 
CHANGE COLUMN `lastUpdate` `lastUpdate` DATETIME NULL COMMENT '' ;
delete from t_sys_user ;
update t_sys_user set lastUpdate = null ;

insert into t_sys_user(id,username,password,pwdsalt,status,superuser,createTime,lastupdate)
select t.user_id, t.username,t.password,t.salt,t.status,0, t2.creation_date, null 
from mooc_school.t_cms_user t left join mooc_school.t_cms_user_detail t2 on t.user_id = t2.user_id ;

update t_sys_user set lastUpdate = createtime ;
update t_sys_user set superUser = 1 where id = 1;
##状态转换，0变成1， 1变成0
update appcenter.t_sys_user set status = 3 where status = 1;
update appcenter.t_sys_user set status = 1 where status = 0;
update appcenter.t_sys_user set status = 0 where status = 3;

delete from t_id_table;
insert into t_id_table(table_name, id_value)
select CONCAT('t_' ,`table`), id_value from mooc_school.t_t_id_table;


insert into t_cms_info_custom(info_id, key_, value_)
select info_id, key_, value_ from mooc_school.t_cms_info_custom;
insert into t_cms_info_clob(info_id, key_,value_ )
SELECT info_id, key_,value_ FROM mooc_school.t_cms_info_clob;
insert into t_cms_info_image(info_id, `name`, image, index_, `text`)
SELECT info_id, `name`, image, `index`, `text` FROM mooc_school.t_cms_info_image;

insert into t_cms_model_custom(model_id, key_, value_)
SELECT model_id, key_, value_ FROM mooc_school.t_cms_model_custom;

insert into t_cms_model_field_custom(modefiel_id, key_, value_)
SELECT t.modefiel_id, key_, value_ FROM mooc_school.t_cms_model_field_custom t;

#广告数据
insert into appcenter.t_cms_ad(ad_id, site_id, adslot_id, name, begin_date, end_date, url, text, script, image, flash, seq, linktype)
select ad_id, site_id, adslot_id, name, begin_date, end_date, url, text, script, image, flash, seq, null
from mooc_school.t_cms_ad;

insert into appcenter.t_cms_ad_slot(adslot_id, site_id, name, number, type)
select friendlinktype_id, site_id, name, number, 2
from mooc_school.t_cms_friendlink_type;

insert into appcenter.t_cms_ad(ad_id, site_id, adslot_id, name,  url, text,  image,  seq, linktype)
select friendlink_id, site_id, friendlinktype_id, name, url, description, logo,seq,link_type
from mooc_school.t_cms_friendlink;

insert into t_points_level(level_id, level_name,min_points,image_url)
select level_id, level_name,min_points, image_url from mooc_school.t_app_points_level;

insert into t_points_rule(rule_id, rule_name, isas, points, max_points, rule_code,status, isspecial, caption)
SELECT rule_id, rule_name, isas, points, max_points, rule_code,status, isspecial, caption 
FROM mooc_school.t_app_points_rule;


insert into t_user_points(user_id, level_id, points)
SELECT user_id, level_id, points
FROM mooc_school.t_app_user_points;


insert into t_user_day_points(rule_id, user_id, lastTime, points, day_id)
SELECT rule_id, user_id, lastTime, max_points, day_id
FROM mooc_school.t_app_user_day_points where mooc_school.t_app_user_day_points.user_id in (select id from appcenter.t_sys_user);

#APP版本管理
insert into t_cms_app(id, site_id, version, url, content, publish_date, isForce)
SELECT id, site_id, version, url, content, publish_date, isForce FROM mooc_school.t_cms_app;