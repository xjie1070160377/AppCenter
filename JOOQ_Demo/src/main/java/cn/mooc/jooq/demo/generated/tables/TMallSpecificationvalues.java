/**
 * This class is generated by jOOQ
 */
package cn.mooc.jooq.demo.generated.tables;


import cn.mooc.jooq.demo.generated.AppcenterMallData;
import cn.mooc.jooq.demo.generated.Keys;
import cn.mooc.jooq.demo.generated.tables.records.TMallSpecificationvaluesRecord;

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
public class TMallSpecificationvalues extends TableImpl<TMallSpecificationvaluesRecord> {

    private static final long serialVersionUID = 705301572;

    /**
     * The reference instance of <code>appcenter_mall_data.t_mall_specificationvalues</code>
     */
    public static final TMallSpecificationvalues T_MALL_SPECIFICATIONVALUES = new TMallSpecificationvalues();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<TMallSpecificationvaluesRecord> getRecordType() {
        return TMallSpecificationvaluesRecord.class;
    }

    /**
     * The column <code>appcenter_mall_data.t_mall_specificationvalues.Id</code>.
     */
    public final TableField<TMallSpecificationvaluesRecord, Long> ID = createField("Id", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>appcenter_mall_data.t_mall_specificationvalues.Specification</code>. 规格名
     */
    public final TableField<TMallSpecificationvaluesRecord, Integer> SPECIFICATION = createField("Specification", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "规格名");

    /**
     * The column <code>appcenter_mall_data.t_mall_specificationvalues.TypeId</code>. 类型ID
     */
    public final TableField<TMallSpecificationvaluesRecord, Long> TYPEID = createField("TypeId", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "类型ID");

    /**
     * The column <code>appcenter_mall_data.t_mall_specificationvalues.Value</code>. 规格值
     */
    public final TableField<TMallSpecificationvaluesRecord, String> VALUE = createField("Value", org.jooq.impl.SQLDataType.VARCHAR.length(100).nullable(false), this, "规格值");

    /**
     * Create a <code>appcenter_mall_data.t_mall_specificationvalues</code> table reference
     */
    public TMallSpecificationvalues() {
        this("t_mall_specificationvalues", null);
    }

    /**
     * Create an aliased <code>appcenter_mall_data.t_mall_specificationvalues</code> table reference
     */
    public TMallSpecificationvalues(String alias) {
        this(alias, T_MALL_SPECIFICATIONVALUES);
    }

    private TMallSpecificationvalues(String alias, Table<TMallSpecificationvaluesRecord> aliased) {
        this(alias, aliased, null);
    }

    private TMallSpecificationvalues(String alias, Table<TMallSpecificationvaluesRecord> aliased, Field<?>[] parameters) {
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
    public Identity<TMallSpecificationvaluesRecord, Long> getIdentity() {
        return Keys.IDENTITY_T_MALL_SPECIFICATIONVALUES;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<TMallSpecificationvaluesRecord> getPrimaryKey() {
        return Keys.KEY_T_MALL_SPECIFICATIONVALUES_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<TMallSpecificationvaluesRecord>> getKeys() {
        return Arrays.<UniqueKey<TMallSpecificationvaluesRecord>>asList(Keys.KEY_T_MALL_SPECIFICATIONVALUES_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ForeignKey<TMallSpecificationvaluesRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<TMallSpecificationvaluesRecord, ?>>asList(Keys.T_MALL_SPECIFICATIONVALUES_IBFK_1);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TMallSpecificationvalues as(String alias) {
        return new TMallSpecificationvalues(alias, this);
    }

    /**
     * Rename this table
     */
    public TMallSpecificationvalues rename(String name) {
        return new TMallSpecificationvalues(name, null);
    }
}