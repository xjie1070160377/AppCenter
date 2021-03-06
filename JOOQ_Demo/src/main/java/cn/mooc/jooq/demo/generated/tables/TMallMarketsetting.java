/**
 * This class is generated by jOOQ
 */
package cn.mooc.jooq.demo.generated.tables;


import cn.mooc.jooq.demo.generated.AppcenterMallData;
import cn.mooc.jooq.demo.generated.Keys;
import cn.mooc.jooq.demo.generated.tables.records.TMallMarketsettingRecord;

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
public class TMallMarketsetting extends TableImpl<TMallMarketsettingRecord> {

    private static final long serialVersionUID = 1463466563;

    /**
     * The reference instance of <code>appcenter_mall_data.t_mall_marketsetting</code>
     */
    public static final TMallMarketsetting T_MALL_MARKETSETTING = new TMallMarketsetting();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<TMallMarketsettingRecord> getRecordType() {
        return TMallMarketsettingRecord.class;
    }

    /**
     * The column <code>appcenter_mall_data.t_mall_marketsetting.Id</code>.
     */
    public final TableField<TMallMarketsettingRecord, Integer> ID = createField("Id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>appcenter_mall_data.t_mall_marketsetting.TypeId</code>. 营销类型ID
     */
    public final TableField<TMallMarketsettingRecord, Integer> TYPEID = createField("TypeId", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "营销类型ID");

    /**
     * The column <code>appcenter_mall_data.t_mall_marketsetting.Price</code>. 营销使用价格（/月）
     */
    public final TableField<TMallMarketsettingRecord, BigDecimal> PRICE = createField("Price", org.jooq.impl.SQLDataType.DECIMAL.precision(8, 2).nullable(false), this, "营销使用价格（/月）");

    /**
     * Create a <code>appcenter_mall_data.t_mall_marketsetting</code> table reference
     */
    public TMallMarketsetting() {
        this("t_mall_marketsetting", null);
    }

    /**
     * Create an aliased <code>appcenter_mall_data.t_mall_marketsetting</code> table reference
     */
    public TMallMarketsetting(String alias) {
        this(alias, T_MALL_MARKETSETTING);
    }

    private TMallMarketsetting(String alias, Table<TMallMarketsettingRecord> aliased) {
        this(alias, aliased, null);
    }

    private TMallMarketsetting(String alias, Table<TMallMarketsettingRecord> aliased, Field<?>[] parameters) {
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
    public Identity<TMallMarketsettingRecord, Integer> getIdentity() {
        return Keys.IDENTITY_T_MALL_MARKETSETTING;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<TMallMarketsettingRecord> getPrimaryKey() {
        return Keys.KEY_T_MALL_MARKETSETTING_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<TMallMarketsettingRecord>> getKeys() {
        return Arrays.<UniqueKey<TMallMarketsettingRecord>>asList(Keys.KEY_T_MALL_MARKETSETTING_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TMallMarketsetting as(String alias) {
        return new TMallMarketsetting(alias, this);
    }

    /**
     * Rename this table
     */
    public TMallMarketsetting rename(String name) {
        return new TMallMarketsetting(name, null);
    }
}
