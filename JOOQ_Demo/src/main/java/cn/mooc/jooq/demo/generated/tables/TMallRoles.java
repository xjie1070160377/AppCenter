/**
 * This class is generated by jOOQ
 */
package cn.mooc.jooq.demo.generated.tables;


import cn.mooc.jooq.demo.generated.AppcenterMallData;
import cn.mooc.jooq.demo.generated.Keys;
import cn.mooc.jooq.demo.generated.tables.records.TMallRolesRecord;

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
public class TMallRoles extends TableImpl<TMallRolesRecord> {

    private static final long serialVersionUID = 1465127797;

    /**
     * The reference instance of <code>appcenter_mall_data.t_mall_roles</code>
     */
    public static final TMallRoles T_MALL_ROLES = new TMallRoles();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<TMallRolesRecord> getRecordType() {
        return TMallRolesRecord.class;
    }

    /**
     * The column <code>appcenter_mall_data.t_mall_roles.Id</code>.
     */
    public final TableField<TMallRolesRecord, Long> ID = createField("Id", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>appcenter_mall_data.t_mall_roles.ShopId</code>. 店铺ID
     */
    public final TableField<TMallRolesRecord, Long> SHOPID = createField("ShopId", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "店铺ID");

    /**
     * The column <code>appcenter_mall_data.t_mall_roles.RoleName</code>. 角色名称
     */
    public final TableField<TMallRolesRecord, String> ROLENAME = createField("RoleName", org.jooq.impl.SQLDataType.VARCHAR.length(100).nullable(false), this, "角色名称");

    /**
     * The column <code>appcenter_mall_data.t_mall_roles.Description</code>. 说明
     */
    public final TableField<TMallRolesRecord, String> DESCRIPTION = createField("Description", org.jooq.impl.SQLDataType.VARCHAR.length(1000), this, "说明");

    /**
     * Create a <code>appcenter_mall_data.t_mall_roles</code> table reference
     */
    public TMallRoles() {
        this("t_mall_roles", null);
    }

    /**
     * Create an aliased <code>appcenter_mall_data.t_mall_roles</code> table reference
     */
    public TMallRoles(String alias) {
        this(alias, T_MALL_ROLES);
    }

    private TMallRoles(String alias, Table<TMallRolesRecord> aliased) {
        this(alias, aliased, null);
    }

    private TMallRoles(String alias, Table<TMallRolesRecord> aliased, Field<?>[] parameters) {
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
    public Identity<TMallRolesRecord, Long> getIdentity() {
        return Keys.IDENTITY_T_MALL_ROLES;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<TMallRolesRecord> getPrimaryKey() {
        return Keys.KEY_T_MALL_ROLES_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<TMallRolesRecord>> getKeys() {
        return Arrays.<UniqueKey<TMallRolesRecord>>asList(Keys.KEY_T_MALL_ROLES_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TMallRoles as(String alias) {
        return new TMallRoles(alias, this);
    }

    /**
     * Rename this table
     */
    public TMallRoles rename(String name) {
        return new TMallRoles(name, null);
    }
}