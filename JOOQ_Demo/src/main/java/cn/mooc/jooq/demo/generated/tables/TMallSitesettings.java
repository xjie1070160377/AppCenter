/**
 * This class is generated by jOOQ
 */
package cn.mooc.jooq.demo.generated.tables;


import cn.mooc.jooq.demo.generated.AppcenterMallData;
import cn.mooc.jooq.demo.generated.Keys;
import cn.mooc.jooq.demo.generated.tables.records.TMallSitesettingsRecord;

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
public class TMallSitesettings extends TableImpl<TMallSitesettingsRecord> {

    private static final long serialVersionUID = 1760528241;

    /**
     * The reference instance of <code>appcenter_mall_data.t_mall_sitesettings</code>
     */
    public static final TMallSitesettings T_MALL_SITESETTINGS = new TMallSitesettings();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<TMallSitesettingsRecord> getRecordType() {
        return TMallSitesettingsRecord.class;
    }

    /**
     * The column <code>appcenter_mall_data.t_mall_sitesettings.Id</code>.
     */
    public final TableField<TMallSitesettingsRecord, Long> ID = createField("Id", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>appcenter_mall_data.t_mall_sitesettings.Key_</code>.
     */
    public final TableField<TMallSitesettingsRecord, String> KEY_ = createField("Key_", org.jooq.impl.SQLDataType.VARCHAR.length(100).nullable(false), this, "");

    /**
     * The column <code>appcenter_mall_data.t_mall_sitesettings.Value_</code>.
     */
    public final TableField<TMallSitesettingsRecord, String> VALUE_ = createField("Value_", org.jooq.impl.SQLDataType.VARCHAR.length(4000).defaultValue(org.jooq.impl.DSL.inline(" ", org.jooq.impl.SQLDataType.VARCHAR)), this, "");

    /**
     * Create a <code>appcenter_mall_data.t_mall_sitesettings</code> table reference
     */
    public TMallSitesettings() {
        this("t_mall_sitesettings", null);
    }

    /**
     * Create an aliased <code>appcenter_mall_data.t_mall_sitesettings</code> table reference
     */
    public TMallSitesettings(String alias) {
        this(alias, T_MALL_SITESETTINGS);
    }

    private TMallSitesettings(String alias, Table<TMallSitesettingsRecord> aliased) {
        this(alias, aliased, null);
    }

    private TMallSitesettings(String alias, Table<TMallSitesettingsRecord> aliased, Field<?>[] parameters) {
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
    public Identity<TMallSitesettingsRecord, Long> getIdentity() {
        return Keys.IDENTITY_T_MALL_SITESETTINGS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<TMallSitesettingsRecord> getPrimaryKey() {
        return Keys.KEY_T_MALL_SITESETTINGS_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<TMallSitesettingsRecord>> getKeys() {
        return Arrays.<UniqueKey<TMallSitesettingsRecord>>asList(Keys.KEY_T_MALL_SITESETTINGS_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TMallSitesettings as(String alias) {
        return new TMallSitesettings(alias, this);
    }

    /**
     * Rename this table
     */
    public TMallSitesettings rename(String name) {
        return new TMallSitesettings(name, null);
    }
}
