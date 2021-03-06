/**
 * This class is generated by jOOQ
 */
package cn.mooc.jooq.demo.generated.tables.records;


import cn.mooc.jooq.demo.generated.tables.TMallFreighttemplate;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record8;
import org.jooq.Row8;
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
public class TMallFreighttemplateRecord extends UpdatableRecordImpl<TMallFreighttemplateRecord> implements Record8<Long, String, Integer, String, Integer, Integer, Integer, Long> {

    private static final long serialVersionUID = -1337887218;

    /**
     * Setter for <code>appcenter_mall_data.t_mall_freighttemplate.Id</code>.
     */
    public void setId(Long value) {
        set(0, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_freighttemplate.Id</code>.
     */
    public Long getId() {
        return (Long) get(0);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_freighttemplate.Name</code>. 运费模板名称
     */
    public void setName(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_freighttemplate.Name</code>. 运费模板名称
     */
    public String getName() {
        return (String) get(1);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_freighttemplate.SourceAddress</code>. 宝贝发货地
     */
    public void setSourceaddress(Integer value) {
        set(2, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_freighttemplate.SourceAddress</code>. 宝贝发货地
     */
    public Integer getSourceaddress() {
        return (Integer) get(2);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_freighttemplate.SendTime</code>. 发送时间
     */
    public void setSendtime(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_freighttemplate.SendTime</code>. 发送时间
     */
    public String getSendtime() {
        return (String) get(3);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_freighttemplate.IsFree</code>. 是否商家负责运费
     */
    public void setIsfree(Integer value) {
        set(4, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_freighttemplate.IsFree</code>. 是否商家负责运费
     */
    public Integer getIsfree() {
        return (Integer) get(4);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_freighttemplate.ValuationMethod</code>. 定价方法(按体积、重量计算）
     */
    public void setValuationmethod(Integer value) {
        set(5, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_freighttemplate.ValuationMethod</code>. 定价方法(按体积、重量计算）
     */
    public Integer getValuationmethod() {
        return (Integer) get(5);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_freighttemplate.ShippingMethod</code>. 运送类型（物流、快递）
     */
    public void setShippingmethod(Integer value) {
        set(6, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_freighttemplate.ShippingMethod</code>. 运送类型（物流、快递）
     */
    public Integer getShippingmethod() {
        return (Integer) get(6);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_freighttemplate.ShopID</code>. 店铺ID
     */
    public void setShopid(Long value) {
        set(7, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_freighttemplate.ShopID</code>. 店铺ID
     */
    public Long getShopid() {
        return (Long) get(7);
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
    // Record8 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row8<Long, String, Integer, String, Integer, Integer, Integer, Long> fieldsRow() {
        return (Row8) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row8<Long, String, Integer, String, Integer, Integer, Integer, Long> valuesRow() {
        return (Row8) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Long> field1() {
        return TMallFreighttemplate.T_MALL_FREIGHTTEMPLATE.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field2() {
        return TMallFreighttemplate.T_MALL_FREIGHTTEMPLATE.NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field3() {
        return TMallFreighttemplate.T_MALL_FREIGHTTEMPLATE.SOURCEADDRESS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field4() {
        return TMallFreighttemplate.T_MALL_FREIGHTTEMPLATE.SENDTIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field5() {
        return TMallFreighttemplate.T_MALL_FREIGHTTEMPLATE.ISFREE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field6() {
        return TMallFreighttemplate.T_MALL_FREIGHTTEMPLATE.VALUATIONMETHOD;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field7() {
        return TMallFreighttemplate.T_MALL_FREIGHTTEMPLATE.SHIPPINGMETHOD;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Long> field8() {
        return TMallFreighttemplate.T_MALL_FREIGHTTEMPLATE.SHOPID;
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
    public String value2() {
        return getName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value3() {
        return getSourceaddress();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value4() {
        return getSendtime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value5() {
        return getIsfree();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value6() {
        return getValuationmethod();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value7() {
        return getShippingmethod();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long value8() {
        return getShopid();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TMallFreighttemplateRecord value1(Long value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TMallFreighttemplateRecord value2(String value) {
        setName(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TMallFreighttemplateRecord value3(Integer value) {
        setSourceaddress(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TMallFreighttemplateRecord value4(String value) {
        setSendtime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TMallFreighttemplateRecord value5(Integer value) {
        setIsfree(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TMallFreighttemplateRecord value6(Integer value) {
        setValuationmethod(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TMallFreighttemplateRecord value7(Integer value) {
        setShippingmethod(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TMallFreighttemplateRecord value8(Long value) {
        setShopid(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TMallFreighttemplateRecord values(Long value1, String value2, Integer value3, String value4, Integer value5, Integer value6, Integer value7, Long value8) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        value7(value7);
        value8(value8);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached TMallFreighttemplateRecord
     */
    public TMallFreighttemplateRecord() {
        super(TMallFreighttemplate.T_MALL_FREIGHTTEMPLATE);
    }

    /**
     * Create a detached, initialised TMallFreighttemplateRecord
     */
    public TMallFreighttemplateRecord(Long id, String name, Integer sourceaddress, String sendtime, Integer isfree, Integer valuationmethod, Integer shippingmethod, Long shopid) {
        super(TMallFreighttemplate.T_MALL_FREIGHTTEMPLATE);

        set(0, id);
        set(1, name);
        set(2, sourceaddress);
        set(3, sendtime);
        set(4, isfree);
        set(5, valuationmethod);
        set(6, shippingmethod);
        set(7, shopid);
    }
}
