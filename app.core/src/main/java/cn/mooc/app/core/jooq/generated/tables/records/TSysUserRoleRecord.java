/**
 * This class is generated by jOOQ
 */
package cn.mooc.app.core.jooq.generated.tables.records;


import cn.mooc.app.core.jooq.generated.tables.TSysUserRole;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record3;
import org.jooq.Row3;
import org.jooq.impl.UpdatableRecordImpl;


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
public class TSysUserRoleRecord extends UpdatableRecordImpl<TSysUserRoleRecord> implements Record3<Long, Long, Long> {

    private static final long serialVersionUID = 1285846778;

    /**
     * Setter for <code>appcenter.t_sys_user_role.id</code>.
     */
    public void setId(Long value) {
        set(0, value);
    }

    /**
     * Getter for <code>appcenter.t_sys_user_role.id</code>.
     */
    public Long getId() {
        return (Long) get(0);
    }

    /**
     * Setter for <code>appcenter.t_sys_user_role.userId</code>. 用户id
     */
    public void setUserid(Long value) {
        set(1, value);
    }

    /**
     * Getter for <code>appcenter.t_sys_user_role.userId</code>. 用户id
     */
    public Long getUserid() {
        return (Long) get(1);
    }

    /**
     * Setter for <code>appcenter.t_sys_user_role.roleId</code>. 角色id
     */
    public void setRoleid(Long value) {
        set(2, value);
    }

    /**
     * Getter for <code>appcenter.t_sys_user_role.roleId</code>. 角色id
     */
    public Long getRoleid() {
        return (Long) get(2);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Record1<Long> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record3 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row3<Long, Long, Long> fieldsRow() {
        return (Row3) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row3<Long, Long, Long> valuesRow() {
        return (Row3) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Long> field1() {
        return TSysUserRole.T_SYS_USER_ROLE.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Long> field2() {
        return TSysUserRole.T_SYS_USER_ROLE.USERID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Long> field3() {
        return TSysUserRole.T_SYS_USER_ROLE.ROLEID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long value1() {
        return getId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long value2() {
        return getUserid();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long value3() {
        return getRoleid();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TSysUserRoleRecord value1(Long value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TSysUserRoleRecord value2(Long value) {
        setUserid(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TSysUserRoleRecord value3(Long value) {
        setRoleid(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TSysUserRoleRecord values(Long value1, Long value2, Long value3) {
        value1(value1);
        value2(value2);
        value3(value3);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached TSysUserRoleRecord
     */
    public TSysUserRoleRecord() {
        super(TSysUserRole.T_SYS_USER_ROLE);
    }

    /**
     * Create a detached, initialised TSysUserRoleRecord
     */
    public TSysUserRoleRecord(Long id, Long userid, Long roleid) {
        super(TSysUserRole.T_SYS_USER_ROLE);

        set(0, id);
        set(1, userid);
        set(2, roleid);
    }
}