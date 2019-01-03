/**
 * This class is generated by jOOQ
 */
package cn.mooc.jooq.demo.generated.tables;


import cn.mooc.jooq.demo.generated.AppcenterMallData;
import cn.mooc.jooq.demo.generated.Keys;
import cn.mooc.jooq.demo.generated.tables.records.TScmArticleRecord;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Identity;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.TableImpl;


/**
 * 商品表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.8.4"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class TScmArticle extends TableImpl<TScmArticleRecord> {

    private static final long serialVersionUID = 1381385606;

    /**
     * The reference instance of <code>appcenter_mall_data.t_scm_article</code>
     */
    public static final TScmArticle T_SCM_ARTICLE = new TScmArticle();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<TScmArticleRecord> getRecordType() {
        return TScmArticleRecord.class;
    }

    /**
     * The column <code>appcenter_mall_data.t_scm_article.articleId</code>.
     */
    public final TableField<TScmArticleRecord, Integer> ARTICLEID = createField("articleId", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>appcenter_mall_data.t_scm_article.name</code>. 名称
     */
    public final TableField<TScmArticleRecord, String> NAME = createField("name", org.jooq.impl.SQLDataType.VARCHAR.length(20), this, "名称");

    /**
     * The column <code>appcenter_mall_data.t_scm_article.spec</code>. 规格
     */
    public final TableField<TScmArticleRecord, String> SPEC = createField("spec", org.jooq.impl.SQLDataType.VARCHAR.length(20), this, "规格");

    /**
     * The column <code>appcenter_mall_data.t_scm_article.brand</code>. 品牌
     */
    public final TableField<TScmArticleRecord, String> BRAND = createField("brand", org.jooq.impl.SQLDataType.VARCHAR.length(20), this, "品牌");

    /**
     * The column <code>appcenter_mall_data.t_scm_article.util</code>. 单位
     */
    public final TableField<TScmArticleRecord, String> UTIL = createField("util", org.jooq.impl.SQLDataType.VARCHAR.length(20), this, "单位");

    /**
     * The column <code>appcenter_mall_data.t_scm_article.onePrice</code>. 一销价
     */
    public final TableField<TScmArticleRecord, BigDecimal> ONEPRICE = createField("onePrice", org.jooq.impl.SQLDataType.DECIMAL.precision(12, 2), this, "一销价");

    /**
     * The column <code>appcenter_mall_data.t_scm_article.twoPrice</code>. 二销价
     */
    public final TableField<TScmArticleRecord, BigDecimal> TWOPRICE = createField("twoPrice", org.jooq.impl.SQLDataType.DECIMAL.precision(12, 2), this, "二销价");

    /**
     * The column <code>appcenter_mall_data.t_scm_article.threePrice</code>. 三销价
     */
    public final TableField<TScmArticleRecord, BigDecimal> THREEPRICE = createField("threePrice", org.jooq.impl.SQLDataType.DECIMAL.precision(12, 2), this, "三销价");

    /**
     * The column <code>appcenter_mall_data.t_scm_article.newPrice</code>. 最新进价
     */
    public final TableField<TScmArticleRecord, BigDecimal> NEWPRICE = createField("newPrice", org.jooq.impl.SQLDataType.DECIMAL.precision(12, 2), this, "最新进价");

    /**
     * The column <code>appcenter_mall_data.t_scm_article.manufactureTime</code>. 有效日期
     */
    public final TableField<TScmArticleRecord, Timestamp> MANUFACTURETIME = createField("manufactureTime", org.jooq.impl.SQLDataType.TIMESTAMP, this, "有效日期");

    /**
     * The column <code>appcenter_mall_data.t_scm_article.points</code>. 可获积分
     */
    public final TableField<TScmArticleRecord, Integer> POINTS = createField("points", org.jooq.impl.SQLDataType.INTEGER, this, "可获积分");

    /**
     * The column <code>appcenter_mall_data.t_scm_article.articleClassId</code>. 商品种类ID
     */
    public final TableField<TScmArticleRecord, Integer> ARTICLECLASSID = createField("articleClassId", org.jooq.impl.SQLDataType.INTEGER, this, "商品种类ID");

    /**
     * The column <code>appcenter_mall_data.t_scm_article.stockTotal</code>. 当前库存
     */
    public final TableField<TScmArticleRecord, Integer> STOCKTOTAL = createField("stockTotal", org.jooq.impl.SQLDataType.INTEGER, this, "当前库存");

    /**
     * The column <code>appcenter_mall_data.t_scm_article.status</code>. 状态
     */
    public final TableField<TScmArticleRecord, Byte> STATUS = createField("status", org.jooq.impl.SQLDataType.TINYINT, this, "状态");

    /**
     * Create a <code>appcenter_mall_data.t_scm_article</code> table reference
     */
    public TScmArticle() {
        this("t_scm_article", null);
    }

    /**
     * Create an aliased <code>appcenter_mall_data.t_scm_article</code> table reference
     */
    public TScmArticle(String alias) {
        this(alias, T_SCM_ARTICLE);
    }

    private TScmArticle(String alias, Table<TScmArticleRecord> aliased) {
        this(alias, aliased, null);
    }

    private TScmArticle(String alias, Table<TScmArticleRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "商品表");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Schema getSchema() {
        return AppcenterMallData.APPCENTER_MALL_DATA;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<TScmArticleRecord, Integer> getIdentity() {
        return Keys.IDENTITY_T_SCM_ARTICLE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<TScmArticleRecord> getPrimaryKey() {
        return Keys.KEY_T_SCM_ARTICLE_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<TScmArticleRecord>> getKeys() {
        return Arrays.<UniqueKey<TScmArticleRecord>>asList(Keys.KEY_T_SCM_ARTICLE_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ForeignKey<TScmArticleRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<TScmArticleRecord, ?>>asList(Keys.T_SCM_ARTICLE_IBFK_1);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TScmArticle as(String alias) {
        return new TScmArticle(alias, this);
    }

    /**
     * Rename this table
     */
    public TScmArticle rename(String name) {
        return new TScmArticle(name, null);
    }
}