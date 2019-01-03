
#��Ҫ�л����ݿ⣬�л���ԭ���ݵ����ݿ�
#�ǵ��޸���������ӵ�ID����
##�������ۼ�����
ALTER TABLE `t_cms_comment` 
CHANGE COLUMN `fid` `fid` INT(11) NULL COMMENT '���ID' ;



ALTER TABLE `t_cms_comment` 
ADD COLUMN `creator_username` VARCHAR(45) NULL COMMENT '' AFTER `info_id`,
ADD COLUMN `source_userid` INT(11) NULL COMMENT '' AFTER `creator_username`,
ADD COLUMN `source_username` VARCHAR(45) NULL COMMENT '' AFTER `source_userid`;
ALTER TABLE `t_cms_comment` 
ADD COLUMN `auditUserName` VARCHAR(45) NULL COMMENT '' AFTER `source_username`,
ADD COLUMN `infoTitle` VARCHAR(200) NULL COMMENT '' AFTER `auditUserName`;


update t_cms_comment set info_id = fid where ftype = 'Info';
update t_cms_comment set fid = null where ftype = 'Info';
##����������
update t_cms_comment set creator_username = 
(select if(nickname is not null and nickname <> '', nickname, if(real_name is not null and real_name <> '', real_name, username)) 
from t_cms_user where user_id = creator_id);
##��Դ������ID
update t_cms_comment t set t.source_userid = 
(select userid from (select t1.creator_id userid, t1.comment_id id from t_cms_comment t1 ) t2 where t2.id = t.fid) 
where t.fid is not null;
##��Դ����������
update t_cms_comment set source_username = 
(select if(nickname is not null and nickname <> '', nickname, if(real_name is not null and real_name <> '', real_name, username)) 
from t_cms_user where user_id = source_userid);
##���������
update t_cms_comment set auditUserName = 
(select if(nickname is not null and nickname <> '', nickname, if(real_name is not null and real_name <> '', real_name, username)) 
from t_cms_user where user_id = auditor_id);
##���±���
update t_cms_comment t set infoTitle = (select title from t_cms_info_detail t1 where t1.info_id = t.info_id);


##�û�����
ALTER TABLE `t_cms_feedback` 
ADD COLUMN `comment_id` int(11) NULL COMMENT '' AFTER `is_reply`,
ADD COLUMN `ftype` VARCHAR(45) NULL COMMENT '' AFTER `comment_id`;
ALTER TABLE `t_cms_feedback` 
ADD COLUMN `state` int(1) NULL COMMENT '' AFTER `ftype`;

update t_cms_feedback set ftype = 'Feedback', comment_id = 5000 + id, state = 0;

##�û������ѻظ�����
CREATE TABLE t_cms_feedback_reply LIKE t_cms_feedback;
insert into t_cms_feedback_reply select * from t_cms_feedback where is_reply = 1;
ALTER TABLE `t_cms_feedback_reply` 
ADD COLUMN `replyid` int(1) NULL COMMENT '' AFTER `state`;
ALTER TABLE `t_cms_feedback_reply` 
ADD COLUMN `replyusername` VARCHAR(45) NULL COMMENT '' AFTER `replyid`;

update t_cms_feedback_reply set replyusername = 
(select if(nickname is not null and nickname <> '', nickname, if(real_name is not null and real_name <> '', real_name, username)) 
from t_cms_user where user_id = replyer_id);

update t_cms_feedback_reply set ftype = 'FeedbackReply', replyid = 6000 + id;



##���µ���
delete from  t_cms_vote_mark where ftype <> 'InfoDigg';

ALTER TABLE `t_cms_vote_mark` 
ADD COLUMN `title` VARCHAR(200) NULL COMMENT '' AFTER `cookie`,
ADD COLUMN `siteid` int(1) NULL COMMENT '' AFTER `title`,
ADD COLUMN `username` VARCHAR(45) NULL COMMENT '' AFTER `siteid`;

update t_cms_vote_mark t set username = 
(select if(nickname is not null and nickname <> '', nickname, if(real_name is not null and real_name <> '', real_name, username)) 
from t_cms_user where user_id = t.user_id);

update t_cms_vote_mark t set title = (select title from t_cms_info_detail t1 where t1.info_id = t.fid);
update t_cms_vote_mark set siteid = 1;



##�����ղ�
ALTER TABLE `t_cms_info_collect` 
ADD COLUMN `ftype` VARCHAR(40) NULL COMMENT '' AFTER `collect_date`,
ADD COLUMN `username` VARCHAR(45) NULL COMMENT '' AFTER `ftype`;

ALTER TABLE `t_cms_info_collect` 
ADD COLUMN `markid` int(11) NULL COMMENT '' AFTER `username`;

update t_cms_info_collect t set username = 
(select if(nickname is not null and nickname <> '', nickname, if(real_name is not null and real_name <> '', real_name, username)) 
from t_cms_user where user_id = t.user_id);

update t_cms_info_collect set ftype = 'InfoSave';

update t_cms_info_collect set markid = 6000 + id;

##��ע cms_attention
##�û�
ALTER TABLE `t_cms_user` 
ADD COLUMN `genderint` int(1) NULL COMMENT '' AFTER `cover_map`;

update t_cms_user set genderint = 1 where gender = 'M';
update t_cms_user set genderint = 2 where gender = 'F';
update t_cms_user set genderint = 0 where gender is null or gender = '';