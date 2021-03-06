/**
 * This class is generated by jOOQ
 */
package cn.mooc.app.core.jooq.generated.tables;


import cn.mooc.app.core.jooq.generated.Appcenter;
import cn.mooc.app.core.jooq.generated.Keys;
import cn.mooc.app.core.jooq.generated.tables.records.TSysUserRecord;

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
public class TSysUser extends TableImpl<TSysUserRecord> {

    private static final long serialVersionUID = -785224692;

    /**
     * The reference instance of <code>appcenter.t_sys_user</code>
     */
    public static final TSysUser T_SYS_USER = new TSysUser();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<TSysUserRecord> getRecordType() {
        return TSysUserRecord.class;
    }

    /**
     * The column <code>appcenter.t_sys_user.id</code>.
     */
    public final TableField<TSysUserRecord, Long> ID = createField("id", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>appcenter.t_sys_user.userName</code>.
     */
    public final TableField<TSysUserRecord, String> USERNAME = createField("userName", org.jooq.impl.SQLDataType.VARCHAR.length(128).nullable(false), this, "");

    /**
     * The column <code>appcenter.t_sys_user.passWord</code>.
     */
    public final TableField<TSysUserRecord, String> PASSWORD = createField("passWord", org.jooq.impl.SQLDataType.VARCHAR.length(128).nullable(false), this, "");

    /**
     * The column <code>appcenter.t_sys_user.pwdSalt</code>.
     */
    public final TableField<TSysUserRecord, String> PWDSALT = createField("pwdSalt", org.jooq.impl.SQLDataType.VARCHAR.length(128), this, "");

    /**
     * The column <code>appcenter.t_sys_user.status</code>. 0:禁用,1:启用
     */
    public final TableField<TSysUserRecord, Integer> STATUS = createField("status", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("1", org.jooq.impl.SQLDataType.INTEGER)), this, "0:禁用,1:启用");

    /**
     * The column <code>appcenter.t_sys_user.superUser</code>. 是否为超级用户
     */
    public final TableField<TSysUserRecord, Byte> SUPERUSER = createField("superUser", org.jooq.impl.SQLDataType.TINYINT, this, "是否为超级用户");

    /**
     * The column <code>appcenter.t_sys_user.createTime</code>.
     */
    public final TableField<TSysUserRecord, Timestamp> CREATETIME = createField("createTime", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false), this, "");

    /**
     * The column <code>appcenter.t_sys_user.lastUpdate</code>.
     */
    public final TableField<TSysUserRecord, Timestamp> LASTUPDATE = createField("lastUpdate", org.jooq.impl.SQLDataType.TIMESTAMP, this, "");

    /**
     * Create a <code>appcenter.t_sys_user</code> table reference
     */
    public TSysUser() {
        this("t_sys_user", null);
    }

    /**
     * Create an aliased <code>appcenter.t_sys_user</code> table reference
     */
    public TSysUser(String alias) {
        this(alias, T_SYS_USER);
    }

    private TSysUser(String alias, Table<TSysUserRecord> aliased) {
        this(alias, aliased, null);
    }

    private TSysUser(String alias, Table<TSysUserRecord> aliased, Field<?>[] parameters) {
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
    public Identity<TSysUserRecord, Long> getIdentity() {
        return Keys.IDENTITY_T_SYS_USER;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<TSysUserRecord> getPrimaryKey() {
        return Keys.KEY_T_SYS_USER_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<TSysUserRecord>> getKeys() {
        return Arrays.<UniqueKey<TSysUserRecord>>asList(Keys.KEY_T_SYS_USER_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TSysUser as(String alias) {
        return new TSysUser(alias, this);
    }

    /**
     * Rename this table
     */
    public TSysUser rename(String name) {
        return new TSysUser(name, null);
    }
}
