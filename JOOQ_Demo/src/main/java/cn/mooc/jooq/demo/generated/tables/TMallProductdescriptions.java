/**
 * This class is generated by jOOQ
 */
package cn.mooc.jooq.demo.generated.tables;


import cn.mooc.jooq.demo.generated.AppcenterMallData;
import cn.mooc.jooq.demo.generated.Keys;
import cn.mooc.jooq.demo.generated.tables.records.TMallProductdescriptionsRecord;

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
public class TMallProductdescriptions extends TableImpl<TMallProductdescriptionsRecord> {

    private static final long serialVersionUID = 933979975;

    /**
     * The reference instance of <code>appcenter_mall_data.t_mall_productdescriptions</code>
     */
    public static final TMallProductdescriptions T_MALL_PRODUCTDESCRIPTIONS = new TMallProductdescriptions();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<TMallProductdescriptionsRecord> getRecordType() {
        return TMallProductdescriptionsRecord.class;
    }

    /**
     * The column <code>appcenter_mall_data.t_mall_productdescriptions.Id</code>.
     */
    public final TableField<TMallProductdescriptionsRecord, Long> ID = createField("Id", org.jooq.impl.SQLDataType.BIGINT, this, "");

    /**
     * The column <code>appcenter_mall_data.t_mall_productdescriptions.ProductId</code>. 商品ID
     */
    public final TableField<TMallProductdescriptionsRecord, Long> PRODUCTID = createField("ProductId", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "商品ID");

    /**
     * The column <code>appcenter_mall_data.t_mall_productdescriptions.AuditReason</code>. 审核原因
     */
    public final TableField<TMallProductdescriptionsRecord, String> AUDITREASON = createField("AuditReason", org.jooq.impl.SQLDataType.VARCHAR.length(1000), this, "审核原因");

    /**
     * The column <code>appcenter_mall_data.t_mall_productdescriptions.Description</code>. 详情
     */
    public final TableField<TMallProductdescriptionsRecord, String> DESCRIPTION = createField("Description", org.jooq.impl.SQLDataType.CLOB, this, "详情");

    /**
     * The column <code>appcenter_mall_data.t_mall_productdescriptions.DescriptionPrefixId</code>. 关联版式
     */
    public final TableField<TMallProductdescriptionsRecord, Long> DESCRIPTIONPREFIXID = createField("DescriptionPrefixId", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "关联版式");

    /**
     * The column <code>appcenter_mall_data.t_mall_productdescriptions.DescriptiondSuffixId</code>.
     */
    public final TableField<TMallProductdescriptionsRecord, Long> DESCRIPTIONDSUFFIXID = createField("DescriptiondSuffixId", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>appcenter_mall_data.t_mall_productdescriptions.Meta_Title</code>. SEO
     */
    public final TableField<TMallProductdescriptionsRecord, String> META_TITLE = createField("Meta_Title", org.jooq.impl.SQLDataType.VARCHAR.length(1000), this, "SEO");

    /**
     * The column <code>appcenter_mall_data.t_mall_productdescriptions.Meta_Description</code>.
     */
    public final TableField<TMallProductdescriptionsRecord, String> META_DESCRIPTION = createField("Meta_Description", org.jooq.impl.SQLDataType.VARCHAR.length(1000), this, "");

    /**
     * The column <code>appcenter_mall_data.t_mall_productdescriptions.Meta_Keywords</code>.
     */
    public final TableField<TMallProductdescriptionsRecord, String> META_KEYWORDS = createField("Meta_Keywords", org.jooq.impl.SQLDataType.VARCHAR.length(1000), this, "");

    /**
     * Create a <code>appcenter_mall_data.t_mall_productdescriptions</code> table reference
     */
    public TMallProductdescriptions() {
        this("t_mall_productdescriptions", null);
    }

    /**
     * Create an aliased <code>appcenter_mall_data.t_mall_productdescriptions</code> table reference
     */
    public TMallProductdescriptions(String alias) {
        this(alias, T_MALL_PRODUCTDESCRIPTIONS);
    }

    private TMallProductdescriptions(String alias, Table<TMallProductdescriptionsRecord> aliased) {
        this(alias, aliased, null);
    }

    private TMallProductdescriptions(String alias, Table<TMallProductdescriptionsRecord> aliased, Field<?>[] parameters) {
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
    public UniqueKey<TMallProductdescriptionsRecord> getPrimaryKey() {
        return Keys.KEY_T_MALL_PRODUCTDESCRIPTIONS_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<TMallProductdescriptionsRecord>> getKeys() {
        return Arrays.<UniqueKey<TMallProductdescriptionsRecord>>asList(Keys.KEY_T_MALL_PRODUCTDESCRIPTIONS_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ForeignKey<TMallProductdescriptionsRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<TMallProductdescriptionsRecord, ?>>asList(Keys.T_MALL_PRODUCTDESCRIPTIONS_IBFK_1);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TMallProductdescriptions as(String alias) {
        return new TMallProductdescriptions(alias, this);
    }

    /**
     * Rename this table
     */
    public TMallProductdescriptions rename(String name) {
        return new TMallProductdescriptions(name, null);
    }
}
