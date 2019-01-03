/**
 * This class is generated by jOOQ
 */
package cn.mooc.jooq.demo.generated.tables;


import cn.mooc.jooq.demo.generated.AppcenterMallData;
import cn.mooc.jooq.demo.generated.Keys;
import cn.mooc.jooq.demo.generated.tables.records.TMallHomerightareaRecord;

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
public class TMallHomerightarea extends TableImpl<TMallHomerightareaRecord> {

    private static final long serialVersionUID = 387826263;

    /**
     * The reference instance of <code>appcenter_mall_data.t_mall_homerightarea</code>
     */
    public static final TMallHomerightarea T_MALL_HOMERIGHTAREA = new TMallHomerightarea();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<TMallHomerightareaRecord> getRecordType() {
        return TMallHomerightareaRecord.class;
    }

    /**
     * The column <code>appcenter_mall_data.t_mall_homerightarea.Aid</code>.
     */
    public final TableField<TMallHomerightareaRecord, Byte> AID = createField("Aid", org.jooq.impl.SQLDataType.TINYINT.nullable(false), this, "");

    /**
     * The column <code>appcenter_mall_data.t_mall_homerightarea.LinkUrl</code>.
     */
    public final TableField<TMallHomerightareaRecord, String> LINKURL = createField("LinkUrl", org.jooq.impl.SQLDataType.VARCHAR.length(255), this, "");

    /**
     * The column <code>appcenter_mall_data.t_mall_homerightarea.ImageUrl</code>.
     */
    public final TableField<TMallHomerightareaRecord, String> IMAGEURL = createField("ImageUrl", org.jooq.impl.SQLDataType.VARCHAR.length(255), this, "");

    /**
     * Create a <code>appcenter_mall_data.t_mall_homerightarea</code> table reference
     */
    public TMallHomerightarea() {
        this("t_mall_homerightarea", null);
    }

    /**
     * Create an aliased <code>appcenter_mall_data.t_mall_homerightarea</code> table reference
     */
    public TMallHomerightarea(String alias) {
        this(alias, T_MALL_HOMERIGHTAREA);
    }

    private TMallHomerightarea(String alias, Table<TMallHomerightareaRecord> aliased) {
        this(alias, aliased, null);
    }

    private TMallHomerightarea(String alias, Table<TMallHomerightareaRecord> aliased, Field<?>[] parameters) {
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
    public UniqueKey<TMallHomerightareaRecord> getPrimaryKey() {
        return Keys.KEY_T_MALL_HOMERIGHTAREA_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<TMallHomerightareaRecord>> getKeys() {
        return Arrays.<UniqueKey<TMallHomerightareaRecord>>asList(Keys.KEY_T_MALL_HOMERIGHTAREA_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TMallHomerightarea as(String alias) {
        return new TMallHomerightarea(alias, this);
    }

    /**
     * Rename this table
     */
    public TMallHomerightarea rename(String name) {
        return new TMallHomerightarea(name, null);
    }
}