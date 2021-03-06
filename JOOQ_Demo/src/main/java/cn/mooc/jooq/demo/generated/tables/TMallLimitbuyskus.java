/**
 * This class is generated by jOOQ
 */
package cn.mooc.jooq.demo.generated.tables;


import cn.mooc.jooq.demo.generated.AppcenterMallData;
import cn.mooc.jooq.demo.generated.Keys;
import cn.mooc.jooq.demo.generated.tables.records.TMallLimitbuyskusRecord;

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
public class TMallLimitbuyskus extends TableImpl<TMallLimitbuyskusRecord> {

    private static final long serialVersionUID = -1970403231;

    /**
     * The reference instance of <code>appcenter_mall_data.t_mall_limitbuyskus</code>
     */
    public static final TMallLimitbuyskus T_MALL_LIMITBUYSKUS = new TMallLimitbuyskus();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<TMallLimitbuyskusRecord> getRecordType() {
        return TMallLimitbuyskusRecord.class;
    }

    /**
     * The column <code>appcenter_mall_data.t_mall_limitbuyskus.LimitMarketId</code>. 促销活动编号
     */
    public final TableField<TMallLimitbuyskusRecord, Long> LIMITMARKETID = createField("LimitMarketId", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "促销活动编号");

    /**
     * The column <code>appcenter_mall_data.t_mall_limitbuyskus.Id</code>.
     */
    public final TableField<TMallLimitbuyskusRecord, String> ID = createField("Id", org.jooq.impl.SQLDataType.VARCHAR.length(100).nullable(false), this, "");

    /**
     * The column <code>appcenter_mall_data.t_mall_limitbuyskus.Color</code>. 颜色
     */
    public final TableField<TMallLimitbuyskusRecord, String> COLOR = createField("Color", org.jooq.impl.SQLDataType.VARCHAR.length(100), this, "颜色");

    /**
     * The column <code>appcenter_mall_data.t_mall_limitbuyskus.Size</code>. 尺寸
     */
    public final TableField<TMallLimitbuyskusRecord, String> SIZE = createField("Size", org.jooq.impl.SQLDataType.VARCHAR.length(100), this, "尺寸");

    /**
     * The column <code>appcenter_mall_data.t_mall_limitbuyskus.Version</code>. 规格
     */
    public final TableField<TMallLimitbuyskusRecord, String> VERSION = createField("Version", org.jooq.impl.SQLDataType.VARCHAR.length(100), this, "规格");

    /**
     * The column <code>appcenter_mall_data.t_mall_limitbuyskus.Sku</code>. 货号
     */
    public final TableField<TMallLimitbuyskusRecord, String> SKU = createField("Sku", org.jooq.impl.SQLDataType.VARCHAR.length(100), this, "货号");

    /**
     * The column <code>appcenter_mall_data.t_mall_limitbuyskus.Stock</code>. 库存
     */
    public final TableField<TMallLimitbuyskusRecord, Long> STOCK = createField("Stock", org.jooq.impl.SQLDataType.BIGINT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.BIGINT)), this, "库存");

    /**
     * The column <code>appcenter_mall_data.t_mall_limitbuyskus.SalePrice</code>. 售价
     */
    public final TableField<TMallLimitbuyskusRecord, BigDecimal> SALEPRICE = createField("SalePrice", org.jooq.impl.SQLDataType.DECIMAL.precision(8, 2).nullable(false).defaultValue(org.jooq.impl.DSL.inline("0.00", org.jooq.impl.SQLDataType.DECIMAL)), this, "售价");

    /**
     * The column <code>appcenter_mall_data.t_mall_limitbuyskus.LimitBuyPrice</code>. 活动价
     */
    public final TableField<TMallLimitbuyskusRecord, BigDecimal> LIMITBUYPRICE = createField("LimitBuyPrice", org.jooq.impl.SQLDataType.DECIMAL.precision(8, 2).nullable(false), this, "活动价");

    /**
     * Create a <code>appcenter_mall_data.t_mall_limitbuyskus</code> table reference
     */
    public TMallLimitbuyskus() {
        this("t_mall_limitbuyskus", null);
    }

    /**
     * Create an aliased <code>appcenter_mall_data.t_mall_limitbuyskus</code> table reference
     */
    public TMallLimitbuyskus(String alias) {
        this(alias, T_MALL_LIMITBUYSKUS);
    }

    private TMallLimitbuyskus(String alias, Table<TMallLimitbuyskusRecord> aliased) {
        this(alias, aliased, null);
    }

    private TMallLimitbuyskus(String alias, Table<TMallLimitbuyskusRecord> aliased, Field<?>[] parameters) {
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
    public UniqueKey<TMallLimitbuyskusRecord> getPrimaryKey() {
        return Keys.KEY_T_MALL_LIMITBUYSKUS_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<TMallLimitbuyskusRecord>> getKeys() {
        return Arrays.<UniqueKey<TMallLimitbuyskusRecord>>asList(Keys.KEY_T_MALL_LIMITBUYSKUS_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ForeignKey<TMallLimitbuyskusRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<TMallLimitbuyskusRecord, ?>>asList(Keys.T_MALL_LIMITBUYSKUS_IBFK_1);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TMallLimitbuyskus as(String alias) {
        return new TMallLimitbuyskus(alias, this);
    }

    /**
     * Rename this table
     */
    public TMallLimitbuyskus rename(String name) {
        return new TMallLimitbuyskus(name, null);
    }
}
