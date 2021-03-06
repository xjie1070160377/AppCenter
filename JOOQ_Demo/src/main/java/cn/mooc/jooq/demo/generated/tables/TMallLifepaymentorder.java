/**
 * This class is generated by jOOQ
 */
package cn.mooc.jooq.demo.generated.tables;


import cn.mooc.jooq.demo.generated.AppcenterMallData;
import cn.mooc.jooq.demo.generated.Keys;
import cn.mooc.jooq.demo.generated.tables.records.TMallLifepaymentorderRecord;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Field;
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
public class TMallLifepaymentorder extends TableImpl<TMallLifepaymentorderRecord> {

    private static final long serialVersionUID = -968605999;

    /**
     * The reference instance of <code>appcenter_mall_data.t_mall_lifepaymentorder</code>
     */
    public static final TMallLifepaymentorder T_MALL_LIFEPAYMENTORDER = new TMallLifepaymentorder();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<TMallLifepaymentorderRecord> getRecordType() {
        return TMallLifepaymentorderRecord.class;
    }

    /**
     * The column <code>appcenter_mall_data.t_mall_lifepaymentorder.Id</code>.
     */
    public final TableField<TMallLifepaymentorderRecord, Long> ID = createField("Id", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>appcenter_mall_data.t_mall_lifepaymentorder.CreateDate</code>. 下单时间
     */
    public final TableField<TMallLifepaymentorderRecord, Timestamp> CREATEDATE = createField("CreateDate", org.jooq.impl.SQLDataType.TIMESTAMP, this, "下单时间");

    /**
     * The column <code>appcenter_mall_data.t_mall_lifepaymentorder.UserId</code>. 会员ID
     */
    public final TableField<TMallLifepaymentorderRecord, Long> USERID = createField("UserId", org.jooq.impl.SQLDataType.BIGINT, this, "会员ID");

    /**
     * The column <code>appcenter_mall_data.t_mall_lifepaymentorder.UserName</code>. 会员名称
     */
    public final TableField<TMallLifepaymentorderRecord, String> USERNAME = createField("UserName", org.jooq.impl.SQLDataType.VARCHAR.length(100), this, "会员名称");

    /**
     * The column <code>appcenter_mall_data.t_mall_lifepaymentorder.UserRemark</code>. UserRemark
     */
    public final TableField<TMallLifepaymentorderRecord, String> USERREMARK = createField("UserRemark", org.jooq.impl.SQLDataType.VARCHAR.length(200), this, "UserRemark");

    /**
     * The column <code>appcenter_mall_data.t_mall_lifepaymentorder.PaymentTypeName</code>. 付款类型名称
     */
    public final TableField<TMallLifepaymentorderRecord, String> PAYMENTTYPENAME = createField("PaymentTypeName", org.jooq.impl.SQLDataType.VARCHAR.length(100), this, "付款类型名称");

    /**
     * The column <code>appcenter_mall_data.t_mall_lifepaymentorder.PaymentTypeGateway</code>. 付款类型使用 插件名称
     */
    public final TableField<TMallLifepaymentorderRecord, String> PAYMENTTYPEGATEWAY = createField("PaymentTypeGateway", org.jooq.impl.SQLDataType.VARCHAR.length(100), this, "付款类型使用 插件名称");

    /**
     * The column <code>appcenter_mall_data.t_mall_lifepaymentorder.OrderStatus</code>. 充值订单状态：-1：订单关闭，0：未付款，1：已付款
     */
    public final TableField<TMallLifepaymentorderRecord, Integer> ORDERSTATUS = createField("OrderStatus", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "充值订单状态：-1：订单关闭，0：未付款，1：已付款");

    /**
     * The column <code>appcenter_mall_data.t_mall_lifepaymentorder.RechargeStatus</code>. 充值状态：-1：充值失败，0：未充值，1：充值成功
     */
    public final TableField<TMallLifepaymentorderRecord, Integer> RECHARGESTATUS = createField("RechargeStatus", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "充值状态：-1：充值失败，0：未充值，1：充值成功");

    /**
     * The column <code>appcenter_mall_data.t_mall_lifepaymentorder.PayRemark</code>. 充值结果信息
     */
    public final TableField<TMallLifepaymentorderRecord, String> PAYREMARK = createField("PayRemark", org.jooq.impl.SQLDataType.VARCHAR.length(200), this, "充值结果信息");

    /**
     * The column <code>appcenter_mall_data.t_mall_lifepaymentorder.PayDate</code>. 支付时间
     */
    public final TableField<TMallLifepaymentorderRecord, Timestamp> PAYDATE = createField("PayDate", org.jooq.impl.SQLDataType.TIMESTAMP, this, "支付时间");

    /**
     * The column <code>appcenter_mall_data.t_mall_lifepaymentorder.GatewayOrderId</code>. 支付接口返回的ID
     */
    public final TableField<TMallLifepaymentorderRecord, String> GATEWAYORDERID = createField("GatewayOrderId", org.jooq.impl.SQLDataType.VARCHAR.length(100), this, "支付接口返回的ID");

    /**
     * The column <code>appcenter_mall_data.t_mall_lifepaymentorder.provId</code>. 省份编号
     */
    public final TableField<TMallLifepaymentorderRecord, String> PROVID = createField("provId", org.jooq.impl.SQLDataType.VARCHAR.length(20).nullable(false), this, "省份编号");

    /**
     * The column <code>appcenter_mall_data.t_mall_lifepaymentorder.provName</code>. 省份名称
     */
    public final TableField<TMallLifepaymentorderRecord, String> PROVNAME = createField("provName", org.jooq.impl.SQLDataType.VARCHAR.length(20), this, "省份名称");

    /**
     * The column <code>appcenter_mall_data.t_mall_lifepaymentorder.cityId</code>. 城市编号
     */
    public final TableField<TMallLifepaymentorderRecord, String> CITYID = createField("cityId", org.jooq.impl.SQLDataType.VARCHAR.length(20).nullable(false), this, "城市编号");

    /**
     * The column <code>appcenter_mall_data.t_mall_lifepaymentorder.cityName</code>. 城市名称
     */
    public final TableField<TMallLifepaymentorderRecord, String> CITYNAME = createField("cityName", org.jooq.impl.SQLDataType.VARCHAR.length(30), this, "城市名称");

    /**
     * The column <code>appcenter_mall_data.t_mall_lifepaymentorder.type</code>. 充值类型编号
     */
    public final TableField<TMallLifepaymentorderRecord, String> TYPE = createField("type", org.jooq.impl.SQLDataType.VARCHAR.length(20).nullable(false), this, "充值类型编号");

    /**
     * The column <code>appcenter_mall_data.t_mall_lifepaymentorder.typeName</code>. 充值类型名称
     */
    public final TableField<TMallLifepaymentorderRecord, String> TYPENAME = createField("typeName", org.jooq.impl.SQLDataType.VARCHAR.length(20), this, "充值类型名称");

    /**
     * The column <code>appcenter_mall_data.t_mall_lifepaymentorder.chargeCompanyCode</code>. 充值单位编号
     */
    public final TableField<TMallLifepaymentorderRecord, String> CHARGECOMPANYCODE = createField("chargeCompanyCode", org.jooq.impl.SQLDataType.VARCHAR.length(20).nullable(false), this, "充值单位编号");

    /**
     * The column <code>appcenter_mall_data.t_mall_lifepaymentorder.chargeCompanyName</code>. 充值单位名称
     */
    public final TableField<TMallLifepaymentorderRecord, String> CHARGECOMPANYNAME = createField("chargeCompanyName", org.jooq.impl.SQLDataType.VARCHAR.length(40), this, "充值单位名称");

    /**
     * The column <code>appcenter_mall_data.t_mall_lifepaymentorder.payModeId</code>. 缴费方式编号
     */
    public final TableField<TMallLifepaymentorderRecord, String> PAYMODEID = createField("payModeId", org.jooq.impl.SQLDataType.VARCHAR.length(20), this, "缴费方式编号");

    /**
     * The column <code>appcenter_mall_data.t_mall_lifepaymentorder.payModeName</code>. 缴费方式名称
     */
    public final TableField<TMallLifepaymentorderRecord, String> PAYMODENAME = createField("payModeName", org.jooq.impl.SQLDataType.VARCHAR.length(20), this, "缴费方式名称");

    /**
     * The column <code>appcenter_mall_data.t_mall_lifepaymentorder.productId</code>. 缴费商品编号
     */
    public final TableField<TMallLifepaymentorderRecord, String> PRODUCTID = createField("productId", org.jooq.impl.SQLDataType.VARCHAR.length(20).nullable(false), this, "缴费商品编号");

    /**
     * The column <code>appcenter_mall_data.t_mall_lifepaymentorder.productName</code>. 缴费商品名称
     */
    public final TableField<TMallLifepaymentorderRecord, String> PRODUCTNAME = createField("productName", org.jooq.impl.SQLDataType.VARCHAR.length(60), this, "缴费商品名称");

    /**
     * The column <code>appcenter_mall_data.t_mall_lifepaymentorder.account</code>. 缴费账户
     */
    public final TableField<TMallLifepaymentorderRecord, String> ACCOUNT = createField("account", org.jooq.impl.SQLDataType.VARCHAR.length(20).nullable(false), this, "缴费账户");

    /**
     * The column <code>appcenter_mall_data.t_mall_lifepaymentorder.actPrice</code>. 缴费金额
     */
    public final TableField<TMallLifepaymentorderRecord, BigDecimal> ACTPRICE = createField("actPrice", org.jooq.impl.SQLDataType.DECIMAL.precision(8, 2).nullable(false), this, "缴费金额");

    /**
     * The column <code>appcenter_mall_data.t_mall_lifepaymentorder.contractNo</code>.
     */
    public final TableField<TMallLifepaymentorderRecord, String> CONTRACTNO = createField("contractNo", org.jooq.impl.SQLDataType.VARCHAR.length(60), this, "");

    /**
     * The column <code>appcenter_mall_data.t_mall_lifepaymentorder.payMentDay</code>.
     */
    public final TableField<TMallLifepaymentorderRecord, String> PAYMENTDAY = createField("payMentDay", org.jooq.impl.SQLDataType.VARCHAR.length(20), this, "");

    /**
     * The column <code>appcenter_mall_data.t_mall_lifepaymentorder.SporderId</code>. 接口商订单号
     */
    public final TableField<TMallLifepaymentorderRecord, String> SPORDERID = createField("SporderId", org.jooq.impl.SQLDataType.VARCHAR.length(32), this, "接口商订单号");

    /**
     * The column <code>appcenter_mall_data.t_mall_lifepaymentorder.Poundage</code>. 手续费
     */
    public final TableField<TMallLifepaymentorderRecord, BigDecimal> POUNDAGE = createField("Poundage", org.jooq.impl.SQLDataType.DECIMAL.precision(8, 2), this, "手续费");

    /**
     * Create a <code>appcenter_mall_data.t_mall_lifepaymentorder</code> table reference
     */
    public TMallLifepaymentorder() {
        this("t_mall_lifepaymentorder", null);
    }

    /**
     * Create an aliased <code>appcenter_mall_data.t_mall_lifepaymentorder</code> table reference
     */
    public TMallLifepaymentorder(String alias) {
        this(alias, T_MALL_LIFEPAYMENTORDER);
    }

    private TMallLifepaymentorder(String alias, Table<TMallLifepaymentorderRecord> aliased) {
        this(alias, aliased, null);
    }

    private TMallLifepaymentorder(String alias, Table<TMallLifepaymentorderRecord> aliased, Field<?>[] parameters) {
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
    public UniqueKey<TMallLifepaymentorderRecord> getPrimaryKey() {
        return Keys.KEY_T_MALL_LIFEPAYMENTORDER_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<TMallLifepaymentorderRecord>> getKeys() {
        return Arrays.<UniqueKey<TMallLifepaymentorderRecord>>asList(Keys.KEY_T_MALL_LIFEPAYMENTORDER_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TMallLifepaymentorder as(String alias) {
        return new TMallLifepaymentorder(alias, this);
    }

    /**
     * Rename this table
     */
    public TMallLifepaymentorder rename(String name) {
        return new TMallLifepaymentorder(name, null);
    }
}
