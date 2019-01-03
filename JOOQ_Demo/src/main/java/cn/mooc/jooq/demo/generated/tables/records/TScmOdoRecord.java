/**
 * This class is generated by jOOQ
 */
package cn.mooc.jooq.demo.generated.tables.records;


import cn.mooc.jooq.demo.generated.tables.TScmOdo;

import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record9;
import org.jooq.Row9;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * 销售出库单
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.8.4"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class TScmOdoRecord extends UpdatableRecordImpl<TScmOdoRecord> implements Record9<Integer, String, Timestamp, Integer, Byte, BigDecimal, Integer, Integer, Timestamp> {

    private static final long serialVersionUID = 948847227;

    /**
     * Setter for <code>appcenter_mall_data.t_scm_odo.odoId</code>.
     */
    public void setOdoid(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_scm_odo.odoId</code>.
     */
    public Integer getOdoid() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_scm_odo.billCode</code>. 单号
     */
    public void setBillcode(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_scm_odo.billCode</code>. 单号
     */
    public String getBillcode() {
        return (String) get(1);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_scm_odo.createTime</code>. 创建时间
     */
    public void setCreatetime(Timestamp value) {
        set(2, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_scm_odo.createTime</code>. 创建时间
     */
    public Timestamp getCreatetime() {
        return (Timestamp) get(2);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_scm_odo.creatorId</code>. 创建人
     */
    public void setCreatorid(Integer value) {
        set(3, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_scm_odo.creatorId</code>. 创建人
     */
    public Integer getCreatorid() {
        return (Integer) get(3);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_scm_odo.status</code>. 状态
     */
    public void setStatus(Byte value) {
        set(4, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_scm_odo.status</code>. 状态
     */
    public Byte getStatus() {
        return (Byte) get(4);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_scm_odo.amount</code>. 金额
     */
    public void setAmount(BigDecimal value) {
        set(5, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_scm_odo.amount</code>. 金额
     */
    public BigDecimal getAmount() {
        return (BigDecimal) get(5);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_scm_odo.custId</code>. 客户ID
     */
    public void setCustid(Integer value) {
        set(6, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_scm_odo.custId</code>. 客户ID
     */
    public Integer getCustid() {
        return (Integer) get(6);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_scm_odo.checkerId</code>. 审核人ID
     */
    public void setCheckerid(Integer value) {
        set(7, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_scm_odo.checkerId</code>. 审核人ID
     */
    public Integer getCheckerid() {
        return (Integer) get(7);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_scm_odo.checkTime</code>. 审核时间
     */
    public void setChecktime(Timestamp value) {
        set(8, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_scm_odo.checkTime</code>. 审核时间
     */
    public Timestamp getChecktime() {
        return (Timestamp) get(8);
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
    // Record9 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row9<Integer, String, Timestamp, Integer, Byte, BigDecimal, Integer, Integer, Timestamp> fieldsRow() {
        return (Row9) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row9<Integer, String, Timestamp, Integer, Byte, BigDecimal, Integer, Integer, Timestamp> valuesRow() {
        return (Row9) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return TScmOdo.T_SCM_ODO.ODOID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field2() {
        return TScmOdo.T_SCM_ODO.BILLCODE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field3() {
        return TScmOdo.T_SCM_ODO.CREATETIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field4() {
        return TScmOdo.T_SCM_ODO.CREATORID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Byte> field5() {
        return TScmOdo.T_SCM_ODO.STATUS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<BigDecimal> field6() {
        return TScmOdo.T_SCM_ODO.AMOUNT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field7() {
        return TScmOdo.T_SCM_ODO.CUSTID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field8() {
        return TScmOdo.T_SCM_ODO.CHECKERID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field9() {
        return TScmOdo.T_SCM_ODO.CHECKTIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value1() {
        return getOdoid();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value2() {
        return getBillcode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value3() {
        return getCreatetime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value4() {
        return getCreatorid();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte value5() {
        return getStatus();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BigDecimal value6() {
        return getAmount();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value7() {
        return getCustid();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value8() {
        return getCheckerid();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value9() {
        return getChecktime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TScmOdoRecord value1(Integer value) {
        setOdoid(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TScmOdoRecord value2(String value) {
        setBillcode(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TScmOdoRecord value3(Timestamp value) {
        setCreatetime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TScmOdoRecord value4(Integer value) {
        setCreatorid(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TScmOdoRecord value5(Byte value) {
        setStatus(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TScmOdoRecord value6(BigDecimal value) {
        setAmount(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TScmOdoRecord value7(Integer value) {
        setCustid(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TScmOdoRecord value8(Integer value) {
        setCheckerid(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TScmOdoRecord value9(Timestamp value) {
        setChecktime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TScmOdoRecord values(Integer value1, String value2, Timestamp value3, Integer value4, Byte value5, BigDecimal value6, Integer value7, Integer value8, Timestamp value9) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        value7(value7);
        value8(value8);
        value9(value9);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached TScmOdoRecord
     */
    public TScmOdoRecord() {
        super(TScmOdo.T_SCM_ODO);
    }

    /**
     * Create a detached, initialised TScmOdoRecord
     */
    public TScmOdoRecord(Integer odoid, String billcode, Timestamp createtime, Integer creatorid, Byte status, BigDecimal amount, Integer custid, Integer checkerid, Timestamp checktime) {
        super(TScmOdo.T_SCM_ODO);

        set(0, odoid);
        set(1, billcode);
        set(2, createtime);
        set(3, creatorid);
        set(4, status);
        set(5, amount);
        set(6, custid);
        set(7, checkerid);
        set(8, checktime);
    }
}