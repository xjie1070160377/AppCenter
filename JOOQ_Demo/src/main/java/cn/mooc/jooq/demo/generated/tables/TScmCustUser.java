/**
 * This class is generated by jOOQ
 */
package cn.mooc.jooq.demo.generated.tables;


import cn.mooc.jooq.demo.generated.AppcenterMallData;
import cn.mooc.jooq.demo.generated.Keys;
import cn.mooc.jooq.demo.generated.tables.records.TScmCustUserRecord;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.TableImpl;


/**
 * 客户帐号表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.8.4"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class TScmCustUser extends TableImpl<TScmCustUserRecord> {

    private static final long serialVersionUID = -899804216;

    /**
     * The reference instance of <code>appcenter_mall_data.t_scm_cust_user</code>
     */
    public static final TScmCustUser T_SCM_CUST_USER = new TScmCustUser();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<TScmCustUserRecord> getRecordType() {
        return TScmCustUserRecord.class;
    }

    /**
     * The column <code>appcenter_mall_data.t_scm_cust_user.custId</code>. 客户ID
     */
    public final TableField<TScmCustUserRecord, Integer> CUSTID = createField("custId", org.jooq.impl.SQLDataType.INTEGER, this, "客户ID");

    /**
     * The column <code>appcenter_mall_data.t_scm_cust_user.userId</code>. 用户ID
     */
    public final TableField<TScmCustUserRecord, Integer> USERID = createField("userId", org.jooq.impl.SQLDataType.INTEGER, this, "用户ID");

    /**
     * The column <code>appcenter_mall_data.t_scm_cust_user.status</code>. 状态
     */
    public final TableField<TScmCustUserRecord, Byte> STATUS = createField("status", org.jooq.impl.SQLDataType.TINYINT, this, "状态");

    /**
     * The column <code>appcenter_mall_data.t_scm_cust_user.ID</code>. ID
     */
    public final TableField<TScmCustUserRecord, Integer> ID = createField("ID", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "ID");

    /**
     * Create a <code>appcenter_mall_data.t_scm_cust_user</code> table reference
     */
    public TScmCustUser() {
        this("t_scm_cust_user", null);
    }

    /**
     * Create an aliased <code>appcenter_mall_data.t_scm_cust_user</code> table reference
     */
    public TScmCustUser(String alias) {
        this(alias, T_SCM_CUST_USER);
    }

    private TScmCustUser(String alias, Table<TScmCustUserRecord> aliased) {
        this(alias, aliased, null);
    }

    private TScmCustUser(String alias, Table<TScmCustUserRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "客户帐号表");
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
    public UniqueKey<TScmCustUserRecord> getPrimaryKey() {
        return Keys.KEY_T_SCM_CUST_USER_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<TScmCustUserRecord>> getKeys() {
        return Arrays.<UniqueKey<TScmCustUserRecord>>asList(Keys.KEY_T_SCM_CUST_USER_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ForeignKey<TScmCustUserRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<TScmCustUserRecord, ?>>asList(Keys.T_SCM_CUST_USER_IBFK_1);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TScmCustUser as(String alias) {
        return new TScmCustUser(alias, this);
    }

    /**
     * Rename this table
     */
    public TScmCustUser rename(String name) {
        return new TScmCustUser(name, null);
    }
}
