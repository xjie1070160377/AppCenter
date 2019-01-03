/**
 * This class is generated by jOOQ
 */
package cn.mooc.jooq.demo.generated.tables.records;


import cn.mooc.jooq.demo.generated.tables.TMallOrderrefunds;

import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Record1;
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
public class TMallOrderrefundsRecord extends UpdatableRecordImpl<TMallOrderrefundsRecord> {

    private static final long serialVersionUID = -796240870;

    /**
     * Setter for <code>appcenter_mall_data.t_mall_orderrefunds.Id</code>.
     */
    public void setId(Long value) {
        set(0, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_orderrefunds.Id</code>.
     */
    public Long getId() {
        return (Long) get(0);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_orderrefunds.OrderId</code>. 订单ID
     */
    public void setOrderid(Long value) {
        set(1, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_orderrefunds.OrderId</code>. 订单ID
     */
    public Long getOrderid() {
        return (Long) get(1);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_orderrefunds.OrderItemId</code>. 订单详情ID
     */
    public void setOrderitemid(Long value) {
        set(2, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_orderrefunds.OrderItemId</code>. 订单详情ID
     */
    public Long getOrderitemid() {
        return (Long) get(2);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_orderrefunds.ShopId</code>. 店铺ID
     */
    public void setShopid(Long value) {
        set(3, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_orderrefunds.ShopId</code>. 店铺ID
     */
    public Long getShopid() {
        return (Long) get(3);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_orderrefunds.ShopName</code>. 店铺名称
     */
    public void setShopname(String value) {
        set(4, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_orderrefunds.ShopName</code>. 店铺名称
     */
    public String getShopname() {
        return (String) get(4);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_orderrefunds.UserId</code>. 用户ID
     */
    public void setUserid(Long value) {
        set(5, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_orderrefunds.UserId</code>. 用户ID
     */
    public Long getUserid() {
        return (Long) get(5);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_orderrefunds.Applicant</code>. 申请内容
     */
    public void setApplicant(String value) {
        set(6, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_orderrefunds.Applicant</code>. 申请内容
     */
    public String getApplicant() {
        return (String) get(6);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_orderrefunds.ContactPerson</code>. 联系人
     */
    public void setContactperson(String value) {
        set(7, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_orderrefunds.ContactPerson</code>. 联系人
     */
    public String getContactperson() {
        return (String) get(7);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_orderrefunds.ContactCellPhone</code>. 联系电话
     */
    public void setContactcellphone(String value) {
        set(8, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_orderrefunds.ContactCellPhone</code>. 联系电话
     */
    public String getContactcellphone() {
        return (String) get(8);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_orderrefunds.RefundAccount</code>. 退款金额
     */
    public void setRefundaccount(String value) {
        set(9, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_orderrefunds.RefundAccount</code>. 退款金额
     */
    public String getRefundaccount() {
        return (String) get(9);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_orderrefunds.ApplyDate</code>. 申请时间
     */
    public void setApplydate(Timestamp value) {
        set(10, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_orderrefunds.ApplyDate</code>. 申请时间
     */
    public Timestamp getApplydate() {
        return (Timestamp) get(10);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_orderrefunds.Amount</code>. 金额
     */
    public void setAmount(BigDecimal value) {
        set(11, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_orderrefunds.Amount</code>. 金额
     */
    public BigDecimal getAmount() {
        return (BigDecimal) get(11);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_orderrefunds.Reason</code>. 退款原因
     */
    public void setReason(String value) {
        set(12, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_orderrefunds.Reason</code>. 退款原因
     */
    public String getReason() {
        return (String) get(12);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_orderrefunds.SellerAuditStatus</code>. 商家审核状态
     */
    public void setSellerauditstatus(Integer value) {
        set(13, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_orderrefunds.SellerAuditStatus</code>. 商家审核状态
     */
    public Integer getSellerauditstatus() {
        return (Integer) get(13);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_orderrefunds.SellerAuditDate</code>. 商家审核时间
     */
    public void setSellerauditdate(Timestamp value) {
        set(14, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_orderrefunds.SellerAuditDate</code>. 商家审核时间
     */
    public Timestamp getSellerauditdate() {
        return (Timestamp) get(14);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_orderrefunds.SellerRemark</code>. 商家注释
     */
    public void setSellerremark(String value) {
        set(15, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_orderrefunds.SellerRemark</code>. 商家注释
     */
    public String getSellerremark() {
        return (String) get(15);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_orderrefunds.ManagerConfirmStatus</code>. 平台审核状态
     */
    public void setManagerconfirmstatus(Integer value) {
        set(16, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_orderrefunds.ManagerConfirmStatus</code>. 平台审核状态
     */
    public Integer getManagerconfirmstatus() {
        return (Integer) get(16);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_orderrefunds.ManagerConfirmDate</code>. 平台审核时间
     */
    public void setManagerconfirmdate(Timestamp value) {
        set(17, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_orderrefunds.ManagerConfirmDate</code>. 平台审核时间
     */
    public Timestamp getManagerconfirmdate() {
        return (Timestamp) get(17);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_orderrefunds.ManagerRemark</code>. 平台注释
     */
    public void setManagerremark(String value) {
        set(18, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_orderrefunds.ManagerRemark</code>. 平台注释
     */
    public String getManagerremark() {
        return (String) get(18);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_orderrefunds.IsReturn</code>. 是否已经退款
     */
    public void setIsreturn(Byte value) {
        set(19, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_orderrefunds.IsReturn</code>. 是否已经退款
     */
    public Byte getIsreturn() {
        return (Byte) get(19);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_orderrefunds.ExpressCompanyName</code>. 快递公司
     */
    public void setExpresscompanyname(String value) {
        set(20, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_orderrefunds.ExpressCompanyName</code>. 快递公司
     */
    public String getExpresscompanyname() {
        return (String) get(20);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_orderrefunds.ShipOrderNumber</code>. 快递单号
     */
    public void setShipordernumber(String value) {
        set(21, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_orderrefunds.ShipOrderNumber</code>. 快递单号
     */
    public String getShipordernumber() {
        return (String) get(21);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_orderrefunds.Payee</code>. 收款人
     */
    public void setPayee(String value) {
        set(22, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_orderrefunds.Payee</code>. 收款人
     */
    public String getPayee() {
        return (String) get(22);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_orderrefunds.PayeeAccount</code>. 收款人账户
     */
    public void setPayeeaccount(String value) {
        set(23, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_orderrefunds.PayeeAccount</code>. 收款人账户
     */
    public String getPayeeaccount() {
        return (String) get(23);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_orderrefunds.RefundMode</code>. 退款方式
     */
    public void setRefundmode(Integer value) {
        set(24, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_orderrefunds.RefundMode</code>. 退款方式
     */
    public Integer getRefundmode() {
        return (Integer) get(24);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_orderrefunds.BuyerDeliverDate</code>. 买家发货时间
     */
    public void setBuyerdeliverdate(Timestamp value) {
        set(25, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_orderrefunds.BuyerDeliverDate</code>. 买家发货时间
     */
    public Timestamp getBuyerdeliverdate() {
        return (Timestamp) get(25);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_orderrefunds.SellerConfirmArrivalDate</code>. 卖家确认到货时间
     */
    public void setSellerconfirmarrivaldate(Timestamp value) {
        set(26, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_orderrefunds.SellerConfirmArrivalDate</code>. 卖家确认到货时间
     */
    public Timestamp getSellerconfirmarrivaldate() {
        return (Timestamp) get(26);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_orderrefunds.Backtrack</code>. 原路退款
     */
    public void setBacktrack(Byte value) {
        set(27, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_orderrefunds.Backtrack</code>. 原路退款
     */
    public Byte getBacktrack() {
        return (Byte) get(27);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_orderrefunds.BacktrackSuccess</code>. 原路退款成功
     */
    public void setBacktracksuccess(Byte value) {
        set(28, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_orderrefunds.BacktrackSuccess</code>. 原路退款成功
     */
    public Byte getBacktracksuccess() {
        return (Byte) get(28);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_orderrefunds.BacktrackTime</code>. 原路退款时间
     */
    public void setBacktracktime(Timestamp value) {
        set(29, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_orderrefunds.BacktrackTime</code>. 原路退款时间
     */
    public Timestamp getBacktracktime() {
        return (Timestamp) get(29);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_orderrefunds.Exception</code>. 接口退款异常
     */
    public void setException(String value) {
        set(30, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_orderrefunds.Exception</code>. 接口退款异常
     */
    public String getException() {
        return (String) get(30);
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
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached TMallOrderrefundsRecord
     */
    public TMallOrderrefundsRecord() {
        super(TMallOrderrefunds.T_MALL_ORDERREFUNDS);
    }

    /**
     * Create a detached, initialised TMallOrderrefundsRecord
     */
    public TMallOrderrefundsRecord(Long id, Long orderid, Long orderitemid, Long shopid, String shopname, Long userid, String applicant, String contactperson, String contactcellphone, String refundaccount, Timestamp applydate, BigDecimal amount, String reason, Integer sellerauditstatus, Timestamp sellerauditdate, String sellerremark, Integer managerconfirmstatus, Timestamp managerconfirmdate, String managerremark, Byte isreturn, String expresscompanyname, String shipordernumber, String payee, String payeeaccount, Integer refundmode, Timestamp buyerdeliverdate, Timestamp sellerconfirmarrivaldate, Byte backtrack, Byte backtracksuccess, Timestamp backtracktime, String exception) {
        super(TMallOrderrefunds.T_MALL_ORDERREFUNDS);

        set(0, id);
        set(1, orderid);
        set(2, orderitemid);
        set(3, shopid);
        set(4, shopname);
        set(5, userid);
        set(6, applicant);
        set(7, contactperson);
        set(8, contactcellphone);
        set(9, refundaccount);
        set(10, applydate);
        set(11, amount);
        set(12, reason);
        set(13, sellerauditstatus);
        set(14, sellerauditdate);
        set(15, sellerremark);
        set(16, managerconfirmstatus);
        set(17, managerconfirmdate);
        set(18, managerremark);
        set(19, isreturn);
        set(20, expresscompanyname);
        set(21, shipordernumber);
        set(22, payee);
        set(23, payeeaccount);
        set(24, refundmode);
        set(25, buyerdeliverdate);
        set(26, sellerconfirmarrivaldate);
        set(27, backtrack);
        set(28, backtracksuccess);
        set(29, backtracktime);
        set(30, exception);
    }
}
