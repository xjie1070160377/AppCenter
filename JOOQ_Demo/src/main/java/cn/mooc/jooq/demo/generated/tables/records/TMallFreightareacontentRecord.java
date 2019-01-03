/**
 * This class is generated by jOOQ
 */
package cn.mooc.jooq.demo.generated.tables.records;


import cn.mooc.jooq.demo.generated.tables.TMallFreightareacontent;

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
public class TMallFreightareacontentRecord extends UpdatableRecordImpl<TMallFreightareacontentRecord> implements Record8<Long, Long, String, Integer, Double, Integer, Double, Byte> {

    private static final long serialVersionUID = 1552682104;

    /**
     * Setter for <code>appcenter_mall_data.t_mall_freightareacontent.Id</code>.
     */
    public void setId(Long value) {
        set(0, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_freightareacontent.Id</code>.
     */
    public Long getId() {
        return (Long) get(0);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_freightareacontent.FreightTemplateId</code>. 运费模板ID
     */
    public void setFreighttemplateid(Long value) {
        set(1, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_freightareacontent.FreightTemplateId</code>. 运费模板ID
     */
    public Long getFreighttemplateid() {
        return (Long) get(1);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_freightareacontent.AreaContent</code>. 地区选择
     */
    public void setAreacontent(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_freightareacontent.AreaContent</code>. 地区选择
     */
    public String getAreacontent() {
        return (String) get(2);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_freightareacontent.FirstUnit</code>. 首笔单元计量
     */
    public void setFirstunit(Integer value) {
        set(3, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_freightareacontent.FirstUnit</code>. 首笔单元计量
     */
    public Integer getFirstunit() {
        return (Integer) get(3);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_freightareacontent.FirstUnitMonry</code>. 首笔单元费用
     */
    public void setFirstunitmonry(Double value) {
        set(4, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_freightareacontent.FirstUnitMonry</code>. 首笔单元费用
     */
    public Double getFirstunitmonry() {
        return (Double) get(4);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_freightareacontent.AccumulationUnit</code>. 递增单元计量
     */
    public void setAccumulationunit(Integer value) {
        set(5, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_freightareacontent.AccumulationUnit</code>. 递增单元计量
     */
    public Integer getAccumulationunit() {
        return (Integer) get(5);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_freightareacontent.AccumulationUnitMoney</code>. 递增单元费用
     */
    public void setAccumulationunitmoney(Double value) {
        set(6, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_freightareacontent.AccumulationUnitMoney</code>. 递增单元费用
     */
    public Double getAccumulationunitmoney() {
        return (Double) get(6);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_freightareacontent.IsDefault</code>. 是否为默认
     */
    public void setIsdefault(Byte value) {
        set(7, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_freightareacontent.IsDefault</code>. 是否为默认
     */
    public Byte getIsdefault() {
        return (Byte) get(7);
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
    public Row8<Long, Long, String, Integer, Double, Integer, Double, Byte> fieldsRow() {
        return (Row8) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row8<Long, Long, String, Integer, Double, Integer, Double, Byte> valuesRow() {
        return (Row8) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Long> field1() {
        return TMallFreightareacontent.T_MALL_FREIGHTAREACONTENT.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Long> field2() {
        return TMallFreightareacontent.T_MALL_FREIGHTAREACONTENT.FREIGHTTEMPLATEID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field3() {
        return TMallFreightareacontent.T_MALL_FREIGHTAREACONTENT.AREACONTENT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field4() {
        return TMallFreightareacontent.T_MALL_FREIGHTAREACONTENT.FIRSTUNIT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Double> field5() {
        return TMallFreightareacontent.T_MALL_FREIGHTAREACONTENT.FIRSTUNITMONRY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field6() {
        return TMallFreightareacontent.T_MALL_FREIGHTAREACONTENT.ACCUMULATIONUNIT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Double> field7() {
        return TMallFreightareacontent.T_MALL_FREIGHTAREACONTENT.ACCUMULATIONUNITMONEY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Byte> field8() {
        return TMallFreightareacontent.T_MALL_FREIGHTAREACONTENT.ISDEFAULT;
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
        return getFreighttemplateid();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value3() {
        return getAreacontent();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value4() {
        return getFirstunit();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Double value5() {
        return getFirstunitmonry();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value6() {
        return getAccumulationunit();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Double value7() {
        return getAccumulationunitmoney();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte value8() {
        return getIsdefault();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TMallFreightareacontentRecord value1(Long value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TMallFreightareacontentRecord value2(Long value) {
        setFreighttemplateid(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TMallFreightareacontentRecord value3(String value) {
        setAreacontent(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TMallFreightareacontentRecord value4(Integer value) {
        setFirstunit(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TMallFreightareacontentRecord value5(Double value) {
        setFirstunitmonry(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TMallFreightareacontentRecord value6(Integer value) {
        setAccumulationunit(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TMallFreightareacontentRecord value7(Double value) {
        setAccumulationunitmoney(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TMallFreightareacontentRecord value8(Byte value) {
        setIsdefault(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TMallFreightareacontentRecord values(Long value1, Long value2, String value3, Integer value4, Double value5, Integer value6, Double value7, Byte value8) {
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
     * Create a detached TMallFreightareacontentRecord
     */
    public TMallFreightareacontentRecord() {
        super(TMallFreightareacontent.T_MALL_FREIGHTAREACONTENT);
    }

    /**
     * Create a detached, initialised TMallFreightareacontentRecord
     */
    public TMallFreightareacontentRecord(Long id, Long freighttemplateid, String areacontent, Integer firstunit, Double firstunitmonry, Integer accumulationunit, Double accumulationunitmoney, Byte isdefault) {
        super(TMallFreightareacontent.T_MALL_FREIGHTAREACONTENT);

        set(0, id);
        set(1, freighttemplateid);
        set(2, areacontent);
        set(3, firstunit);
        set(4, firstunitmonry);
        set(5, accumulationunit);
        set(6, accumulationunitmoney);
        set(7, isdefault);
    }
}