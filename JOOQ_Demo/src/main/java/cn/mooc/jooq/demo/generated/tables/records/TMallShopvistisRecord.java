/**
 * This class is generated by jOOQ
 */
package cn.mooc.jooq.demo.generated.tables.records;


import cn.mooc.jooq.demo.generated.tables.TMallShopvistis;

import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record6;
import org.jooq.Row6;
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
public class TMallShopvistisRecord extends UpdatableRecordImpl<TMallShopvistisRecord> implements Record6<Long, Long, Timestamp, Long, Long, BigDecimal> {

    private static final long serialVersionUID = -315403949;

    /**
     * Setter for <code>appcenter_mall_data.t_mall_shopvistis.Id</code>.
     */
    public void setId(Long value) {
        set(0, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_shopvistis.Id</code>.
     */
    public Long getId() {
        return (Long) get(0);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_shopvistis.ShopId</code>. 店铺ID
     */
    public void setShopid(Long value) {
        set(1, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_shopvistis.ShopId</code>. 店铺ID
     */
    public Long getShopid() {
        return (Long) get(1);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_shopvistis.Date</code>. 日期
     */
    public void setDate(Timestamp value) {
        set(2, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_shopvistis.Date</code>. 日期
     */
    public Timestamp getDate() {
        return (Timestamp) get(2);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_shopvistis.VistiCounts</code>. 浏览量
     */
    public void setVisticounts(Long value) {
        set(3, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_shopvistis.VistiCounts</code>. 浏览量
     */
    public Long getVisticounts() {
        return (Long) get(3);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_shopvistis.SaleCounts</code>. 销售量
     */
    public void setSalecounts(Long value) {
        set(4, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_shopvistis.SaleCounts</code>. 销售量
     */
    public Long getSalecounts() {
        return (Long) get(4);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_shopvistis.SaleAmounts</code>. 销售额
     */
    public void setSaleamounts(BigDecimal value) {
        set(5, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_shopvistis.SaleAmounts</code>. 销售额
     */
    public BigDecimal getSaleamounts() {
        return (BigDecimal) get(5);
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
    // Record6 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row6<Long, Long, Timestamp, Long, Long, BigDecimal> fieldsRow() {
        return (Row6) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row6<Long, Long, Timestamp, Long, Long, BigDecimal> valuesRow() {
        return (Row6) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Long> field1() {
        return TMallShopvistis.T_MALL_SHOPVISTIS.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Long> field2() {
        return TMallShopvistis.T_MALL_SHOPVISTIS.SHOPID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field3() {
        return TMallShopvistis.T_MALL_SHOPVISTIS.DATE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Long> field4() {
        return TMallShopvistis.T_MALL_SHOPVISTIS.VISTICOUNTS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Long> field5() {
        return TMallShopvistis.T_MALL_SHOPVISTIS.SALECOUNTS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<BigDecimal> field6() {
        return TMallShopvistis.T_MALL_SHOPVISTIS.SALEAMOUNTS;
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
    public Timestamp value3() {
        return getDate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long value4() {
        return getVisticounts();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long value5() {
        return getSalecounts();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BigDecimal value6() {
        return getSaleamounts();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TMallShopvistisRecord value1(Long value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TMallShopvistisRecord value2(Long value) {
        setShopid(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TMallShopvistisRecord value3(Timestamp value) {
        setDate(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TMallShopvistisRecord value4(Long value) {
        setVisticounts(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TMallShopvistisRecord value5(Long value) {
        setSalecounts(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TMallShopvistisRecord value6(BigDecimal value) {
        setSaleamounts(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TMallShopvistisRecord values(Long value1, Long value2, Timestamp value3, Long value4, Long value5, BigDecimal value6) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached TMallShopvistisRecord
     */
    public TMallShopvistisRecord() {
        super(TMallShopvistis.T_MALL_SHOPVISTIS);
    }

    /**
     * Create a detached, initialised TMallShopvistisRecord
     */
    public TMallShopvistisRecord(Long id, Long shopid, Timestamp date, Long visticounts, Long salecounts, BigDecimal saleamounts) {
        super(TMallShopvistis.T_MALL_SHOPVISTIS);

        set(0, id);
        set(1, shopid);
        set(2, date);
        set(3, visticounts);
        set(4, salecounts);
        set(5, saleamounts);
    }
}