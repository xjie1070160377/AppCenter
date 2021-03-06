/**
 * This class is generated by jOOQ
 */
package cn.mooc.jooq.demo.generated.tables.records;


import cn.mooc.jooq.demo.generated.tables.TMallArticles;

import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record13;
import org.jooq.Row13;
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
public class TMallArticlesRecord extends UpdatableRecordImpl<TMallArticlesRecord> implements Record13<Long, Long, String, String, String, Timestamp, Long, String, String, String, Byte, String, Integer> {

    private static final long serialVersionUID = -2022537195;

    /**
     * Setter for <code>appcenter_mall_data.t_mall_articles.Id</code>.
     */
    public void setId(Long value) {
        set(0, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_articles.Id</code>.
     */
    public Long getId() {
        return (Long) get(0);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_articles.CategoryId</code>. 文章分类ID
     */
    public void setCategoryid(Long value) {
        set(1, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_articles.CategoryId</code>. 文章分类ID
     */
    public Long getCategoryid() {
        return (Long) get(1);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_articles.Title</code>. 文章标题
     */
    public void setTitle(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_articles.Title</code>. 文章标题
     */
    public String getTitle() {
        return (String) get(2);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_articles.IconUrl</code>.
     */
    public void setIconurl(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_articles.IconUrl</code>.
     */
    public String getIconurl() {
        return (String) get(3);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_articles.Content</code>. 文档内容
     */
    public void setContent(String value) {
        set(4, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_articles.Content</code>. 文档内容
     */
    public String getContent() {
        return (String) get(4);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_articles.AddDate</code>.
     */
    public void setAdddate(Timestamp value) {
        set(5, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_articles.AddDate</code>.
     */
    public Timestamp getAdddate() {
        return (Timestamp) get(5);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_articles.DisplaySequence</code>.
     */
    public void setDisplaysequence(Long value) {
        set(6, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_articles.DisplaySequence</code>.
     */
    public Long getDisplaysequence() {
        return (Long) get(6);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_articles.Meta_Title</code>. SEO标题
     */
    public void setMetaTitle(String value) {
        set(7, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_articles.Meta_Title</code>. SEO标题
     */
    public String getMetaTitle() {
        return (String) get(7);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_articles.Meta_Description</code>. SEO说明
     */
    public void setMetaDescription(String value) {
        set(8, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_articles.Meta_Description</code>. SEO说明
     */
    public String getMetaDescription() {
        return (String) get(8);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_articles.Meta_Keywords</code>. SEO关键字
     */
    public void setMetaKeywords(String value) {
        set(9, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_articles.Meta_Keywords</code>. SEO关键字
     */
    public String getMetaKeywords() {
        return (String) get(9);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_articles.IsRelease</code>. 是否显示
     */
    public void setIsrelease(Byte value) {
        set(10, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_articles.IsRelease</code>. 是否显示
     */
    public Byte getIsrelease() {
        return (Byte) get(10);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_articles.LinkUrl</code>.
     */
    public void setLinkurl(String value) {
        set(11, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_articles.LinkUrl</code>.
     */
    public String getLinkurl() {
        return (String) get(11);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_articles.IsTop</code>.
     */
    public void setIstop(Integer value) {
        set(12, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_articles.IsTop</code>.
     */
    public Integer getIstop() {
        return (Integer) get(12);
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
    // Record13 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row13<Long, Long, String, String, String, Timestamp, Long, String, String, String, Byte, String, Integer> fieldsRow() {
        return (Row13) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row13<Long, Long, String, String, String, Timestamp, Long, String, String, String, Byte, String, Integer> valuesRow() {
        return (Row13) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Long> field1() {
        return TMallArticles.T_MALL_ARTICLES.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Long> field2() {
        return TMallArticles.T_MALL_ARTICLES.CATEGORYID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field3() {
        return TMallArticles.T_MALL_ARTICLES.TITLE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field4() {
        return TMallArticles.T_MALL_ARTICLES.ICONURL;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field5() {
        return TMallArticles.T_MALL_ARTICLES.CONTENT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field6() {
        return TMallArticles.T_MALL_ARTICLES.ADDDATE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Long> field7() {
        return TMallArticles.T_MALL_ARTICLES.DISPLAYSEQUENCE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field8() {
        return TMallArticles.T_MALL_ARTICLES.META_TITLE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field9() {
        return TMallArticles.T_MALL_ARTICLES.META_DESCRIPTION;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field10() {
        return TMallArticles.T_MALL_ARTICLES.META_KEYWORDS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Byte> field11() {
        return TMallArticles.T_MALL_ARTICLES.ISRELEASE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field12() {
        return TMallArticles.T_MALL_ARTICLES.LINKURL;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field13() {
        return TMallArticles.T_MALL_ARTICLES.ISTOP;
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
        return getCategoryid();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value3() {
        return getTitle();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value4() {
        return getIconurl();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value5() {
        return getContent();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value6() {
        return getAdddate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long value7() {
        return getDisplaysequence();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value8() {
        return getMetaTitle();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value9() {
        return getMetaDescription();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value10() {
        return getMetaKeywords();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte value11() {
        return getIsrelease();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value12() {
        return getLinkurl();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value13() {
        return getIstop();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TMallArticlesRecord value1(Long value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TMallArticlesRecord value2(Long value) {
        setCategoryid(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TMallArticlesRecord value3(String value) {
        setTitle(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TMallArticlesRecord value4(String value) {
        setIconurl(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TMallArticlesRecord value5(String value) {
        setContent(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TMallArticlesRecord value6(Timestamp value) {
        setAdddate(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TMallArticlesRecord value7(Long value) {
        setDisplaysequence(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TMallArticlesRecord value8(String value) {
        setMetaTitle(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TMallArticlesRecord value9(String value) {
        setMetaDescription(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TMallArticlesRecord value10(String value) {
        setMetaKeywords(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TMallArticlesRecord value11(Byte value) {
        setIsrelease(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TMallArticlesRecord value12(String value) {
        setLinkurl(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TMallArticlesRecord value13(Integer value) {
        setIstop(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TMallArticlesRecord values(Long value1, Long value2, String value3, String value4, String value5, Timestamp value6, Long value7, String value8, String value9, String value10, Byte value11, String value12, Integer value13) {
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
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached TMallArticlesRecord
     */
    public TMallArticlesRecord() {
        super(TMallArticles.T_MALL_ARTICLES);
    }

    /**
     * Create a detached, initialised TMallArticlesRecord
     */
    public TMallArticlesRecord(Long id, Long categoryid, String title, String iconurl, String content, Timestamp adddate, Long displaysequence, String metaTitle, String metaDescription, String metaKeywords, Byte isrelease, String linkurl, Integer istop) {
        super(TMallArticles.T_MALL_ARTICLES);

        set(0, id);
        set(1, categoryid);
        set(2, title);
        set(3, iconurl);
        set(4, content);
        set(5, adddate);
        set(6, displaysequence);
        set(7, metaTitle);
        set(8, metaDescription);
        set(9, metaKeywords);
        set(10, isrelease);
        set(11, linkurl);
        set(12, istop);
    }
}
