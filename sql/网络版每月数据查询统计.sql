select b.author as ����,b.source as ��λ,DATE_FORMAT(a.publish_date,'%Y-%m-%d %H:%i') as ����ʱ��,b.title as ����,c.`name` as ����
from t_cms_info a,t_cms_info_detail b,t_cms_node c
where a.info_id=b.info_id and a.publish_date>=STR_TO_DATE('2016-06-21','%Y-%m-%d') and a.node_id=c.node_id and a.`status`='A' and c.number<>'readilyTake'
order by a.publish_date DESC