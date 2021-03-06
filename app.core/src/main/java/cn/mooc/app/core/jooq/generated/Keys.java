/**
 * This class is generated by jOOQ
 */
package cn.mooc.app.core.jooq.generated;


import cn.mooc.app.core.jooq.generated.tables.TSysConfig;
import cn.mooc.app.core.jooq.generated.tables.TSysMenu;
import cn.mooc.app.core.jooq.generated.tables.TSysMenuPerms;
import cn.mooc.app.core.jooq.generated.tables.TSysOrg;
import cn.mooc.app.core.jooq.generated.tables.TSysPermission;
import cn.mooc.app.core.jooq.generated.tables.TSysRole;
import cn.mooc.app.core.jooq.generated.tables.TSysRoleMenu;
import cn.mooc.app.core.jooq.generated.tables.TSysRolePerms;
import cn.mooc.app.core.jooq.generated.tables.TSysUser;
import cn.mooc.app.core.jooq.generated.tables.TSysUserPerms;
import cn.mooc.app.core.jooq.generated.tables.TSysUserRole;
import cn.mooc.app.core.jooq.generated.tables.TSysUserVcoin;
import cn.mooc.app.core.jooq.generated.tables.records.TSysConfigRecord;
import cn.mooc.app.core.jooq.generated.tables.records.TSysMenuPermsRecord;
import cn.mooc.app.core.jooq.generated.tables.records.TSysMenuRecord;
import cn.mooc.app.core.jooq.generated.tables.records.TSysOrgRecord;
import cn.mooc.app.core.jooq.generated.tables.records.TSysPermissionRecord;
import cn.mooc.app.core.jooq.generated.tables.records.TSysRoleMenuRecord;
import cn.mooc.app.core.jooq.generated.tables.records.TSysRolePermsRecord;
import cn.mooc.app.core.jooq.generated.tables.records.TSysRoleRecord;
import cn.mooc.app.core.jooq.generated.tables.records.TSysUserPermsRecord;
import cn.mooc.app.core.jooq.generated.tables.records.TSysUserRecord;
import cn.mooc.app.core.jooq.generated.tables.records.TSysUserRoleRecord;
import cn.mooc.app.core.jooq.generated.tables.records.TSysUserVcoinRecord;

import javax.annotation.Generated;

import org.jooq.ForeignKey;
import org.jooq.Identity;
import org.jooq.UniqueKey;
import org.jooq.impl.AbstractKeys;


