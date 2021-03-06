/**
 * This class is generated by jOOQ
 */
package cn.mooc.jooq.demo.generated.tables;


import cn.mooc.jooq.demo.generated.AppcenterMallData;
import cn.mooc.jooq.demo.generated.Keys;
import cn.mooc.jooq.demo.generated.tables.records.TMallGuestbooktypeRecord;

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
public class TMallGuestbooktype extends TableImpl<TMallGuestbooktypeRecord> {

    private static final long serialVersionUID = 680474247;

    /**
     * The reference instance of <code>appcenter_mall_data.t_mall_guestbooktype</code>
     */
    public static final TMallGuestbooktype T_MALL_GUESTBOOKTYPE = new TMallGuestbooktype();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<TMallGuestbooktypeRecord> getRecordType() {
        return TMallGuestbooktypeRecord.class;
    }

    /**
     * The column <code>appcenter_mall_data.t_mall_guestbooktype.Id</code>.
     */
    public final TableField<TMallGuestbooktypeRecord, Integer> ID = createField("Id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>appcenter_mall_data.t_mall_guestbooktype.Name</code>. 类型名称
     */
    public final TableField<TMallGuestbooktypeRecord, String> NAME = createField("Name", org.jooq.impl.SQLDataType.VARCHAR.length(100).nullable(false), this, "类型名称");

    /**
     * The column <code>appcenter_mall_data.t_mall_guestbooktype.Number</code>. 类型编码
     */
    public final TableField<TMallGuestbooktypeRecord, String> NUMBER = createField("Number", org.jooq.impl.SQLDataType.VARCHAR.length(100).nullable(false), this, "类型编码");

    /**
     * The column <code>appcenter_mall_data.t_mall_guestbooktype.Description</code>. 类型描述
     */
    public final TableField<TMallGuestbooktypeRecord, String> DESCRIPTION = createField("Description", org.jooq.impl.SQLDataType.VARCHAR.length(255).nullable(false), this, "类型描述");

    /**
     * Create a <code>appcenter_mall_data.t_mall_guestbooktype</code> table reference
     */
    public TMallGuestbooktype() {
        this("t_mall_guestbooktype", null);
    }

    /**
     * Create an aliased <code>appcenter_mall_data.t_mall_guestbooktype</code> table reference
     */
    public TMallGuestbooktype(String alias) {
        this(alias, T_MALL_GUESTBOOKTYPE);
    }

    private TMallGuestbooktype(String alias, Table<TMallGuestbooktypeRecord> aliased) {
        this(alias, aliased, null);
    }

    private TMallGuestbooktype(String alias, Table<TMallGuestbooktypeRecord> aliased, Field<?>[] parameters) {
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
    public Identity<TMallGuestbooktypeRecord, Integer> getIdentity() {
        return Keys.IDENTITY_T_MALL_GUESTBOOKTYPE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<TMallGuestbooktypeRecord> getPrimaryKey() {
        return Keys.KEY_T_MALL_GUESTBOOKTYPE_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<TMallGuestbooktypeRecord>> getKeys() {
        return Arrays.<UniqueKey<TMallGuestbooktypeRecord>>asList(Keys.KEY_T_MALL_GUESTBOOKTYPE_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TMallGuestbooktype as(String alias) {
        return new TMallGuestbooktype(alias, this);
    }

    /**
     * Rename this table
     */
    public TMallGuestbooktype rename(String name) {
        return new TMallGuestbooktype(name, null);
    }
}
