/**
 * This class is generated by jOOQ
 */
package cn.mooc.app.core.jooq.generated.tables.records;


import cn.mooc.app.core.jooq.generated.tables.TSysUser;

import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record8;
import org.jooq.Row8;
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
public class TSysUserRecord extends UpdatableRecordImpl<TSysUserRecord> implements Record8<Long, String, String, String, Integer, Byte, Timestamp, Timestamp> {

    private static final long serialVersionUID = -612146794;

    /**
     * Setter for <code>appcenter.t_sys_user.id</code>.
     */
    public void setId(Long value) {
        set(0, value);
    }

    /**
     * Getter for <code>appcenter.t_sys_user.id</code>.
     */
    public Long getId() {
        return (Long) get(0);
    }

    /**
     * Setter for <code>appcenter.t_sys_user.userName</code>.
     */
    public void setUsername(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>appcenter.t_sys_user.userName</code>.
     */
    public String getUsername() {
        return (String) get(1);
    }

    /**
     * Setter for <code>appcenter.t_sys_user.passWord</code>.
     */
    public void setPassword(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>appcenter.t_sys_user.passWord</code>.
     */
    public String getPassword() {
        return (String) get(2);
    }

    /**
     * Setter for <code>appcenter.t_sys_user.pwdSalt</code>.
     */
    public void setPwdsalt(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>appcenter.t_sys_user.pwdSalt</code>.
     */
    public String getPwdsalt() {
        return (String) get(3);
    }

    /**
     * Setter for <code>appcenter.t_sys_user.status</code>. 0:禁用,1:启用
     */
    public void setStatus(Integer value) {
        set(4, value);
    }

    /**
     * Getter for <code>appcenter.t_sys_user.status</code>. 0:禁用,1:启用
     */
    public Integer getStatus() {
        return (Integer) get(4);
    }

    /**
     * Setter for <code>appcenter.t_sys_user.superUser</code>. 是否为超级用户
     */
    public void setSuperuser(Byte value) {
        set(5, value);
    }

    /**
     * Getter for <code>appcenter.t_sys_user.superUser</code>. 是否为超级用户
     */
    public Byte getSuperuser() {
        return (Byte) get(5);
    }

    /**
     * Setter for <code>appcenter.t_sys_user.createTime</code>.
     */
    public void setCreatetime(Timestamp value) {
        set(6, value);
    }

    /**
     * Getter for <code>appcenter.t_sys_user.createTime</code>.
     */
    public Timestamp getCreatetime() {
        return (Timestamp) get(6);
    }

    /**
     * Setter for <code>appcenter.t_sys_user.lastUpdate</code>.
     */
    public void setLastupdate(Timestamp value) {
        set(7, value);
    }

    /**
     * Getter for <code>appcenter.t_sys_user.lastUpdate</code>.
     */
    public Timestamp getLastupdate() {
        return (Timestamp) get(7);
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
    // Record8 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row8<Long, String, String, String, Integer, Byte, Timestamp, Timestamp> fieldsRow() {
        return (Row8) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row8<Long, String, String, String, Integer, Byte, Timestamp, Timestamp> valuesRow() {
        return (Row8) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Long> field1() {
        return TSysUser.T_SYS_USER.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field2() {
        return TSysUser.T_SYS_USER.USERNAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field3() {
        return TSysUser.T_SYS_USER.PASSWORD;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field4() {
        return TSysUser.T_SYS_USER.PWDSALT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field5() {
        return TSysUser.T_SYS_USER.STATUS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Byte> field6() {
        return TSysUser.T_SYS_USER.SUPERUSER;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field7() {
        return TSysUser.T_SYS_USER.CREATETIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field8() {
        return TSysUser.T_SYS_USER.LASTUPDATE;
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
    public String value2() {
        return getUsername();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value3() {
        return getPassword();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value4() {
        return getPwdsalt();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value5() {
        return getStatus();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte value6() {
        return getSuperuser();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value7() {
        return getCreatetime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value8() {
        return getLastupdate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TSysUserRecord value1(Long value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TSysUserRecord value2(String value) {
        setUsername(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TSysUserRecord value3(String value) {
        setPassword(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TSysUserRecord value4(String value) {
        setPwdsalt(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TSysUserRecord value5(Integer value) {
        setStatus(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TSysUserRecord value6(Byte value) {
        setSuperuser(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TSysUserRecord value7(Timestamp value) {
        setCreatetime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TSysUserRecord value8(Timestamp value) {
        setLastupdate(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TSysUserRecord values(Long value1, String value2, String value3, String value4, Integer value5, Byte value6, Timestamp value7, Timestamp value8) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        value7(value7);
        value8(value8);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached TSysUserRecord
     */
    public TSysUserRecord() {
        super(TSysUser.T_SYS_USER);
    }

    /**
     * Create a detached, initialised TSysUserRecord
     */
    public TSysUserRecord(Long id, String username, String password, String pwdsalt, Integer status, Byte superuser, Timestamp createtime, Timestamp lastupdate) {
        super(TSysUser.T_SYS_USER);

        set(0, id);
        set(1, username);
        set(2, password);
        set(3, pwdsalt);
        set(4, status);
        set(5, superuser);
        set(6, createtime);
        set(7, lastupdate);
    }
}
