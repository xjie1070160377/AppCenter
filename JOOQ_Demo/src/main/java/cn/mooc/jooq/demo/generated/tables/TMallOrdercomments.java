/**
 * This class is generated by jOOQ
 */
package cn.mooc.jooq.demo.generated.tables;


import cn.mooc.jooq.demo.generated.AppcenterMallData;
import cn.mooc.jooq.demo.generated.Keys;
import cn.mooc.jooq.demo.generated.tables.records.TMallOrdercommentsRecord;

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
public class TMallOrdercomments extends TableImpl<TMallOrdercommentsRecord> {

    private static final long serialVersionUID = 1978225019;

    /**
     * The reference instance of <code>appcenter_mall_data.t_mall_ordercomments</code>
     */
    public static final TMallOrdercomments T_MALL_ORDERCOMMENTS = new TMallOrdercomments();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<TMallOrdercommentsRecord> getRecordType() {
        return TMallOrdercommentsRecord.class;
    }

    /**
     * The column <code>appcenter_mall_data.t_mall_ordercomments.Id</code>.
     */
    public final TableField<TMallOrdercommentsRecord, Long> ID = createField("Id", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>appcenter_mall_data.t_mall_ordercomments.OrderId</code>. 订单ID
     */
    public final TableField<TMallOrdercommentsRecord, Long> ORDERID = createField("OrderId", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "订单ID");

    /**
     * The column <code>appcenter_mall_data.t_mall_ordercomments.ShopId</code>. 店铺ID
     */
    public final TableField<TMallOrdercommentsRecord, Long> SHOPID = createField("ShopId", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "店铺ID");

    /**
     * The column <code>appcenter_mall_data.t_mall_ordercomments.ShopName</code>. 店铺名称
     */
    public final TableField<TMallOrdercommentsRecord, String> SHOPNAME = createField("ShopName", org.jooq.impl.SQLDataType.VARCHAR.length(100).nullable(false), this, "店铺名称");

    /**
     * The column <code>appcenter_mall_data.t_mall_ordercomments.UserId</code>. 用户ID
     */
    public final TableField<TMallOrdercommentsRecord, Long> USERID = createField("UserId", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "用户ID");

    /**
     * The column <code>appcenter_mall_data.t_mall_ordercomments.UserName</code>. 用户名称
     */
    public final TableField<TMallOrdercommentsRecord, String> USERNAME = createField("UserName", org.jooq.impl.SQLDataType.VARCHAR.length(100).nullable(false), this, "用户名称");

    /**
     * The column <code>appcenter_mall_data.t_mall_ordercomments.CommentDate</code>. 评价日期
     */
    public final TableField<TMallOrdercommentsRecord, Timestamp> COMMENTDATE = createField("CommentDate", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false), this, "评价日期");

    /**
     * The column <code>appcenter_mall_data.t_mall_ordercomments.PackMark</code>. 包装评分
     */
    public final TableField<TMallOrdercommentsRecord, Integer> PACKMARK = createField("PackMark", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "包装评分");

    /**
     * The column <code>appcenter_mall_data.t_mall_ordercomments.DeliveryMark</code>. 物流评分
     */
    public final TableField<TMallOrdercommentsRecord, Integer> DELIVERYMARK = createField("DeliveryMark", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "物流评分");

    /**
     * The column <code>appcenter_mall_data.t_mall_ordercomments.ServiceMark</code>. 服务评分
     */
    public final TableField<TMallOrdercommentsRecord, Integer> SERVICEMARK = createField("ServiceMark", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "服务评分");

    /**
     * Create a <code>appcenter_mall_data.t_mall_ordercomments</code> table reference
     */
    public TMallOrdercomments() {
        this("t_mall_ordercomments", null);
    }

    /**
     * Create an aliased <code>appcenter_mall_data.t_mall_ordercomments</code> table reference
     */
    public TMallOrdercomments(String alias) {
        this(alias, T_MALL_ORDERCOMMENTS);
    }

    private TMallOrdercomments(String alias, Table<TMallOrdercommentsRecord> aliased) {
        this(alias, aliased, null);
    }

    private TMallOrdercomments(String alias, Table<TMallOrdercommentsRecord> aliased, Field<?>[] parameters) {
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
    public Identity<TMallOrdercommentsRecord, Long> getIdentity() {
        return Keys.IDENTITY_T_MALL_ORDERCOMMENTS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<TMallOrdercommentsRecord> getPrimaryKey() {
        return Keys.KEY_T_MALL_ORDERCOMMENTS_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<TMallOrdercommentsRecord>> getKeys() {
        return Arrays.<UniqueKey<TMallOrdercommentsRecord>>asList(Keys.KEY_T_MALL_ORDERCOMMENTS_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ForeignKey<TMallOrdercommentsRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<TMallOrdercommentsRecord, ?>>asList(Keys.T_MALL_ORDERCOMMENTS_IBFK_1);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TMallOrdercomments as(String alias) {
        return new TMallOrdercomments(alias, this);
    }

    /**
     * Rename this table
     */
    public TMallOrdercomments rename(String name) {
        return new TMallOrdercomments(name, null);
    }
}
