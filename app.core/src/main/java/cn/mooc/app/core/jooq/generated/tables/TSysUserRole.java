/**
 * This class is generated by jOOQ
 */
package cn.mooc.app.core.jooq.generated.tables;


import cn.mooc.app.core.jooq.generated.Appcenter;
import cn.mooc.app.core.jooq.generated.Keys;
import cn.mooc.app.core.jooq.generated.tables.records.TSysUserRoleRecord;

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
public class TSysUserRole extends TableImpl<TSysUserRoleRecord> {

    private static final long serialVersionUID = -1840820593;

    /**
     * The reference instance of <code>appcenter.t_sys_user_role</code>
     */
    public static final TSysUserRole T_SYS_USER_ROLE = new TSysUserRole();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<TSysUserRoleRecord> getRecordType() {
        return TSysUserRoleRecord.class;
    }

    /**
     * The column <code>appcenter.t_sys_user_role.id</code>.
     */
    public final TableField<TSysUserRoleRecord, Long> ID = createField("id", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>appcenter.t_sys_user_role.userId</code>. 用户id
     */
    public final TableField<TSysUserRoleRecord, Long> USERID = createField("userId", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "用户id");

    /**
     * The column <code>appcenter.t_sys_user_role.roleId</code>. 角色id
     */
    public final TableField<TSysUserRoleRecord, Long> ROLEID = createField("roleId", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "角色id");

    /**
     * Create a <code>appcenter.t_sys_user_role</code> table reference
     */
    public TSysUserRole() {
        this("t_sys_user_role", null);
    }

    /**
     * Create an aliased <code>appcenter.t_sys_user_role</code> table reference
     */
    public TSysUserRole(String alias) {
        this(alias, T_SYS_USER_ROLE);
    }

    private TSysUserRole(String alias, Table<TSysUserRoleRecord> aliased) {
        this(alias, aliased, null);
    }

    private TSysUserRole(String alias, Table<TSysUserRoleRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Schema getSchema() {
        return Appcenter.APPCENTER;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<TSysUserRoleRecord, Long> getIdentity() {
        return Keys.IDENTITY_T_SYS_USER_ROLE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<TSysUserRoleRecord> getPrimaryKey() {
        return Keys.KEY_T_SYS_USER_ROLE_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<TSysUserRoleRecord>> getKeys() {
        return Arrays.<UniqueKey<TSysUserRoleRecord>>asList(Keys.KEY_T_SYS_USER_ROLE_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TSysUserRole as(String alias) {
        return new TSysUserRole(alias, this);
    }

    /**
     * Rename this table
     */
    public TSysUserRole rename(String name) {
        return new TSysUserRole(name, null);
    }
}