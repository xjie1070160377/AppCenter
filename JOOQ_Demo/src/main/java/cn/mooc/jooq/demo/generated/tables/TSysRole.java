/**
 * This class is generated by jOOQ
 */
package cn.mooc.jooq.demo.generated.tables;


import cn.mooc.jooq.demo.generated.AppcenterMallData;
import cn.mooc.jooq.demo.generated.Keys;
import cn.mooc.jooq.demo.generated.tables.records.TSysRoleRecord;

import java.sql.Timestamp;
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
public class TSysRole extends TableImpl<TSysRoleRecord> {

    private static final long serialVersionUID = -575700005;

    /**
     * The reference instance of <code>appcenter_mall_data.t_sys_role</code>
     */
    public static final TSysRole T_SYS_ROLE = new TSysRole();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<TSysRoleRecord> getRecordType() {
        return TSysRoleRecord.class;
    }

    /**
     * The column <code>appcenter_mall_data.t_sys_role.id</code>. 管理角色标识
     */
    public final TableField<TSysRoleRecord, Long> ID = createField("id", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "管理角色标识");

    /**
     * The column <code>appcenter_mall_data.t_sys_role.roleName</code>. 角色名称
     */
    public final TableField<TSysRoleRecord, String> ROLENAME = createField("roleName", org.jooq.impl.SQLDataType.VARCHAR.length(32).nullable(false), this, "角色名称");

    /**
     * The column <code>appcenter_mall_data.t_sys_role.status</code>. 0:禁用,1:启用
     */
    public final TableField<TSysRoleRecord, Integer> STATUS = createField("status", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "0:禁用,1:启用");

    /**
     * The column <code>appcenter_mall_data.t_sys_role.createTime</code>. 角色创建时间
     */
    public final TableField<TSysRoleRecord, Timestamp> CREATETIME = createField("createTime", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false), this, "角色创建时间");

    /**
     * The column <code>appcenter_mall_data.t_sys_role.lastUpdate</code>. 角色最近修改时间
     */
    public final TableField<TSysRoleRecord, Timestamp> LASTUPDATE = createField("lastUpdate", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false), this, "角色最近修改时间");

    /**
     * Create a <code>appcenter_mall_data.t_sys_role</code> table reference
     */
    public TSysRole() {
        this("t_sys_role", null);
    }

    /**
     * Create an aliased <code>appcenter_mall_data.t_sys_role</code> table reference
     */
    public TSysRole(String alias) {
        this(alias, T_SYS_ROLE);
    }

    private TSysRole(String alias, Table<TSysRoleRecord> aliased) {
        this(alias, aliased, null);
    }

    private TSysRole(String alias, Table<TSysRoleRecord> aliased, Field<?>[] parameters) {
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
    public Identity<TSysRoleRecord, Long> getIdentity() {
        return Keys.IDENTITY_T_SYS_ROLE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<TSysRoleRecord> getPrimaryKey() {
        return Keys.KEY_T_SYS_ROLE_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<TSysRoleRecord>> getKeys() {
        return Arrays.<UniqueKey<TSysRoleRecord>>asList(Keys.KEY_T_SYS_ROLE_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TSysRole as(String alias) {
        return new TSysRole(alias, this);
    }

    /**
     * Rename this table
     */
    public TSysRole rename(String name) {
        return new TSysRole(name, null);
    }
}
