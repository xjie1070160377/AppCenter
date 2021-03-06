/**
 * This class is generated by jOOQ
 */
package cn.mooc.jooq.demo.generated.tables;


import cn.mooc.jooq.demo.generated.AppcenterMallData;
import cn.mooc.jooq.demo.generated.Keys;
import cn.mooc.jooq.demo.generated.tables.records.TMallHandslideadsRecord;

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
public class TMallHandslideads extends TableImpl<TMallHandslideadsRecord> {

    private static final long serialVersionUID = 1984467898;

    /**
     * The reference instance of <code>appcenter_mall_data.t_mall_handslideads</code>
     */
    public static final TMallHandslideads T_MALL_HANDSLIDEADS = new TMallHandslideads();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<TMallHandslideadsRecord> getRecordType() {
        return TMallHandslideadsRecord.class;
    }

    /**
     * The column <code>appcenter_mall_data.t_mall_handslideads.Id</code>.
     */
    public final TableField<TMallHandslideadsRecord, Long> ID = createField("Id", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>appcenter_mall_data.t_mall_handslideads.ImageUrl</code>. 图片URL
     */
    public final TableField<TMallHandslideadsRecord, String> IMAGEURL = createField("ImageUrl", org.jooq.impl.SQLDataType.VARCHAR.length(100).nullable(false), this, "图片URL");

    /**
     * The column <code>appcenter_mall_data.t_mall_handslideads.Url</code>. 图片跳转URL
     */
    public final TableField<TMallHandslideadsRecord, String> URL = createField("Url", org.jooq.impl.SQLDataType.VARCHAR.length(1000).nullable(false), this, "图片跳转URL");

    /**
     * The column <code>appcenter_mall_data.t_mall_handslideads.DisplaySequence</code>. 排序
     */
    public final TableField<TMallHandslideadsRecord, Long> DISPLAYSEQUENCE = createField("DisplaySequence", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "排序");

    /**
     * Create a <code>appcenter_mall_data.t_mall_handslideads</code> table reference
     */
    public TMallHandslideads() {
        this("t_mall_handslideads", null);
    }

    /**
     * Create an aliased <code>appcenter_mall_data.t_mall_handslideads</code> table reference
     */
    public TMallHandslideads(String alias) {
        this(alias, T_MALL_HANDSLIDEADS);
    }

    private TMallHandslideads(String alias, Table<TMallHandslideadsRecord> aliased) {
        this(alias, aliased, null);
    }

    private TMallHandslideads(String alias, Table<TMallHandslideadsRecord> aliased, Field<?>[] parameters) {
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
    public Identity<TMallHandslideadsRecord, Long> getIdentity() {
        return Keys.IDENTITY_T_MALL_HANDSLIDEADS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<TMallHandslideadsRecord> getPrimaryKey() {
        return Keys.KEY_T_MALL_HANDSLIDEADS_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<TMallHandslideadsRecord>> getKeys() {
        return Arrays.<UniqueKey<TMallHandslideadsRecord>>asList(Keys.KEY_T_MALL_HANDSLIDEADS_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TMallHandslideads as(String alias) {
        return new TMallHandslideads(alias, this);
    }

    /**
     * Rename this table
     */
    public TMallHandslideads rename(String name) {
        return new TMallHandslideads(name, null);
    }
}
