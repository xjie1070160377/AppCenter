/**
 * This class is generated by jOOQ
 */
package cn.mooc.jooq.demo.generated.tables;


import cn.mooc.jooq.demo.generated.AppcenterMallData;
import cn.mooc.jooq.demo.generated.Keys;
import cn.mooc.jooq.demo.generated.tables.records.TMallShopgradesRecord;

import java.math.BigDecimal;
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
public class TMallShopgrades extends TableImpl<TMallShopgradesRecord> {

    private static final long serialVersionUID = 463844876;

    /**
     * The reference instance of <code>appcenter_mall_data.t_mall_shopgrades</code>
     */
    public static final TMallShopgrades T_MALL_SHOPGRADES = new TMallShopgrades();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<TMallShopgradesRecord> getRecordType() {
        return TMallShopgradesRecord.class;
    }

    /**
     * The column <code>appcenter_mall_data.t_mall_shopgrades.Id</code>.
     */
    public final TableField<TMallShopgradesRecord, Long> ID = createField("Id", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>appcenter_mall_data.t_mall_shopgrades.Name</code>. 店铺等级名称
     */
    public final TableField<TMallShopgradesRecord, String> NAME = createField("Name", org.jooq.impl.SQLDataType.VARCHAR.length(100).nullable(false), this, "店铺等级名称");

    /**
     * The column <code>appcenter_mall_data.t_mall_shopgrades.ProductLimit</code>. 最大上传商品数量
     */
    public final TableField<TMallShopgradesRecord, Integer> PRODUCTLIMIT = createField("ProductLimit", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "最大上传商品数量");

    /**
     * The column <code>appcenter_mall_data.t_mall_shopgrades.ImageLimit</code>. 最大图片可使用空间数量
     */
    public final TableField<TMallShopgradesRecord, Integer> IMAGELIMIT = createField("ImageLimit", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "最大图片可使用空间数量");

    /**
     * The column <code>appcenter_mall_data.t_mall_shopgrades.TemplateLimit</code>.
     */
    public final TableField<TMallShopgradesRecord, Integer> TEMPLATELIMIT = createField("TemplateLimit", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>appcenter_mall_data.t_mall_shopgrades.ChargeStandard</code>.
     */
    public final TableField<TMallShopgradesRecord, BigDecimal> CHARGESTANDARD = createField("ChargeStandard", org.jooq.impl.SQLDataType.DECIMAL.precision(8, 2).nullable(false), this, "");

    /**
     * The column <code>appcenter_mall_data.t_mall_shopgrades.Remark</code>.
     */
    public final TableField<TMallShopgradesRecord, String> REMARK = createField("Remark", org.jooq.impl.SQLDataType.VARCHAR.length(1000), this, "");

    /**
     * Create a <code>appcenter_mall_data.t_mall_shopgrades</code> table reference
     */
    public TMallShopgrades() {
        this("t_mall_shopgrades", null);
    }

    /**
     * Create an aliased <code>appcenter_mall_data.t_mall_shopgrades</code> table reference
     */
    public TMallShopgrades(String alias) {
        this(alias, T_MALL_SHOPGRADES);
    }

    private TMallShopgrades(String alias, Table<TMallShopgradesRecord> aliased) {
        this(alias, aliased, null);
    }

    private TMallShopgrades(String alias, Table<TMallShopgradesRecord> aliased, Field<?>[] parameters) {
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
    public Identity<TMallShopgradesRecord, Long> getIdentity() {
        return Keys.IDENTITY_T_MALL_SHOPGRADES;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<TMallShopgradesRecord> getPrimaryKey() {
        return Keys.KEY_T_MALL_SHOPGRADES_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<TMallShopgradesRecord>> getKeys() {
        return Arrays.<UniqueKey<TMallShopgradesRecord>>asList(Keys.KEY_T_MALL_SHOPGRADES_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TMallShopgrades as(String alias) {
        return new TMallShopgrades(alias, this);
    }

    /**
     * Rename this table
     */
    public TMallShopgrades rename(String name) {
        return new TMallShopgrades(name, null);
    }
}
