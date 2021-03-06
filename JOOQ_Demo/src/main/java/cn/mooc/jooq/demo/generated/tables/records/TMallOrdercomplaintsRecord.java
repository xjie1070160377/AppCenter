/**
 * This class is generated by jOOQ
 */
package cn.mooc.jooq.demo.generated.tables.records;


import cn.mooc.jooq.demo.generated.tables.TMallOrdercomplaints;

import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record12;
import org.jooq.Row12;
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
public class TMallOrdercomplaintsRecord extends UpdatableRecordImpl<TMallOrdercomplaintsRecord> implements Record12<Long, Long, Integer, Long, String, String, Long, String, String, Timestamp, String, String> {

    private static final long serialVersionUID = -434242528;

    /**
     * Setter for <code>appcenter_mall_data.t_mall_ordercomplaints.Id</code>.
     */
    public void setId(Long value) {
        set(0, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_ordercomplaints.Id</code>.
     */
    public Long getId() {
        return (Long) get(0);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_ordercomplaints.OrderId</code>. 订单ID
     */
    public void setOrderid(Long value) {
        set(1, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_ordercomplaints.OrderId</code>. 订单ID
     */
    public Long getOrderid() {
        return (Long) get(1);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_ordercomplaints.Status</code>. 审核状态
     */
    public void setStatus(Integer value) {
        set(2, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_ordercomplaints.Status</code>. 审核状态
     */
    public Integer getStatus() {
        return (Integer) get(2);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_ordercomplaints.ShopId</code>. 店铺ID
     */
    public void setShopid(Long value) {
        set(3, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_ordercomplaints.ShopId</code>. 店铺ID
     */
    public Long getShopid() {
        return (Long) get(3);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_ordercomplaints.ShopName</code>. 店铺名称
     */
    public void setShopname(String value) {
        set(4, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_ordercomplaints.ShopName</code>. 店铺名称
     */
    public String getShopname() {
        return (String) get(4);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_ordercomplaints.ShopPhone</code>. 店铺联系方式
     */
    public void setShopphone(String value) {
        set(5, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_ordercomplaints.ShopPhone</code>. 店铺联系方式
     */
    public String getShopphone() {
        return (String) get(5);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_ordercomplaints.UserId</code>. 用户ID
     */
    public void setUserid(Long value) {
        set(6, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_ordercomplaints.UserId</code>. 用户ID
     */
    public Long getUserid() {
        return (Long) get(6);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_ordercomplaints.UserName</code>. 用户名称
     */
    public void setUsername(String value) {
        set(7, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_ordercomplaints.UserName</code>. 用户名称
     */
    public String getUsername() {
        return (String) get(7);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_ordercomplaints.UserPhone</code>. 用户联系方式
     */
    public void setUserphone(String value) {
        set(8, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_ordercomplaints.UserPhone</code>. 用户联系方式
     */
    public String getUserphone() {
        return (String) get(8);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_ordercomplaints.ComplaintDate</code>. 投诉日期
     */
    public void setComplaintdate(Timestamp value) {
        set(9, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_ordercomplaints.ComplaintDate</code>. 投诉日期
     */
    public Timestamp getComplaintdate() {
        return (Timestamp) get(9);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_ordercomplaints.ComplaintReason</code>. 投诉原因
     */
    public void setComplaintreason(String value) {
        set(10, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_ordercomplaints.ComplaintReason</code>. 投诉原因
     */
    public String getComplaintreason() {
        return (String) get(10);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_ordercomplaints.SellerReply</code>. 商家反馈信息
     */
    public void setSellerreply(String value) {
        set(11, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_ordercomplaints.SellerReply</code>. 商家反馈信息
     */
    public String getSellerreply() {
        return (String) get(11);
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
    // Record12 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row12<Long, Long, Integer, Long, String, String, Long, String, String, Timestamp, String, String> fieldsRow() {
        return (Row12) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row12<Long, Long, Integer, Long, String, String, Long, String, String, Timestamp, String, String> valuesRow() {
        return (Row12) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Long> field1() {
        return TMallOrdercomplaints.T_MALL_ORDERCOMPLAINTS.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Long> field2() {
        return TMallOrdercomplaints.T_MALL_ORDERCOMPLAINTS.ORDERID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field3() {
        return TMallOrdercomplaints.T_MALL_ORDERCOMPLAINTS.STATUS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Long> field4() {
        return TMallOrdercomplaints.T_MALL_ORDERCOMPLAINTS.SHOPID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field5() {
        return TMallOrdercomplaints.T_MALL_ORDERCOMPLAINTS.SHOPNAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field6() {
        return TMallOrdercomplaints.T_MALL_ORDERCOMPLAINTS.SHOPPHONE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Long> field7() {
        return TMallOrdercomplaints.T_MALL_ORDERCOMPLAINTS.USERID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field8() {
        return TMallOrdercomplaints.T_MALL_ORDERCOMPLAINTS.USERNAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field9() {
        return TMallOrdercomplaints.T_MALL_ORDERCOMPLAINTS.USERPHONE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field10() {
        return TMallOrdercomplaints.T_MALL_ORDERCOMPLAINTS.COMPLAINTDATE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field11() {
        return TMallOrdercomplaints.T_MALL_ORDERCOMPLAINTS.COMPLAINTREASON;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field12() {
        return TMallOrdercomplaints.T_MALL_ORDERCOMPLAINTS.SELLERREPLY;
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
        return getOrderid();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value3() {
        return getStatus();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long value4() {
        return getShopid();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value5() {
        return getShopname();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value6() {
        return getShopphone();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long value7() {
        return getUserid();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value8() {
        return getUsername();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value9() {
        return getUserphone();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value10() {
        return getComplaintdate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value11() {
        return getComplaintreason();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value12() {
        return getSellerreply();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TMallOrdercomplaintsRecord value1(Long value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TMallOrdercomplaintsRecord value2(Long value) {
        setOrderid(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TMallOrdercomplaintsRecord value3(Integer value) {
        setStatus(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TMallOrdercomplaintsRecord value4(Long value) {
        setShopid(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TMallOrdercomplaintsRecord value5(String value) {
        setShopname(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TMallOrdercomplaintsRecord value6(String value) {
        setShopphone(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TMallOrdercomplaintsRecord value7(Long value) {
        setUserid(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TMallOrdercomplaintsRecord value8(String value) {
        setUsername(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TMallOrdercomplaintsRecord value9(String value) {
        setUserphone(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TMallOrdercomplaintsRecord value10(Timestamp value) {
        setComplaintdate(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TMallOrdercomplaintsRecord value11(String value) {
        setComplaintreason(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TMallOrdercomplaintsRecord value12(String value) {
        setSellerreply(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TMallOrdercomplaintsRecord values(Long value1, Long value2, Integer value3, Long value4, String value5, String value6, Long value7, String value8, String value9, Timestamp value10, String value11, String value12) {
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
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached TMallOrdercomplaintsRecord
     */
    public TMallOrdercomplaintsRecord() {
        super(TMallOrdercomplaints.T_MALL_ORDERCOMPLAINTS);
    }

    /**
     * Create a detached, initialised TMallOrdercomplaintsRecord
     */
    public TMallOrdercomplaintsRecord(Long id, Long orderid, Integer status, Long shopid, String shopname, String shopphone, Long userid, String username, String userphone, Timestamp complaintdate, String complaintreason, String sellerreply) {
        super(TMallOrdercomplaints.T_MALL_ORDERCOMPLAINTS);

        set(0, id);
        set(1, orderid);
        set(2, status);
        set(3, shopid);
        set(4, shopname);
        set(5, shopphone);
        set(6, userid);
        set(7, username);
        set(8, userphone);
        set(9, complaintdate);
        set(10, complaintreason);
        set(11, sellerreply);
    }
}
