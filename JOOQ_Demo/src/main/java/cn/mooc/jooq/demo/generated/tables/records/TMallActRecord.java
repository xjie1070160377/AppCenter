/**
 * This class is generated by jOOQ
 */
package cn.mooc.jooq.demo.generated.tables.records;


import cn.mooc.jooq.demo.generated.tables.TMallAct;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record7;
import org.jooq.Row7;
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
public class TMallActRecord extends UpdatableRecordImpl<TMallActRecord> implements Record7<Integer, Integer, String, String, Integer, String, Integer> {

    private static final long serialVersionUID = -1402098350;

    /**
     * Setter for <code>appcenter_mall_data.t_mall_act.Id</code>.
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_act.Id</code>.
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_act.TypeId</code>. 活动类型
     */
    public void setTypeid(Integer value) {
        set(1, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_act.TypeId</code>. 活动类型
     */
    public Integer getTypeid() {
        return (Integer) get(1);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_act.Name</code>. 活动名称
     */
    public void setName(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_act.Name</code>. 活动名称
     */
    public String getName() {
        return (String) get(2);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_act.Number</code>. 活动编码
     */
    public void setNumber(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_act.Number</code>. 活动编码
     */
    public String getNumber() {
        return (String) get(3);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_act.TemplateId</code>. 活动模板
     */
    public void setTemplateid(Integer value) {
        set(4, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_act.TemplateId</code>. 活动模板
     */
    public Integer getTemplateid() {
        return (Integer) get(4);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_act.ImageUrl</code>. 图片链接
     */
    public void setImageurl(String value) {
        set(5, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_act.ImageUrl</code>. 图片链接
     */
    public String getImageurl() {
        return (String) get(5);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_act.IsRelease</code>. 是否启用
     */
    public void setIsrelease(Integer value) {
        set(6, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_act.IsRelease</code>. 是否启用
     */
    public Integer getIsrelease() {
        return (Integer) get(6);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Record1<Integer> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record7 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row7<Integer, Integer, String, String, Integer, String, Integer> fieldsRow() {
        return (Row7) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row7<Integer, Integer, String, String, Integer, String, Integer> valuesRow() {
        return (Row7) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return TMallAct.T_MALL_ACT.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field2() {
        return TMallAct.T_MALL_ACT.TYPEID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field3() {
        return TMallAct.T_MALL_ACT.NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field4() {
        return TMallAct.T_MALL_ACT.NUMBER;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field5() {
        return TMallAct.T_MALL_ACT.TEMPLATEID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field6() {
        return TMallAct.T_MALL_ACT.IMAGEURL;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field7() {
        return TMallAct.T_MALL_ACT.ISRELEASE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value1() {
        return getId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value2() {
        return getTypeid();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value3() {
        return getName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value4() {
        return getNumber();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value5() {
        return getTemplateid();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value6() {
        return getImageurl();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value7() {
        return getIsrelease();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TMallActRecord value1(Integer value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TMallActRecord value2(Integer value) {
        setTypeid(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TMallActRecord value3(String value) {
        setName(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TMallActRecord value4(String value) {
        setNumber(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TMallActRecord value5(Integer value) {
        setTemplateid(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TMallActRecord value6(String value) {
        setImageurl(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TMallActRecord value7(Integer value) {
        setIsrelease(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TMallActRecord values(Integer value1, Integer value2, String value3, String value4, Integer value5, String value6, Integer value7) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        value7(value7);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached TMallActRecord
     */
    public TMallActRecord() {
        super(TMallAct.T_MALL_ACT);
    }

    /**
     * Create a detached, initialised TMallActRecord
     */
    public TMallActRecord(Integer id, Integer typeid, String name, String number, Integer templateid, String imageurl, Integer isrelease) {
        super(TMallAct.T_MALL_ACT);

        set(0, id);
        set(1, typeid);
        set(2, name);
        set(3, number);
        set(4, templateid);
        set(5, imageurl);
        set(6, isrelease);
    }
}
