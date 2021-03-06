/**
 * This class is generated by jOOQ
 */
package cn.mooc.jooq.demo.generated.tables;


import cn.mooc.jooq.demo.generated.AppcenterMallData;
import cn.mooc.jooq.demo.generated.Keys;
import cn.mooc.jooq.demo.generated.tables.records.TMallProductattributesRecord;

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
public class TMallProductattributes extends TableImpl<TMallProductattributesRecord> {

    private static final long serialVersionUID = -147817827;

    /**
     * The reference instance of <code>appcenter_mall_data.t_mall_productattributes</code>
     */
    public static final TMallProductattributes T_MALL_PRODUCTATTRIBUTES = new TMallProductattributes();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<TMallProductattributesRecord> getRecordType() {
        return TMallProductattributesRecord.class;
    }

    /**
     * The column <code>appcenter_mall_data.t_mall_productattributes.Id</code>.
     */
    public final TableField<TMallProductattributesRecord, Long> ID = createField("Id", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>appcenter_mall_data.t_mall_productattributes.ProductId</code>. 商品ID
     */
    public final TableField<TMallProductattributesRecord, Long> PRODUCTID = createField("ProductId", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "商品ID");

    /**
     * The column <code>appcenter_mall_data.t_mall_productattributes.AttributeId</code>. 属性ID
     */
    public final TableField<TMallProductattributesRecord, Long> ATTRIBUTEID = createField("AttributeId", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "属性ID");

    /**
     * The column <code>appcenter_mall_data.t_mall_productattributes.ValueId</code>. 属性值ID
     */
    public final TableField<TMallProductattributesRecord, Long> VALUEID = createField("ValueId", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "属性值ID");

    /**
     * Create a <code>appcenter_mall_data.t_mall_productattributes</code> table reference
     */
    public TMallProductattributes() {
        this("t_mall_productattributes", null);
    }

    /**
     * Create an aliased <code>appcenter_mall_data.t_mall_productattributes</code> table reference
     */
    public TMallProductattributes(String alias) {
        this(alias, T_MALL_PRODUCTATTRIBUTES);
    }

    private TMallProductattributes(String alias, Table<TMallProductattributesRecord> aliased) {
        this(alias, aliased, null);
    }

    private TMallProductattributes(String alias, Table<TMallProductattributesRecord> aliased, Field<?>[] parameters) {
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
    public Identity<TMallProductattributesRecord, Long> getIdentity() {
        return Keys.IDENTITY_T_MALL_PRODUCTATTRIBUTES;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<TMallProductattributesRecord> getPrimaryKey() {
        return Keys.KEY_T_MALL_PRODUCTATTRIBUTES_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<TMallProductattributesRecord>> getKeys() {
        return Arrays.<UniqueKey<TMallProductattributesRecord>>asList(Keys.KEY_T_MALL_PRODUCTATTRIBUTES_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ForeignKey<TMallProductattributesRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<TMallProductattributesRecord, ?>>asList(Keys.T_MALL_PRODUCTATTRIBUTES_IBFK_2, Keys.T_MALL_PRODUCTATTRIBUTES_IBFK_1);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TMallProductattributes as(String alias) {
        return new TMallProductattributes(alias, this);
    }

    /**
     * Rename this table
     */
    public TMallProductattributes rename(String name) {
        return new TMallProductattributes(name, null);
    }
}