/**
 * A class modelling foreign key relationships between tables of the <code>appcenter</code> 
 * schema
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.8.4"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Keys {

    // -------------------------------------------------------------------------
    // IDENTITY definitions
    // -------------------------------------------------------------------------

    public static final Identity<TSysMenuRecord, Long> IDENTITY_T_SYS_MENU = Identities0.IDENTITY_T_SYS_MENU;
    public static final Identity<TSysMenuPermsRecord, Long> IDENTITY_T_SYS_MENU_PERMS = Identities0.IDENTITY_T_SYS_MENU_PERMS;
    public static final Identity<TSysPermissionRecord, Long> IDENTITY_T_SYS_PERMISSION = Identities0.IDENTITY_T_SYS_PERMISSION;
    public static final Identity<TSysRoleRecord, Long> IDENTITY_T_SYS_ROLE = Identities0.IDENTITY_T_SYS_ROLE;
    public static final Identity<TSysRoleMenuRecord, Long> IDENTITY_T_SYS_ROLE_MENU = Identities0.IDENTITY_T_SYS_ROLE_MENU;
    public static final Identity<TSysRolePermsRecord, Long> IDENTITY_T_SYS_ROLE_PERMS = Identities0.IDENTITY_T_SYS_ROLE_PERMS;
    public static final Identity<TSysUserRecord, Long> IDENTITY_T_SYS_USER = Identities0.IDENTITY_T_SYS_USER;
    public static final Identity<TSysUserPermsRecord, Long> IDENTITY_T_SYS_USER_PERMS = Identities0.IDENTITY_T_SYS_USER_PERMS;
    public static final Identity<TSysUserRoleRecord, Long> IDENTITY_T_SYS_USER_ROLE = Identities0.IDENTITY_T_SYS_USER_ROLE;

    // -------------------------------------------------------------------------
    // UNIQUE and PRIMARY KEY definitions
    // -------------------------------------------------------------------------

    public static final UniqueKey<TSysConfigRecord> KEY_T_SYS_CONFIG_PRIMARY = UniqueKeys0.KEY_T_SYS_CONFIG_PRIMARY;
    public static final UniqueKey<TSysMenuRecord> KEY_T_SYS_MENU_PRIMARY = UniqueKeys0.KEY_T_SYS_MENU_PRIMARY;
    public static final UniqueKey<TSysMenuPermsRecord> KEY_T_SYS_MENU_PERMS_PRIMARY = UniqueKeys0.KEY_T_SYS_MENU_PERMS_PRIMARY;
    public static final UniqueKey<TSysOrgRecord> KEY_T_SYS_ORG_PRIMARY = UniqueKeys0.KEY_T_SYS_ORG_PRIMARY;
    public static final UniqueKey<TSysPermissionRecord> KEY_T_SYS_PERMISSION_PRIMARY = UniqueKeys0.KEY_T_SYS_PERMISSION_PRIMARY;
    public static final UniqueKey<TSysPermissionRecord> KEY_T_SYS_PERMISSION_INDEX_1 = UniqueKeys0.KEY_T_SYS_PERMISSION_INDEX_1;
    public static final UniqueKey<TSysRoleRecord> KEY_T_SYS_ROLE_PRIMARY = UniqueKeys0.KEY_T_SYS_ROLE_PRIMARY;
    public static final UniqueKey<TSysRoleMenuRecord> KEY_T_SYS_ROLE_MENU_PRIMARY = UniqueKeys0.KEY_T_SYS_ROLE_MENU_PRIMARY;
    public static final UniqueKey<TSysRolePermsRecord> KEY_T_SYS_ROLE_PERMS_PRIMARY = UniqueKeys0.KEY_T_SYS_ROLE_PERMS_PRIMARY;
    public static final UniqueKey<TSysUserRecord> KEY_T_SYS_USER_PRIMARY = UniqueKeys0.KEY_T_SYS_USER_PRIMARY;
    public static final UniqueKey<TSysUserPermsRecord> KEY_T_SYS_USER_PERMS_PRIMARY = UniqueKeys0.KEY_T_SYS_USER_PERMS_PRIMARY;
    public static final UniqueKey<TSysUserRoleRecord> KEY_T_SYS_USER_ROLE_PRIMARY = UniqueKeys0.KEY_T_SYS_USER_ROLE_PRIMARY;
    public static final UniqueKey<TSysUserVcoinRecord> KEY_T_SYS_USER_VCOIN_PRIMARY = UniqueKeys0.KEY_T_SYS_USER_VCOIN_PRIMARY;

    // -------------------------------------------------------------------------
    // FOREIGN KEY definitions
    // -------------------------------------------------------------------------

    public static final ForeignKey<TSysOrgRecord, TSysOrgRecord> FK_CMS_ORG_PARENT = ForeignKeys0.FK_CMS_ORG_PARENT;

    // -------------------------------------------------------------------------
    // [#1459] distribute members to avoid static initialisers > 64kb
    // -------------------------------------------------------------------------

    private static class Identities0 extends AbstractKeys {
        public static Identity<TSysMenuRecord, Long> IDENTITY_T_SYS_MENU = createIdentity(TSysMenu.T_SYS_MENU, TSysMenu.T_SYS_MENU.ID);
        public static Identity<TSysMenuPermsRecord, Long> IDENTITY_T_SYS_MENU_PERMS = createIdentity(TSysMenuPerms.T_SYS_MENU_PERMS, TSysMenuPerms.T_SYS_MENU_PERMS.ID);
        public static Identity<TSysPermissionRecord, Long> IDENTITY_T_SYS_PERMISSION = createIdentity(TSysPermission.T_SYS_PERMISSION, TSysPermission.T_SYS_PERMISSION.ID);
        public static Identity<TSysRoleRecord, Long> IDENTITY_T_SYS_ROLE = createIdentity(TSysRole.T_SYS_ROLE, TSysRole.T_SYS_ROLE.ID);
        public static Identity<TSysRoleMenuRecord, Long> IDENTITY_T_SYS_ROLE_MENU = createIdentity(TSysRoleMenu.T_SYS_ROLE_MENU, TSysRoleMenu.T_SYS_ROLE_MENU.ID);
        public static Identity<TSysRolePermsRecord, Long> IDENTITY_T_SYS_ROLE_PERMS = createIdentity(TSysRolePerms.T_SYS_ROLE_PERMS, TSysRolePerms.T_SYS_ROLE_PERMS.ID);
        public static Identity<TSysUserRecord, Long> IDENTITY_T_SYS_USER = createIdentity(TSysUser.T_SYS_USER, TSysUser.T_SYS_USER.ID);
        public static Identity<TSysUserPermsRecord, Long> IDENTITY_T_SYS_USER_PERMS = createIdentity(TSysUserPerms.T_SYS_USER_PERMS, TSysUserPerms.T_SYS_USER_PERMS.ID);
        public static Identity<TSysUserRoleRecord, Long> IDENTITY_T_SYS_USER_ROLE = createIdentity(TSysUserRole.T_SYS_USER_ROLE, TSysUserRole.T_SYS_USER_ROLE.ID);
    }

    private static class UniqueKeys0 extends AbstractKeys {
        public static final UniqueKey<TSysConfigRecord> KEY_T_SYS_CONFIG_PRIMARY = createUniqueKey(TSysConfig.T_SYS_CONFIG, "KEY_t_sys_config_PRIMARY", TSysConfig.T_SYS_CONFIG.KEYNAME);
        public static final UniqueKey<TSysMenuRecord> KEY_T_SYS_MENU_PRIMARY = createUniqueKey(TSysMenu.T_SYS_MENU, "KEY_t_sys_menu_PRIMARY", TSysMenu.T_SYS_MENU.ID);
        public static final UniqueKey<TSysMenuPermsRecord> KEY_T_SYS_MENU_PERMS_PRIMARY = createUniqueKey(TSysMenuPerms.T_SYS_MENU_PERMS, "KEY_t_sys_menu_perms_PRIMARY", TSysMenuPerms.T_SYS_MENU_PERMS.ID);
        public static final UniqueKey<TSysOrgRecord> KEY_T_SYS_ORG_PRIMARY = createUniqueKey(TSysOrg.T_SYS_ORG, "KEY_t_sys_org_PRIMARY", TSysOrg.T_SYS_ORG.ORG_ID);
        public static final UniqueKey<TSysPermissionRecord> KEY_T_SYS_PERMISSION_PRIMARY = createUniqueKey(TSysPermission.T_SYS_PERMISSION, "KEY_t_sys_permission_PRIMARY", TSysPermission.T_SYS_PERMISSION.ID);
        public static final UniqueKey<TSysPermissionRecord> KEY_T_SYS_PERMISSION_INDEX_1 = createUniqueKey(TSysPermission.T_SYS_PERMISSION, "KEY_t_sys_permission_Index_1", TSysPermission.T_SYS_PERMISSION.PERMCODE);
        public static final UniqueKey<TSysRoleRecord> KEY_T_SYS_ROLE_PRIMARY = createUniqueKey(TSysRole.T_SYS_ROLE, "KEY_t_sys_role_PRIMARY", TSysRole.T_SYS_ROLE.ID);
        public static final UniqueKey<TSysRoleMenuRecord> KEY_T_SYS_ROLE_MENU_PRIMARY = createUniqueKey(TSysRoleMenu.T_SYS_ROLE_MENU, "KEY_t_sys_role_menu_PRIMARY", TSysRoleMenu.T_SYS_ROLE_MENU.ID);
        public static final UniqueKey<TSysRolePermsRecord> KEY_T_SYS_ROLE_PERMS_PRIMARY = createUniqueKey(TSysRolePerms.T_SYS_ROLE_PERMS, "KEY_t_sys_role_perms_PRIMARY", TSysRolePerms.T_SYS_ROLE_PERMS.ID);
        public static final UniqueKey<TSysUserRecord> KEY_T_SYS_USER_PRIMARY = createUniqueKey(TSysUser.T_SYS_USER, "KEY_t_sys_user_PRIMARY", TSysUser.T_SYS_USER.ID);
        public static final UniqueKey<TSysUserPermsRecord> KEY_T_SYS_USER_PERMS_PRIMARY = createUniqueKey(TSysUserPerms.T_SYS_USER_PERMS, "KEY_t_sys_user_perms_PRIMARY", TSysUserPerms.T_SYS_USER_PERMS.ID);
        public static final UniqueKey<TSysUserRoleRecord> KEY_T_SYS_USER_ROLE_PRIMARY = createUniqueKey(TSysUserRole.T_SYS_USER_ROLE, "KEY_t_sys_user_role_PRIMARY", TSysUserRole.T_SYS_USER_ROLE.ID);
        public static final UniqueKey<TSysUserVcoinRecord> KEY_T_SYS_USER_VCOIN_PRIMARY = createUniqueKey(TSysUserVcoin.T_SYS_USER_VCOIN, "KEY_t_sys_user_vcoin_PRIMARY", TSysUserVcoin.T_SYS_USER_VCOIN.USERID);
    }

    private static class ForeignKeys0 extends AbstractKeys {
        public static final ForeignKey<TSysOrgRecord, TSysOrgRecord> FK_CMS_ORG_PARENT = createForeignKey(cn.mooc.app.core.jooq.generated.Keys.KEY_T_SYS_ORG_PRIMARY, TSysOrg.T_SYS_ORG, "fk_cms_org_parent", TSysOrg.T_SYS_ORG.PARENT_ID);
    }
}
