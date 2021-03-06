/**
 * This class is generated by jOOQ
 */
package cn.mooc.jooq.demo.generated.tables;


import cn.mooc.jooq.demo.generated.AppcenterMallData;
import cn.mooc.jooq.demo.generated.Keys;
import cn.mooc.jooq.demo.generated.tables.records.TNoticeNodeRecord;

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
 * 通知公告栏目
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.8.4"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class TNoticeNode extends TableImpl<TNoticeNodeRecord> {

    private static final long serialVersionUID = -712413357;

    /**
     * The reference instance of <code>appcenter_mall_data.t_notice_node</code>
     */
    public static final TNoticeNode T_NOTICE_NODE = new TNoticeNode();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<TNoticeNodeRecord> getRecordType() {
        return TNoticeNodeRecord.class;
    }

    /**
     * The column <code>appcenter_mall_data.t_notice_node.id</code>.
     */
    public final TableField<TNoticeNodeRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>appcenter_mall_data.t_notice_node.parent</code>. 父栏目编号
     */
    public final TableField<TNoticeNodeRecord, Integer> PARENT = createField("parent", org.jooq.impl.SQLDataType.INTEGER, this, "父栏目编号");

    /**
     * The column <code>appcenter_mall_data.t_notice_node.number</code>. 编码
     */
    public final TableField<TNoticeNodeRecord, String> NUMBER = createField("number", org.jooq.impl.SQLDataType.VARCHAR.length(20), this, "编码");

    /**
     * The column <code>appcenter_mall_data.t_notice_node.description</code>. 备注
     */
    public final TableField<TNoticeNodeRecord, String> DESCRIPTION = createField("description", org.jooq.impl.SQLDataType.VARCHAR.length(200), this, "备注");

    /**
     * The column <code>appcenter_mall_data.t_notice_node.treeNumber</code>. 树编码
     */
    public final TableField<TNoticeNodeRecord, String> TREENUMBER = createField("treeNumber", org.jooq.impl.SQLDataType.VARCHAR.length(100), this, "树编码");

    /**
     * The column <code>appcenter_mall_data.t_notice_node.treeLevel</code>. 树层级
     */
    public final TableField<TNoticeNodeRecord, Byte> TREELEVEL = createField("treeLevel", org.jooq.impl.SQLDataType.TINYINT, this, "树层级");

    /**
     * The column <code>appcenter_mall_data.t_notice_node.treeMax</code>. 树子节点最大编码
     */
    public final TableField<TNoticeNodeRecord, String> TREEMAX = createField("treeMax", org.jooq.impl.SQLDataType.VARCHAR.length(10), this, "树子节点最大编码");

    /**
     * The column <code>appcenter_mall_data.t_notice_node.name</code>. 名称
     */
    public final TableField<TNoticeNodeRecord, String> NAME = createField("name", org.jooq.impl.SQLDataType.VARCHAR.length(20), this, "名称");

    /**
     * Create a <code>appcenter_mall_data.t_notice_node</code> table reference
     */
    public TNoticeNode() {
        this("t_notice_node", null);
    }

    /**
     * Create an aliased <code>appcenter_mall_data.t_notice_node</code> table reference
     */
    public TNoticeNode(String alias) {
        this(alias, T_NOTICE_NODE);
    }

    private TNoticeNode(String alias, Table<TNoticeNodeRecord> aliased) {
        this(alias, aliased, null);
    }

    private TNoticeNode(String alias, Table<TNoticeNodeRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "通知公告栏目");
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
    public Identity<TNoticeNodeRecord, Integer> getIdentity() {
        return Keys.IDENTITY_T_NOTICE_NODE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<TNoticeNodeRecord> getPrimaryKey() {
        return Keys.KEY_T_NOTICE_NODE_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<TNoticeNodeRecord>> getKeys() {
        return Arrays.<UniqueKey<TNoticeNodeRecord>>asList(Keys.KEY_T_NOTICE_NODE_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ForeignKey<TNoticeNodeRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<TNoticeNodeRecord, ?>>asList(Keys.T_NOTICE_NODE_IBFK_1);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TNoticeNode as(String alias) {
        return new TNoticeNode(alias, this);
    }

    /**
     * Rename this table
     */
    public TNoticeNode rename(String name) {
        return new TNoticeNode(name, null);
    }
}
