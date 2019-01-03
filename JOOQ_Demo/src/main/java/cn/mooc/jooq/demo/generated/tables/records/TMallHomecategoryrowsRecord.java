/**
 * This class is generated by jOOQ
 */
package cn.mooc.jooq.demo.generated.tables.records;


import cn.mooc.jooq.demo.generated.tables.TMallHomecategoryrows;

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
public class TMallHomecategoryrowsRecord extends UpdatableRecordImpl<TMallHomecategoryrowsRecord> implements Record6<Long, Integer, String, String, String, String> {

    private static final long serialVersionUID = -1972754897;

    /**
     * Setter for <code>appcenter_mall_data.t_mall_homecategoryrows.Id</code>.
     */
    public void setId(Long value) {
        set(0, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_homecategoryrows.Id</code>.
     */
    public Long getId() {
        return (Long) get(0);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_homecategoryrows.RowId</code>. 行ID
     */
    public void setRowid(Integer value) {
        set(1, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_homecategoryrows.RowId</code>. 行ID
     */
    public Integer getRowid() {
        return (Integer) get(1);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_homecategoryrows.Image1</code>. 所属行推荐图片1
     */
    public void setImage1(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_homecategoryrows.Image1</code>. 所属行推荐图片1
     */
    public String getImage1() {
        return (String) get(2);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_homecategoryrows.Url1</code>. 所属行推荐图片1的URL
     */
    public void setUrl1(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_homecategoryrows.Url1</code>. 所属行推荐图片1的URL
     */
    public String getUrl1() {
        return (String) get(3);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_homecategoryrows.Image2</code>. 所属行推荐图片2
     */
    public void setImage2(String value) {
        set(4, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_homecategoryrows.Image2</code>. 所属行推荐图片2
     */
    public String getImage2() {
        return (String) get(4);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_homecategoryrows.Url2</code>. 所属行推荐图片2的URL
     */
    public void setUrl2(String value) {
        set(5, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_homecategoryrows.Url2</code>. 所属行推荐图片2的URL
     */
    public String getUrl2() {
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
    public Row6<Long, Integer, String, String, String, String> fieldsRow() {
        return (Row6) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row6<Long, Integer, String, String, String, String> valuesRow() {
        return (Row6) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Long> field1() {
        return TMallHomecategoryrows.T_MALL_HOMECATEGORYROWS.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field2() {
        return TMallHomecategoryrows.T_MALL_HOMECATEGORYROWS.ROWID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field3() {
        return TMallHomecategoryrows.T_MALL_HOMECATEGORYROWS.IMAGE1;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field4() {
        return TMallHomecategoryrows.T_MALL_HOMECATEGORYROWS.URL1;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field5() {
        return TMallHomecategoryrows.T_MALL_HOMECATEGORYROWS.IMAGE2;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field6() {
        return TMallHomecategoryrows.T_MALL_HOMECATEGORYROWS.URL2;
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
    public Integer value2() {
        return getRowid();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value3() {
        return getImage1();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value4() {
        return getUrl1();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value5() {
        return getImage2();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value6() {
        return getUrl2();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TMallHomecategoryrowsRecord value1(Long value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TMallHomecategoryrowsRecord value2(Integer value) {
        setRowid(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TMallHomecategoryrowsRecord value3(String value) {
        setImage1(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TMallHomecategoryrowsRecord value4(String value) {
        setUrl1(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TMallHomecategoryrowsRecord value5(String value) {
        setImage2(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TMallHomecategoryrowsRecord value6(String value) {
        setUrl2(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TMallHomecategoryrowsRecord values(Long value1, Integer value2, String value3, String value4, String value5, String value6) {
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
     * Create a detached TMallHomecategoryrowsRecord
     */
    public TMallHomecategoryrowsRecord() {
        super(TMallHomecategoryrows.T_MALL_HOMECATEGORYROWS);
    }

    /**
     * Create a detached, initialised TMallHomecategoryrowsRecord
     */
    public TMallHomecategoryrowsRecord(Long id, Integer rowid, String image1, String url1, String image2, String url2) {
        super(TMallHomecategoryrows.T_MALL_HOMECATEGORYROWS);

        set(0, id);
        set(1, rowid);
        set(2, image1);
        set(3, url1);
        set(4, image2);
        set(5, url2);
    }
}
