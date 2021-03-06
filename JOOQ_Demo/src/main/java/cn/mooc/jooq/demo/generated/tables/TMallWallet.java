/**
 * This class is generated by jOOQ
 */
package cn.mooc.jooq.demo.generated.tables;


import cn.mooc.jooq.demo.generated.AppcenterMallData;
import cn.mooc.jooq.demo.generated.Keys;
import cn.mooc.jooq.demo.generated.tables.records.TMallWalletRecord;

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
public class TMallWallet extends TableImpl<TMallWalletRecord> {

    private static final long serialVersionUID = -1964782199;

    /**
     * The reference instance of <code>appcenter_mall_data.t_mall_wallet</code>
     */
    public static final TMallWallet T_MALL_WALLET = new TMallWallet();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<TMallWalletRecord> getRecordType() {
        return TMallWalletRecord.class;
    }

    /**
     * The column <code>appcenter_mall_data.t_mall_wallet.userId</code>.
     */
    public final TableField<TMallWalletRecord, Long> USERID = createField("userId", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>appcenter_mall_data.t_mall_wallet.umoney</code>.
     */
    public final TableField<TMallWalletRecord, Double> UMONEY = createField("umoney", org.jooq.impl.SQLDataType.DOUBLE.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.DOUBLE)), this, "");

    /**
     * The column <code>appcenter_mall_data.t_mall_wallet.frozen</code>.
     */
    public final TableField<TMallWalletRecord, Double> FROZEN = createField("frozen", org.jooq.impl.SQLDataType.DOUBLE.defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.DOUBLE)), this, "");

    /**
     * Create a <code>appcenter_mall_data.t_mall_wallet</code> table reference
     */
    public TMallWallet() {
        this("t_mall_wallet", null);
    }

    /**
     * Create an aliased <code>appcenter_mall_data.t_mall_wallet</code> table reference
     */
    public TMallWallet(String alias) {
        this(alias, T_MALL_WALLET);
    }

    private TMallWallet(String alias, Table<TMallWalletRecord> aliased) {
        this(alias, aliased, null);
    }

    private TMallWallet(String alias, Table<TMallWalletRecord> aliased, Field<?>[] parameters) {
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
    public Identity<TMallWalletRecord, Long> getIdentity() {
        return Keys.IDENTITY_T_MALL_WALLET;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<TMallWalletRecord> getPrimaryKey() {
        return Keys.KEY_T_MALL_WALLET_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<TMallWalletRecord>> getKeys() {
        return Arrays.<UniqueKey<TMallWalletRecord>>asList(Keys.KEY_T_MALL_WALLET_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TMallWallet as(String alias) {
        return new TMallWallet(alias, this);
    }

    /**
     * Rename this table
     */
    public TMallWallet rename(String name) {
        return new TMallWallet(name, null);
    }
}
