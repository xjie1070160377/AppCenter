/**
 * This class is generated by jOOQ
 */
package cn.mooc.jooq.demo.generated.tables;


import cn.mooc.jooq.demo.generated.AppcenterMallData;
import cn.mooc.jooq.demo.generated.Keys;
import cn.mooc.jooq.demo.generated.tables.records.TMallWalletLogRecord;

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
public class TMallWalletLog extends TableImpl<TMallWalletLogRecord> {

    private static final long serialVersionUID = -15538793;

    /**
     * The reference instance of <code>appcenter_mall_data.t_mall_wallet_log</code>
     */
    public static final TMallWalletLog T_MALL_WALLET_LOG = new TMallWalletLog();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<TMallWalletLogRecord> getRecordType() {
        return TMallWalletLogRecord.class;
    }

    /**
     * The column <code>appcenter_mall_data.t_mall_wallet_log.userId</code>.
     */
    public final TableField<TMallWalletLogRecord, Long> USERID = createField("userId", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>appcenter_mall_data.t_mall_wallet_log.logType</code>.
     */
    public final TableField<TMallWalletLogRecord, Integer> LOGTYPE = createField("logType", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "");

    /**
     * The column <code>appcenter_mall_data.t_mall_wallet_log.cost</code>.
     */
    public final TableField<TMallWalletLogRecord, Double> COST = createField("cost", org.jooq.impl.SQLDataType.DOUBLE.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.DOUBLE)), this, "");

    /**
     * The column <code>appcenter_mall_data.t_mall_wallet_log.earn</code>.
     */
    public final TableField<TMallWalletLogRecord, Double> EARN = createField("earn", org.jooq.impl.SQLDataType.DOUBLE.defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.DOUBLE)), this, "");

    /**
     * The column <code>appcenter_mall_data.t_mall_wallet_log.logTime</code>.
     */
    public final TableField<TMallWalletLogRecord, Timestamp> LOGTIME = createField("logTime", org.jooq.impl.SQLDataType.TIMESTAMP, this, "");

    /**
     * The column <code>appcenter_mall_data.t_mall_wallet_log.remark</code>.
     */
    public final TableField<TMallWalletLogRecord, String> REMARK = createField("remark", org.jooq.impl.SQLDataType.VARCHAR.length(32), this, "");

    /**
     * The column <code>appcenter_mall_data.t_mall_wallet_log.orderType</code>.
     */
    public final TableField<TMallWalletLogRecord, Integer> ORDERTYPE = createField("orderType", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "");

    /**
     * The column <code>appcenter_mall_data.t_mall_wallet_log.orderId</code>.
     */
    public final TableField<TMallWalletLogRecord, Long> ORDERID = createField("orderId", org.jooq.impl.SQLDataType.BIGINT, this, "");

    /**
     * The column <code>appcenter_mall_data.t_mall_wallet_log.id</code>.
     */
    public final TableField<TMallWalletLogRecord, Long> ID = createField("id", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * Create a <code>appcenter_mall_data.t_mall_wallet_log</code> table reference
     */
    public TMallWalletLog() {
        this("t_mall_wallet_log", null);
    }

    /**
     * Create an aliased <code>appcenter_mall_data.t_mall_wallet_log</code> table reference
     */
    public TMallWalletLog(String alias) {
        this(alias, T_MALL_WALLET_LOG);
    }

    private TMallWalletLog(String alias, Table<TMallWalletLogRecord> aliased) {
        this(alias, aliased, null);
    }

    private TMallWalletLog(String alias, Table<TMallWalletLogRecord> aliased, Field<?>[] parameters) {
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
    public Identity<TMallWalletLogRecord, Long> getIdentity() {
        return Keys.IDENTITY_T_MALL_WALLET_LOG;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<TMallWalletLogRecord> getPrimaryKey() {
        return Keys.KEY_T_MALL_WALLET_LOG_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<TMallWalletLogRecord>> getKeys() {
        return Arrays.<UniqueKey<TMallWalletLogRecord>>asList(Keys.KEY_T_MALL_WALLET_LOG_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TMallWalletLog as(String alias) {
        return new TMallWalletLog(alias, this);
    }

    /**
     * Rename this table
     */
    public TMallWalletLog rename(String name) {
        return new TMallWalletLog(name, null);
    }
}
