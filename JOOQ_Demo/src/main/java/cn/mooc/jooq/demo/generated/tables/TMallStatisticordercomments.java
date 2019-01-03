/**
 * This class is generated by jOOQ
 */
package cn.mooc.jooq.demo.generated.tables;


import cn.mooc.jooq.demo.generated.AppcenterMallData;
import cn.mooc.jooq.demo.generated.Keys;
import cn.mooc.jooq.demo.generated.tables.records.TMallStatisticordercommentsRecord;

import java.math.BigDecimal;
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
public class TMallStatisticordercomments extends TableImpl<TMallStatisticordercommentsRecord> {

    private static final long serialVersionUID = -1183627024;

    /**
     * The reference instance of <code>appcenter_mall_data.t_mall_statisticordercomments</code>
     */
    public static final TMallStatisticordercomments T_MALL_STATISTICORDERCOMMENTS = new TMallStatisticordercomments();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<TMallStatisticordercommentsRecord> getRecordType() {
        return TMallStatisticordercommentsRecord.class;
    }

    /**
     * The column <code>appcenter_mall_data.t_mall_statisticordercomments.Id</code>.
     */
    public final TableField<TMallStatisticordercommentsRecord, Long> ID = createField("Id", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>appcenter_mall_data.t_mall_statisticordercomments.ShopId</code>.
     */
    public final TableField<TMallStatisticordercommentsRecord, Long> SHOPID = createField("ShopId", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>appcenter_mall_data.t_mall_statisticordercomments.CommentKey</code>. 评价的枚举（宝贝与描述相符 商家得分）
     */
    public final TableField<TMallStatisticordercommentsRecord, Integer> COMMENTKEY = createField("CommentKey", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "评价的枚举（宝贝与描述相符 商家得分）");

    /**
     * The column <code>appcenter_mall_data.t_mall_statisticordercomments.CommentValue</code>.
     */
    public final TableField<TMallStatisticordercommentsRecord, BigDecimal> COMMENTVALUE = createField("CommentValue", org.jooq.impl.SQLDataType.DECIMAL.precision(10, 4).nullable(false), this, "");

    /**
     * Create a <code>appcenter_mall_data.t_mall_statisticordercomments</code> table reference
     */
    public TMallStatisticordercomments() {
        this("t_mall_statisticordercomments", null);
    }

    /**
     * Create an aliased <code>appcenter_mall_data.t_mall_statisticordercomments</code> table reference
     */
    public TMallStatisticordercomments(String alias) {
        this(alias, T_MALL_STATISTICORDERCOMMENTS);
    }

    private TMallStatisticordercomments(String alias, Table<TMallStatisticordercommentsRecord> aliased) {
        this(alias, aliased, null);
    }

    private TMallStatisticordercomments(String alias, Table<TMallStatisticordercommentsRecord> aliased, Field<?>[] parameters) {
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
    public Identity<TMallStatisticordercommentsRecord, Long> getIdentity() {
        return Keys.IDENTITY_T_MALL_STATISTICORDERCOMMENTS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<TMallStatisticordercommentsRecord> getPrimaryKey() {
        return Keys.KEY_T_MALL_STATISTICORDERCOMMENTS_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<TMallStatisticordercommentsRecord>> getKeys() {
        return Arrays.<UniqueKey<TMallStatisticordercommentsRecord>>asList(Keys.KEY_T_MALL_STATISTICORDERCOMMENTS_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ForeignKey<TMallStatisticordercommentsRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<TMallStatisticordercommentsRecord, ?>>asList(Keys.T_MALL_STATISTICORDERCOMMENTS_IBFK_1);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TMallStatisticordercomments as(String alias) {
        return new TMallStatisticordercomments(alias, this);
    }

    /**
     * Rename this table
     */
    public TMallStatisticordercomments rename(String name) {
        return new TMallStatisticordercomments(name, null);
    }
}
