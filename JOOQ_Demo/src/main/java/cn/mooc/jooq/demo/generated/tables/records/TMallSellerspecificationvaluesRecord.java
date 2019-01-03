/**
 * This class is generated by jOOQ
 */
package cn.mooc.jooq.demo.generated.tables.records;


import cn.mooc.jooq.demo.generated.tables.TMallSellerspecificationvalues;

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
public class TMallSellerspecificationvaluesRecord extends UpdatableRecordImpl<TMallSellerspecificationvaluesRecord> implements Record6<Long, Long, Long, Integer, Long, String> {

    private static final long serialVersionUID = 427030449;

    /**
     * Setter for <code>appcenter_mall_data.t_mall_sellerspecificationvalues.Id</code>.
     */
    public void setId(Long value) {
        set(0, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_sellerspecificationvalues.Id</code>.
     */
    public Long getId() {
        return (Long) get(0);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_sellerspecificationvalues.ShopId</code>. 店铺ID
     */
    public void setShopid(Long value) {
        set(1, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_sellerspecificationvalues.ShopId</code>. 店铺ID
     */
    public Long getShopid() {
        return (Long) get(1);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_sellerspecificationvalues.ValueId</code>. 规格值ID
     */
    public void setValueid(Long value) {
        set(2, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_sellerspecificationvalues.ValueId</code>. 规格值ID
     */
    public Long getValueid() {
        return (Long) get(2);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_sellerspecificationvalues.Specification</code>. 规格（颜色、尺寸、版本）
     */
    public void setSpecification(Integer value) {
        set(3, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_sellerspecificationvalues.Specification</code>. 规格（颜色、尺寸、版本）
     */
    public Integer getSpecification() {
        return (Integer) get(3);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_sellerspecificationvalues.TypeId</code>. 类型ID
     */
    public void setTypeid(Long value) {
        set(4, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_sellerspecificationvalues.TypeId</code>. 类型ID
     */
    public Long getTypeid() {
        return (Long) get(4);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_sellerspecificationvalues.Value</code>. 商家的规格值
     */
    public void setValue(String value) {
        set(5, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_sellerspecificationvalues.Value</code>. 商家的规格值
     */
    public String getValue() {
        return (String) get(5);
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
    public Row6<Long, Long, Long, Integer, Long, String> fieldsRow() {
        return (Row6) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row6<Long, Long, Long, Integer, Long, String> valuesRow() {
        return (Row6) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Long> field1() {
        return TMallSellerspecificationvalues.T_MALL_SELLERSPECIFICATIONVALUES.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Long> field2() {
        return TMallSellerspecificationvalues.T_MALL_SELLERSPECIFICATIONVALUES.SHOPID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Long> field3() {
        return TMallSellerspecificationvalues.T_MALL_SELLERSPECIFICATIONVALUES.VALUEID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field4() {
        return TMallSellerspecificationvalues.T_MALL_SELLERSPECIFICATIONVALUES.SPECIFICATION;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Long> field5() {
        return TMallSellerspecificationvalues.T_MALL_SELLERSPECIFICATIONVALUES.TYPEID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field6() {
        return TMallSellerspecificationvalues.T_MALL_SELLERSPECIFICATIONVALUES.VALUE;
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
        return getValueid();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value4() {
        return getSpecification();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long value5() {
        return getTypeid();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value6() {
        return getValue();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TMallSellerspecificationvaluesRecord value1(Long value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TMallSellerspecificationvaluesRecord value2(Long value) {
        setShopid(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TMallSellerspecificationvaluesRecord value3(Long value) {
        setValueid(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TMallSellerspecificationvaluesRecord value4(Integer value) {
        setSpecification(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TMallSellerspecificationvaluesRecord value5(Long value) {
        setTypeid(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TMallSellerspecificationvaluesRecord value6(String value) {
        setValue(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TMallSellerspecificationvaluesRecord values(Long value1, Long value2, Long value3, Integer value4, Long value5, String value6) {
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
     * Create a detached TMallSellerspecificationvaluesRecord
     */
    public TMallSellerspecificationvaluesRecord() {
        super(TMallSellerspecificationvalues.T_MALL_SELLERSPECIFICATIONVALUES);
    }

    /**
     * Create a detached, initialised TMallSellerspecificationvaluesRecord
     */
    public TMallSellerspecificationvaluesRecord(Long id, Long shopid, Long valueid, Integer specification, Long typeid, String value) {
        super(TMallSellerspecificationvalues.T_MALL_SELLERSPECIFICATIONVALUES);

        set(0, id);
        set(1, shopid);
        set(2, valueid);
        set(3, specification);
        set(4, typeid);
        set(5, value);
    }
}