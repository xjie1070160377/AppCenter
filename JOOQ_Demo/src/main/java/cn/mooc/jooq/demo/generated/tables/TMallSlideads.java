/**
 * This class is generated by jOOQ
 */
package cn.mooc.jooq.demo.generated.tables;


import cn.mooc.jooq.demo.generated.AppcenterMallData;
import cn.mooc.jooq.demo.generated.Keys;
import cn.mooc.jooq.demo.generated.tables.records.TMallSlideadsRecord;

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
public class TMallSlideads extends TableImpl<TMallSlideadsRecord> {

    private static final long serialVersionUID = -1335509420;

    /**
     * The reference instance of <code>appcenter_mall_data.t_mall_slideads</code>
     */
    public static final TMallSlideads T_MALL_SLIDEADS = new TMallSlideads();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<TMallSlideadsRecord> getRecordType() {
        return TMallSlideadsRecord.class;
    }

    /**
     * The column <code>appcenter_mall_data.t_mall_slideads.Id</code>.
     */
    public final TableField<TMallSlideadsRecord, Long> ID = createField("Id", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>appcenter_mall_data.t_mall_slideads.ShopId</code>. 店铺ID，0：平台轮播图  
     */
    public final TableField<TMallSlideadsRecord, Long> SHOPID = createField("ShopId", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "店铺ID，0：平台轮播图  ");

    /**
     * The column <code>appcenter_mall_data.t_mall_slideads.ImageUrl</code>. 图片保存URL
     */
    public final TableField<TMallSlideadsRecord, String> IMAGEURL = createField("ImageUrl", org.jooq.impl.SQLDataType.VARCHAR.length(100).nullable(false), this, "图片保存URL");

    /**
     * The column <code>appcenter_mall_data.t_mall_slideads.Url</code>. 图片跳转URL
     */
    public final TableField<TMallSlideadsRecord, String> URL = createField("Url", org.jooq.impl.SQLDataType.VARCHAR.length(1000).nullable(false), this, "图片跳转URL");

    /**
     * The column <code>appcenter_mall_data.t_mall_slideads.DisplaySequence</code>.
     */
    public final TableField<TMallSlideadsRecord, Long> DISPLAYSEQUENCE = createField("DisplaySequence", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>appcenter_mall_data.t_mall_slideads.TypeId</code>.
     */
    public final TableField<TMallSlideadsRecord, Integer> TYPEID = createField("TypeId", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "");

    /**
     * The column <code>appcenter_mall_data.t_mall_slideads.Description</code>.
     */
    public final TableField<TMallSlideadsRecord, String> DESCRIPTION = createField("Description", org.jooq.impl.SQLDataType.VARCHAR.length(100), this, "");

    /**
     * The column <code>appcenter_mall_data.t_mall_slideads.templateTypeId</code>.
     */
    public final TableField<TMallSlideadsRecord, Integer> TEMPLATETYPEID = createField("templateTypeId", org.jooq.impl.SQLDataType.INTEGER, this, "");

    /**
     * Create a <code>appcenter_mall_data.t_mall_slideads</code> table reference
     */
    public TMallSlideads() {
        this("t_mall_slideads", null);
    }

    /**
     * Create an aliased <code>appcenter_mall_data.t_mall_slideads</code> table reference
     */
    public TMallSlideads(String alias) {
        this(alias, T_MALL_SLIDEADS);
    }

    private TMallSlideads(String alias, Table<TMallSlideadsRecord> aliased) {
        this(alias, aliased, null);
    }

    private TMallSlideads(String alias, Table<TMallSlideadsRecord> aliased, Field<?>[] parameters) {
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
    public Identity<TMallSlideadsRecord, Long> getIdentity() {
        return Keys.IDENTITY_T_MALL_SLIDEADS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<TMallSlideadsRecord> getPrimaryKey() {
        return Keys.KEY_T_MALL_SLIDEADS_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<TMallSlideadsRecord>> getKeys() {
        return Arrays.<UniqueKey<TMallSlideadsRecord>>asList(Keys.KEY_T_MALL_SLIDEADS_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TMallSlideads as(String alias) {
        return new TMallSlideads(alias, this);
    }

    /**
     * Rename this table
     */
    public TMallSlideads rename(String name) {
        return new TMallSlideads(name, null);
    }
}