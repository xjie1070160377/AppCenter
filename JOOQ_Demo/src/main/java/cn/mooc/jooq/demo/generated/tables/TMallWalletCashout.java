/**
 * This class is generated by jOOQ
 */
package cn.mooc.jooq.demo.generated.tables;


import cn.mooc.jooq.demo.generated.AppcenterMallData;
import cn.mooc.jooq.demo.generated.Keys;
import cn.mooc.jooq.demo.generated.tables.records.TMallWalletCashoutRecord;

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
public class TMallWalletCashout extends TableImpl<TMallWalletCashoutRecord> {

    private static final long serialVersionUID = 531998061;

    /**
     * The reference instance of <code>appcenter_mall_data.t_mall_wallet_cashout</code>
     */
    public static final TMallWalletCashout T_MALL_WALLET_CASHOUT = new TMallWalletCashout();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<TMallWalletCashoutRecord> getRecordType() {
        return TMallWalletCashoutRecord.class;
    }

    /**
     * The column <code>appcenter_mall_data.t_mall_wallet_cashout.applyUserId</code>.
     */
    public final TableField<TMallWalletCashoutRecord, Long> APPLYUSERID = createField("applyUserId", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>appcenter_mall_data.t_mall_wallet_cashout.id</code>.
     */
    public final TableField<TMallWalletCashoutRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>appcenter_mall_data.t_mall_wallet_cashout.cash</code>.
     */
    public final TableField<TMallWalletCashoutRecord, Double> CASH = createField("cash", org.jooq.impl.SQLDataType.DOUBLE.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.DOUBLE)), this, "");

    /**
     * The column <code>appcenter_mall_data.t_mall_wallet_cashout.bankType</code>.
     */
    public final TableField<TMallWalletCashoutRecord, Integer> BANKTYPE = createField("bankType", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>appcenter_mall_data.t_mall_wallet_cashout.applyTime</code>.
     */
    public final TableField<TMallWalletCashoutRecord, Timestamp> APPLYTIME = createField("applyTime", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false), this, "");

    /**
     * The column <code>appcenter_mall_data.t_mall_wallet_cashout.status</code>.
     */
    public final TableField<TMallWalletCashoutRecord, Integer> STATUS = createField("status", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>appcenter_mall_data.t_mall_wallet_cashout.auditUserId</code>.
     */
    public final TableField<TMallWalletCashoutRecord, Long> AUDITUSERID = createField("auditUserId", org.jooq.impl.SQLDataType.BIGINT, this, "");

    /**
     * The column <code>appcenter_mall_data.t_mall_wallet_cashout.auditTime</code>.
     */
    public final TableField<TMallWalletCashoutRecord, Timestamp> AUDITTIME = createField("auditTime", org.jooq.impl.SQLDataType.TIMESTAMP, this, "");

    /**
     * The column <code>appcenter_mall_data.t_mall_wallet_cashout.reason</code>.
     */
    public final TableField<TMallWalletCashoutRecord, String> REASON = createField("reason", org.jooq.impl.SQLDataType.VARCHAR.length(64), this, "");

    /**
     * The column <code>appcenter_mall_data.t_mall_wallet_cashout.remark</code>.
     */
    public final TableField<TMallWalletCashoutRecord, String> REMARK = createField("remark", org.jooq.impl.SQLDataType.VARCHAR.length(64), this, "");

    /**
     * Create a <code>appcenter_mall_data.t_mall_wallet_cashout</code> table reference
     */
    public TMallWalletCashout() {
        this("t_mall_wallet_cashout", null);
    }

    /**
     * Create an aliased <code>appcenter_mall_data.t_mall_wallet_cashout</code> table reference
     */
    public TMallWalletCashout(String alias) {
        this(alias, T_MALL_WALLET_CASHOUT);
    }

    private TMallWalletCashout(String alias, Table<TMallWalletCashoutRecord> aliased) {
        this(alias, aliased, null);
    }

    private TMallWalletCashout(String alias, Table<TMallWalletCashoutRecord> aliased, Field<?>[] parameters) {
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
    public Identity<TMallWalletCashoutRecord, Integer> getIdentity() {
        return Keys.IDENTITY_T_MALL_WALLET_CASHOUT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<TMallWalletCashoutRecord> getPrimaryKey() {
        return Keys.KEY_T_MALL_WALLET_CASHOUT_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<TMallWalletCashoutRecord>> getKeys() {
        return Arrays.<UniqueKey<TMallWalletCashoutRecord>>asList(Keys.KEY_T_MALL_WALLET_CASHOUT_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TMallWalletCashout as(String alias) {
        return new TMallWalletCashout(alias, this);
    }

    /**
     * Rename this table
     */
    public TMallWalletCashout rename(String name) {
        return new TMallWalletCashout(name, null);
    }
}