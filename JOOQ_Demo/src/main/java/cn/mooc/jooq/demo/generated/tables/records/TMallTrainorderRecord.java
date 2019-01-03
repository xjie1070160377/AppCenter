/**
 * This class is generated by jOOQ
 */
package cn.mooc.jooq.demo.generated.tables.records;


import cn.mooc.jooq.demo.generated.tables.TMallTrainorder;

import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Record1;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * t_LifePaymentOrder
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.8.4"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class TMallTrainorderRecord extends UpdatableRecordImpl<TMallTrainorderRecord> {

    private static final long serialVersionUID = 860210956;

    /**
     * Setter for <code>appcenter_mall_data.t_mall_trainorder.Id</code>. Id
     */
    public void setId(Long value) {
        set(0, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_trainorder.Id</code>. Id
     */
    public Long getId() {
        return (Long) get(0);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_trainorder.CreateDate</code>. CreateDate
     */
    public void setCreatedate(Timestamp value) {
        set(1, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_trainorder.CreateDate</code>. CreateDate
     */
    public Timestamp getCreatedate() {
        return (Timestamp) get(1);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_trainorder.UserId</code>. UserId
     */
    public void setUserid(Long value) {
        set(2, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_trainorder.UserId</code>. UserId
     */
    public Long getUserid() {
        return (Long) get(2);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_trainorder.UserName</code>. UserName
     */
    public void setUsername(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_trainorder.UserName</code>. UserName
     */
    public String getUsername() {
        return (String) get(3);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_trainorder.UserRemark</code>. UserRemark
     */
    public void setUserremark(String value) {
        set(4, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_trainorder.UserRemark</code>. UserRemark
     */
    public String getUserremark() {
        return (String) get(4);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_trainorder.PaymentTypeName</code>. PaymentTypeName
     */
    public void setPaymenttypename(String value) {
        set(5, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_trainorder.PaymentTypeName</code>. PaymentTypeName
     */
    public String getPaymenttypename() {
        return (String) get(5);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_trainorder.PaymentTypeGateway</code>. PaymentTypeGateway
     */
    public void setPaymenttypegateway(String value) {
        set(6, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_trainorder.PaymentTypeGateway</code>. PaymentTypeGateway
     */
    public String getPaymenttypegateway() {
        return (String) get(6);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_trainorder.OrderStatus</code>. OrderStatus
     */
    public void setOrderstatus(Integer value) {
        set(7, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_trainorder.OrderStatus</code>. OrderStatus
     */
    public Integer getOrderstatus() {
        return (Integer) get(7);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_trainorder.RechargeStatus</code>. RechargeStatus
     */
    public void setRechargestatus(Integer value) {
        set(8, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_trainorder.RechargeStatus</code>. RechargeStatus
     */
    public Integer getRechargestatus() {
        return (Integer) get(8);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_trainorder.PayRemark</code>. PayRemark
     */
    public void setPayremark(String value) {
        set(9, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_trainorder.PayRemark</code>. PayRemark
     */
    public String getPayremark() {
        return (String) get(9);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_trainorder.RechargeRemark</code>. RechargeRemark
     */
    public void setRechargeremark(String value) {
        set(10, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_trainorder.RechargeRemark</code>. RechargeRemark
     */
    public String getRechargeremark() {
        return (String) get(10);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_trainorder.PayDate</code>. PayDate
     */
    public void setPaydate(Timestamp value) {
        set(11, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_trainorder.PayDate</code>. PayDate
     */
    public Timestamp getPaydate() {
        return (Timestamp) get(11);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_trainorder.JHOrderNum</code>. 聚合订单号
     */
    public void setJhordernum(String value) {
        set(12, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_trainorder.JHOrderNum</code>. 聚合订单号
     */
    public String getJhordernum() {
        return (String) get(12);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_trainorder.TrainOrderNum</code>. 12306订单号
     */
    public void setTrainordernum(String value) {
        set(13, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_trainorder.TrainOrderNum</code>. 12306订单号
     */
    public String getTrainordernum() {
        return (String) get(13);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_trainorder.TrainDate</code>. 乘车日期
     */
    public void setTraindate(String value) {
        set(14, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_trainorder.TrainDate</code>. 乘车日期
     */
    public String getTraindate() {
        return (String) get(14);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_trainorder.FromStationName</code>. 出发站名字
     */
    public void setFromstationname(String value) {
        set(15, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_trainorder.FromStationName</code>. 出发站名字
     */
    public String getFromstationname() {
        return (String) get(15);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_trainorder.FromStationCode</code>. 出发站简码
     */
    public void setFromstationcode(String value) {
        set(16, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_trainorder.FromStationCode</code>. 出发站简码
     */
    public String getFromstationcode() {
        return (String) get(16);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_trainorder.ToStationCode</code>. 到达站简码
     */
    public void setTostationcode(String value) {
        set(17, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_trainorder.ToStationCode</code>. 到达站简码
     */
    public String getTostationcode() {
        return (String) get(17);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_trainorder.ToStationName</code>. 到达站名字
     */
    public void setTostationname(String value) {
        set(18, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_trainorder.ToStationName</code>. 到达站名字
     */
    public String getTostationname() {
        return (String) get(18);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_trainorder.TrainCode</code>. 车次
     */
    public void setTraincode(String value) {
        set(19, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_trainorder.TrainCode</code>. 车次
     */
    public String getTraincode() {
        return (String) get(19);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_trainorder.PassengerId</code>. 乘客的顺序号
     */
    public void setPassengerid(Integer value) {
        set(20, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_trainorder.PassengerId</code>. 乘客的顺序号
     */
    public Integer getPassengerid() {
        return (Integer) get(20);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_trainorder.PassengerseName</code>. 乘车人姓名
     */
    public void setPassengersename(String value) {
        set(21, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_trainorder.PassengerseName</code>. 乘车人姓名
     */
    public String getPassengersename() {
        return (String) get(21);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_trainorder.PiaoType</code>. 票种
     */
    public void setPiaotype(Integer value) {
        set(22, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_trainorder.PiaoType</code>. 票种
     */
    public Integer getPiaotype() {
        return (Integer) get(22);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_trainorder.PiaoTypeName</code>. 票种名称
     */
    public void setPiaotypename(String value) {
        set(23, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_trainorder.PiaoTypeName</code>. 票种名称
     */
    public String getPiaotypename() {
        return (String) get(23);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_trainorder.PassportTypeseId</code>. 证件类型
     */
    public void setPassporttypeseid(String value) {
        set(24, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_trainorder.PassportTypeseId</code>. 证件类型
     */
    public String getPassporttypeseid() {
        return (String) get(24);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_trainorder.PassportTypeseIdName</code>. 证件类型名称
     */
    public void setPassporttypeseidname(String value) {
        set(25, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_trainorder.PassportTypeseIdName</code>. 证件类型名称
     */
    public String getPassporttypeseidname() {
        return (String) get(25);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_trainorder.PassportSeNo</code>. 乘客证件号码
     */
    public void setPassportseno(String value) {
        set(26, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_trainorder.PassportSeNo</code>. 乘客证件号码
     */
    public String getPassportseno() {
        return (String) get(26);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_trainorder.Price</code>. 票价
     */
    public void setPrice(BigDecimal value) {
        set(27, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_trainorder.Price</code>. 票价
     */
    public BigDecimal getPrice() {
        return (BigDecimal) get(27);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_trainorder.Amount</code>. 实际票价
     */
    public void setAmount(BigDecimal value) {
        set(28, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_trainorder.Amount</code>. 实际票价
     */
    public BigDecimal getAmount() {
        return (BigDecimal) get(28);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_trainorder.ZWcode</code>. 席别
     */
    public void setZwcode(String value) {
        set(29, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_trainorder.ZWcode</code>. 席别
     */
    public String getZwcode() {
        return (String) get(29);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_trainorder.ZWname</code>. 席别名称
     */
    public void setZwname(String value) {
        set(30, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_trainorder.ZWname</code>. 席别名称
     */
    public String getZwname() {
        return (String) get(30);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_trainorder.TicketNo</code>. 票号
     */
    public void setTicketno(String value) {
        set(31, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_trainorder.TicketNo</code>. 票号
     */
    public String getTicketno() {
        return (String) get(31);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_trainorder.GatewayOrderId</code>. GatewayOrderId
     */
    public void setGatewayorderid(String value) {
        set(32, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_trainorder.GatewayOrderId</code>. GatewayOrderId
     */
    public String getGatewayorderid() {
        return (String) get(32);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_trainorder.JHSubmitTime</code>. 聚合提交订单的时间
     */
    public void setJhsubmittime(String value) {
        set(33, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_trainorder.JHSubmitTime</code>. 聚合提交订单的时间
     */
    public String getJhsubmittime() {
        return (String) get(33);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_trainorder.JHDealTime</code>. 聚合处理完订单的时间
     */
    public void setJhdealtime(String value) {
        set(34, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_trainorder.JHDealTime</code>. 聚合处理完订单的时间
     */
    public String getJhdealtime() {
        return (String) get(34);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_trainorder.JHPayTime</code>. 聚合账户扣款时间
     */
    public void setJhpaytime(String value) {
        set(35, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_trainorder.JHPayTime</code>. 聚合账户扣款时间
     */
    public String getJhpaytime() {
        return (String) get(35);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_trainorder.JHfinishedTime</code>. 聚合出票成功时间
     */
    public void setJhfinishedtime(String value) {
        set(36, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_trainorder.JHfinishedTime</code>. 聚合出票成功时间
     */
    public String getJhfinishedtime() {
        return (String) get(36);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_trainorder.CXin</code>. 车厢席座
     */
    public void setCxin(String value) {
        set(37, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_trainorder.CXin</code>. 车厢席座
     */
    public String getCxin() {
        return (String) get(37);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_trainorder.ReturnSuccess</code>. 退票是否成功
     */
    public void setReturnsuccess(Integer value) {
        set(38, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_trainorder.ReturnSuccess</code>. 退票是否成功
     */
    public Integer getReturnsuccess() {
        return (Integer) get(38);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_trainorder.ReturnMoney</code>. 退票（改签差价）金额
     */
    public void setReturnmoney(BigDecimal value) {
        set(39, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_trainorder.ReturnMoney</code>. 退票（改签差价）金额
     */
    public BigDecimal getReturnmoney() {
        return (BigDecimal) get(39);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_trainorder.ReturnTime</code>. 完成此退票的时间
     */
    public void setReturntime(String value) {
        set(40, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_trainorder.ReturnTime</code>. 完成此退票的时间
     */
    public String getReturntime() {
        return (String) get(40);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_trainorder.ReturnFailId</code>. 退票失败原因ID
     */
    public void setReturnfailid(String value) {
        set(41, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_trainorder.ReturnFailId</code>. 退票失败原因ID
     */
    public String getReturnfailid() {
        return (String) get(41);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_trainorder.ReturnFailMsg</code>. 退票失败原因描述
     */
    public void setReturnfailmsg(String value) {
        set(42, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_trainorder.ReturnFailMsg</code>. 退票失败原因描述
     */
    public String getReturnfailmsg() {
        return (String) get(42);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_trainorder.ReturnType</code>. 退票类型
     */
    public void setReturntype(Integer value) {
        set(43, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_trainorder.ReturnType</code>. 退票类型
     */
    public Integer getReturntype() {
        return (Integer) get(43);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_trainorder.StartTime</code>. 发车时间
     */
    public void setStarttime(String value) {
        set(44, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_trainorder.StartTime</code>. 发车时间
     */
    public String getStarttime() {
        return (String) get(44);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_trainorder.ArriveTime</code>. 到达时间
     */
    public void setArrivetime(String value) {
        set(45, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_trainorder.ArriveTime</code>. 到达时间
     */
    public String getArrivetime() {
        return (String) get(45);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_trainorder.RefundConPerson</code>. 退款联系人
     */
    public void setRefundconperson(String value) {
        set(46, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_trainorder.RefundConPerson</code>. 退款联系人
     */
    public String getRefundconperson() {
        return (String) get(46);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_trainorder.RefundConMobile</code>. 退款联系人电话
     */
    public void setRefundconmobile(String value) {
        set(47, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_trainorder.RefundConMobile</code>. 退款联系人电话
     */
    public String getRefundconmobile() {
        return (String) get(47);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_trainorder.RefundTime</code>. 退款申请时间
     */
    public void setRefundtime(Timestamp value) {
        set(48, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_trainorder.RefundTime</code>. 退款申请时间
     */
    public Timestamp getRefundtime() {
        return (Timestamp) get(48);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_trainorder.RefundApplyTime</code>. 退款审核时间
     */
    public void setRefundapplytime(Timestamp value) {
        set(49, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_trainorder.RefundApplyTime</code>. 退款审核时间
     */
    public Timestamp getRefundapplytime() {
        return (Timestamp) get(49);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_trainorder.RefundReason</code>. 退票说明
     */
    public void setRefundreason(String value) {
        set(50, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_trainorder.RefundReason</code>. 退票说明
     */
    public String getRefundreason() {
        return (String) get(50);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_trainorder.IsRefund</code>. 是否退回
     */
    public void setIsrefund(Byte value) {
        set(51, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_trainorder.IsRefund</code>. 是否退回
     */
    public Byte getIsrefund() {
        return (Byte) get(51);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_trainorder.RefundMode</code>. 退款方式
     */
    public void setRefundmode(String value) {
        set(52, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_trainorder.RefundMode</code>. 退款方式
     */
    public String getRefundmode() {
        return (String) get(52);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_trainorder.RefundAccount</code>. 退款账户
     */
    public void setRefundaccount(String value) {
        set(53, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_trainorder.RefundAccount</code>. 退款账户
     */
    public String getRefundaccount() {
        return (String) get(53);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_trainorder.BacktrackSuccess</code>.
     */
    public void setBacktracksuccess(Byte value) {
        set(54, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_trainorder.BacktrackSuccess</code>.
     */
    public Byte getBacktracksuccess() {
        return (Byte) get(54);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_trainorder.BacktrackTime</code>.
     */
    public void setBacktracktime(Timestamp value) {
        set(55, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_trainorder.BacktrackTime</code>.
     */
    public Timestamp getBacktracktime() {
        return (Timestamp) get(55);
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
     * Create a detached TMallTrainorderRecord
     */
    public TMallTrainorderRecord() {
        super(TMallTrainorder.T_MALL_TRAINORDER);
    }

    /**
     * Create a detached, initialised TMallTrainorderRecord
     */
    public TMallTrainorderRecord(Long id, Timestamp createdate, Long userid, String username, String userremark, String paymenttypename, String paymenttypegateway, Integer orderstatus, Integer rechargestatus, String payremark, String rechargeremark, Timestamp paydate, String jhordernum, String trainordernum, String traindate, String fromstationname, String fromstationcode, String tostationcode, String tostationname, String traincode, Integer passengerid, String passengersename, Integer piaotype, String piaotypename, String passporttypeseid, String passporttypeseidname, String passportseno, BigDecimal price, BigDecimal amount, String zwcode, String zwname, String ticketno, String gatewayorderid, String jhsubmittime, String jhdealtime, String jhpaytime, String jhfinishedtime, String cxin, Integer returnsuccess, BigDecimal returnmoney, String returntime, String returnfailid, String returnfailmsg, Integer returntype, String starttime, String arrivetime, String refundconperson, String refundconmobile, Timestamp refundtime, Timestamp refundapplytime, String refundreason, Byte isrefund, String refundmode, String refundaccount, Byte backtracksuccess, Timestamp backtracktime) {
        super(TMallTrainorder.T_MALL_TRAINORDER);

        set(0, id);
        set(1, createdate);
        set(2, userid);
        set(3, username);
        set(4, userremark);
        set(5, paymenttypename);
        set(6, paymenttypegateway);
        set(7, orderstatus);
        set(8, rechargestatus);
        set(9, payremark);
        set(10, rechargeremark);
        set(11, paydate);
        set(12, jhordernum);
        set(13, trainordernum);
        set(14, traindate);
        set(15, fromstationname);
        set(16, fromstationcode);
        set(17, tostationcode);
        set(18, tostationname);
        set(19, traincode);
        set(20, passengerid);
        set(21, passengersename);
        set(22, piaotype);
        set(23, piaotypename);
        set(24, passporttypeseid);
        set(25, passporttypeseidname);
        set(26, passportseno);
        set(27, price);
        set(28, amount);
        set(29, zwcode);
        set(30, zwname);
        set(31, ticketno);
        set(32, gatewayorderid);
        set(33, jhsubmittime);
        set(34, jhdealtime);
        set(35, jhpaytime);
        set(36, jhfinishedtime);
        set(37, cxin);
        set(38, returnsuccess);
        set(39, returnmoney);
        set(40, returntime);
        set(41, returnfailid);
        set(42, returnfailmsg);
        set(43, returntype);
        set(44, starttime);
        set(45, arrivetime);
        set(46, refundconperson);
        set(47, refundconmobile);
        set(48, refundtime);
        set(49, refundapplytime);
        set(50, refundreason);
        set(51, isrefund);
        set(52, refundmode);
        set(53, refundaccount);
        set(54, backtracksuccess);
        set(55, backtracktime);
    }
}