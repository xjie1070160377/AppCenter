/**
 * This class is generated by jOOQ
 */
package cn.mooc.jooq.demo.generated.tables.records;


import cn.mooc.jooq.demo.generated.tables.TMallFloorproducts;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record4;
import org.jooq.Row4;
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
public class TMallFloorproductsRecord extends UpdatableRecordImpl<TMallFloorproductsRecord> implements Record4<Long, Long, Integer, Long> {

    private static final long serialVersionUID = 1646232031;

    /**
     * Setter for <code>appcenter_mall_data.t_mall_floorproducts.Id</code>.
     */
    public void setId(Long value) {
        set(0, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_floorproducts.Id</code>.
     */
    public Long getId() {
        return (Long) get(0);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_floorproducts.FloorId</code>. 楼层ID
     */
    public void setFloorid(Long value) {
        set(1, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_floorproducts.FloorId</code>. 楼层ID
     */
    public Long getFloorid() {
        return (Long) get(1);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_floorproducts.Tab</code>. 楼层标签
     */
    public void setTab(Integer value) {
        set(2, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_floorproducts.Tab</code>. 楼层标签
     */
    public Integer getTab() {
        return (Integer) get(2);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_floorproducts.ProductId</code>. 商品ID
     */
    public void setProductid(Long value) {
        set(3, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_floorproducts.ProductId</code>. 商品ID
     */
    public Long getProductid() {
        return (Long) get(3);
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
    // Record4 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row4<Long, Long, Integer, Long> fieldsRow() {
        return (Row4) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row4<Long, Long, Integer, Long> valuesRow() {
        return (Row4) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Long> field1() {
        return TMallFloorproducts.T_MALL_FLOORPRODUCTS.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Long> field2() {
        return TMallFloorproducts.T_MALL_FLOORPRODUCTS.FLOORID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field3() {
        return TMallFloorproducts.T_MALL_FLOORPRODUCTS.TAB;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Long> field4() {
        return TMallFloorproducts.T_MALL_FLOORPRODUCTS.PRODUCTID;
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
        return getFloorid();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value3() {
        return getTab();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long value4() {
        return getProductid();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TMallFloorproductsRecord value1(Long value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TMallFloorproductsRecord value2(Long value) {
        setFloorid(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TMallFloorproductsRecord value3(Integer value) {
        setTab(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TMallFloorproductsRecord value4(Long value) {
        setProductid(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TMallFloorproductsRecord values(Long value1, Long value2, Integer value3, Long value4) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached TMallFloorproductsRecord
     */
    public TMallFloorproductsRecord() {
        super(TMallFloorproducts.T_MALL_FLOORPRODUCTS);
    }

    /**
     * Create a detached, initialised TMallFloorproductsRecord
     */
    public TMallFloorproductsRecord(Long id, Long floorid, Integer tab, Long productid) {
        super(TMallFloorproducts.T_MALL_FLOORPRODUCTS);

        set(0, id);
        set(1, floorid);
        set(2, tab);
        set(3, productid);
    }
}
