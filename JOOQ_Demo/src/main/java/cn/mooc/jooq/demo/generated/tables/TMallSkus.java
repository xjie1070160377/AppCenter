/**
 * This class is generated by jOOQ
 */
package cn.mooc.jooq.demo.generated.tables;


import cn.mooc.jooq.demo.generated.AppcenterMallData;
import cn.mooc.jooq.demo.generated.Keys;
import cn.mooc.jooq.demo.generated.tables.records.TMallSkusRecord;

import java.math.BigDecimal;
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
public class TMallSkus extends TableImpl<TMallSkusRecord> {

    private static final long serialVersionUID = 1009993038;

    /**
     * The reference instance of <code>appcenter_mall_data.t_mall_skus</code>
     */
    public static final TMallSkus T_MALL_SKUS = new TMallSkus();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<TMallSkusRecord> getRecordType() {
        return TMallSkusRecord.class;
    }

    /**
     * The column <code>appcenter_mall_data.t_mall_skus.Id</code>. 商品ID_颜色规格ID_颜色规格ID_尺寸规格
     */
    public final TableField<TMallSkusRecord, String> ID = createField("Id", org.jooq.impl.SQLDataType.VARCHAR.length(100).nullable(false), this, "商品ID_颜色规格ID_颜色规格ID_尺寸规格");

    /**
     * The column <code>appcenter_mall_data.t_mall_skus.ProductId</code>. 商品ID
     */
    public final TableField<TMallSkusRecord, Long> PRODUCTID = createField("ProductId", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "商品ID");

    /**
     * The column <code>appcenter_mall_data.t_mall_skus.Color</code>. 颜色规格
     */
    public final TableField<TMallSkusRecord, String> COLOR = createField("Color", org.jooq.impl.SQLDataType.VARCHAR.length(100), this, "颜色规格");

    /**
     * The column <code>appcenter_mall_data.t_mall_skus.Size</code>. 尺寸规格
     */
    public final TableField<TMallSkusRecord, String> SIZE = createField("Size", org.jooq.impl.SQLDataType.VARCHAR.length(100), this, "尺寸规格");

    /**
     * The column <code>appcenter_mall_data.t_mall_skus.Version</code>. 版本规格
     */
    public final TableField<TMallSkusRecord, String> VERSION = createField("Version", org.jooq.impl.SQLDataType.VARCHAR.length(100), this, "版本规格");

    /**
     * The column <code>appcenter_mall_data.t_mall_skus.Sku</code>. SKU
     */
    public final TableField<TMallSkusRecord, String> SKU = createField("Sku", org.jooq.impl.SQLDataType.VARCHAR.length(100), this, "SKU");

    /**
     * The column <code>appcenter_mall_data.t_mall_skus.Stock</code>. 库存
     */
    public final TableField<TMallSkusRecord, Long> STOCK = createField("Stock", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "库存");

    /**
     * The column <code>appcenter_mall_data.t_mall_skus.CostPrice</code>. 成本价
     */
    public final TableField<TMallSkusRecord, BigDecimal> COSTPRICE = createField("CostPrice", org.jooq.impl.SQLDataType.DECIMAL.precision(8, 2).nullable(false), this, "成本价");

    /**
     * The column <code>appcenter_mall_data.t_mall_skus.SalePrice</code>. 销售价
     */
    public final TableField<TMallSkusRecord, BigDecimal> SALEPRICE = createField("SalePrice", org.jooq.impl.SQLDataType.DECIMAL.precision(8, 2).nullable(false), this, "销售价");

    /**
     * Create a <code>appcenter_mall_data.t_mall_skus</code> table reference
     */
    public TMallSkus() {
        this("t_mall_skus", null);
    }

    /**
     * Create an aliased <code>appcenter_mall_data.t_mall_skus</code> table reference
     */
    public TMallSkus(String alias) {
        this(alias, T_MALL_SKUS);
    }

    private TMallSkus(String alias, Table<TMallSkusRecord> aliased) {
        this(alias, aliased, null);
    }

    private TMallSkus(String alias, Table<TMallSkusRecord> aliased, Field<?>[] parameters) {
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
    public UniqueKey<TMallSkusRecord> getPrimaryKey() {
        return Keys.KEY_T_MALL_SKUS_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<TMallSkusRecord>> getKeys() {
        return Arrays.<UniqueKey<TMallSkusRecord>>asList(Keys.KEY_T_MALL_SKUS_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ForeignKey<TMallSkusRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<TMallSkusRecord, ?>>asList(Keys.T_MALL_SKUS_IBFK_1);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TMallSkus as(String alias) {
        return new TMallSkus(alias, this);
    }

    /**
     * Rename this table
     */
    public TMallSkus rename(String name) {
        return new TMallSkus(name, null);
    }
}