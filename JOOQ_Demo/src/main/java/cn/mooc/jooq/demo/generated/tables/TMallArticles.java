/**
 * This class is generated by jOOQ
 */
package cn.mooc.jooq.demo.generated.tables;


import cn.mooc.jooq.demo.generated.AppcenterMallData;
import cn.mooc.jooq.demo.generated.Keys;
import cn.mooc.jooq.demo.generated.tables.records.TMallArticlesRecord;

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
public class TMallArticles extends TableImpl<TMallArticlesRecord> {

    private static final long serialVersionUID = 431890198;

    /**
     * The reference instance of <code>appcenter_mall_data.t_mall_articles</code>
     */
    public static final TMallArticles T_MALL_ARTICLES = new TMallArticles();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<TMallArticlesRecord> getRecordType() {
        return TMallArticlesRecord.class;
    }

    /**
     * The column <code>appcenter_mall_data.t_mall_articles.Id</code>.
     */
    public final TableField<TMallArticlesRecord, Long> ID = createField("Id", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>appcenter_mall_data.t_mall_articles.CategoryId</code>. 文章分类ID
     */
    public final TableField<TMallArticlesRecord, Long> CATEGORYID = createField("CategoryId", org.jooq.impl.SQLDataType.BIGINT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.BIGINT)), this, "文章分类ID");

    /**
     * The column <code>appcenter_mall_data.t_mall_articles.Title</code>. 文章标题
     */
    public final TableField<TMallArticlesRecord, String> TITLE = createField("Title", org.jooq.impl.SQLDataType.VARCHAR.length(100).nullable(false), this, "文章标题");

    /**
     * The column <code>appcenter_mall_data.t_mall_articles.IconUrl</code>.
     */
    public final TableField<TMallArticlesRecord, String> ICONURL = createField("IconUrl", org.jooq.impl.SQLDataType.VARCHAR.length(100), this, "");

    /**
     * The column <code>appcenter_mall_data.t_mall_articles.Content</code>. 文档内容
     */
    public final TableField<TMallArticlesRecord, String> CONTENT = createField("Content", org.jooq.impl.SQLDataType.CLOB.nullable(false), this, "文档内容");

    /**
     * The column <code>appcenter_mall_data.t_mall_articles.AddDate</code>.
     */
    public final TableField<TMallArticlesRecord, Timestamp> ADDDATE = createField("AddDate", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false), this, "");

    /**
     * The column <code>appcenter_mall_data.t_mall_articles.DisplaySequence</code>.
     */
    public final TableField<TMallArticlesRecord, Long> DISPLAYSEQUENCE = createField("DisplaySequence", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>appcenter_mall_data.t_mall_articles.Meta_Title</code>. SEO标题
     */
    public final TableField<TMallArticlesRecord, String> META_TITLE = createField("Meta_Title", org.jooq.impl.SQLDataType.CLOB, this, "SEO标题");

    /**
     * The column <code>appcenter_mall_data.t_mall_articles.Meta_Description</code>. SEO说明
     */
    public final TableField<TMallArticlesRecord, String> META_DESCRIPTION = createField("Meta_Description", org.jooq.impl.SQLDataType.CLOB, this, "SEO说明");

    /**
     * The column <code>appcenter_mall_data.t_mall_articles.Meta_Keywords</code>. SEO关键字
     */
    public final TableField<TMallArticlesRecord, String> META_KEYWORDS = createField("Meta_Keywords", org.jooq.impl.SQLDataType.CLOB, this, "SEO关键字");

    /**
     * The column <code>appcenter_mall_data.t_mall_articles.IsRelease</code>. 是否显示
     */
    public final TableField<TMallArticlesRecord, Byte> ISRELEASE = createField("IsRelease", org.jooq.impl.SQLDataType.TINYINT.nullable(false), this, "是否显示");

    /**
     * The column <code>appcenter_mall_data.t_mall_articles.LinkUrl</code>.
     */
    public final TableField<TMallArticlesRecord, String> LINKURL = createField("LinkUrl", org.jooq.impl.SQLDataType.VARCHAR.length(200), this, "");

    /**
     * The column <code>appcenter_mall_data.t_mall_articles.IsTop</code>.
     */
    public final TableField<TMallArticlesRecord, Integer> ISTOP = createField("IsTop", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * Create a <code>appcenter_mall_data.t_mall_articles</code> table reference
     */
    public TMallArticles() {
        this("t_mall_articles", null);
    }

    /**
     * Create an aliased <code>appcenter_mall_data.t_mall_articles</code> table reference
     */
    public TMallArticles(String alias) {
        this(alias, T_MALL_ARTICLES);
    }

    private TMallArticles(String alias, Table<TMallArticlesRecord> aliased) {
        this(alias, aliased, null);
    }

    private TMallArticles(String alias, Table<TMallArticlesRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "");
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
    public Identity<TMallArticlesRecord, Long> getIdentity() {
        return Keys.IDENTITY_T_MALL_ARTICLES;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<TMallArticlesRecord> getPrimaryKey() {
        return Keys.KEY_T_MALL_ARTICLES_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<TMallArticlesRecord>> getKeys() {
        return Arrays.<UniqueKey<TMallArticlesRecord>>asList(Keys.KEY_T_MALL_ARTICLES_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ForeignKey<TMallArticlesRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<TMallArticlesRecord, ?>>asList(Keys.T_MALL_ARTICLES_IBFK_1);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TMallArticles as(String alias) {
        return new TMallArticles(alias, this);
    }

    /**
     * Rename this table
     */
    public TMallArticles rename(String name) {
        return new TMallArticles(name, null);
    }
}
