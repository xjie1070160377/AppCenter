/**
 * This class is generated by jOOQ
 */
package cn.mooc.jooq.demo.generated.tables.records;


import cn.mooc.jooq.demo.generated.tables.TMallMemberintegral;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record5;
import org.jooq.Row5;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.8.4"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class TMallMemberintegralRecord extends UpdatableRecordImpl<TMallMemberintegralRecord> implements Record5<Long, Long, String, Integer, Integer> {

    private static final long serialVersionUID = -594985378;

    /**
     * Setter for <code>appcenter_mall_data.t_mall_memberintegral.Id</code>.
     */
    public void setId(Long value) {
        set(0, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_memberintegral.Id</code>.
     */
    public Long getId() {
        return (Long) get(0);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_memberintegral.MemberId</code>. 会员ID
     */
    public void setMemberid(Long value) {
        set(1, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_memberintegral.MemberId</code>. 会员ID
     */
    public Long getMemberid() {
        return (Long) get(1);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_memberintegral.UserName</code>. 用户名称
     */
    public void setUsername(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_memberintegral.UserName</code>. 用户名称
     */
    public String getUsername() {
        return (String) get(2);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_memberintegral.HistoryIntegrals</code>. 用户历史积分
     */
    public void setHistoryintegrals(Integer value) {
        set(3, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_memberintegral.HistoryIntegrals</code>. 用户历史积分
     */
    public Integer getHistoryintegrals() {
        return (Integer) get(3);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_memberintegral.AvailableIntegrals</code>. 用户可用积分
     */
    public void setAvailableintegrals(Integer value) {
        set(4, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_memberintegral.AvailableIntegrals</code>. 用户可用积分
     */
    public Integer getAvailableintegrals() {
        return (Integer) get(4);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Record1<Long> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record5 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row5<Long, Long, String, Integer, Integer> fieldsRow() {
        return (Row5) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row5<Long, Long, String, Integer, Integer> valuesRow() {
        return (Row5) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Long> field1() {
        return TMallMemberintegral.T_MALL_MEMBERINTEGRAL.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Long> field2() {
        return TMallMemberintegral.T_MALL_MEMBERINTEGRAL.MEMBERID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field3() {
        return TMallMemberintegral.T_MALL_MEMBERINTEGRAL.USERNAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field4() {
        return TMallMemberintegral.T_MALL_MEMBERINTEGRAL.HISTORYINTEGRALS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field5() {
        return TMallMemberintegral.T_MALL_MEMBERINTEGRAL.AVAILABLEINTEGRALS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long value1() {
        return getId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long value2() {
        return getMemberid();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value3() {
        return getUsername();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value4() {
        return getHistoryintegrals();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value5() {
        return getAvailableintegrals();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TMallMemberintegralRecord value1(Long value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TMallMemberintegralRecord value2(Long value) {
        setMemberid(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TMallMemberintegralRecord value3(String value) {
        setUsername(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TMallMemberintegralRecord value4(Integer value) {
        setHistoryintegrals(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TMallMemberintegralRecord value5(Integer value) {
        setAvailableintegrals(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TMallMemberintegralRecord values(Long value1, Long value2, String value3, Integer value4, Integer value5) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached TMallMemberintegralRecord
     */
    public TMallMemberintegralRecord() {
        super(TMallMemberintegral.T_MALL_MEMBERINTEGRAL);
    }

    /**
     * Create a detached, initialised TMallMemberintegralRecord
     */
    public TMallMemberintegralRecord(Long id, Long memberid, String username, Integer historyintegrals, Integer availableintegrals) {
        super(TMallMemberintegral.T_MALL_MEMBERINTEGRAL);

        set(0, id);
        set(1, memberid);
        set(2, username);
        set(3, historyintegrals);
        set(4, availableintegrals);
    }
}