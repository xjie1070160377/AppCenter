/**
 * This class is generated by jOOQ
 */
package cn.mooc.jooq.demo.generated.tables.records;


import cn.mooc.jooq.demo.generated.tables.TNoticeInfo;

import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record7;
import org.jooq.Row7;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * 通知公告
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.8.4"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class TNoticeInfoRecord extends UpdatableRecordImpl<TNoticeInfoRecord> implements Record7<Integer, Integer, String, String, Timestamp, Long, String> {

    private static final long serialVersionUID = 380914046;

    /**
     * Setter for <code>appcenter_mall_data.t_notice_info.id</code>.
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_notice_info.id</code>.
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_notice_info.nodeId</code>. 栏目编号
     */
    public void setNodeid(Integer value) {
        set(1, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_notice_info.nodeId</code>. 栏目编号
     */
    public Integer getNodeid() {
        return (Integer) get(1);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_notice_info.title</code>. 标题
     */
    public void setTitle(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_notice_info.title</code>. 标题
     */
    public String getTitle() {
        return (String) get(2);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_notice_info.description</code>. 摘要
     */
    public void setDescription(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_notice_info.description</code>. 摘要
     */
    public String getDescription() {
        return (String) get(3);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_notice_info.createTime</code>. 创建时间
     */
    public void setCreatetime(Timestamp value) {
        set(4, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_notice_info.createTime</code>. 创建时间
     */
    public Timestamp getCreatetime() {
        return (Timestamp) get(4);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_notice_info.creatorId</code>. 创建人编号
     */
    public void setCreatorid(Long value) {
        set(5, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_notice_info.creatorId</code>. 创建人编号
     */
    public Long getCreatorid() {
        return (Long) get(5);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_notice_info.smallImage</code>. 标题图
     */
    public void setSmallimage(String value) {
        set(6, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_notice_info.smallImage</code>. 标题图
     */
    public String getSmallimage() {
        return (String) get(6);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Record1<Integer> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record7 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row7<Integer, Integer, String, String, Timestamp, Long, String> fieldsRow() {
        return (Row7) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row7<Integer, Integer, String, String, Timestamp, Long, String> valuesRow() {
        return (Row7) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return TNoticeInfo.T_NOTICE_INFO.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field2() {
        return TNoticeInfo.T_NOTICE_INFO.NODEID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field3() {
        return TNoticeInfo.T_NOTICE_INFO.TITLE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field4() {
        return TNoticeInfo.T_NOTICE_INFO.DESCRIPTION;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field5() {
        return TNoticeInfo.T_NOTICE_INFO.CREATETIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Long> field6() {
        return TNoticeInfo.T_NOTICE_INFO.CREATORID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field7() {
        return TNoticeInfo.T_NOTICE_INFO.SMALLIMAGE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value1() {
        return getId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value2() {
        return getNodeid();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value3() {
        return getTitle();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value4() {
        return getDescription();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value5() {
        return getCreatetime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long value6() {
        return getCreatorid();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value7() {
        return getSmallimage();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TNoticeInfoRecord value1(Integer value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TNoticeInfoRecord value2(Integer value) {
        setNodeid(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TNoticeInfoRecord value3(String value) {
        setTitle(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TNoticeInfoRecord value4(String value) {
        setDescription(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TNoticeInfoRecord value5(Timestamp value) {
        setCreatetime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TNoticeInfoRecord value6(Long value) {
        setCreatorid(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TNoticeInfoRecord value7(String value) {
        setSmallimage(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TNoticeInfoRecord values(Integer value1, Integer value2, String value3, String value4, Timestamp value5, Long value6, String value7) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        value7(value7);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached TNoticeInfoRecord
     */
    public TNoticeInfoRecord() {
        super(TNoticeInfo.T_NOTICE_INFO);
    }

    /**
     * Create a detached, initialised TNoticeInfoRecord
     */
    public TNoticeInfoRecord(Integer id, Integer nodeid, String title, String description, Timestamp createtime, Long creatorid, String smallimage) {
        super(TNoticeInfo.T_NOTICE_INFO);

        set(0, id);
        set(1, nodeid);
        set(2, title);
        set(3, description);
        set(4, createtime);
        set(5, creatorid);
        set(6, smallimage);
    }
}