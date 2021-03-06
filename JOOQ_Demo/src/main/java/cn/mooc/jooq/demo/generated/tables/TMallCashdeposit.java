/**
 * This class is generated by jOOQ
 */
package cn.mooc.jooq.demo.generated.tables;


import cn.mooc.jooq.demo.generated.AppcenterMallData;
import cn.mooc.jooq.demo.generated.Keys;
import cn.mooc.jooq.demo.generated.tables.records.TMallCashdepositRecord;

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
public class TMallCashdeposit extends TableImpl<TMallCashdepositRecord> {

    private static final long serialVersionUID = -479358876;

    /**
     * The reference instance of <code>appcenter_mall_data.t_mall_cashdeposit</code>
     */
    public static final TMallCashdeposit T_MALL_CASHDEPOSIT = new TMallCashdeposit();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<TMallCashdepositRecord> getRecordType() {
        return TMallCashdepositRecord.class;
    }

    /**
     * The column <code>appcenter_mall_data.t_mall_cashdeposit.Id</code>. 主键ID
     */
    public final TableField<TMallCashdepositRecord, Long> ID = createField("Id", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "主键ID");

    /**
     * The column <code>appcenter_mall_data.t_mall_cashdeposit.ShopId</code>. Shop表外键
     */
    public final TableField<TMallCashdepositRecord, Long> SHOPID = createField("ShopId", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "Shop表外键");

    /**
     * The column <code>appcenter_mall_data.t_mall_cashdeposit.CurrentBalance</code>. 可用金额
     */
    public final TableField<TMallCashdepositRecord, BigDecimal> CURRENTBALANCE = createField("CurrentBalance", org.jooq.impl.SQLDataType.DECIMAL.precision(10, 2).nullable(false).defaultValue(org.jooq.impl.DSL.inline("0.00", org.jooq.impl.SQLDataType.DECIMAL)), this, "可用金额");

    /**
     * The column <code>appcenter_mall_data.t_mall_cashdeposit.TotalBalance</code>. 已缴纳金额
     */
    public final TableField<TMallCashdepositRecord, BigDecimal> TOTALBALANCE = createField("TotalBalance", org.jooq.impl.SQLDataType.DECIMAL.precision(10, 2).nullable(false).defaultValue(org.jooq.impl.DSL.inline("0.00", org.jooq.impl.SQLDataType.DECIMAL)), this, "已缴纳金额");

    /**
     * The column <code>appcenter_mall_data.t_mall_cashdeposit.Date</code>. 最后一次缴纳时间
     */
    public final TableField<TMallCashdepositRecord, Timestamp> DATE = createField("Date", org.jooq.impl.SQLDataType.TIMESTAMP, this, "最后一次缴纳时间");

    /**
     * Create a <code>appcenter_mall_data.t_mall_cashdeposit</code> table reference
     */
    public TMallCashdeposit() {
        this("t_mall_cashdeposit", null);
    }

    /**
     * Create an aliased <code>appcenter_mall_data.t_mall_cashdeposit</code> table reference
     */
    public TMallCashdeposit(String alias) {
        this(alias, T_MALL_CASHDEPOSIT);
    }

    private TMallCashdeposit(String alias, Table<TMallCashdepositRecord> aliased) {
        this(alias, aliased, null);
    }

    private TMallCashdeposit(String alias, Table<TMallCashdepositRecord> aliased, Field<?>[] parameters) {
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
    public Identity<TMallCashdepositRecord, Long> getIdentity() {
        return Keys.IDENTITY_T_MALL_CASHDEPOSIT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<TMallCashdepositRecord> getPrimaryKey() {
        return Keys.KEY_T_MALL_CASHDEPOSIT_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<TMallCashdepositRecord>> getKeys() {
        return Arrays.<UniqueKey<TMallCashdepositRecord>>asList(Keys.KEY_T_MALL_CASHDEPOSIT_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ForeignKey<TMallCashdepositRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<TMallCashdepositRecord, ?>>asList(Keys.T_MALL_CASHDEPOSIT_IBFK_1);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TMallCashdeposit as(String alias) {
        return new TMallCashdeposit(alias, this);
    }

    /**
     * Rename this table
     */
    public TMallCashdeposit rename(String name) {
        return new TMallCashdeposit(name, null);
    }
}
