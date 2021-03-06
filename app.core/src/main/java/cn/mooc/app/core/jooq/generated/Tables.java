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

import javax.annotation.Generated;


/**
 * Convenience access to all tables in appcenter
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.8.4"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Tables {

    /**
     * The table <code>appcenter.t_sys_config</code>.
     */
    public static final TSysConfig T_SYS_CONFIG = cn.mooc.app.core.jooq.generated.tables.TSysConfig.T_SYS_CONFIG;

    /**
     * The table <code>appcenter.t_sys_menu</code>.
     */
    public static final TSysMenu T_SYS_MENU = cn.mooc.app.core.jooq.generated.tables.TSysMenu.T_SYS_MENU;

    /**
     * The table <code>appcenter.t_sys_menu_perms</code>.
     */
    public static final TSysMenuPerms T_SYS_MENU_PERMS = cn.mooc.app.core.jooq.generated.tables.TSysMenuPerms.T_SYS_MENU_PERMS;

    /**
     * 组织表
     */
    public static final TSysOrg T_SYS_ORG = cn.mooc.app.core.jooq.generated.tables.TSysOrg.T_SYS_ORG;

    /**
     * Perms权限点
     */
    public static final TSysPermission T_SYS_PERMISSION = cn.mooc.app.core.jooq.generated.tables.TSysPermission.T_SYS_PERMISSION;

    /**
     * The table <code>appcenter.t_sys_role</code>.
     */
    public static final TSysRole T_SYS_ROLE = cn.mooc.app.core.jooq.generated.tables.TSysRole.T_SYS_ROLE;

    /**
     * The table <code>appcenter.t_sys_role_menu</code>.
     */
    public static final TSysRoleMenu T_SYS_ROLE_MENU = cn.mooc.app.core.jooq.generated.tables.TSysRoleMenu.T_SYS_ROLE_MENU;

    /**
     * 角色与Perms
     */
    public static final TSysRolePerms T_SYS_ROLE_PERMS = cn.mooc.app.core.jooq.generated.tables.TSysRolePerms.T_SYS_ROLE_PERMS;

    /**
     * The table <code>appcenter.t_sys_user</code>.
     */
    public static final TSysUser T_SYS_USER = cn.mooc.app.core.jooq.generated.tables.TSysUser.T_SYS_USER;

    /**
     * 用户与Perms
     */
    public static final TSysUserPerms T_SYS_USER_PERMS = cn.mooc.app.core.jooq.generated.tables.TSysUserPerms.T_SYS_USER_PERMS;

    /**
     * The table <code>appcenter.t_sys_user_role</code>.
     */
    public static final TSysUserRole T_SYS_USER_ROLE = cn.mooc.app.core.jooq.generated.tables.TSysUserRole.T_SYS_USER_ROLE;

    /**
     * The table <code>appcenter.t_sys_user_vcoin</code>.
     */
    public static final TSysUserVcoin T_SYS_USER_VCOIN = cn.mooc.app.core.jooq.generated.tables.TSysUserVcoin.T_SYS_USER_VCOIN;
}
