/**
 * This class is generated by jOOQ
 */
package cn.mooc.jooq.demo.generated.tables.records;


import cn.mooc.jooq.demo.generated.tables.TMallVshop;

import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record14;
import org.jooq.Row14;
import org.jooq.impl.UpdatableRecordImpl;
import org.jooq.types.UInteger;


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
public class TMallVshopRecord extends UpdatableRecordImpl<TMallVshopRecord> implements Record14<Long, String, Long, Timestamp, Integer, Integer, Integer, String, String, String, String, String, UInteger, UInteger> {

    private static final long serialVersionUID = 1318035304;

    /**
     * Setter for <code>appcenter_mall_data.t_mall_vshop.Id</code>.
     */
    public void setId(Long value) {
        set(0, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_vshop.Id</code>.
     */
    public Long getId() {
        return (Long) get(0);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_vshop.Name</code>. 名称
     */
    public void setName(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_vshop.Name</code>. 名称
     */
    public String getName() {
        return (String) get(1);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_vshop.ShopId</code>. 店铺ID
     */
    public void setShopid(Long value) {
        set(2, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_vshop.ShopId</code>. 店铺ID
     */
    public Long getShopid() {
        return (Long) get(2);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_vshop.CreateTime</code>. 创建日期
     */
    public void setCreatetime(Timestamp value) {
        set(3, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_vshop.CreateTime</code>. 创建日期
     */
    public Timestamp getCreatetime() {
        return (Timestamp) get(3);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_vshop.VisitNum</code>. 历览次数
     */
    public void setVisitnum(Integer value) {
        set(4, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_vshop.VisitNum</code>. 历览次数
     */
    public Integer getVisitnum() {
        return (Integer) get(4);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_vshop.buyNum</code>. 购买数量
     */
    public void setBuynum(Integer value) {
        set(5, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_vshop.buyNum</code>. 购买数量
     */
    public Integer getBuynum() {
        return (Integer) get(5);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_vshop.State</code>. 状态
     */
    public void setState(Integer value) {
        set(6, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_vshop.State</code>. 状态
     */
    public Integer getState() {
        return (Integer) get(6);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_vshop.Logo</code>. LOGO
     */
    public void setLogo(String value) {
        set(7, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_vshop.Logo</code>. LOGO
     */
    public String getLogo() {
        return (String) get(7);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_vshop.BackgroundImage</code>. 背景图
     */
    public void setBackgroundimage(String value) {
        set(8, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_vshop.BackgroundImage</code>. 背景图
     */
    public String getBackgroundimage() {
        return (String) get(8);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_vshop.Description</code>. 详情
     */
    public void setDescription(String value) {
        set(9, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_vshop.Description</code>. 详情
     */
    public String getDescription() {
        return (String) get(9);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_vshop.Tags</code>. 标签
     */
    public void setTags(String value) {
        set(10, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_vshop.Tags</code>. 标签
     */
    public String getTags() {
        return (String) get(10);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_vshop.HomePageTitle</code>. 微信首页显示的标题
     */
    public void setHomepagetitle(String value) {
        set(11, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_vshop.HomePageTitle</code>. 微信首页显示的标题
     */
    public String getHomepagetitle() {
        return (String) get(11);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_vshop.IsDistribution</code>. 微信分销店
     */
    public void setIsdistribution(UInteger value) {
        set(12, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_vshop.IsDistribution</code>. 微信分销店
     */
    public UInteger getIsdistribution() {
        return (UInteger) get(12);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_vshop.Distributions</code>. 微信分销代理商人数
     */
    public void setDistributions(UInteger value) {
        set(13, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_vshop.Distributions</code>. 微信分销代理商人数
     */
    public UInteger getDistributions() {
        return (UInteger) get(13);
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
    // Record14 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row14<Long, String, Long, Timestamp, Integer, Integer, Integer, String, String, String, String, String, UInteger, UInteger> fieldsRow() {
        return (Row14) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row14<Long, String, Long, Timestamp, Integer, Integer, Integer, String, String, String, String, String, UInteger, UInteger> valuesRow() {
        return (Row14) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Long> field1() {
        return TMallVshop.T_MALL_VSHOP.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field2() {
        return TMallVshop.T_MALL_VSHOP.NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Long> field3() {
        return TMallVshop.T_MALL_VSHOP.SHOPID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field4() {
        return TMallVshop.T_MALL_VSHOP.CREATETIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field5() {
        return TMallVshop.T_MALL_VSHOP.VISITNUM;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field6() {
        return TMallVshop.T_MALL_VSHOP.BUYNUM;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field7() {
        return TMallVshop.T_MALL_VSHOP.STATE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field8() {
        return TMallVshop.T_MALL_VSHOP.LOGO;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field9() {
        return TMallVshop.T_MALL_VSHOP.BACKGROUNDIMAGE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field10() {
        return TMallVshop.T_MALL_VSHOP.DESCRIPTION;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field11() {
        return TMallVshop.T_MALL_VSHOP.TAGS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field12() {
        return TMallVshop.T_MALL_VSHOP.HOMEPAGETITLE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<UInteger> field13() {
        return TMallVshop.T_MALL_VSHOP.ISDISTRIBUTION;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<UInteger> field14() {
        return TMallVshop.T_MALL_VSHOP.DISTRIBUTIONS;
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
    public Long value3() {
        return getShopid();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value4() {
        return getCreatetime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value5() {
        return getVisitnum();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value6() {
        return getBuynum();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value7() {
        return getState();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value8() {
        return getLogo();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value9() {
        return getBackgroundimage();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value10() {
        return getDescription();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value11() {
        return getTags();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value12() {
        return getHomepagetitle();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UInteger value13() {
        return getIsdistribution();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UInteger value14() {
        return getDistributions();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TMallVshopRecord value1(Long value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TMallVshopRecord value2(String value) {
        setName(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TMallVshopRecord value3(Long value) {
        setShopid(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TMallVshopRecord value4(Timestamp value) {
        setCreatetime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TMallVshopRecord value5(Integer value) {
        setVisitnum(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TMallVshopRecord value6(Integer value) {
        setBuynum(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TMallVshopRecord value7(Integer value) {
        setState(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TMallVshopRecord value8(String value) {
        setLogo(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TMallVshopRecord value9(String value) {
        setBackgroundimage(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TMallVshopRecord value10(String value) {
        setDescription(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TMallVshopRecord value11(String value) {
        setTags(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TMallVshopRecord value12(String value) {
        setHomepagetitle(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TMallVshopRecord value13(UInteger value) {
        setIsdistribution(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TMallVshopRecord value14(UInteger value) {
        setDistributions(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TMallVshopRecord values(Long value1, String value2, Long value3, Timestamp value4, Integer value5, Integer value6, Integer value7, String value8, String value9, String value10, String value11, String value12, UInteger value13, UInteger value14) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        value7(value7);
        value8(value8);
        value9(value9);
        value10(value10);
        value11(value11);
        value12(value12);
        value13(value13);
        value14(value14);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached TMallVshopRecord
     */
    public TMallVshopRecord() {
        super(TMallVshop.T_MALL_VSHOP);
    }

    /**
     * Create a detached, initialised TMallVshopRecord
     */
    public TMallVshopRecord(Long id, String name, Long shopid, Timestamp createtime, Integer visitnum, Integer buynum, Integer state, String logo, String backgroundimage, String description, String tags, String homepagetitle, UInteger isdistribution, UInteger distributions) {
        super(TMallVshop.T_MALL_VSHOP);

        set(0, id);
        set(1, name);
        set(2, shopid);
        set(3, createtime);
        set(4, visitnum);
        set(5, buynum);
        set(6, state);
        set(7, logo);
        set(8, backgroundimage);
        set(9, description);
        set(10, tags);
        set(11, homepagetitle);
        set(12, isdistribution);
        set(13, distributions);
    }
}
