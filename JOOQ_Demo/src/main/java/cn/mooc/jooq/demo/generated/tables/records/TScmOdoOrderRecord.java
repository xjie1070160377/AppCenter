/**
 * This class is generated by jOOQ
 */
package cn.mooc.jooq.demo.generated.tables.records;


import cn.mooc.jooq.demo.generated.tables.TScmOdoOrder;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record3;
import org.jooq.Row3;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * 出库单订单关联表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.8.4"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class TScmOdoOrderRecord extends UpdatableRecordImpl<TScmOdoOrderRecord> implements Record3<Integer, Integer, Integer> {

    private static final long serialVersionUID = -2020452955;

    /**
     * Setter for <code>appcenter_mall_data.t_scm_odo_order.orderId</code>. 订单ID
     */
    public void setOrderid(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_scm_odo_order.orderId</code>. 订单ID
     */
    public Integer getOrderid() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_scm_odo_order.odoId</code>. 出库单ID
     */
    public void setOdoid(Integer value) {
        set(1, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_scm_odo_order.odoId</code>. 出库单ID
     */
    public Integer getOdoid() {
        return (Integer) get(1);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_scm_odo_order.id</code>.
     */
    public void setId(Integer value) {
        set(2, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_scm_odo_order.id</code>.
     */
    public Integer getId() {
        return (Integer) get(2);
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
    // Record3 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row3<Integer, Integer, Integer> fieldsRow() {
        return (Row3) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row3<Integer, Integer, Integer> valuesRow() {
        return (Row3) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return TScmOdoOrder.T_SCM_ODO_ORDER.ORDERID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field2() {
        return TScmOdoOrder.T_SCM_ODO_ORDER.ODOID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field3() {
        return TScmOdoOrder.T_SCM_ODO_ORDER.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value1() {
        return getOrderid();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value2() {
        return getOdoid();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value3() {
        return getId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TScmOdoOrderRecord value1(Integer value) {
        setOrderid(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TScmOdoOrderRecord value2(Integer value) {
        setOdoid(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TScmOdoOrderRecord value3(Integer value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TScmOdoOrderRecord values(Integer value1, Integer value2, Integer value3) {
        value1(value1);
        value2(value2);
        value3(value3);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached TScmOdoOrderRecord
     */
    public TScmOdoOrderRecord() {
        super(TScmOdoOrder.T_SCM_ODO_ORDER);
    }

    /**
     * Create a detached, initialised TScmOdoOrderRecord
     */
    public TScmOdoOrderRecord(Integer orderid, Integer odoid, Integer id) {
        super(TScmOdoOrder.T_SCM_ODO_ORDER);

        set(0, orderid);
        set(1, odoid);
        set(2, id);
    }
}
