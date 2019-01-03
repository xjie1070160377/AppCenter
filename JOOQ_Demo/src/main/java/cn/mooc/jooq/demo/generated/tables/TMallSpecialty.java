/**
 * This class is generated by jOOQ
 */
package cn.mooc.jooq.demo.generated.tables;


import cn.mooc.jooq.demo.generated.AppcenterMallData;
import cn.mooc.jooq.demo.generated.Keys;
import cn.mooc.jooq.demo.generated.tables.records.TMallSpecialtyRecord;

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
public class TMallSpecialty extends TableImpl<TMallSpecialtyRecord> {

    private static final long serialVersionUID = 1368550041;

    /**
     * The reference instance of <code>appcenter_mall_data.t_mall_specialty</code>
     */
    public static final TMallSpecialty T_MALL_SPECIALTY = new TMallSpecialty();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<TMallSpecialtyRecord> getRecordType() {
        return TMallSpecialtyRecord.class;
    }

    /**
     * The column <code>appcenter_mall_data.t_mall_specialty.Id</code>.
     */
    public final TableField<TMallSpecialtyRecord, Integer> ID = createField("Id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>appcenter_mall_data.t_mall_specialty.Number</code>.
     */
    public final TableField<TMallSpecialtyRecord, String> NUMBER = createField("Number", org.jooq.impl.SQLDataType.VARCHAR.length(100).nullable(false), this, "");

    /**
     * The column <code>appcenter_mall_data.t_mall_specialty.Name</code>.
     */
    public final TableField<TMallSpecialtyRecord, String> NAME = createField("Name", org.jooq.impl.SQLDataType.VARCHAR.length(100).nullable(false), this, "");

    /**
     * The column <code>appcenter_mall_data.t_mall_specialty.Content</code>.
     */
    public final TableField<TMallSpecialtyRecord, String> CONTENT = createField("Content", org.jooq.impl.SQLDataType.CLOB.nullable(false), this, "");

    /**
     * The column <code>appcenter_mall_data.t_mall_specialty.IsIndex</code>.
     */
    public final TableField<TMallSpecialtyRecord, Integer> ISINDEX = createField("IsIndex", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * Create a <code>appcenter_mall_data.t_mall_specialty</code> table reference
     */
    public TMallSpecialty() {
        this("t_mall_specialty", null);
    }

    /**
     * Create an aliased <code>appcenter_mall_data.t_mall_specialty</code> table reference
     */
    public TMallSpecialty(String alias) {
        this(alias, T_MALL_SPECIALTY);
    }

    private TMallSpecialty(String alias, Table<TMallSpecialtyRecord> aliased) {
        this(alias, aliased, null);
    }

    private TMallSpecialty(String alias, Table<TMallSpecialtyRecord> aliased, Field<?>[] parameters) {
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
    public Identity<TMallSpecialtyRecord, Integer> getIdentity() {
        return Keys.IDENTITY_T_MALL_SPECIALTY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<TMallSpecialtyRecord> getPrimaryKey() {
        return Keys.KEY_T_MALL_SPECIALTY_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<TMallSpecialtyRecord>> getKeys() {
        return Arrays.<UniqueKey<TMallSpecialtyRecord>>asList(Keys.KEY_T_MALL_SPECIALTY_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TMallSpecialty as(String alias) {
        return new TMallSpecialty(alias, this);
    }

    /**
     * Rename this table
     */
    public TMallSpecialty rename(String name) {
        return new TMallSpecialty(name, null);
    }
}
