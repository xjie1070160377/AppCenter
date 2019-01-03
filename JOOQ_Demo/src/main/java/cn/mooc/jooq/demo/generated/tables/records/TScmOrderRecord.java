/**
 * This class is generated by jOOQ
 */
package cn.mooc.jooq.demo.generated.tables.records;


import cn.mooc.jooq.demo.generated.tables.TScmOrder;

import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record15;
import org.jooq.Row15;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * 会员订单表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.8.4"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class TScmOrderRecord extends UpdatableRecordImpl<TScmOrderRecord> implements Record15<Integer, Timestamp, BigDecimal, Timestamp, Timestamp, String, String, Timestamp, Byte, Timestamp, Integer, Integer, Integer, Timestamp, Byte> {

    private static final long serialVersionUID = -1536748332;

    /**
     * Setter for <code>appcenter_mall_data.t_scm_order.orderId</code>.
     */
    public void setOrderid(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_scm_order.orderId</code>.
     */
    public Integer getOrderid() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_scm_order.createTime</code>. 下订时间
     */
    public void setCreatetime(Timestamp value) {
        set(1, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_scm_order.createTime</code>. 下订时间
     */
    public Timestamp getCreatetime() {
        return (Timestamp) get(1);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_scm_order.amount</code>. 订单总金额
     */
    public void setAmount(BigDecimal value) {
        set(2, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_scm_order.amount</code>. 订单总金额
     */
    public BigDecimal getAmount() {
        return (BigDecimal) get(2);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_scm_order.opTime</code>. 处理时间
     */
    public void setOptime(Timestamp value) {
        set(3, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_scm_order.opTime</code>. 处理时间
     */
    public Timestamp getOptime() {
        return (Timestamp) get(3);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_scm_order.endTime</code>. 完成时间
     */
    public void setEndtime(Timestamp value) {
        set(4, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_scm_order.endTime</code>. 完成时间
     */
    public Timestamp getEndtime() {
        return (Timestamp) get(4);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_scm_order.contactName</code>. 收货人
     */
    public void setContactname(String value) {
        set(5, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_scm_order.contactName</code>. 收货人
     */
    public String getContactname() {
        return (String) get(5);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_scm_order.address</code>. 收货地址
     */
    public void setAddress(String value) {
        set(6, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_scm_order.address</code>. 收货地址
     */
    public String getAddress() {
        return (String) get(6);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_scm_order.receviceTime</code>. 收货时间
     */
    public void setRecevicetime(Timestamp value) {
        set(7, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_scm_order.receviceTime</code>. 收货时间
     */
    public Timestamp getRecevicetime() {
        return (Timestamp) get(7);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_scm_order.status</code>. 状态
     */
    public void setStatus(Byte value) {
        set(8, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_scm_order.status</code>. 状态
     */
    public Byte getStatus() {
        return (Byte) get(8);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_scm_order.needTime</code>. 预计收货时间
     */
    public void setNeedtime(Timestamp value) {
        set(9, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_scm_order.needTime</code>. 预计收货时间
     */
    public Timestamp getNeedtime() {
        return (Timestamp) get(9);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_scm_order.custId</code>. 客户ID
     */
    public void setCustid(Integer value) {
        set(10, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_scm_order.custId</code>. 客户ID
     */
    public Integer getCustid() {
        return (Integer) get(10);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_scm_order.creatorId</code>. 创建人ID
     */
    public void setCreatorid(Integer value) {
        set(11, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_scm_order.creatorId</code>. 创建人ID
     */
    public Integer getCreatorid() {
        return (Integer) get(11);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_scm_order.checkerId</code>. 审核人ID
     */
    public void setCheckerid(Integer value) {
        set(12, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_scm_order.checkerId</code>. 审核人ID
     */
    public Integer getCheckerid() {
        return (Integer) get(12);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_scm_order.checkTime</code>. 审核时间
     */
    public void setChecktime(Timestamp value) {
        set(13, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_scm_order.checkTime</code>. 审核时间
     */
    public Timestamp getChecktime() {
        return (Timestamp) get(13);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_scm_order.cancel</code>. 作废
     */
    public void setCancel(Byte value) {
        set(14, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_scm_order.cancel</code>. 作废
     */
    public Byte getCancel() {
        return (Byte) get(14);
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
    // Record15 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row15<Integer, Timestamp, BigDecimal, Timestamp, Timestamp, String, String, Timestamp, Byte, Timestamp, Integer, Integer, Integer, Timestamp, Byte> fieldsRow() {
        return (Row15) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row15<Integer, Timestamp, BigDecimal, Timestamp, Timestamp, String, String, Timestamp, Byte, Timestamp, Integer, Integer, Integer, Timestamp, Byte> valuesRow() {
        return (Row15) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return TScmOrder.T_SCM_ORDER.ORDERID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field2() {
        return TScmOrder.T_SCM_ORDER.CREATETIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<BigDecimal> field3() {
        return TScmOrder.T_SCM_ORDER.AMOUNT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field4() {
        return TScmOrder.T_SCM_ORDER.OPTIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field5() {
        return TScmOrder.T_SCM_ORDER.ENDTIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field6() {
        return TScmOrder.T_SCM_ORDER.CONTACTNAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field7() {
        return TScmOrder.T_SCM_ORDER.ADDRESS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field8() {
        return TScmOrder.T_SCM_ORDER.RECEVICETIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Byte> field9() {
        return TScmOrder.T_SCM_ORDER.STATUS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field10() {
        return TScmOrder.T_SCM_ORDER.NEEDTIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field11() {
        return TScmOrder.T_SCM_ORDER.CUSTID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field12() {
        return TScmOrder.T_SCM_ORDER.CREATORID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field13() {
        return TScmOrder.T_SCM_ORDER.CHECKERID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field14() {
        return TScmOrder.T_SCM_ORDER.CHECKTIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Byte> field15() {
        return TScmOrder.T_SCM_ORDER.CANCEL;
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
    public Timestamp value2() {
        return getCreatetime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BigDecimal value3() {
        return getAmount();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value4() {
        return getOptime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value5() {
        return getEndtime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value6() {
        return getContactname();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value7() {
        return getAddress();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value8() {
        return getRecevicetime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte value9() {
        return getStatus();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value10() {
        return getNeedtime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value11() {
        return getCustid();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value12() {
        return getCreatorid();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value13() {
        return getCheckerid();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value14() {
        return getChecktime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte value15() {
        return getCancel();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TScmOrderRecord value1(Integer value) {
        setOrderid(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TScmOrderRecord value2(Timestamp value) {
        setCreatetime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TScmOrderRecord value3(BigDecimal value) {
        setAmount(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TScmOrderRecord value4(Timestamp value) {
        setOptime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TScmOrderRecord value5(Timestamp value) {
        setEndtime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TScmOrderRecord value6(String value) {
        setContactname(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TScmOrderRecord value7(String value) {
        setAddress(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TScmOrderRecord value8(Timestamp value) {
        setRecevicetime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TScmOrderRecord value9(Byte value) {
        setStatus(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TScmOrderRecord value10(Timestamp value) {
        setNeedtime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TScmOrderRecord value11(Integer value) {
        setCustid(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TScmOrderRecord value12(Integer value) {
        setCreatorid(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TScmOrderRecord value13(Integer value) {
        setCheckerid(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TScmOrderRecord value14(Timestamp value) {
        setChecktime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TScmOrderRecord value15(Byte value) {
        setCancel(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TScmOrderRecord values(Integer value1, Timestamp value2, BigDecimal value3, Timestamp value4, Timestamp value5, String value6, String value7, Timestamp value8, Byte value9, Timestamp value10, Integer value11, Integer value12, Integer value13, Timestamp value14, Byte value15) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        value7(value7);
        value8(value8);
        value9(value9);
        value10(value10);
        value11(value11);
        value12(value12);
        value13(value13);
        value14(value14);
        value15(value15);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached TScmOrderRecord
     */
    public TScmOrderRecord() {
        super(TScmOrder.T_SCM_ORDER);
    }

    /**
     * Create a detached, initialised TScmOrderRecord
     */
    public TScmOrderRecord(Integer orderid, Timestamp createtime, BigDecimal amount, Timestamp optime, Timestamp endtime, String contactname, String address, Timestamp recevicetime, Byte status, Timestamp needtime, Integer custid, Integer creatorid, Integer checkerid, Timestamp checktime, Byte cancel) {
        super(TScmOrder.T_SCM_ORDER);

        set(0, orderid);
        set(1, createtime);
        set(2, amount);
        set(3, optime);
        set(4, endtime);
        set(5, contactname);
        set(6, address);
        set(7, recevicetime);
        set(8, status);
        set(9, needtime);
        set(10, custid);
        set(11, creatorid);
        set(12, checkerid);
        set(13, checktime);
        set(14, cancel);
    }
}