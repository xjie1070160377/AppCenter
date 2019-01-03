/**
 * This class is generated by jOOQ
 */
package cn.mooc.jooq.demo.generated.tables;


import cn.mooc.jooq.demo.generated.AppcenterMallData;
import cn.mooc.jooq.demo.generated.Keys;
import cn.mooc.jooq.demo.generated.tables.records.TMallCustomerservicesRecord;

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
public class TMallCustomerservices extends TableImpl<TMallCustomerservicesRecord> {

    private static final long serialVersionUID = -581474850;

    /**
     * The reference instance of <code>appcenter_mall_data.t_mall_customerservices</code>
     */
    public static final TMallCustomerservices T_MALL_CUSTOMERSERVICES = new TMallCustomerservices();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<TMallCustomerservicesRecord> getRecordType() {
        return TMallCustomerservicesRecord.class;
    }

    /**
     * The column <code>appcenter_mall_data.t_mall_customerservices.Id</code>.
     */
    public final TableField<TMallCustomerservicesRecord, Long> ID = createField("Id", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>appcenter_mall_data.t_mall_customerservices.ShopId</code>.
     */
    public final TableField<TMallCustomerservicesRecord, Long> SHOPID = createField("ShopId", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>appcenter_mall_data.t_mall_customerservices.Tool</code>. 工具类型（QQ、旺旺）
     */
    public final TableField<TMallCustomerservicesRecord, Integer> TOOL = createField("Tool", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "工具类型（QQ、旺旺）");

    /**
     * The column <code>appcenter_mall_data.t_mall_customerservices.Type</code>.
     */
    public final TableField<TMallCustomerservicesRecord, Integer> TYPE = createField("Type", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>appcenter_mall_data.t_mall_customerservices.Name</code>. 客服名称
     */
    public final TableField<TMallCustomerservicesRecord, String> NAME = createField("Name", org.jooq.impl.SQLDataType.VARCHAR.length(1000).nullable(false), this, "客服名称");

    /**
     * The column <code>appcenter_mall_data.t_mall_customerservices.AccountCode</code>. 通信账号
     */
    public final TableField<TMallCustomerservicesRecord, String> ACCOUNTCODE = createField("AccountCode", org.jooq.impl.SQLDataType.VARCHAR.length(1000).nullable(false), this, "通信账号");

    /**
     * Create a <code>appcenter_mall_data.t_mall_customerservices</code> table reference
     */
    public TMallCustomerservices() {
        this("t_mall_customerservices", null);
    }

    /**
     * Create an aliased <code>appcenter_mall_data.t_mall_customerservices</code> table reference
     */
    public TMallCustomerservices(String alias) {
        this(alias, T_MALL_CUSTOMERSERVICES);
    }

    private TMallCustomerservices(String alias, Table<TMallCustomerservicesRecord> aliased) {
        this(alias, aliased, null);
    }

    private TMallCustomerservices(String alias, Table<TMallCustomerservicesRecord> aliased, Field<?>[] parameters) {
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
    public Identity<TMallCustomerservicesRecord, Long> getIdentity() {
        return Keys.IDENTITY_T_MALL_CUSTOMERSERVICES;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<TMallCustomerservicesRecord> getPrimaryKey() {
        return Keys.KEY_T_MALL_CUSTOMERSERVICES_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<TMallCustomerservicesRecord>> getKeys() {
        return Arrays.<UniqueKey<TMallCustomerservicesRecord>>asList(Keys.KEY_T_MALL_CUSTOMERSERVICES_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TMallCustomerservices as(String alias) {
        return new TMallCustomerservices(alias, this);
    }

    /**
     * Rename this table
     */
    public TMallCustomerservices rename(String name) {
        return new TMallCustomerservices(name, null);
    }
}
