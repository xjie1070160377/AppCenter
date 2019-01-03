/**
 * This class is generated by jOOQ
 */
package cn.mooc.jooq.demo.generated.tables;


import cn.mooc.jooq.demo.generated.AppcenterMallData;
import cn.mooc.jooq.demo.generated.Keys;
import cn.mooc.jooq.demo.generated.tables.records.TMallAccountpurchaseagreementRecord;

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
public class TMallAccountpurchaseagreement extends TableImpl<TMallAccountpurchaseagreementRecord> {

    private static final long serialVersionUID = -1212245334;

    /**
     * The reference instance of <code>appcenter_mall_data.t_mall_accountpurchaseagreement</code>
     */
    public static final TMallAccountpurchaseagreement T_MALL_ACCOUNTPURCHASEAGREEMENT = new TMallAccountpurchaseagreement();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<TMallAccountpurchaseagreementRecord> getRecordType() {
        return TMallAccountpurchaseagreementRecord.class;
    }

    /**
     * The column <code>appcenter_mall_data.t_mall_accountpurchaseagreement.Id</code>.
     */
    public final TableField<TMallAccountpurchaseagreementRecord, Long> ID = createField("Id", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>appcenter_mall_data.t_mall_accountpurchaseagreement.AccountId</code>.
     */
    public final TableField<TMallAccountpurchaseagreementRecord, Long> ACCOUNTID = createField("AccountId", org.jooq.impl.SQLDataType.BIGINT, this, "");

    /**
     * The column <code>appcenter_mall_data.t_mall_accountpurchaseagreement.ShopId</code>.
     */
    public final TableField<TMallAccountpurchaseagreementRecord, Long> SHOPID = createField("ShopId", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>appcenter_mall_data.t_mall_accountpurchaseagreement.Date</code>. 日期
     */
    public final TableField<TMallAccountpurchaseagreementRecord, Timestamp> DATE = createField("Date", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false), this, "日期");

    /**
     * The column <code>appcenter_mall_data.t_mall_accountpurchaseagreement.PurchaseAgreementId</code>.
     */
    public final TableField<TMallAccountpurchaseagreementRecord, Long> PURCHASEAGREEMENTID = createField("PurchaseAgreementId", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>appcenter_mall_data.t_mall_accountpurchaseagreement.AdvancePayment</code>. 预付款金额
     */
    public final TableField<TMallAccountpurchaseagreementRecord, BigDecimal> ADVANCEPAYMENT = createField("AdvancePayment", org.jooq.impl.SQLDataType.DECIMAL.precision(8, 2).nullable(false), this, "预付款金额");

    /**
     * The column <code>appcenter_mall_data.t_mall_accountpurchaseagreement.FinishDate</code>. 平台审核时间
     */
    public final TableField<TMallAccountpurchaseagreementRecord, Timestamp> FINISHDATE = createField("FinishDate", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false), this, "平台审核时间");

    /**
     * The column <code>appcenter_mall_data.t_mall_accountpurchaseagreement.ApplyDate</code>. 申请
     */
    public final TableField<TMallAccountpurchaseagreementRecord, Timestamp> APPLYDATE = createField("ApplyDate", org.jooq.impl.SQLDataType.TIMESTAMP, this, "申请");

    /**
     * Create a <code>appcenter_mall_data.t_mall_accountpurchaseagreement</code> table reference
     */
    public TMallAccountpurchaseagreement() {
        this("t_mall_accountpurchaseagreement", null);
    }

    /**
     * Create an aliased <code>appcenter_mall_data.t_mall_accountpurchaseagreement</code> table reference
     */
    public TMallAccountpurchaseagreement(String alias) {
        this(alias, T_MALL_ACCOUNTPURCHASEAGREEMENT);
    }

    private TMallAccountpurchaseagreement(String alias, Table<TMallAccountpurchaseagreementRecord> aliased) {
        this(alias, aliased, null);
    }

    private TMallAccountpurchaseagreement(String alias, Table<TMallAccountpurchaseagreementRecord> aliased, Field<?>[] parameters) {
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
    public Identity<TMallAccountpurchaseagreementRecord, Long> getIdentity() {
        return Keys.IDENTITY_T_MALL_ACCOUNTPURCHASEAGREEMENT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<TMallAccountpurchaseagreementRecord> getPrimaryKey() {
        return Keys.KEY_T_MALL_ACCOUNTPURCHASEAGREEMENT_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<TMallAccountpurchaseagreementRecord>> getKeys() {
        return Arrays.<UniqueKey<TMallAccountpurchaseagreementRecord>>asList(Keys.KEY_T_MALL_ACCOUNTPURCHASEAGREEMENT_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ForeignKey<TMallAccountpurchaseagreementRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<TMallAccountpurchaseagreementRecord, ?>>asList(Keys.T_MALL_ACCOUNTPURCHASEAGREEMENT_IBFK_1);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TMallAccountpurchaseagreement as(String alias) {
        return new TMallAccountpurchaseagreement(alias, this);
    }

    /**
     * Rename this table
     */
    public TMallAccountpurchaseagreement rename(String name) {
        return new TMallAccountpurchaseagreement(name, null);
    }
}
