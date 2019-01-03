/**
 * This class is generated by jOOQ
 */
package cn.mooc.jooq.demo.generated.tables.records;


import cn.mooc.jooq.demo.generated.tables.TMallMemberintegralexchangerules;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record3;
import org.jooq.Row3;
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
public class TMallMemberintegralexchangerulesRecord extends UpdatableRecordImpl<TMallMemberintegralexchangerulesRecord> implements Record3<Long, Integer, Integer> {

    private static final long serialVersionUID = 1252286366;

    /**
     * Setter for <code>appcenter_mall_data.t_mall_memberintegralexchangerules.Id</code>.
     */
    public void setId(Long value) {
        set(0, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_memberintegralexchangerules.Id</code>.
     */
    public Long getId() {
        return (Long) get(0);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_memberintegralexchangerules.IntegralPerMoney</code>. 一块钱对应多少积分
     */
    public void setIntegralpermoney(Integer value) {
        set(1, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_memberintegralexchangerules.IntegralPerMoney</code>. 一块钱对应多少积分
     */
    public Integer getIntegralpermoney() {
        return (Integer) get(1);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_memberintegralexchangerules.MoneyPerIntegral</code>. 一个积分对应多少钱
     */
    public void setMoneyperintegral(Integer value) {
        set(2, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_memberintegralexchangerules.MoneyPerIntegral</code>. 一个积分对应多少钱
     */
    public Integer getMoneyperintegral() {
        return (Integer) get(2);
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
    // Record3 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row3<Long, Integer, Integer> fieldsRow() {
        return (Row3) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row3<Long, Integer, Integer> valuesRow() {
        return (Row3) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Long> field1() {
        return TMallMemberintegralexchangerules.T_MALL_MEMBERINTEGRALEXCHANGERULES.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field2() {
        return TMallMemberintegralexchangerules.T_MALL_MEMBERINTEGRALEXCHANGERULES.INTEGRALPERMONEY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field3() {
        return TMallMemberintegralexchangerules.T_MALL_MEMBERINTEGRALEXCHANGERULES.MONEYPERINTEGRAL;
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
    public Integer value2() {
        return getIntegralpermoney();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value3() {
        return getMoneyperintegral();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TMallMemberintegralexchangerulesRecord value1(Long value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TMallMemberintegralexchangerulesRecord value2(Integer value) {
        setIntegralpermoney(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TMallMemberintegralexchangerulesRecord value3(Integer value) {
        setMoneyperintegral(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TMallMemberintegralexchangerulesRecord values(Long value1, Integer value2, Integer value3) {
        value1(value1);
        value2(value2);
        value3(value3);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached TMallMemberintegralexchangerulesRecord
     */
    public TMallMemberintegralexchangerulesRecord() {
        super(TMallMemberintegralexchangerules.T_MALL_MEMBERINTEGRALEXCHANGERULES);
    }

    /**
     * Create a detached, initialised TMallMemberintegralexchangerulesRecord
     */
    public TMallMemberintegralexchangerulesRecord(Long id, Integer integralpermoney, Integer moneyperintegral) {
        super(TMallMemberintegralexchangerules.T_MALL_MEMBERINTEGRALEXCHANGERULES);

        set(0, id);
        set(1, integralpermoney);
        set(2, moneyperintegral);
    }
}