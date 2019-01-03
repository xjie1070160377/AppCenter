/**
 * This class is generated by jOOQ
 */
package cn.mooc.jooq.demo.generated.tables;


import cn.mooc.jooq.demo.generated.AppcenterMallData;
import cn.mooc.jooq.demo.generated.Keys;
import cn.mooc.jooq.demo.generated.tables.records.TMallOrderrefundsRecord;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Identity;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.TableImpl;


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
public class TMallOrderrefunds extends TableImpl<TMallOrderrefundsRecord> {

    private static final long serialVersionUID = -41231803;

    /**
     * The reference instance of <code>appcenter_mall_data.t_mall_orderrefunds</code>
     */
    public static final TMallOrderrefunds T_MALL_ORDERREFUNDS = new TMallOrderrefunds();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<TMallOrderrefundsRecord> getRecordType() {
        return TMallOrderrefundsRecord.class;
    }

    /**
     * The column <code>appcenter_mall_data.t_mall_orderrefunds.Id</code>.
     */
    public final TableField<TMallOrderrefundsRecord, Long> ID = createField("Id", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>appcenter_mall_data.t_mall_orderrefunds.OrderId</code>. 订单ID
     */
    public final TableField<TMallOrderrefundsRecord, Long> ORDERID = createField("OrderId", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "订单ID");

    /**
     * The column <code>appcenter_mall_data.t_mall_orderrefunds.OrderItemId</code>. 订单详情ID
     */
    public final TableField<TMallOrderrefundsRecord, Long> ORDERITEMID = createField("OrderItemId", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "订单详情ID");

    /**
     * The column <code>appcenter_mall_data.t_mall_orderrefunds.ShopId</code>. 店铺ID
     */
    public final TableField<TMallOrderrefundsRecord, Long> SHOPID = createField("ShopId", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "店铺ID");

    /**
     * The column <code>appcenter_mall_data.t_mall_orderrefunds.ShopName</code>. 店铺名称
     */
    public final TableField<TMallOrderrefundsRecord, String> SHOPNAME = createField("ShopName", org.jooq.impl.SQLDataType.VARCHAR.length(100).nullable(false), this, "店铺名称");

    /**
     * The column <code>appcenter_mall_data.t_mall_orderrefunds.UserId</code>. 用户ID
     */
    public final TableField<TMallOrderrefundsRecord, Long> USERID = createField("UserId", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "用户ID");

    /**
     * The column <code>appcenter_mall_data.t_mall_orderrefunds.Applicant</code>. 申请内容
     */
    public final TableField<TMallOrderrefundsRecord, String> APPLICANT = createField("Applicant", org.jooq.impl.SQLDataType.VARCHAR.length(100).nullable(false), this, "申请内容");

    /**
     * The column <code>appcenter_mall_data.t_mall_orderrefunds.ContactPerson</code>. 联系人
     */
    public final TableField<TMallOrderrefundsRecord, String> CONTACTPERSON = createField("ContactPerson", org.jooq.impl.SQLDataType.VARCHAR.length(100), this, "联系人");

    /**
     * The column <code>appcenter_mall_data.t_mall_orderrefunds.ContactCellPhone</code>. 联系电话
     */
    public final TableField<TMallOrderrefundsRecord, String> CONTACTCELLPHONE = createField("ContactCellPhone", org.jooq.impl.SQLDataType.VARCHAR.length(100), this, "联系电话");

    /**
     * The column <code>appcenter_mall_data.t_mall_orderrefunds.RefundAccount</code>. 退款金额
     */
    public final TableField<TMallOrderrefundsRecord, String> REFUNDACCOUNT = createField("RefundAccount", org.jooq.impl.SQLDataType.VARCHAR.length(100), this, "退款金额");

    /**
     * The column <code>appcenter_mall_data.t_mall_orderrefunds.ApplyDate</code>. 申请时间
     */
    public final TableField<TMallOrderrefundsRecord, Timestamp> APPLYDATE = createField("ApplyDate", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false), this, "申请时间");

