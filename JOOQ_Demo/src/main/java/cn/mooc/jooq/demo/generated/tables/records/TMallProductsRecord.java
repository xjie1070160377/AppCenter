/**
 * This class is generated by jOOQ
 */
package cn.mooc.jooq.demo.generated.tables.records;


import cn.mooc.jooq.demo.generated.tables.TMallProducts;

import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Record1;
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
public class TMallProductsRecord extends UpdatableRecordImpl<TMallProductsRecord> {

    private static final long serialVersionUID = -1339857993;

    /**
     * Setter for <code>appcenter_mall_data.t_mall_products.Id</code>.
     */
    public void setId(Long value) {
        set(0, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_products.Id</code>.
     */
    public Long getId() {
        return (Long) get(0);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_products.ShopId</code>. 店铺ID
     */
    public void setShopid(Long value) {
        set(1, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_products.ShopId</code>. 店铺ID
     */
    public Long getShopid() {
        return (Long) get(1);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_products.CategoryId</code>. 分类ID
     */
    public void setCategoryid(Long value) {
        set(2, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_products.CategoryId</code>. 分类ID
     */
    public Long getCategoryid() {
        return (Long) get(2);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_products.CategoryPath</code>. 分类路径
     */
    public void setCategorypath(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_products.CategoryPath</code>. 分类路径
     */
    public String getCategorypath() {
        return (String) get(3);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_products.TypeId</code>. 类型ID
     */
    public void setTypeid(Long value) {
        set(4, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_products.TypeId</code>. 类型ID
     */
    public Long getTypeid() {
        return (Long) get(4);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_products.BrandId</code>. 品牌ID
     */
    public void setBrandid(Long value) {
        set(5, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_products.BrandId</code>. 品牌ID
     */
    public Long getBrandid() {
        return (Long) get(5);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_products.ProductName</code>. 商品名称
     */
    public void setProductname(String value) {
        set(6, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_products.ProductName</code>. 商品名称
     */
    public String getProductname() {
        return (String) get(6);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_products.ProductCode</code>. 商品编号
     */
    public void setProductcode(String value) {
        set(7, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_products.ProductCode</code>. 商品编号
     */
    public String getProductcode() {
        return (String) get(7);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_products.ShortDescription</code>. 广告词
     */
    public void setShortdescription(String value) {
        set(8, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_products.ShortDescription</code>. 广告词
     */
    public String getShortdescription() {
        return (String) get(8);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_products.SaleStatus</code>. 销售状态
     */
    public void setSalestatus(Integer value) {
        set(9, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_products.SaleStatus</code>. 销售状态
     */
    public Integer getSalestatus() {
        return (Integer) get(9);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_products.AuditStatus</code>. 审核状态
     */
    public void setAuditstatus(Integer value) {
        set(10, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_products.AuditStatus</code>. 审核状态
     */
    public Integer getAuditstatus() {
        return (Integer) get(10);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_products.AddedDate</code>. 添加日期
     */
    public void setAddeddate(Timestamp value) {
        set(11, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_products.AddedDate</code>. 添加日期
     */
    public Timestamp getAddeddate() {
        return (Timestamp) get(11);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_products.DisplaySequence</code>. 显示顺序
     */
    public void setDisplaysequence(Long value) {
        set(12, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_products.DisplaySequence</code>. 显示顺序
     */
    public Long getDisplaysequence() {
        return (Long) get(12);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_products.ImagePath</code>. 存放图片的目录
     */
    public void setImagepath(String value) {
        set(13, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_products.ImagePath</code>. 存放图片的目录
     */
    public String getImagepath() {
        return (String) get(13);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_products.MarketPrice</code>. 市场价
     */
    public void setMarketprice(BigDecimal value) {
        set(14, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_products.MarketPrice</code>. 市场价
     */
    public BigDecimal getMarketprice() {
        return (BigDecimal) get(14);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_products.MinSalePrice</code>. 最小销售价
     */
    public void setMinsaleprice(BigDecimal value) {
        set(15, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_products.MinSalePrice</code>. 最小销售价
     */
    public BigDecimal getMinsaleprice() {
        return (BigDecimal) get(15);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_products.HasSKU</code>. 是否有SKU
     */
    public void setHassku(Byte value) {
        set(16, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_products.HasSKU</code>. 是否有SKU
     */
    public Byte getHassku() {
        return (Byte) get(16);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_products.VistiCounts</code>. 浏览次数
     */
    public void setVisticounts(Long value) {
        set(17, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_products.VistiCounts</code>. 浏览次数
     */
    public Long getVisticounts() {
        return (Long) get(17);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_products.SaleCounts</code>. 销售量
     */
    public void setSalecounts(Long value) {
        set(18, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_products.SaleCounts</code>. 销售量
     */
    public Long getSalecounts() {
        return (Long) get(18);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_products.FreightTemplateId</code>. 运费模板ID
     */
    public void setFreighttemplateid(Long value) {
        set(19, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_products.FreightTemplateId</code>. 运费模板ID
     */
    public Long getFreighttemplateid() {
        return (Long) get(19);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_products.Weight</code>. 重量
     */
    public void setWeight(BigDecimal value) {
        set(20, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_products.Weight</code>. 重量
     */
    public BigDecimal getWeight() {
        return (BigDecimal) get(20);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_products.Volume</code>. 体积
     */
    public void setVolume(BigDecimal value) {
        set(21, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_products.Volume</code>. 体积
     */
    public BigDecimal getVolume() {
        return (BigDecimal) get(21);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_products.Quantity</code>. 数量
     */
    public void setQuantity(Integer value) {
        set(22, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_products.Quantity</code>. 数量
     */
    public Integer getQuantity() {
        return (Integer) get(22);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_products.MeasureUnit</code>. 计量单位
     */
    public void setMeasureunit(String value) {
        set(23, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_products.MeasureUnit</code>. 计量单位
     */
    public String getMeasureunit() {
        return (String) get(23);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_products.EditStatus</code>. 修改状态 0 正常 1己修改 2待审核 3 己修改并待审核
     */
    public void setEditstatus(Integer value) {
        set(24, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_products.EditStatus</code>. 修改状态 0 正常 1己修改 2待审核 3 己修改并待审核
     */
    public Integer getEditstatus() {
        return (Integer) get(24);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_products.DistributionType</code>. 分销类型 (0:普通商品,1:分销商品,2:代理商品)
     */
    public void setDistributiontype(Integer value) {
        set(25, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_products.DistributionType</code>. 分销类型 (0:普通商品,1:分销商品,2:代理商品)
     */
    public Integer getDistributiontype() {
        return (Integer) get(25);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_products.DistributionSeqNo</code>. 分销调价序号
     */
    public void setDistributionseqno(Integer value) {
        set(26, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_products.DistributionSeqNo</code>. 分销调价序号
     */
    public Integer getDistributionseqno() {
        return (Integer) get(26);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_products.PurchasePrice</code>. 进货价
     */
    public void setPurchaseprice(BigDecimal value) {
        set(27, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_products.PurchasePrice</code>. 进货价
     */
    public BigDecimal getPurchaseprice() {
        return (BigDecimal) get(27);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_products.GuidePrice</code>. 指导售价
     */
    public void setGuideprice(BigDecimal value) {
        set(28, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_products.GuidePrice</code>. 指导售价
     */
    public BigDecimal getGuideprice() {
        return (BigDecimal) get(28);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_products.CommissionType</code>. 提成方式
     */
    public void setCommissiontype(Integer value) {
        set(29, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_products.CommissionType</code>. 提成方式
     */
    public Integer getCommissiontype() {
        return (Integer) get(29);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_products.CommissionRate</code>. 提成比例
     */
    public void setCommissionrate(BigDecimal value) {
        set(30, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_products.CommissionRate</code>. 提成比例
     */
    public BigDecimal getCommissionrate() {
        return (BigDecimal) get(30);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_products.ProhibitPrice</code>. 禁止代理商调价
     */
    public void setProhibitprice(Integer value) {
        set(31, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_products.ProhibitPrice</code>. 禁止代理商调价
     */
    public Integer getProhibitprice() {
        return (Integer) get(31);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_products.WeChatDistribution</code>.
     */
    public void setWechatdistribution(UInteger value) {
        set(32, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_products.WeChatDistribution</code>.
     */
    public UInteger getWechatdistribution() {
        return (UInteger) get(32);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_products.FirstCommissionRate</code>. 一级分销提成比例
     */
    public void setFirstcommissionrate(BigDecimal value) {
        set(33, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_products.FirstCommissionRate</code>. 一级分销提成比例
     */
    public BigDecimal getFirstcommissionrate() {
        return (BigDecimal) get(33);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_products.SecondCommissionRate</code>. 二级分销提成比例
     */
    public void setSecondcommissionrate(BigDecimal value) {
        set(34, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_products.SecondCommissionRate</code>. 二级分销提成比例
     */
    public BigDecimal getSecondcommissionrate() {
        return (BigDecimal) get(34);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_products.ThirdCommissionRate</code>. 三级分销提成比例
     */
    public void setThirdcommissionrate(BigDecimal value) {
        set(35, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_products.ThirdCommissionRate</code>. 三级分销提成比例
     */
    public BigDecimal getThirdcommissionrate() {
        return (BigDecimal) get(35);
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
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached TMallProductsRecord
     */
    public TMallProductsRecord() {
        super(TMallProducts.T_MALL_PRODUCTS);
    }

    /**
     * Create a detached, initialised TMallProductsRecord
     */
    public TMallProductsRecord(Long id, Long shopid, Long categoryid, String categorypath, Long typeid, Long brandid, String productname, String productcode, String shortdescription, Integer salestatus, Integer auditstatus, Timestamp addeddate, Long displaysequence, String imagepath, BigDecimal marketprice, BigDecimal minsaleprice, Byte hassku, Long visticounts, Long salecounts, Long freighttemplateid, BigDecimal weight, BigDecimal volume, Integer quantity, String measureunit, Integer editstatus, Integer distributiontype, Integer distributionseqno, BigDecimal purchaseprice, BigDecimal guideprice, Integer commissiontype, BigDecimal commissionrate, Integer prohibitprice, UInteger wechatdistribution, BigDecimal firstcommissionrate, BigDecimal secondcommissionrate, BigDecimal thirdcommissionrate) {
        super(TMallProducts.T_MALL_PRODUCTS);

        set(0, id);
        set(1, shopid);
        set(2, categoryid);
        set(3, categorypath);
        set(4, typeid);
        set(5, brandid);
        set(6, productname);
        set(7, productcode);
        set(8, shortdescription);
        set(9, salestatus);
        set(10, auditstatus);
        set(11, addeddate);
        set(12, displaysequence);
        set(13, imagepath);
        set(14, marketprice);
        set(15, minsaleprice);
        set(16, hassku);
        set(17, visticounts);
        set(18, salecounts);
        set(19, freighttemplateid);
        set(20, weight);
        set(21, volume);
        set(22, quantity);
        set(23, measureunit);
        set(24, editstatus);
        set(25, distributiontype);
        set(26, distributionseqno);
        set(27, purchaseprice);
        set(28, guideprice);
        set(29, commissiontype);
        set(30, commissionrate);
        set(31, prohibitprice);
        set(32, wechatdistribution);
        set(33, firstcommissionrate);
        set(34, secondcommissionrate);
        set(35, thirdcommissionrate);
    }
}