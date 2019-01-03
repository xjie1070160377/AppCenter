/**
 * This class is generated by jOOQ
 */
package cn.mooc.app.core.jooq.generated.tables.records;


import cn.mooc.app.core.jooq.generated.tables.TSysMenuPerms;

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
public class TSysMenuPermsRecord extends UpdatableRecordImpl<TSysMenuPermsRecord> implements Record3<Long, Long, Long> {

    private static final long serialVersionUID = -319897363;

    /**
     * Setter for <code>appcenter.t_sys_menu_perms.id</code>.
     */
    public void setId(Long value) {
        set(0, value);
    }

    /**
     * Getter for <code>appcenter.t_sys_menu_perms.id</code>.
     */
    public Long getId() {
        return (Long) get(0);
    }

    /**
     * Setter for <code>appcenter.t_sys_menu_perms.menuId</code>. 菜单id
     */
    public void setMenuid(Long value) {
        set(1, value);
    }

    /**
     * Getter for <code>appcenter.t_sys_menu_perms.menuId</code>. 菜单id
     */
    public Long getMenuid() {
        return (Long) get(1);
    }

    /**
     * Setter for <code>appcenter.t_sys_menu_perms.permId</code>. 权限点ID
     */
    public void setPermid(Long value) {
        set(2, value);
    }

    /**
     * Getter for <code>appcenter.t_sys_menu_perms.permId</code>. 权限点ID
     */
    public Long getPermid() {
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
        return TSysMenuPerms.T_SYS_MENU_PERMS.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Long> field2() {
        return TSysMenuPerms.T_SYS_MENU_PERMS.MENUID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Long> field3() {
        return TSysMenuPerms.T_SYS_MENU_PERMS.PERMID;
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
        return getMenuid();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long value3() {
        return getPermid();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TSysMenuPermsRecord value1(Long value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TSysMenuPermsRecord value2(Long value) {
        setMenuid(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TSysMenuPermsRecord value3(Long value) {
        setPermid(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TSysMenuPermsRecord values(Long value1, Long value2, Long value3) {
        value1(value1);
        value2(value2);
        value3(value3);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached TSysMenuPermsRecord
     */
    public TSysMenuPermsRecord() {
        super(TSysMenuPerms.T_SYS_MENU_PERMS);
    }

    /**
     * Create a detached, initialised TSysMenuPermsRecord
     */
    public TSysMenuPermsRecord(Long id, Long menuid, Long permid) {
        super(TSysMenuPerms.T_SYS_MENU_PERMS);

        set(0, id);
        set(1, menuid);
        set(2, permid);
    }
}
