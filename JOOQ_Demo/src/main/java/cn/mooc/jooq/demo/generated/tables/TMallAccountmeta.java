/**
 * This class is generated by jOOQ
 */
package cn.mooc.jooq.demo.generated.tables;


import cn.mooc.jooq.demo.generated.AppcenterMallData;
import cn.mooc.jooq.demo.generated.Keys;
import cn.mooc.jooq.demo.generated.tables.records.TMallAccountmetaRecord;

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
public class TMallAccountmeta extends TableImpl<TMallAccountmetaRecord> {

    private static final long serialVersionUID = -903530092;

    /**
     * The reference instance of <code>appcenter_mall_data.t_mall_accountmeta</code>
     */
    public static final TMallAccountmeta T_MALL_ACCOUNTMETA = new TMallAccountmeta();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<TMallAccountmetaRecord> getRecordType() {
        return TMallAccountmetaRecord.class;
    }

    /**
     * The column <code>appcenter_mall_data.t_mall_accountmeta.Id</code>.
     */
    public final TableField<TMallAccountmetaRecord, Long> ID = createField("Id", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>appcenter_mall_data.t_mall_accountmeta.AccountId</code>.
     */
    public final TableField<TMallAccountmetaRecord, Long> ACCOUNTID = createField("AccountId", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>appcenter_mall_data.t_mall_accountmeta.MetaKey</code>.
     */
    public final TableField<TMallAccountmetaRecord, String> METAKEY = createField("MetaKey", org.jooq.impl.SQLDataType.VARCHAR.length(100).nullable(false), this, "");

    /**
     * The column <code>appcenter_mall_data.t_mall_accountmeta.MetaValue</code>.
     */
    public final TableField<TMallAccountmetaRecord, String> METAVALUE = createField("MetaValue", org.jooq.impl.SQLDataType.CLOB.nullable(false), this, "");

    /**
     * The column <code>appcenter_mall_data.t_mall_accountmeta.ServiceStartTime</code>. 营销服务开始时间
     */
    public final TableField<TMallAccountmetaRecord, Timestamp> SERVICESTARTTIME = createField("ServiceStartTime", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false), this, "营销服务开始时间");

    /**
     * The column <code>appcenter_mall_data.t_mall_accountmeta.ServiceEndTime</code>. 营销服务结束时间
     */
    public final TableField<TMallAccountmetaRecord, Timestamp> SERVICEENDTIME = createField("ServiceEndTime", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false), this, "营销服务结束时间");

    /**
     * Create a <code>appcenter_mall_data.t_mall_accountmeta</code> table reference
     */
    public TMallAccountmeta() {
        this("t_mall_accountmeta", null);
    }

    /**
     * Create an aliased <code>appcenter_mall_data.t_mall_accountmeta</code> table reference
     */
    public TMallAccountmeta(String alias) {
        this(alias, T_MALL_ACCOUNTMETA);
    }

    private TMallAccountmeta(String alias, Table<TMallAccountmetaRecord> aliased) {
        this(alias, aliased, null);
    }

    private TMallAccountmeta(String alias, Table<TMallAccountmetaRecord> aliased, Field<?>[] parameters) {
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
    public Identity<TMallAccountmetaRecord, Long> getIdentity() {
        return Keys.IDENTITY_T_MALL_ACCOUNTMETA;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<TMallAccountmetaRecord> getPrimaryKey() {
        return Keys.KEY_T_MALL_ACCOUNTMETA_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<TMallAccountmetaRecord>> getKeys() {
        return Arrays.<UniqueKey<TMallAccountmetaRecord>>asList(Keys.KEY_T_MALL_ACCOUNTMETA_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TMallAccountmeta as(String alias) {
        return new TMallAccountmeta(alias, this);
    }

    /**
     * Rename this table
     */
    public TMallAccountmeta rename(String name) {
        return new TMallAccountmeta(name, null);
    }
}
