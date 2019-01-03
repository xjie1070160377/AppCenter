/**
 * This class is generated by jOOQ
 */
package cn.mooc.jooq.demo.generated.tables;


import cn.mooc.jooq.demo.generated.AppcenterMallData;
import cn.mooc.jooq.demo.generated.Keys;
import cn.mooc.jooq.demo.generated.tables.records.TMallMobilecategoriesRecord;

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
public class TMallMobilecategories extends TableImpl<TMallMobilecategoriesRecord> {

    private static final long serialVersionUID = -347203158;

    /**
     * The reference instance of <code>appcenter_mall_data.t_mall_mobilecategories</code>
     */
    public static final TMallMobilecategories T_MALL_MOBILECATEGORIES = new TMallMobilecategories();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<TMallMobilecategoriesRecord> getRecordType() {
        return TMallMobilecategoriesRecord.class;
    }

    /**
     * The column <code>appcenter_mall_data.t_mall_mobilecategories.Id</code>.
     */
    public final TableField<TMallMobilecategoriesRecord, Integer> ID = createField("Id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>appcenter_mall_data.t_mall_mobilecategories.CategoryId</code>.
     */
    public final TableField<TMallMobilecategoriesRecord, Long> CATEGORYID = createField("CategoryId", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>appcenter_mall_data.t_mall_mobilecategories.DisplaySequence</code>.
     */
    public final TableField<TMallMobilecategoriesRecord, Integer> DISPLAYSEQUENCE = createField("DisplaySequence", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>appcenter_mall_data.t_mall_mobilecategories.DisplayTypeId</code>.
     */
    public final TableField<TMallMobilecategoriesRecord, Integer> DISPLAYTYPEID = createField("DisplayTypeId", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * Create a <code>appcenter_mall_data.t_mall_mobilecategories</code> table reference
     */
    public TMallMobilecategories() {
        this("t_mall_mobilecategories", null);
    }

    /**
     * Create an aliased <code>appcenter_mall_data.t_mall_mobilecategories</code> table reference
     */
    public TMallMobilecategories(String alias) {
        this(alias, T_MALL_MOBILECATEGORIES);
    }

    private TMallMobilecategories(String alias, Table<TMallMobilecategoriesRecord> aliased) {
        this(alias, aliased, null);
    }

    private TMallMobilecategories(String alias, Table<TMallMobilecategoriesRecord> aliased, Field<?>[] parameters) {
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
    public Identity<TMallMobilecategoriesRecord, Integer> getIdentity() {
        return Keys.IDENTITY_T_MALL_MOBILECATEGORIES;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<TMallMobilecategoriesRecord> getPrimaryKey() {
        return Keys.KEY_T_MALL_MOBILECATEGORIES_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<TMallMobilecategoriesRecord>> getKeys() {
        return Arrays.<UniqueKey<TMallMobilecategoriesRecord>>asList(Keys.KEY_T_MALL_MOBILECATEGORIES_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ForeignKey<TMallMobilecategoriesRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<TMallMobilecategoriesRecord, ?>>asList(Keys.T_MALL_MOBILECATEGORIES_IBFK_1);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TMallMobilecategories as(String alias) {
        return new TMallMobilecategories(alias, this);
    }

    /**
     * Rename this table
     */
    public TMallMobilecategories rename(String name) {
        return new TMallMobilecategories(name, null);
    }
}
