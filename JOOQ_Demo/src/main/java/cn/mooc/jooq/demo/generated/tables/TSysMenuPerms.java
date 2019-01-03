/**
 * This class is generated by jOOQ
 */
package cn.mooc.jooq.demo.generated.tables;


import cn.mooc.jooq.demo.generated.AppcenterMallData;
import cn.mooc.jooq.demo.generated.Keys;
import cn.mooc.jooq.demo.generated.tables.records.TSysMenuPermsRecord;

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
public class TSysMenuPerms extends TableImpl<TSysMenuPermsRecord> {

    private static final long serialVersionUID = -420955095;

    /**
     * The reference instance of <code>appcenter_mall_data.t_sys_menu_perms</code>
     */
    public static final TSysMenuPerms T_SYS_MENU_PERMS = new TSysMenuPerms();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<TSysMenuPermsRecord> getRecordType() {
        return TSysMenuPermsRecord.class;
    }

    /**
     * The column <code>appcenter_mall_data.t_sys_menu_perms.id</code>.
     */
    public final TableField<TSysMenuPermsRecord, Long> ID = createField("id", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>appcenter_mall_data.t_sys_menu_perms.menuId</code>. 菜单id
     */
    public final TableField<TSysMenuPermsRecord, Long> MENUID = createField("menuId", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "菜单id");

    /**
     * The column <code>appcenter_mall_data.t_sys_menu_perms.permId</code>. 权限点ID
     */
    public final TableField<TSysMenuPermsRecord, Long> PERMID = createField("permId", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "权限点ID");

    /**
     * Create a <code>appcenter_mall_data.t_sys_menu_perms</code> table reference
     */
    public TSysMenuPerms() {
        this("t_sys_menu_perms", null);
    }

    /**
     * Create an aliased <code>appcenter_mall_data.t_sys_menu_perms</code> table reference
     */
    public TSysMenuPerms(String alias) {
        this(alias, T_SYS_MENU_PERMS);
    }

    private TSysMenuPerms(String alias, Table<TSysMenuPermsRecord> aliased) {
        this(alias, aliased, null);
    }

    private TSysMenuPerms(String alias, Table<TSysMenuPermsRecord> aliased, Field<?>[] parameters) {
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
    public Identity<TSysMenuPermsRecord, Long> getIdentity() {
        return Keys.IDENTITY_T_SYS_MENU_PERMS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<TSysMenuPermsRecord> getPrimaryKey() {
        return Keys.KEY_T_SYS_MENU_PERMS_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<TSysMenuPermsRecord>> getKeys() {
        return Arrays.<UniqueKey<TSysMenuPermsRecord>>asList(Keys.KEY_T_SYS_MENU_PERMS_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ForeignKey<TSysMenuPermsRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<TSysMenuPermsRecord, ?>>asList(Keys.FK_REFERENCE_S7, Keys.FK_REFERENCE_S8);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TSysMenuPerms as(String alias) {
        return new TSysMenuPerms(alias, this);
    }

    /**
     * Rename this table
     */
    public TSysMenuPerms rename(String name) {
        return new TSysMenuPerms(name, null);
    }
}
