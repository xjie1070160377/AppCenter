/**
 * This class is generated by jOOQ
 */
package cn.mooc.jooq.demo.generated.tables;


import cn.mooc.jooq.demo.generated.AppcenterMallData;
import cn.mooc.jooq.demo.generated.Keys;
import cn.mooc.jooq.demo.generated.tables.records.TMallAccountsRecord;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Field;
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
public class TMallAccounts extends TableImpl<TMallAccountsRecord> {

    private static final long serialVersionUID = 473823921;

    /**
     * The reference instance of <code>appcenter_mall_data.t_mall_accounts</code>
     */
    public static final TMallAccounts T_MALL_ACCOUNTS = new TMallAccounts();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<TMallAccountsRecord> getRecordType() {
        return TMallAccountsRecord.class;
    }

    /**
     * The column <code>appcenter_mall_data.t_mall_accounts.Id</code>.
     */
    public final TableField<TMallAccountsRecord, Long> ID = createField("Id", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>appcenter_mall_data.t_mall_accounts.ShopId</code>. 店铺ID
     */
    public final TableField<TMallAccountsRecord, Long> SHOPID = createField("ShopId", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "店铺ID");

    /**
     * The column <code>appcenter_mall_data.t_mall_accounts.ShopName</code>. 店铺名称
     */
    public final TableField<TMallAccountsRecord, String> SHOPNAME = createField("ShopName", org.jooq.impl.SQLDataType.VARCHAR.length(100).nullable(false), this, "店铺名称");

    /**
     * The column <code>appcenter_mall_data.t_mall_accounts.AccountDate</code>. 出账日期
     */
    public final TableField<TMallAccountsRecord, Timestamp> ACCOUNTDATE = createField("AccountDate", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false), this, "出账日期");

    /**
     * The column <code>appcenter_mall_data.t_mall_accounts.StartDate</code>. 开始时间
     */
    public final TableField<TMallAccountsRecord, Timestamp> STARTDATE = createField("StartDate", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false), this, "开始时间");

    /**
     * The column <code>appcenter_mall_data.t_mall_accounts.EndDate</code>. 结束时间
     */
    public final TableField<TMallAccountsRecord, Timestamp> ENDDATE = createField("EndDate", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false), this, "结束时间");

    /**
     * The column <code>appcenter_mall_data.t_mall_accounts.Status</code>. 枚举 0未结账，1已结账
     */
    public final TableField<TMallAccountsRecord, Integer> STATUS = createField("Status", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "枚举 0未结账，1已结账");

    /**
     * The column <code>appcenter_mall_data.t_mall_accounts.ProductActualPaidAmount</code>. 商品实付总额
     */
    public final TableField<TMallAccountsRecord, BigDecimal> PRODUCTACTUALPAIDAMOUNT = createField("ProductActualPaidAmount", org.jooq.impl.SQLDataType.DECIMAL.precision(8, 2).nullable(false), this, "商品实付总额");

    /**
     * The column <code>appcenter_mall_data.t_mall_accounts.FreightAmount</code>. 运费
     */
    public final TableField<TMallAccountsRecord, BigDecimal> FREIGHTAMOUNT = createField("FreightAmount", org.jooq.impl.SQLDataType.DECIMAL.precision(8, 2).nullable(false), this, "运费");

    /**
     * The column <code>appcenter_mall_data.t_mall_accounts.CommissionAmount</code>. 佣金
     */
    public final TableField<TMallAccountsRecord, BigDecimal> COMMISSIONAMOUNT = createField("CommissionAmount", org.jooq.impl.SQLDataType.DECIMAL.precision(8, 2).nullable(false), this, "佣金");

    /**
     * The column <code>appcenter_mall_data.t_mall_accounts.RefundCommissionAmount</code>. 退还佣金
     */
    public final TableField<TMallAccountsRecord, BigDecimal> REFUNDCOMMISSIONAMOUNT = createField("RefundCommissionAmount", org.jooq.impl.SQLDataType.DECIMAL.precision(8, 2).nullable(false), this, "退还佣金");

    /**
     * The column <code>appcenter_mall_data.t_mall_accounts.RefundAmount</code>. 退款金额
     */
    public final TableField<TMallAccountsRecord, BigDecimal> REFUNDAMOUNT = createField("RefundAmount", org.jooq.impl.SQLDataType.DECIMAL.precision(8, 2).nullable(false), this, "退款金额");

    /**
     * The column <code>appcenter_mall_data.t_mall_accounts.AdvancePaymentAmount</code>. 预付款总额
     */
    public final TableField<TMallAccountsRecord, BigDecimal> ADVANCEPAYMENTAMOUNT = createField("AdvancePaymentAmount", org.jooq.impl.SQLDataType.DECIMAL.precision(8, 2).nullable(false), this, "预付款总额");

    /**
     * The column <code>appcenter_mall_data.t_mall_accounts.PeriodSettlement</code>. 本期应结
     */
    public final TableField<TMallAccountsRecord, BigDecimal> PERIODSETTLEMENT = createField("PeriodSettlement", org.jooq.impl.SQLDataType.DECIMAL.precision(8, 2).nullable(false), this, "本期应结");

    /**
     * The column <code>appcenter_mall_data.t_mall_accounts.Remark</code>.
     */
    public final TableField<TMallAccountsRecord, String> REMARK = createField("Remark", org.jooq.impl.SQLDataType.CLOB, this, "");

    /**
     * The column <code>appcenter_mall_data.t_mall_accounts.DistributorCommissionAmount</code>. 分销佣金
     */
    public final TableField<TMallAccountsRecord, BigDecimal> DISTRIBUTORCOMMISSIONAMOUNT = createField("DistributorCommissionAmount", org.jooq.impl.SQLDataType.DECIMAL.precision(8, 2).nullable(false), this, "分销佣金");

    /**
     * Create a <code>appcenter_mall_data.t_mall_accounts</code> table reference
     */
    public TMallAccounts() {
        this("t_mall_accounts", null);
    }

    /**
     * Create an aliased <code>appcenter_mall_data.t_mall_accounts</code> table reference
     */
    public TMallAccounts(String alias) {
        this(alias, T_MALL_ACCOUNTS);
    }

    private TMallAccounts(String alias, Table<TMallAccountsRecord> aliased) {
        this(alias, aliased, null);
    }

    private TMallAccounts(String alias, Table<TMallAccountsRecord> aliased, Field<?>[] parameters) {
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
    public Identity<TMallAccountsRecord, Long> getIdentity() {
        return Keys.IDENTITY_T_MALL_ACCOUNTS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<TMallAccountsRecord> getPrimaryKey() {
        return Keys.KEY_T_MALL_ACCOUNTS_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<TMallAccountsRecord>> getKeys() {
        return Arrays.<UniqueKey<TMallAccountsRecord>>asList(Keys.KEY_T_MALL_ACCOUNTS_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TMallAccounts as(String alias) {
        return new TMallAccounts(alias, this);
    }

    /**
     * Rename this table
     */
    public TMallAccounts rename(String name) {
        return new TMallAccounts(name, null);
    }
}
