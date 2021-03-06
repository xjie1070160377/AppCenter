/**
 * This class is generated by jOOQ
 */
package cn.mooc.jooq.demo.generated.tables;


import cn.mooc.jooq.demo.generated.AppcenterMallData;
import cn.mooc.jooq.demo.generated.Keys;
import cn.mooc.jooq.demo.generated.tables.records.TMallInvoicetitleRecord;

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
public class TMallInvoicetitle extends TableImpl<TMallInvoicetitleRecord> {

    private static final long serialVersionUID = -1611983079;

    /**
     * The reference instance of <code>appcenter_mall_data.t_mall_invoicetitle</code>
     */
    public static final TMallInvoicetitle T_MALL_INVOICETITLE = new TMallInvoicetitle();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<TMallInvoicetitleRecord> getRecordType() {
        return TMallInvoicetitleRecord.class;
    }

    /**
     * The column <code>appcenter_mall_data.t_mall_invoicetitle.Id</code>.
     */
    public final TableField<TMallInvoicetitleRecord, Long> ID = createField("Id", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>appcenter_mall_data.t_mall_invoicetitle.UserId</code>. 用户ID
     */
    public final TableField<TMallInvoicetitleRecord, Long> USERID = createField("UserId", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "用户ID");

    /**
     * The column <code>appcenter_mall_data.t_mall_invoicetitle.Name</code>. 抬头名称
     */
    public final TableField<TMallInvoicetitleRecord, String> NAME = createField("Name", org.jooq.impl.SQLDataType.VARCHAR.length(200), this, "抬头名称");

    /**
     * The column <code>appcenter_mall_data.t_mall_invoicetitle.IsDefault</code>. 是否默认
     */
    public final TableField<TMallInvoicetitleRecord, Byte> ISDEFAULT = createField("IsDefault", org.jooq.impl.SQLDataType.TINYINT.nullable(false), this, "是否默认");

    /**
     * Create a <code>appcenter_mall_data.t_mall_invoicetitle</code> table reference
     */
    public TMallInvoicetitle() {
        this("t_mall_invoicetitle", null);
    }

    /**
     * Create an aliased <code>appcenter_mall_data.t_mall_invoicetitle</code> table reference
     */
    public TMallInvoicetitle(String alias) {
        this(alias, T_MALL_INVOICETITLE);
    }

    private TMallInvoicetitle(String alias, Table<TMallInvoicetitleRecord> aliased) {
        this(alias, aliased, null);
    }

    private TMallInvoicetitle(String alias, Table<TMallInvoicetitleRecord> aliased, Field<?>[] parameters) {
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
    public Identity<TMallInvoicetitleRecord, Long> getIdentity() {
        return Keys.IDENTITY_T_MALL_INVOICETITLE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<TMallInvoicetitleRecord> getPrimaryKey() {
        return Keys.KEY_T_MALL_INVOICETITLE_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<TMallInvoicetitleRecord>> getKeys() {
        return Arrays.<UniqueKey<TMallInvoicetitleRecord>>asList(Keys.KEY_T_MALL_INVOICETITLE_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TMallInvoicetitle as(String alias) {
        return new TMallInvoicetitle(alias, this);
    }

    /**
     * Rename this table
     */
    public TMallInvoicetitle rename(String name) {
        return new TMallInvoicetitle(name, null);
    }
}
