/**
 * This class is generated by jOOQ
 */
package cn.mooc.jooq.demo.generated.tables;


import cn.mooc.jooq.demo.generated.AppcenterMallData;
import cn.mooc.jooq.demo.generated.Keys;
import cn.mooc.jooq.demo.generated.tables.records.TMallOrderoperationlogsRecord;

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
public class TMallOrderoperationlogs extends TableImpl<TMallOrderoperationlogsRecord> {

    private static final long serialVersionUID = -1864653542;

    /**
     * The reference instance of <code>appcenter_mall_data.t_mall_orderoperationlogs</code>
     */
    public static final TMallOrderoperationlogs T_MALL_ORDEROPERATIONLOGS = new TMallOrderoperationlogs();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<TMallOrderoperationlogsRecord> getRecordType() {
        return TMallOrderoperationlogsRecord.class;
    }

    /**
     * The column <code>appcenter_mall_data.t_mall_orderoperationlogs.Id</code>.
     */
    public final TableField<TMallOrderoperationlogsRecord, Long> ID = createField("Id", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>appcenter_mall_data.t_mall_orderoperationlogs.OrderId</code>. 订单ID
     */
    public final TableField<TMallOrderoperationlogsRecord, Long> ORDERID = createField("OrderId", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "订单ID");

    /**
     * The column <code>appcenter_mall_data.t_mall_orderoperationlogs.Operator</code>. 操作者
     */
    public final TableField<TMallOrderoperationlogsRecord, String> OPERATOR = createField("Operator", org.jooq.impl.SQLDataType.VARCHAR.length(100).nullable(false), this, "操作者");

    /**
     * The column <code>appcenter_mall_data.t_mall_orderoperationlogs.OperateDate</code>. 操作日期
     */
    public final TableField<TMallOrderoperationlogsRecord, Timestamp> OPERATEDATE = createField("OperateDate", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false), this, "操作日期");

    /**
     * The column <code>appcenter_mall_data.t_mall_orderoperationlogs.OperateContent</code>. 操作内容
     */
    public final TableField<TMallOrderoperationlogsRecord, String> OPERATECONTENT = createField("OperateContent", org.jooq.impl.SQLDataType.VARCHAR.length(1000), this, "操作内容");

    /**
     * Create a <code>appcenter_mall_data.t_mall_orderoperationlogs</code> table reference
     */
    public TMallOrderoperationlogs() {
        this("t_mall_orderoperationlogs", null);
    }

    /**
     * Create an aliased <code>appcenter_mall_data.t_mall_orderoperationlogs</code> table reference
     */
    public TMallOrderoperationlogs(String alias) {
        this(alias, T_MALL_ORDEROPERATIONLOGS);
    }

    private TMallOrderoperationlogs(String alias, Table<TMallOrderoperationlogsRecord> aliased) {
        this(alias, aliased, null);
    }

    private TMallOrderoperationlogs(String alias, Table<TMallOrderoperationlogsRecord> aliased, Field<?>[] parameters) {
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
    public Identity<TMallOrderoperationlogsRecord, Long> getIdentity() {
        return Keys.IDENTITY_T_MALL_ORDEROPERATIONLOGS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<TMallOrderoperationlogsRecord> getPrimaryKey() {
        return Keys.KEY_T_MALL_ORDEROPERATIONLOGS_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<TMallOrderoperationlogsRecord>> getKeys() {
        return Arrays.<UniqueKey<TMallOrderoperationlogsRecord>>asList(Keys.KEY_T_MALL_ORDEROPERATIONLOGS_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ForeignKey<TMallOrderoperationlogsRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<TMallOrderoperationlogsRecord, ?>>asList(Keys.T_MALL_ORDEROPERATIONLOGS_IBFK_1);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TMallOrderoperationlogs as(String alias) {
        return new TMallOrderoperationlogs(alias, this);
    }

    /**
     * Rename this table
     */
    public TMallOrderoperationlogs rename(String name) {
        return new TMallOrderoperationlogs(name, null);
    }
}
