/**
 * This class is generated by jOOQ
 */
package cn.mooc.jooq.demo.generated.tables;


import cn.mooc.jooq.demo.generated.AppcenterMallData;
import cn.mooc.jooq.demo.generated.Keys;
import cn.mooc.jooq.demo.generated.tables.records.TMallTopicmodulesRecord;

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
public class TMallTopicmodules extends TableImpl<TMallTopicmodulesRecord> {

    private static final long serialVersionUID = -26724764;

    /**
     * The reference instance of <code>appcenter_mall_data.t_mall_topicmodules</code>
     */
    public static final TMallTopicmodules T_MALL_TOPICMODULES = new TMallTopicmodules();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<TMallTopicmodulesRecord> getRecordType() {
        return TMallTopicmodulesRecord.class;
    }

    /**
     * The column <code>appcenter_mall_data.t_mall_topicmodules.Id</code>.
     */
    public final TableField<TMallTopicmodulesRecord, Long> ID = createField("Id", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>appcenter_mall_data.t_mall_topicmodules.TopicId</code>. 专题ID
     */
    public final TableField<TMallTopicmodulesRecord, Long> TOPICID = createField("TopicId", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "专题ID");

    /**
     * The column <code>appcenter_mall_data.t_mall_topicmodules.Name</code>. 专题名称
     */
    public final TableField<TMallTopicmodulesRecord, String> NAME = createField("Name", org.jooq.impl.SQLDataType.VARCHAR.length(100).nullable(false), this, "专题名称");

    /**
     * Create a <code>appcenter_mall_data.t_mall_topicmodules</code> table reference
     */
    public TMallTopicmodules() {
        this("t_mall_topicmodules", null);
    }

    /**
     * Create an aliased <code>appcenter_mall_data.t_mall_topicmodules</code> table reference
     */
    public TMallTopicmodules(String alias) {
        this(alias, T_MALL_TOPICMODULES);
    }

    private TMallTopicmodules(String alias, Table<TMallTopicmodulesRecord> aliased) {
        this(alias, aliased, null);
    }

    private TMallTopicmodules(String alias, Table<TMallTopicmodulesRecord> aliased, Field<?>[] parameters) {
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
    public Identity<TMallTopicmodulesRecord, Long> getIdentity() {
        return Keys.IDENTITY_T_MALL_TOPICMODULES;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<TMallTopicmodulesRecord> getPrimaryKey() {
        return Keys.KEY_T_MALL_TOPICMODULES_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<TMallTopicmodulesRecord>> getKeys() {
        return Arrays.<UniqueKey<TMallTopicmodulesRecord>>asList(Keys.KEY_T_MALL_TOPICMODULES_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ForeignKey<TMallTopicmodulesRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<TMallTopicmodulesRecord, ?>>asList(Keys.T_MALL_TOPICMODULES_IBFK_1);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TMallTopicmodules as(String alias) {
        return new TMallTopicmodules(alias, this);
    }

    /**
     * Rename this table
     */
    public TMallTopicmodules rename(String name) {
        return new TMallTopicmodules(name, null);
    }
}