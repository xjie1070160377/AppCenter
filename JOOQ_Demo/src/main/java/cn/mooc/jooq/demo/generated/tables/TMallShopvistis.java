/**
 * This class is generated by jOOQ
 */
package cn.mooc.jooq.demo.generated.tables;


import cn.mooc.jooq.demo.generated.AppcenterMallData;
import cn.mooc.jooq.demo.generated.Keys;
import cn.mooc.jooq.demo.generated.tables.records.TMallShopvistisRecord;

import java.math.BigDecimal;
import java.sql.Timestamp;
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
public class TMallShopvistis extends TableImpl<TMallShopvistisRecord> {

    private static final long serialVersionUID = -743942649;

    /**
     * The reference instance of <code>appcenter_mall_data.t_mall_shopvistis</code>
     */
    public static final TMallShopvistis T_MALL_SHOPVISTIS = new TMallShopvistis();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<TMallShopvistisRecord> getRecordType() {
        return TMallShopvistisRecord.class;
    }

    /**
     * The column <code>appcenter_mall_data.t_mall_shopvistis.Id</code>.
     */
    public final TableField<TMallShopvistisRecord, Long> ID = createField("Id", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>appcenter_mall_data.t_mall_shopvistis.ShopId</code>. 店铺ID
     */
    public final TableField<TMallShopvistisRecord, Long> SHOPID = createField("ShopId", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "店铺ID");

    /**
     * The column <code>appcenter_mall_data.t_mall_shopvistis.Date</code>. 日期
     */
    public final TableField<TMallShopvistisRecord, Timestamp> DATE = createField("Date", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false), this, "日期");

    /**
     * The column <code>appcenter_mall_data.t_mall_shopvistis.VistiCounts</code>. 浏览量
     */
    public final TableField<TMallShopvistisRecord, Long> VISTICOUNTS = createField("VistiCounts", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "浏览量");

    /**
     * The column <code>appcenter_mall_data.t_mall_shopvistis.SaleCounts</code>. 销售量
     */
    public final TableField<TMallShopvistisRecord, Long> SALECOUNTS = createField("SaleCounts", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "销售量");

    /**
     * The column <code>appcenter_mall_data.t_mall_shopvistis.SaleAmounts</code>. 销售额
     */
    public final TableField<TMallShopvistisRecord, BigDecimal> SALEAMOUNTS = createField("SaleAmounts", org.jooq.impl.SQLDataType.DECIMAL.precision(8, 2).nullable(false), this, "销售额");

    /**
     * Create a <code>appcenter_mall_data.t_mall_shopvistis</code> table reference
     */
    public TMallShopvistis() {
        this("t_mall_shopvistis", null);
    }

    /**
     * Create an aliased <code>appcenter_mall_data.t_mall_shopvistis</code> table reference
     */
    public TMallShopvistis(String alias) {
        this(alias, T_MALL_SHOPVISTIS);
    }

    private TMallShopvistis(String alias, Table<TMallShopvistisRecord> aliased) {
        this(alias, aliased, null);
    }

    private TMallShopvistis(String alias, Table<TMallShopvistisRecord> aliased, Field<?>[] parameters) {
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
    public Identity<TMallShopvistisRecord, Long> getIdentity() {
        return Keys.IDENTITY_T_MALL_SHOPVISTIS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<TMallShopvistisRecord> getPrimaryKey() {
        return Keys.KEY_T_MALL_SHOPVISTIS_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<TMallShopvistisRecord>> getKeys() {
        return Arrays.<UniqueKey<TMallShopvistisRecord>>asList(Keys.KEY_T_MALL_SHOPVISTIS_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TMallShopvistis as(String alias) {
        return new TMallShopvistis(alias, this);
    }

    /**
     * Rename this table
     */
    public TMallShopvistis rename(String name) {
        return new TMallShopvistis(name, null);
    }
}
