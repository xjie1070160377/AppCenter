/**
 * This class is generated by jOOQ
 */
package cn.mooc.jooq.demo.generated.tables;


import cn.mooc.jooq.demo.generated.AppcenterMallData;
import cn.mooc.jooq.demo.generated.Keys;
import cn.mooc.jooq.demo.generated.tables.records.TMallFreighttemplateRecord;

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
public class TMallFreighttemplate extends TableImpl<TMallFreighttemplateRecord> {

    private static final long serialVersionUID = 1354892863;

    /**
     * The reference instance of <code>appcenter_mall_data.t_mall_freighttemplate</code>
     */
    public static final TMallFreighttemplate T_MALL_FREIGHTTEMPLATE = new TMallFreighttemplate();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<TMallFreighttemplateRecord> getRecordType() {
        return TMallFreighttemplateRecord.class;
    }

    /**
     * The column <code>appcenter_mall_data.t_mall_freighttemplate.Id</code>.
     */
    public final TableField<TMallFreighttemplateRecord, Long> ID = createField("Id", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>appcenter_mall_data.t_mall_freighttemplate.Name</code>. 运费模板名称
     */
    public final TableField<TMallFreighttemplateRecord, String> NAME = createField("Name", org.jooq.impl.SQLDataType.VARCHAR.length(100), this, "运费模板名称");

    /**
     * The column <code>appcenter_mall_data.t_mall_freighttemplate.SourceAddress</code>. 宝贝发货地
     */
    public final TableField<TMallFreighttemplateRecord, Integer> SOURCEADDRESS = createField("SourceAddress", org.jooq.impl.SQLDataType.INTEGER, this, "宝贝发货地");

    /**
     * The column <code>appcenter_mall_data.t_mall_freighttemplate.SendTime</code>. 发送时间
     */
    public final TableField<TMallFreighttemplateRecord, String> SENDTIME = createField("SendTime", org.jooq.impl.SQLDataType.VARCHAR.length(100), this, "发送时间");

    /**
     * The column <code>appcenter_mall_data.t_mall_freighttemplate.IsFree</code>. 是否商家负责运费
     */
    public final TableField<TMallFreighttemplateRecord, Integer> ISFREE = createField("IsFree", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "是否商家负责运费");

    /**
     * The column <code>appcenter_mall_data.t_mall_freighttemplate.ValuationMethod</code>. 定价方法(按体积、重量计算）
     */
    public final TableField<TMallFreighttemplateRecord, Integer> VALUATIONMETHOD = createField("ValuationMethod", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "定价方法(按体积、重量计算）");

    /**
     * The column <code>appcenter_mall_data.t_mall_freighttemplate.ShippingMethod</code>. 运送类型（物流、快递）
     */
    public final TableField<TMallFreighttemplateRecord, Integer> SHIPPINGMETHOD = createField("ShippingMethod", org.jooq.impl.SQLDataType.INTEGER, this, "运送类型（物流、快递）");

    /**
     * The column <code>appcenter_mall_data.t_mall_freighttemplate.ShopID</code>. 店铺ID
     */
    public final TableField<TMallFreighttemplateRecord, Long> SHOPID = createField("ShopID", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "店铺ID");

    /**
     * Create a <code>appcenter_mall_data.t_mall_freighttemplate</code> table reference
     */
    public TMallFreighttemplate() {
        this("t_mall_freighttemplate", null);
    }

    /**
     * Create an aliased <code>appcenter_mall_data.t_mall_freighttemplate</code> table reference
     */
    public TMallFreighttemplate(String alias) {
        this(alias, T_MALL_FREIGHTTEMPLATE);
    }

    private TMallFreighttemplate(String alias, Table<TMallFreighttemplateRecord> aliased) {
        this(alias, aliased, null);
    }

    private TMallFreighttemplate(String alias, Table<TMallFreighttemplateRecord> aliased, Field<?>[] parameters) {
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
    public Identity<TMallFreighttemplateRecord, Long> getIdentity() {
        return Keys.IDENTITY_T_MALL_FREIGHTTEMPLATE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<TMallFreighttemplateRecord> getPrimaryKey() {
        return Keys.KEY_T_MALL_FREIGHTTEMPLATE_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<TMallFreighttemplateRecord>> getKeys() {
        return Arrays.<UniqueKey<TMallFreighttemplateRecord>>asList(Keys.KEY_T_MALL_FREIGHTTEMPLATE_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TMallFreighttemplate as(String alias) {
        return new TMallFreighttemplate(alias, this);
    }

    /**
     * Rename this table
     */
    public TMallFreighttemplate rename(String name) {
        return new TMallFreighttemplate(name, null);
    }
}