    /**
     * The column <code>appcenter_mall_data.t_mall_orderrefunds.Amount</code>. 金额
     */
    public final TableField<TMallOrderrefundsRecord, BigDecimal> AMOUNT = createField("Amount", org.jooq.impl.SQLDataType.DECIMAL.precision(8, 2).nullable(false), this, "金额");

    /**
     * The column <code>appcenter_mall_data.t_mall_orderrefunds.Reason</code>. 退款原因
     */
    public final TableField<TMallOrderrefundsRecord, String> REASON = createField("Reason", org.jooq.impl.SQLDataType.VARCHAR.length(1000).nullable(false), this, "退款原因");

    /**
     * The column <code>appcenter_mall_data.t_mall_orderrefunds.SellerAuditStatus</code>. 商家审核状态
     */
    public final TableField<TMallOrderrefundsRecord, Integer> SELLERAUDITSTATUS = createField("SellerAuditStatus", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "商家审核状态");

    /**
     * The column <code>appcenter_mall_data.t_mall_orderrefunds.SellerAuditDate</code>. 商家审核时间
     */
    public final TableField<TMallOrderrefundsRecord, Timestamp> SELLERAUDITDATE = createField("SellerAuditDate", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false), this, "商家审核时间");

    /**
     * The column <code>appcenter_mall_data.t_mall_orderrefunds.SellerRemark</code>. 商家注释
     */
    public final TableField<TMallOrderrefundsRecord, String> SELLERREMARK = createField("SellerRemark", org.jooq.impl.SQLDataType.VARCHAR.length(1000), this, "商家注释");

    /**
     * The column <code>appcenter_mall_data.t_mall_orderrefunds.ManagerConfirmStatus</code>. 平台审核状态
     */
    public final TableField<TMallOrderrefundsRecord, Integer> MANAGERCONFIRMSTATUS = createField("ManagerConfirmStatus", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "平台审核状态");

    /**
     * The column <code>appcenter_mall_data.t_mall_orderrefunds.ManagerConfirmDate</code>. 平台审核时间
     */
    public final TableField<TMallOrderrefundsRecord, Timestamp> MANAGERCONFIRMDATE = createField("ManagerConfirmDate", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false), this, "平台审核时间");

    /**
     * The column <code>appcenter_mall_data.t_mall_orderrefunds.ManagerRemark</code>. 平台注释
     */
    public final TableField<TMallOrderrefundsRecord, String> MANAGERREMARK = createField("ManagerRemark", org.jooq.impl.SQLDataType.VARCHAR.length(1000), this, "平台注释");

    /**
     * The column <code>appcenter_mall_data.t_mall_orderrefunds.IsReturn</code>. 是否已经退款
     */
    public final TableField<TMallOrderrefundsRecord, Byte> ISRETURN = createField("IsReturn", org.jooq.impl.SQLDataType.TINYINT.nullable(false), this, "是否已经退款");

    /**
     * The column <code>appcenter_mall_data.t_mall_orderrefunds.ExpressCompanyName</code>. 快递公司
     */
    public final TableField<TMallOrderrefundsRecord, String> EXPRESSCOMPANYNAME = createField("ExpressCompanyName", org.jooq.impl.SQLDataType.VARCHAR.length(100), this, "快递公司");

    /**
     * The column <code>appcenter_mall_data.t_mall_orderrefunds.ShipOrderNumber</code>. 快递单号
     */
    public final TableField<TMallOrderrefundsRecord, String> SHIPORDERNUMBER = createField("ShipOrderNumber", org.jooq.impl.SQLDataType.VARCHAR.length(100), this, "快递单号");

    /**
     * The column <code>appcenter_mall_data.t_mall_orderrefunds.Payee</code>. 收款人
     */
    public final TableField<TMallOrderrefundsRecord, String> PAYEE = createField("Payee", org.jooq.impl.SQLDataType.VARCHAR.length(200), this, "收款人");

    /**
     * The column <code>appcenter_mall_data.t_mall_orderrefunds.PayeeAccount</code>. 收款人账户
     */
    public final TableField<TMallOrderrefundsRecord, String> PAYEEACCOUNT = createField("PayeeAccount", org.jooq.impl.SQLDataType.VARCHAR.length(200), this, "收款人账户");

    /**
     * The column <code>appcenter_mall_data.t_mall_orderrefunds.RefundMode</code>. 退款方式
     */
    public final TableField<TMallOrderrefundsRecord, Integer> REFUNDMODE = createField("RefundMode", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "退款方式");

    /**
     * The column <code>appcenter_mall_data.t_mall_orderrefunds.BuyerDeliverDate</code>. 买家发货时间
     */
    public final TableField<TMallOrderrefundsRecord, Timestamp> BUYERDELIVERDATE = createField("BuyerDeliverDate", org.jooq.impl.SQLDataType.TIMESTAMP, this, "买家发货时间");

    /**
     * The column <code>appcenter_mall_data.t_mall_orderrefunds.SellerConfirmArrivalDate</code>. 卖家确认到货时间
     */
    public final TableField<TMallOrderrefundsRecord, Timestamp> SELLERCONFIRMARRIVALDATE = createField("SellerConfirmArrivalDate", org.jooq.impl.SQLDataType.TIMESTAMP, this, "卖家确认到货时间");

    /**
     * The column <code>appcenter_mall_data.t_mall_orderrefunds.Backtrack</code>. 原路退款
     */
    public final TableField<TMallOrderrefundsRecord, Byte> BACKTRACK = createField("Backtrack", org.jooq.impl.SQLDataType.TINYINT, this, "原路退款");

    /**
     * The column <code>appcenter_mall_data.t_mall_orderrefunds.BacktrackSuccess</code>. 原路退款成功
     */
    public final TableField<TMallOrderrefundsRecord, Byte> BACKTRACKSUCCESS = createField("BacktrackSuccess", org.jooq.impl.SQLDataType.TINYINT, this, "原路退款成功");

    /**
     * The column <code>appcenter_mall_data.t_mall_orderrefunds.BacktrackTime</code>. 原路退款时间
     */
    public final TableField<TMallOrderrefundsRecord, Timestamp> BACKTRACKTIME = createField("BacktrackTime", org.jooq.impl.SQLDataType.TIMESTAMP, this, "原路退款时间");

    /**
     * The column <code>appcenter_mall_data.t_mall_orderrefunds.Exception</code>. 接口退款异常
     */
    public final TableField<TMallOrderrefundsRecord, String> EXCEPTION = createField("Exception", org.jooq.impl.SQLDataType.VARCHAR.length(200), this, "接口退款异常");

    /**
     * Create a <code>appcenter_mall_data.t_mall_orderrefunds</code> table reference
     */
    public TMallOrderrefunds() {
        this("t_mall_orderrefunds", null);
    }

    /**
     * Create an aliased <code>appcenter_mall_data.t_mall_orderrefunds</code> table reference
     */
    public TMallOrderrefunds(String alias) {
        this(alias, T_MALL_ORDERREFUNDS);
    }

    private TMallOrderrefunds(String alias, Table<TMallOrderrefundsRecord> aliased) {
        this(alias, aliased, null);
    }

    private TMallOrderrefunds(String alias, Table<TMallOrderrefundsRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Schema getSchema() {
        return AppcenterMallData.APPCENTER_MALL_DATA;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<TMallOrderrefundsRecord, Long> getIdentity() {
        return Keys.IDENTITY_T_MALL_ORDERREFUNDS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<TMallOrderrefundsRecord> getPrimaryKey() {
        return Keys.KEY_T_MALL_ORDERREFUNDS_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<TMallOrderrefundsRecord>> getKeys() {
        return Arrays.<UniqueKey<TMallOrderrefundsRecord>>asList(Keys.KEY_T_MALL_ORDERREFUNDS_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ForeignKey<TMallOrderrefundsRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<TMallOrderrefundsRecord, ?>>asList(Keys.T_MALL_ORDERREFUNDS_IBFK_1);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TMallOrderrefunds as(String alias) {
        return new TMallOrderrefunds(alias, this);
    }

    /**
     * Rename this table
     */
    public TMallOrderrefunds rename(String name) {
        return new TMallOrderrefunds(name, null);
    }
}
