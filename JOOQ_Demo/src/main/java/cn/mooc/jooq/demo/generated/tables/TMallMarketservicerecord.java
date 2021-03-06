/**
 * This class is generated by jOOQ
 */
package cn.mooc.jooq.demo.generated.tables;


import cn.mooc.jooq.demo.generated.AppcenterMallData;
import cn.mooc.jooq.demo.generated.Keys;
import cn.mooc.jooq.demo.generated.tables.records.TMallMarketservicerecordRecord;

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
import org.jooq.types.UInteger;


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
public class TMallMarketservicerecord extends TableImpl<TMallMarketservicerecordRecord> {

    private static final long serialVersionUID = 1153342902;

    /**
     * The reference instance of <code>appcenter_mall_data.t_mall_marketservicerecord</code>
     */
    public static final TMallMarketservicerecord T_MALL_MARKETSERVICERECORD = new TMallMarketservicerecord();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<TMallMarketservicerecordRecord> getRecordType() {
        return TMallMarketservicerecordRecord.class;
    }

    /**
     * The column <code>appcenter_mall_data.t_mall_marketservicerecord.Id</code>.
     */
    public final TableField<TMallMarketservicerecordRecord, Long> ID = createField("Id", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>appcenter_mall_data.t_mall_marketservicerecord.MarketServiceId</code>.
     */
    public final TableField<TMallMarketservicerecordRecord, Long> MARKETSERVICEID = createField("MarketServiceId", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>appcenter_mall_data.t_mall_marketservicerecord.StartTime</code>. 开始时间
     */
    public final TableField<TMallMarketservicerecordRecord, Timestamp> STARTTIME = createField("StartTime", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false), this, "开始时间");

    /**
     * The column <code>appcenter_mall_data.t_mall_marketservicerecord.EndTime</code>. 结束时间
     */
    public final TableField<TMallMarketservicerecordRecord, Timestamp> ENDTIME = createField("EndTime", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false), this, "结束时间");

    /**
     * The column <code>appcenter_mall_data.t_mall_marketservicerecord.SettlementFlag</code>.
     */
    public final TableField<TMallMarketservicerecordRecord, UInteger> SETTLEMENTFLAG = createField("SettlementFlag", org.jooq.impl.SQLDataType.INTEGERUNSIGNED.nullable(false), this, "");

    /**
     * Create a <code>appcenter_mall_data.t_mall_marketservicerecord</code> table reference
     */
    public TMallMarketservicerecord() {
        this("t_mall_marketservicerecord", null);
    }

    /**
     * Create an aliased <code>appcenter_mall_data.t_mall_marketservicerecord</code> table reference
     */
    public TMallMarketservicerecord(String alias) {
        this(alias, T_MALL_MARKETSERVICERECORD);
    }

    private TMallMarketservicerecord(String alias, Table<TMallMarketservicerecordRecord> aliased) {
        this(alias, aliased, null);
    }

    private TMallMarketservicerecord(String alias, Table<TMallMarketservicerecordRecord> aliased, Field<?>[] parameters) {
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
    public Identity<TMallMarketservicerecordRecord, Long> getIdentity() {
        return Keys.IDENTITY_T_MALL_MARKETSERVICERECORD;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<TMallMarketservicerecordRecord> getPrimaryKey() {
        return Keys.KEY_T_MALL_MARKETSERVICERECORD_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<TMallMarketservicerecordRecord>> getKeys() {
        return Arrays.<UniqueKey<TMallMarketservicerecordRecord>>asList(Keys.KEY_T_MALL_MARKETSERVICERECORD_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ForeignKey<TMallMarketservicerecordRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<TMallMarketservicerecordRecord, ?>>asList(Keys.T_MALL_MARKETSERVICERECORD_IBFK_1);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TMallMarketservicerecord as(String alias) {
        return new TMallMarketservicerecord(alias, this);
    }

    /**
     * Rename this table
     */
    public TMallMarketservicerecord rename(String name) {
        return new TMallMarketservicerecord(name, null);
    }
}
