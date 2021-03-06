/**
 * This class is generated by jOOQ
 */
package cn.mooc.jooq.demo.generated.tables.records;


import cn.mooc.jooq.demo.generated.tables.TMallShopbrands;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record3;
import org.jooq.Row3;
import org.jooq.impl.UpdatableRecordImpl;


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
public class TMallShopbrandsRecord extends UpdatableRecordImpl<TMallShopbrandsRecord> implements Record3<Long, Long, Long> {

    private static final long serialVersionUID = -518419637;

    /**
     * Setter for <code>appcenter_mall_data.t_mall_shopbrands.Id</code>. 编号
     */
    public void setId(Long value) {
        set(0, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_shopbrands.Id</code>. 编号
     */
    public Long getId() {
        return (Long) get(0);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_shopbrands.ShopId</code>. 商家Id
     */
    public void setShopid(Long value) {
        set(1, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_shopbrands.ShopId</code>. 商家Id
     */
    public Long getShopid() {
        return (Long) get(1);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_shopbrands.BrandId</code>. 品牌Id
     */
    public void setBrandid(Long value) {
        set(2, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_shopbrands.BrandId</code>. 品牌Id
     */
    public Long getBrandid() {
        return (Long) get(2);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Record1<Long> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record3 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row3<Long, Long, Long> fieldsRow() {
        return (Row3) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row3<Long, Long, Long> valuesRow() {
        return (Row3) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Long> field1() {
        return TMallShopbrands.T_MALL_SHOPBRANDS.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Long> field2() {
        return TMallShopbrands.T_MALL_SHOPBRANDS.SHOPID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Long> field3() {
        return TMallShopbrands.T_MALL_SHOPBRANDS.BRANDID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long value1() {
        return getId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long value2() {
        return getShopid();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long value3() {
        return getBrandid();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TMallShopbrandsRecord value1(Long value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TMallShopbrandsRecord value2(Long value) {
        setShopid(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TMallShopbrandsRecord value3(Long value) {
        setBrandid(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TMallShopbrandsRecord values(Long value1, Long value2, Long value3) {
        value1(value1);
        value2(value2);
        value3(value3);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached TMallShopbrandsRecord
     */
    public TMallShopbrandsRecord() {
        super(TMallShopbrands.T_MALL_SHOPBRANDS);
    }

    /**
     * Create a detached, initialised TMallShopbrandsRecord
     */
    public TMallShopbrandsRecord(Long id, Long shopid, Long brandid) {
        super(TMallShopbrands.T_MALL_SHOPBRANDS);

        set(0, id);
        set(1, shopid);
        set(2, brandid);
    }
}
