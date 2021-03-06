/**
 * This class is generated by jOOQ
 */
package cn.mooc.jooq.demo.generated.tables;


import cn.mooc.jooq.demo.generated.AppcenterMallData;
import cn.mooc.jooq.demo.generated.Keys;
import cn.mooc.jooq.demo.generated.tables.records.TSysConfigRecord;

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
public class TSysConfig extends TableImpl<TSysConfigRecord> {

    private static final long serialVersionUID = -1934555184;

    /**
     * The reference instance of <code>appcenter_mall_data.t_sys_config</code>
     */
    public static final TSysConfig T_SYS_CONFIG = new TSysConfig();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<TSysConfigRecord> getRecordType() {
        return TSysConfigRecord.class;
    }

    /**
     * The column <code>appcenter_mall_data.t_sys_config.keyName</code>.
     */
    public final TableField<TSysConfigRecord, String> KEYNAME = createField("keyName", org.jooq.impl.SQLDataType.VARCHAR.length(64).nullable(false), this, "");

    /**
     * The column <code>appcenter_mall_data.t_sys_config.keyValue</code>.
     */
    public final TableField<TSysConfigRecord, String> KEYVALUE = createField("keyValue", org.jooq.impl.SQLDataType.VARCHAR.length(128).nullable(false), this, "");

    /**
     * The column <code>appcenter_mall_data.t_sys_config.dataType</code>.
     */
    public final TableField<TSysConfigRecord, Integer> DATATYPE = createField("dataType", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "");

    /**
     * The column <code>appcenter_mall_data.t_sys_config.createTime</code>.
     */
    public final TableField<TSysConfigRecord, Timestamp> CREATETIME = createField("createTime", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false), this, "");

    /**
     * The column <code>appcenter_mall_data.t_sys_config.lastUpdate</code>.
     */
    public final TableField<TSysConfigRecord, Timestamp> LASTUPDATE = createField("lastUpdate", org.jooq.impl.SQLDataType.TIMESTAMP, this, "");

    /**
     * The column <code>appcenter_mall_data.t_sys_config.note</code>.
     */
    public final TableField<TSysConfigRecord, String> NOTE = createField("note", org.jooq.impl.SQLDataType.VARCHAR.length(64), this, "");

    /**
     * Create a <code>appcenter_mall_data.t_sys_config</code> table reference
     */
    public TSysConfig() {
        this("t_sys_config", null);
    }

    /**
     * Create an aliased <code>appcenter_mall_data.t_sys_config</code> table reference
     */
    public TSysConfig(String alias) {
        this(alias, T_SYS_CONFIG);
    }

    private TSysConfig(String alias, Table<TSysConfigRecord> aliased) {
        this(alias, aliased, null);
    }

    private TSysConfig(String alias, Table<TSysConfigRecord> aliased, Field<?>[] parameters) {
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
    public UniqueKey<TSysConfigRecord> getPrimaryKey() {
        return Keys.KEY_T_SYS_CONFIG_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<TSysConfigRecord>> getKeys() {
        return Arrays.<UniqueKey<TSysConfigRecord>>asList(Keys.KEY_T_SYS_CONFIG_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TSysConfig as(String alias) {
        return new TSysConfig(alias, this);
    }

    /**
     * Rename this table
     */
    public TSysConfig rename(String name) {
        return new TSysConfig(name, null);
    }
}
