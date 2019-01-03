/**
 * This class is generated by jOOQ
 */
package cn.mooc.jooq.demo.generated.tables;


import cn.mooc.jooq.demo.generated.AppcenterMallData;
import cn.mooc.jooq.demo.generated.Keys;
import cn.mooc.jooq.demo.generated.tables.records.TMallMemberopenidsRecord;

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
public class TMallMemberopenids extends TableImpl<TMallMemberopenidsRecord> {

    private static final long serialVersionUID = -1436654859;

    /**
     * The reference instance of <code>appcenter_mall_data.t_mall_memberopenids</code>
     */
    public static final TMallMemberopenids T_MALL_MEMBEROPENIDS = new TMallMemberopenids();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<TMallMemberopenidsRecord> getRecordType() {
        return TMallMemberopenidsRecord.class;
    }

    /**
     * The column <code>appcenter_mall_data.t_mall_memberopenids.Id</code>.
     */
    public final TableField<TMallMemberopenidsRecord, Long> ID = createField("Id", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>appcenter_mall_data.t_mall_memberopenids.UserId</code>. 用户ID
     */
    public final TableField<TMallMemberopenidsRecord, Long> USERID = createField("UserId", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "用户ID");

    /**
     * The column <code>appcenter_mall_data.t_mall_memberopenids.OpenId</code>. 微信OpenID
     */
    public final TableField<TMallMemberopenidsRecord, String> OPENID = createField("OpenId", org.jooq.impl.SQLDataType.VARCHAR.length(100).nullable(false), this, "微信OpenID");

    /**
     * The column <code>appcenter_mall_data.t_mall_memberopenids.ServiceProvider</code>. 插件名称（Himall.Plugin.OAuth.WeiXin）
     */
    public final TableField<TMallMemberopenidsRecord, String> SERVICEPROVIDER = createField("ServiceProvider", org.jooq.impl.SQLDataType.VARCHAR.length(40).nullable(false), this, "插件名称（Himall.Plugin.OAuth.WeiXin）");

    /**
     * The column <code>appcenter_mall_data.t_mall_memberopenids.AppIdType</code>.
     */
    public final TableField<TMallMemberopenidsRecord, Integer> APPIDTYPE = createField("AppIdType", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * Create a <code>appcenter_mall_data.t_mall_memberopenids</code> table reference
     */
    public TMallMemberopenids() {
        this("t_mall_memberopenids", null);
    }

    /**
     * Create an aliased <code>appcenter_mall_data.t_mall_memberopenids</code> table reference
     */
    public TMallMemberopenids(String alias) {
        this(alias, T_MALL_MEMBEROPENIDS);
    }

    private TMallMemberopenids(String alias, Table<TMallMemberopenidsRecord> aliased) {
        this(alias, aliased, null);
    }

    private TMallMemberopenids(String alias, Table<TMallMemberopenidsRecord> aliased, Field<?>[] parameters) {
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
    public Identity<TMallMemberopenidsRecord, Long> getIdentity() {
        return Keys.IDENTITY_T_MALL_MEMBEROPENIDS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<TMallMemberopenidsRecord> getPrimaryKey() {
        return Keys.KEY_T_MALL_MEMBEROPENIDS_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<TMallMemberopenidsRecord>> getKeys() {
        return Arrays.<UniqueKey<TMallMemberopenidsRecord>>asList(Keys.KEY_T_MALL_MEMBEROPENIDS_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ForeignKey<TMallMemberopenidsRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<TMallMemberopenidsRecord, ?>>asList(Keys.T_MALL_MEMBEROPENIDS_IBFK_1);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TMallMemberopenids as(String alias) {
        return new TMallMemberopenids(alias, this);
    }

    /**
     * Rename this table
     */
    public TMallMemberopenids rename(String name) {
        return new TMallMemberopenids(name, null);
    }
}
