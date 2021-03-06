/**
 * This class is generated by jOOQ
 */
package cn.mooc.jooq.demo.generated.tables;


import cn.mooc.jooq.demo.generated.AppcenterMallData;
import cn.mooc.jooq.demo.generated.Keys;
import cn.mooc.jooq.demo.generated.tables.records.TMallTypebrandsRecord;

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
public class TMallTypebrands extends TableImpl<TMallTypebrandsRecord> {

    private static final long serialVersionUID = -1386062509;

    /**
     * The reference instance of <code>appcenter_mall_data.t_mall_typebrands</code>
     */
    public static final TMallTypebrands T_MALL_TYPEBRANDS = new TMallTypebrands();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<TMallTypebrandsRecord> getRecordType() {
        return TMallTypebrandsRecord.class;
    }

    /**
     * The column <code>appcenter_mall_data.t_mall_typebrands.Id</code>.
     */
    public final TableField<TMallTypebrandsRecord, Long> ID = createField("Id", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>appcenter_mall_data.t_mall_typebrands.TypeId</code>.
     */
    public final TableField<TMallTypebrandsRecord, Long> TYPEID = createField("TypeId", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>appcenter_mall_data.t_mall_typebrands.BrandId</code>.
     */
    public final TableField<TMallTypebrandsRecord, Long> BRANDID = createField("BrandId", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * Create a <code>appcenter_mall_data.t_mall_typebrands</code> table reference
     */
    public TMallTypebrands() {
        this("t_mall_typebrands", null);
    }

    /**
     * Create an aliased <code>appcenter_mall_data.t_mall_typebrands</code> table reference
     */
    public TMallTypebrands(String alias) {
        this(alias, T_MALL_TYPEBRANDS);
    }

    private TMallTypebrands(String alias, Table<TMallTypebrandsRecord> aliased) {
        this(alias, aliased, null);
    }

    private TMallTypebrands(String alias, Table<TMallTypebrandsRecord> aliased, Field<?>[] parameters) {
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
    public Identity<TMallTypebrandsRecord, Long> getIdentity() {
        return Keys.IDENTITY_T_MALL_TYPEBRANDS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<TMallTypebrandsRecord> getPrimaryKey() {
        return Keys.KEY_T_MALL_TYPEBRANDS_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<TMallTypebrandsRecord>> getKeys() {
        return Arrays.<UniqueKey<TMallTypebrandsRecord>>asList(Keys.KEY_T_MALL_TYPEBRANDS_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ForeignKey<TMallTypebrandsRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<TMallTypebrandsRecord, ?>>asList(Keys.T_MALL_TYPEBRANDS_IBFK_2, Keys.T_MALL_TYPEBRANDS_IBFK_1);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TMallTypebrands as(String alias) {
        return new TMallTypebrands(alias, this);
    }

    /**
     * Rename this table
     */
    public TMallTypebrands rename(String name) {
        return new TMallTypebrands(name, null);
    }
}
