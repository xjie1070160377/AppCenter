/**
 * This class is generated by jOOQ
 */
package cn.mooc.jooq.demo.generated.tables.records;


import cn.mooc.jooq.demo.generated.tables.TMallImageads;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record5;
import org.jooq.Row5;
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
public class TMallImageadsRecord extends UpdatableRecordImpl<TMallImageadsRecord> implements Record5<Long, Long, String, String, Integer> {

    private static final long serialVersionUID = 1104645715;

    /**
     * Setter for <code>appcenter_mall_data.t_mall_imageads.Id</code>.
     */
    public void setId(Long value) {
        set(0, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_imageads.Id</code>.
     */
    public Long getId() {
        return (Long) get(0);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_imageads.ShopId</code>.
     */
    public void setShopid(Long value) {
        set(1, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_imageads.ShopId</code>.
     */
    public Long getShopid() {
        return (Long) get(1);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_imageads.ImageUrl</code>. 图片的存放URL
     */
    public void setImageurl(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_imageads.ImageUrl</code>. 图片的存放URL
     */
    public String getImageurl() {
        return (String) get(2);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_imageads.Url</code>. 图片的调整地址
     */
    public void setUrl(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_imageads.Url</code>. 图片的调整地址
     */
    public String getUrl() {
        return (String) get(3);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_imageads.templateTypeId</code>.
     */
    public void setTemplatetypeid(Integer value) {
        set(4, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_imageads.templateTypeId</code>.
     */
    public Integer getTemplatetypeid() {
        return (Integer) get(4);
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
    // Record5 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row5<Long, Long, String, String, Integer> fieldsRow() {
        return (Row5) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row5<Long, Long, String, String, Integer> valuesRow() {
        return (Row5) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Long> field1() {
        return TMallImageads.T_MALL_IMAGEADS.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Long> field2() {
        return TMallImageads.T_MALL_IMAGEADS.SHOPID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field3() {
        return TMallImageads.T_MALL_IMAGEADS.IMAGEURL;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field4() {
        return TMallImageads.T_MALL_IMAGEADS.URL;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field5() {
        return TMallImageads.T_MALL_IMAGEADS.TEMPLATETYPEID;
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
    public String value3() {
        return getImageurl();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value4() {
        return getUrl();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value5() {
        return getTemplatetypeid();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TMallImageadsRecord value1(Long value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TMallImageadsRecord value2(Long value) {
        setShopid(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TMallImageadsRecord value3(String value) {
        setImageurl(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TMallImageadsRecord value4(String value) {
        setUrl(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TMallImageadsRecord value5(Integer value) {
        setTemplatetypeid(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TMallImageadsRecord values(Long value1, Long value2, String value3, String value4, Integer value5) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached TMallImageadsRecord
     */
    public TMallImageadsRecord() {
        super(TMallImageads.T_MALL_IMAGEADS);
    }

    /**
     * Create a detached, initialised TMallImageadsRecord
     */
    public TMallImageadsRecord(Long id, Long shopid, String imageurl, String url, Integer templatetypeid) {
        super(TMallImageads.T_MALL_IMAGEADS);

        set(0, id);
        set(1, shopid);
        set(2, imageurl);
        set(3, url);
        set(4, templatetypeid);
    }
}
