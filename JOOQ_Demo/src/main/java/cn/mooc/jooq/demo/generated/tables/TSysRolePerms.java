/**
 * This class is generated by jOOQ
 */
package cn.mooc.jooq.demo.generated.tables;


import cn.mooc.jooq.demo.generated.AppcenterMallData;
import cn.mooc.jooq.demo.generated.Keys;
import cn.mooc.jooq.demo.generated.tables.records.TSysRolePermsRecord;

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
 * 角色与Perms
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.8.4"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class TSysRolePerms extends TableImpl<TSysRolePermsRecord> {

    private static final long serialVersionUID = -1001632581;

    /**
     * The reference instance of <code>appcenter_mall_data.t_sys_role_perms</code>
     */
    public static final TSysRolePerms T_SYS_ROLE_PERMS = new TSysRolePerms();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<TSysRolePermsRecord> getRecordType() {
        return TSysRolePermsRecord.class;
    }

    /**
     * The column <code>appcenter_mall_data.t_sys_role_perms.id</code>.
     */
    public final TableField<TSysRolePermsRecord, Long> ID = createField("id", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>appcenter_mall_data.t_sys_role_perms.roleId</code>. 角色id
     */
    public final TableField<TSysRolePermsRecord, Long> ROLEID = createField("roleId", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "角色id");

    /**
     * The column <code>appcenter_mall_data.t_sys_role_perms.permId</code>. 权限点ID
     */
    public final TableField<TSysRolePermsRecord, Long> PERMID = createField("permId", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "权限点ID");

    /**
     * Create a <code>appcenter_mall_data.t_sys_role_perms</code> table reference
     */
    public TSysRolePerms() {
        this("t_sys_role_perms", null);
    }

    /**
     * Create an aliased <code>appcenter_mall_data.t_sys_role_perms</code> table reference
     */
    public TSysRolePerms(String alias) {
        this(alias, T_SYS_ROLE_PERMS);
    }

    private TSysRolePerms(String alias, Table<TSysRolePermsRecord> aliased) {
        this(alias, aliased, null);
    }

    private TSysRolePerms(String alias, Table<TSysRolePermsRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "角色与Perms");
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
    public Identity<TSysRolePermsRecord, Long> getIdentity() {
        return Keys.IDENTITY_T_SYS_ROLE_PERMS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<TSysRolePermsRecord> getPrimaryKey() {
        return Keys.KEY_T_SYS_ROLE_PERMS_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<TSysRolePermsRecord>> getKeys() {
        return Arrays.<UniqueKey<TSysRolePermsRecord>>asList(Keys.KEY_T_SYS_ROLE_PERMS_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ForeignKey<TSysRolePermsRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<TSysRolePermsRecord, ?>>asList(Keys.FK_REFERENCE_S6, Keys.FK_REFERENCE_S5);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TSysRolePerms as(String alias) {
        return new TSysRolePerms(alias, this);
    }

    /**
     * Rename this table
     */
    public TSysRolePerms rename(String name) {
        return new TSysRolePerms(name, null);
    }
}