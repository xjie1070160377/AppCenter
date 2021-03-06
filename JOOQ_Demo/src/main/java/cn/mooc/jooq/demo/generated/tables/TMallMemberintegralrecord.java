/**
 * This class is generated by jOOQ
 */
package cn.mooc.jooq.demo.generated.tables;


import cn.mooc.jooq.demo.generated.AppcenterMallData;
import cn.mooc.jooq.demo.generated.Keys;
import cn.mooc.jooq.demo.generated.tables.records.TMallMemberintegralrecordRecord;

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
public class TMallMemberintegralrecord extends TableImpl<TMallMemberintegralrecordRecord> {

    private static final long serialVersionUID = -745472633;

    /**
     * The reference instance of <code>appcenter_mall_data.t_mall_memberintegralrecord</code>
     */
    public static final TMallMemberintegralrecord T_MALL_MEMBERINTEGRALRECORD = new TMallMemberintegralrecord();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<TMallMemberintegralrecordRecord> getRecordType() {
        return TMallMemberintegralrecordRecord.class;
    }

    /**
     * The column <code>appcenter_mall_data.t_mall_memberintegralrecord.Id</code>.
     */
    public final TableField<TMallMemberintegralrecordRecord, Long> ID = createField("Id", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>appcenter_mall_data.t_mall_memberintegralrecord.MemberId</code>.
     */
    public final TableField<TMallMemberintegralrecordRecord, Long> MEMBERID = createField("MemberId", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>appcenter_mall_data.t_mall_memberintegralrecord.UserName</code>. 用户名称
     */
    public final TableField<TMallMemberintegralrecordRecord, String> USERNAME = createField("UserName", org.jooq.impl.SQLDataType.VARCHAR.length(100).nullable(false), this, "用户名称");

    /**
     * The column <code>appcenter_mall_data.t_mall_memberintegralrecord.TypeId</code>. 兑换类型（登录、下单等）
     */
    public final TableField<TMallMemberintegralrecordRecord, Integer> TYPEID = createField("TypeId", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "兑换类型（登录、下单等）");

    /**
     * The column <code>appcenter_mall_data.t_mall_memberintegralrecord.Integral</code>. 积分数量
     */
    public final TableField<TMallMemberintegralrecordRecord, Integer> INTEGRAL = createField("Integral", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "积分数量");

    /**
     * The column <code>appcenter_mall_data.t_mall_memberintegralrecord.RecordDate</code>. 记录日期
     */
    public final TableField<TMallMemberintegralrecordRecord, Timestamp> RECORDDATE = createField("RecordDate", org.jooq.impl.SQLDataType.TIMESTAMP, this, "记录日期");

    /**
     * The column <code>appcenter_mall_data.t_mall_memberintegralrecord.ReMark</code>. 说明
     */
    public final TableField<TMallMemberintegralrecordRecord, String> REMARK = createField("ReMark", org.jooq.impl.SQLDataType.VARCHAR.length(100), this, "说明");

    /**
     * Create a <code>appcenter_mall_data.t_mall_memberintegralrecord</code> table reference
     */
    public TMallMemberintegralrecord() {
        this("t_mall_memberintegralrecord", null);
    }

    /**
     * Create an aliased <code>appcenter_mall_data.t_mall_memberintegralrecord</code> table reference
     */
    public TMallMemberintegralrecord(String alias) {
        this(alias, T_MALL_MEMBERINTEGRALRECORD);
    }

    private TMallMemberintegralrecord(String alias, Table<TMallMemberintegralrecordRecord> aliased) {
        this(alias, aliased, null);
    }

    private TMallMemberintegralrecord(String alias, Table<TMallMemberintegralrecordRecord> aliased, Field<?>[] parameters) {
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
    public Identity<TMallMemberintegralrecordRecord, Long> getIdentity() {
        return Keys.IDENTITY_T_MALL_MEMBERINTEGRALRECORD;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<TMallMemberintegralrecordRecord> getPrimaryKey() {
        return Keys.KEY_T_MALL_MEMBERINTEGRALRECORD_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<TMallMemberintegralrecordRecord>> getKeys() {
        return Arrays.<UniqueKey<TMallMemberintegralrecordRecord>>asList(Keys.KEY_T_MALL_MEMBERINTEGRALRECORD_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ForeignKey<TMallMemberintegralrecordRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<TMallMemberintegralrecordRecord, ?>>asList(Keys.T_MALL_MEMBERINTEGRALRECORD_IBFK_1);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TMallMemberintegralrecord as(String alias) {
        return new TMallMemberintegralrecord(alias, this);
    }

    /**
     * Rename this table
     */
    public TMallMemberintegralrecord rename(String name) {
        return new TMallMemberintegralrecord(name, null);
    }
}
