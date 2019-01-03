/**
 * This class is generated by jOOQ
 */
package cn.mooc.jooq.demo.generated.tables;


import cn.mooc.jooq.demo.generated.AppcenterMallData;
import cn.mooc.jooq.demo.generated.Keys;
import cn.mooc.jooq.demo.generated.tables.records.TMallGuestbookRecord;

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
public class TMallGuestbook extends TableImpl<TMallGuestbookRecord> {

    private static final long serialVersionUID = 1015526377;

    /**
     * The reference instance of <code>appcenter_mall_data.t_mall_guestbook</code>
     */
    public static final TMallGuestbook T_MALL_GUESTBOOK = new TMallGuestbook();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<TMallGuestbookRecord> getRecordType() {
        return TMallGuestbookRecord.class;
    }

    /**
     * The column <code>appcenter_mall_data.t_mall_guestbook.Id</code>. 留言ID
     */
    public final TableField<TMallGuestbookRecord, Integer> ID = createField("Id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "留言ID");

    /**
     * The column <code>appcenter_mall_data.t_mall_guestbook.CreatorId</code>. 创建者
     */
    public final TableField<TMallGuestbookRecord, Long> CREATORID = createField("CreatorId", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "创建者");

    /**
     * The column <code>appcenter_mall_data.t_mall_guestbook.ReplyerId</code>. 回复者
     */
    public final TableField<TMallGuestbookRecord, Long> REPLYERID = createField("ReplyerId", org.jooq.impl.SQLDataType.BIGINT, this, "回复者");

    /**
     * The column <code>appcenter_mall_data.t_mall_guestbook.Title</code>. 留言标题
     */
    public final TableField<TMallGuestbookRecord, String> TITLE = createField("Title", org.jooq.impl.SQLDataType.VARCHAR.length(100).nullable(false), this, "留言标题");

    /**
     * The column <code>appcenter_mall_data.t_mall_guestbook.Text</code>. 留言内容
     */
    public final TableField<TMallGuestbookRecord, String> TEXT = createField("Text", org.jooq.impl.SQLDataType.CLOB.nullable(false), this, "留言内容");

    /**
     * The column <code>appcenter_mall_data.t_mall_guestbook.CreateDate</code>. 创建时间
     */
    public final TableField<TMallGuestbookRecord, Timestamp> CREATEDATE = createField("CreateDate", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false), this, "创建时间");

    /**
     * The column <code>appcenter_mall_data.t_mall_guestbook.CreateIp</code>. 创建Ip
     */
    public final TableField<TMallGuestbookRecord, String> CREATEIP = createField("CreateIp", org.jooq.impl.SQLDataType.VARCHAR.length(100).nullable(false), this, "创建Ip");

    /**
     * The column <code>appcenter_mall_data.t_mall_guestbook.ReplyText</code>. 回复内容
     */
    public final TableField<TMallGuestbookRecord, String> REPLYTEXT = createField("ReplyText", org.jooq.impl.SQLDataType.CLOB, this, "回复内容");

    /**
     * The column <code>appcenter_mall_data.t_mall_guestbook.IsReply</code>. 是否已回复
     */
    public final TableField<TMallGuestbookRecord, Integer> ISREPLY = createField("IsReply", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "是否已回复");

    /**
     * The column <code>appcenter_mall_data.t_mall_guestbook.IsRecommend</code>. 是否推荐
     */
    public final TableField<TMallGuestbookRecord, Integer> ISRECOMMEND = createField("IsRecommend", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "是否推荐");

    /**
     * The column <code>appcenter_mall_data.t_mall_guestbook.Status</code>. 状态(0:未审核,1:已审核,2:屏蔽)
     */
    public final TableField<TMallGuestbookRecord, Integer> STATUS = createField("Status", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "状态(0:未审核,1:已审核,2:屏蔽)");

    /**
     * The column <code>appcenter_mall_data.t_mall_guestbook.TypeId</code>. 留言类型ID
     */
    public final TableField<TMallGuestbookRecord, Integer> TYPEID = createField("TypeId", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "留言类型ID");

    /**
     * The column <code>appcenter_mall_data.t_mall_guestbook.ReplyDate</code>. 回复时间
     */
    public final TableField<TMallGuestbookRecord, Timestamp> REPLYDATE = createField("ReplyDate", org.jooq.impl.SQLDataType.TIMESTAMP, this, "回复时间");

    /**
     * The column <code>appcenter_mall_data.t_mall_guestbook.IsAnonymous</code>. 是否匿名
     */
    public final TableField<TMallGuestbookRecord, Integer> ISANONYMOUS = createField("IsAnonymous", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "是否匿名");

    /**
     * Create a <code>appcenter_mall_data.t_mall_guestbook</code> table reference
     */
    public TMallGuestbook() {
        this("t_mall_guestbook", null);
    }

    /**
     * Create an aliased <code>appcenter_mall_data.t_mall_guestbook</code> table reference
     */
    public TMallGuestbook(String alias) {
        this(alias, T_MALL_GUESTBOOK);
    }

    private TMallGuestbook(String alias, Table<TMallGuestbookRecord> aliased) {
        this(alias, aliased, null);
    }

    private TMallGuestbook(String alias, Table<TMallGuestbookRecord> aliased, Field<?>[] parameters) {
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
    public Identity<TMallGuestbookRecord, Integer> getIdentity() {
        return Keys.IDENTITY_T_MALL_GUESTBOOK;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<TMallGuestbookRecord> getPrimaryKey() {
        return Keys.KEY_T_MALL_GUESTBOOK_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<TMallGuestbookRecord>> getKeys() {
        return Arrays.<UniqueKey<TMallGuestbookRecord>>asList(Keys.KEY_T_MALL_GUESTBOOK_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ForeignKey<TMallGuestbookRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<TMallGuestbookRecord, ?>>asList(Keys.T_MALL_GUESTBOOK_IBFK_1, Keys.T_MALL_GUESTBOOK_IBFK_3, Keys.T_MALL_GUESTBOOK_IBFK_2);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TMallGuestbook as(String alias) {
        return new TMallGuestbook(alias, this);
    }

    /**
     * Rename this table
     */
    public TMallGuestbook rename(String name) {
        return new TMallGuestbook(name, null);
    }
}