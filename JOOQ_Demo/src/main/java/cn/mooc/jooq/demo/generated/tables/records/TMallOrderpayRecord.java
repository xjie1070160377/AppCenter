/**
 * This class is generated by jOOQ
 */
package cn.mooc.jooq.demo.generated.tables.records;


import cn.mooc.jooq.demo.generated.tables.TMallOrderpay;

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
public class TMallOrderpayRecord extends UpdatableRecordImpl<TMallOrderpayRecord> implements Record3<Long, Long, Long> {

    private static final long serialVersionUID = -1972963388;

    /**
     * Setter for <code>appcenter_mall_data.t_mall_orderpay.Id</code>.
     */
    public void setId(Long value) {
        set(0, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_orderpay.Id</code>.
     */
    public Long getId() {
        return (Long) get(0);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_orderpay.PayId</code>.
     */
    public void setPayid(Long value) {
        set(1, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_orderpay.PayId</code>.
     */
    public Long getPayid() {
        return (Long) get(1);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_orderpay.OrderId</code>.
     */
    public void setOrderid(Long value) {
        set(2, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_orderpay.OrderId</code>.
     */
    public Long getOrderid() {
        return (Long) get(2);
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
    public Row3<Long, Long, Long> fieldsRow() {
        return (Row3) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row3<Long, Long, Long> valuesRow() {
        return (Row3) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Long> field1() {
        return TMallOrderpay.T_MALL_ORDERPAY.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Long> field2() {
        return TMallOrderpay.T_MALL_ORDERPAY.PAYID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Long> field3() {
        return TMallOrderpay.T_MALL_ORDERPAY.ORDERID;
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
        return getPayid();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long value3() {
        return getOrderid();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TMallOrderpayRecord value1(Long value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TMallOrderpayRecord value2(Long value) {
        setPayid(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TMallOrderpayRecord value3(Long value) {
        setOrderid(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TMallOrderpayRecord values(Long value1, Long value2, Long value3) {
        value1(value1);
        value2(value2);
        value3(value3);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached TMallOrderpayRecord
     */
    public TMallOrderpayRecord() {
        super(TMallOrderpay.T_MALL_ORDERPAY);
    }

    /**
     * Create a detached, initialised TMallOrderpayRecord
     */
    public TMallOrderpayRecord(Long id, Long payid, Long orderid) {
        super(TMallOrderpay.T_MALL_ORDERPAY);

        set(0, id);
        set(1, payid);
        set(2, orderid);
    }
}