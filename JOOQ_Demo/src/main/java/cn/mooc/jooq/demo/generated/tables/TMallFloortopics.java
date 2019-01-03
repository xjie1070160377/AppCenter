/**
 * This class is generated by jOOQ
 */
package cn.mooc.jooq.demo.generated.tables;


import cn.mooc.jooq.demo.generated.AppcenterMallData;
import cn.mooc.jooq.demo.generated.Keys;
import cn.mooc.jooq.demo.generated.tables.records.TMallFloortopicsRecord;

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
public class TMallFloortopics extends TableImpl<TMallFloortopicsRecord> {

    private static final long serialVersionUID = 1198058445;

    /**
     * The reference instance of <code>appcenter_mall_data.t_mall_floortopics</code>
     */
    public static final TMallFloortopics T_MALL_FLOORTOPICS = new TMallFloortopics();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<TMallFloortopicsRecord> getRecordType() {
        return TMallFloortopicsRecord.class;
    }

    /**
     * The column <code>appcenter_mall_data.t_mall_floortopics.Id</code>.
     */
    public final TableField<TMallFloortopicsRecord, Long> ID = createField("Id", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>appcenter_mall_data.t_mall_floortopics.FloorId</code>. 楼层ID
     */
    public final TableField<TMallFloortopicsRecord, Long> FLOORID = createField("FloorId", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "楼层ID");

    /**
     * The column <code>appcenter_mall_data.t_mall_floortopics.TopicType</code>. 专题类型
     */
    public final TableField<TMallFloortopicsRecord, Integer> TOPICTYPE = createField("TopicType", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "专题类型");

    /**
     * The column <code>appcenter_mall_data.t_mall_floortopics.TopicImage</code>. 专题封面图片
     */
    public final TableField<TMallFloortopicsRecord, String> TOPICIMAGE = createField("TopicImage", org.jooq.impl.SQLDataType.VARCHAR.length(100).nullable(false), this, "专题封面图片");

    /**
     * The column <code>appcenter_mall_data.t_mall_floortopics.TopicName</code>. 专题名称
     */
    public final TableField<TMallFloortopicsRecord, String> TOPICNAME = createField("TopicName", org.jooq.impl.SQLDataType.VARCHAR.length(100).nullable(false), this, "专题名称");

    /**
     * The column <code>appcenter_mall_data.t_mall_floortopics.Url</code>. 专题跳转URL
     */
    public final TableField<TMallFloortopicsRecord, String> URL = createField("Url", org.jooq.impl.SQLDataType.VARCHAR.length(100).nullable(false), this, "专题跳转URL");

    /**
     * Create a <code>appcenter_mall_data.t_mall_floortopics</code> table reference
     */
    public TMallFloortopics() {
        this("t_mall_floortopics", null);
    }

    /**
     * Create an aliased <code>appcenter_mall_data.t_mall_floortopics</code> table reference
     */
    public TMallFloortopics(String alias) {
        this(alias, T_MALL_FLOORTOPICS);
    }

    private TMallFloortopics(String alias, Table<TMallFloortopicsRecord> aliased) {
        this(alias, aliased, null);
    }

    private TMallFloortopics(String alias, Table<TMallFloortopicsRecord> aliased, Field<?>[] parameters) {
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
    public Identity<TMallFloortopicsRecord, Long> getIdentity() {
        return Keys.IDENTITY_T_MALL_FLOORTOPICS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<TMallFloortopicsRecord> getPrimaryKey() {
        return Keys.KEY_T_MALL_FLOORTOPICS_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<TMallFloortopicsRecord>> getKeys() {
        return Arrays.<UniqueKey<TMallFloortopicsRecord>>asList(Keys.KEY_T_MALL_FLOORTOPICS_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ForeignKey<TMallFloortopicsRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<TMallFloortopicsRecord, ?>>asList(Keys.T_MALL_FLOORTOPICS_IBFK_1);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TMallFloortopics as(String alias) {
        return new TMallFloortopics(alias, this);
    }

    /**
     * Rename this table
     */
    public TMallFloortopics rename(String name) {
        return new TMallFloortopics(name, null);
    }
}
