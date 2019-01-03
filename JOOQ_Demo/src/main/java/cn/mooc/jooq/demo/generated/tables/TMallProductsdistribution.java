/**
 * This class is generated by jOOQ
 */
package cn.mooc.jooq.demo.generated.tables;


import cn.mooc.jooq.demo.generated.AppcenterMallData;
import cn.mooc.jooq.demo.generated.Keys;
import cn.mooc.jooq.demo.generated.tables.records.TMallProductsdistributionRecord;

import java.sql.Timestamp;
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
public class TMallProductsdistribution extends TableImpl<TMallProductsdistributionRecord> {

    private static final long serialVersionUID = 364903193;

    /**
     * The reference instance of <code>appcenter_mall_data.t_mall_productsdistribution</code>
     */
    public static final TMallProductsdistribution T_MALL_PRODUCTSDISTRIBUTION = new TMallProductsdistribution();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<TMallProductsdistributionRecord> getRecordType() {
        return TMallProductsdistributionRecord.class;
    }

    /**
     * The column <code>appcenter_mall_data.t_mall_productsdistribution.Id</code>.
     */
    public final TableField<TMallProductsdistributionRecord, Long> ID = createField("Id", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>appcenter_mall_data.t_mall_productsdistribution.DistibutionProductId</code>. 分销商品编号
     */
    public final TableField<TMallProductsdistributionRecord, Long> DISTIBUTIONPRODUCTID = createField("DistibutionProductId", org.jooq.impl.SQLDataType.BIGINT, this, "分销商品编号");

    /**
     * The column <code>appcenter_mall_data.t_mall_productsdistribution.ProxyProductId</code>. 代理商品编号
     */
    public final TableField<TMallProductsdistributionRecord, Long> PROXYPRODUCTID = createField("ProxyProductId", org.jooq.impl.SQLDataType.BIGINT, this, "代理商品编号");

    /**
     * The column <code>appcenter_mall_data.t_mall_productsdistribution.DistributionSeqNo</code>. 分销调价序号
     */
    public final TableField<TMallProductsdistributionRecord, Integer> DISTRIBUTIONSEQNO = createField("DistributionSeqNo", org.jooq.impl.SQLDataType.INTEGER, this, "分销调价序号");

    /**
     * The column <code>appcenter_mall_data.t_mall_productsdistribution.ProxyTime</code>. 代理时间
     */
    public final TableField<TMallProductsdistributionRecord, Timestamp> PROXYTIME = createField("ProxyTime", org.jooq.impl.SQLDataType.TIMESTAMP, this, "代理时间");

    /**
     * The column <code>appcenter_mall_data.t_mall_productsdistribution.ProxyShopId</code>. 代理商家编号
     */
    public final TableField<TMallProductsdistributionRecord, Long> PROXYSHOPID = createField("ProxyShopId", org.jooq.impl.SQLDataType.BIGINT, this, "代理商家编号");

    /**
     * The column <code>appcenter_mall_data.t_mall_productsdistribution.ShopName</code>. 代理商家
     */
    public final TableField<TMallProductsdistributionRecord, String> SHOPNAME = createField("ShopName", org.jooq.impl.SQLDataType.VARCHAR.length(100), this, "代理商家");

    /**
     * The column <code>appcenter_mall_data.t_mall_productsdistribution.DistributionShopName</code>. 供货商家
     */
    public final TableField<TMallProductsdistributionRecord, String> DISTRIBUTIONSHOPNAME = createField("DistributionShopName", org.jooq.impl.SQLDataType.VARCHAR.length(100), this, "供货商家");

    /**
     * The column <code>appcenter_mall_data.t_mall_productsdistribution.SaleOffType</code>. 供货商下架类型
     */
    public final TableField<TMallProductsdistributionRecord, Integer> SALEOFFTYPE = createField("SaleOffType", org.jooq.impl.SQLDataType.INTEGER, this, "供货商下架类型");

    /**
     * The column <code>appcenter_mall_data.t_mall_productsdistribution.applyType</code>. 请求类型(-1:作废,1:申请,2:审核)
     */
    public final TableField<TMallProductsdistributionRecord, Integer> APPLYTYPE = createField("applyType", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "请求类型(-1:作废,1:申请,2:审核)");

    /**
     * Create a <code>appcenter_mall_data.t_mall_productsdistribution</code> table reference
     */
    public TMallProductsdistribution() {
        this("t_mall_productsdistribution", null);
    }

    /**
     * Create an aliased <code>appcenter_mall_data.t_mall_productsdistribution</code> table reference
     */
    public TMallProductsdistribution(String alias) {
        this(alias, T_MALL_PRODUCTSDISTRIBUTION);
    }

    private TMallProductsdistribution(String alias, Table<TMallProductsdistributionRecord> aliased) {
        this(alias, aliased, null);
    }

    private TMallProductsdistribution(String alias, Table<TMallProductsdistributionRecord> aliased, Field<?>[] parameters) {
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
    public Identity<TMallProductsdistributionRecord, Long> getIdentity() {
        return Keys.IDENTITY_T_MALL_PRODUCTSDISTRIBUTION;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<TMallProductsdistributionRecord> getPrimaryKey() {
        return Keys.KEY_T_MALL_PRODUCTSDISTRIBUTION_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<TMallProductsdistributionRecord>> getKeys() {
        return Arrays.<UniqueKey<TMallProductsdistributionRecord>>asList(Keys.KEY_T_MALL_PRODUCTSDISTRIBUTION_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ForeignKey<TMallProductsdistributionRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<TMallProductsdistributionRecord, ?>>asList(Keys.T_MALL_PRODUCTSDISTRIBUTION_IBFK_1, Keys.T_MALL_PRODUCTSDISTRIBUTION_IBFK_2, Keys.T_MALL_PRODUCTSDISTRIBUTION_IBFK_3);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TMallProductsdistribution as(String alias) {
        return new TMallProductsdistribution(alias, this);
    }

    /**
     * Rename this table
     */
    public TMallProductsdistribution rename(String name) {
        return new TMallProductsdistribution(name, null);
    }
}
