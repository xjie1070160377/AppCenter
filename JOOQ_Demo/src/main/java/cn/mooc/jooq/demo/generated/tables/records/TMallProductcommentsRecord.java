/**
 * This class is generated by jOOQ
 */
package cn.mooc.jooq.demo.generated.tables.records;


import cn.mooc.jooq.demo.generated.tables.TMallProductcomments;

import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record15;
import org.jooq.Row15;
import org.jooq.impl.UpdatableRecordImpl;


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
public class TMallProductcommentsRecord extends UpdatableRecordImpl<TMallProductcommentsRecord> implements Record15<Long, Long, Long, Long, String, Long, String, String, String, Timestamp, Integer, String, Timestamp, String, Integer> {

    private static final long serialVersionUID = 1939700393;

    /**
     * Setter for <code>appcenter_mall_data.t_mall_productcomments.Id</code>.
     */
    public void setId(Long value) {
        set(0, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_productcomments.Id</code>.
     */
    public Long getId() {
        return (Long) get(0);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_productcomments.SubOrderId</code>. 订单详细ID
     */
    public void setSuborderid(Long value) {
        set(1, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_productcomments.SubOrderId</code>. 订单详细ID
     */
    public Long getSuborderid() {
        return (Long) get(1);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_productcomments.ProductId</code>. 商品ID
     */
    public void setProductid(Long value) {
        set(2, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_productcomments.ProductId</code>. 商品ID
     */
    public Long getProductid() {
        return (Long) get(2);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_productcomments.ShopId</code>. 店铺ID
     */
    public void setShopid(Long value) {
        set(3, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_productcomments.ShopId</code>. 店铺ID
     */
    public Long getShopid() {
        return (Long) get(3);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_productcomments.ShopName</code>. 店铺名称
     */
    public void setShopname(String value) {
        set(4, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_productcomments.ShopName</code>. 店铺名称
     */
    public String getShopname() {
        return (String) get(4);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_productcomments.UserId</code>. 用户ID
     */
    public void setUserid(Long value) {
        set(5, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_productcomments.UserId</code>. 用户ID
     */
    public Long getUserid() {
        return (Long) get(5);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_productcomments.UserName</code>. 用户名称
     */
    public void setUsername(String value) {
        set(6, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_productcomments.UserName</code>. 用户名称
     */
    public String getUsername() {
        return (String) get(6);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_productcomments.Email</code>. Email
     */
    public void setEmail(String value) {
        set(7, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_productcomments.Email</code>. Email
     */
    public String getEmail() {
        return (String) get(7);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_productcomments.ReviewContent</code>. 评价内容
     */
    public void setReviewcontent(String value) {
        set(8, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_productcomments.ReviewContent</code>. 评价内容
     */
    public String getReviewcontent() {
        return (String) get(8);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_productcomments.ReviewDate</code>. 评价日期
     */
    public void setReviewdate(Timestamp value) {
        set(9, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_productcomments.ReviewDate</code>. 评价日期
     */
    public Timestamp getReviewdate() {
        return (Timestamp) get(9);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_productcomments.ReviewMark</code>. 评价说明
     */
    public void setReviewmark(Integer value) {
        set(10, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_productcomments.ReviewMark</code>. 评价说明
     */
    public Integer getReviewmark() {
        return (Integer) get(10);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_productcomments.ReplyContent</code>. 商家回复内容
     */
    public void setReplycontent(String value) {
        set(11, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_productcomments.ReplyContent</code>. 商家回复内容
     */
    public String getReplycontent() {
        return (String) get(11);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_productcomments.ReplyDate</code>. 商家回复日期
     */
    public void setReplydate(Timestamp value) {
        set(12, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_productcomments.ReplyDate</code>. 商家回复日期
     */
    public Timestamp getReplydate() {
        return (Timestamp) get(12);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_productcomments.CommentName</code>. 评论名
     */
    public void setCommentname(String value) {
        set(13, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_productcomments.CommentName</code>. 评论名
     */
    public String getCommentname() {
        return (String) get(13);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_productcomments.Anonymous</code>. 匿名
     */
    public void setAnonymous(Integer value) {
        set(14, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_productcomments.Anonymous</code>. 匿名
     */
    public Integer getAnonymous() {
        return (Integer) get(14);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Record1<Long> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record15 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row15<Long, Long, Long, Long, String, Long, String, String, String, Timestamp, Integer, String, Timestamp, String, Integer> fieldsRow() {
        return (Row15) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row15<Long, Long, Long, Long, String, Long, String, String, String, Timestamp, Integer, String, Timestamp, String, Integer> valuesRow() {
        return (Row15) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Long> field1() {
        return TMallProductcomments.T_MALL_PRODUCTCOMMENTS.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Long> field2() {
        return TMallProductcomments.T_MALL_PRODUCTCOMMENTS.SUBORDERID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Long> field3() {
        return TMallProductcomments.T_MALL_PRODUCTCOMMENTS.PRODUCTID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Long> field4() {
        return TMallProductcomments.T_MALL_PRODUCTCOMMENTS.SHOPID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field5() {
        return TMallProductcomments.T_MALL_PRODUCTCOMMENTS.SHOPNAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Long> field6() {
        return TMallProductcomments.T_MALL_PRODUCTCOMMENTS.USERID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field7() {
        return TMallProductcomments.T_MALL_PRODUCTCOMMENTS.USERNAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field8() {
        return TMallProductcomments.T_MALL_PRODUCTCOMMENTS.EMAIL;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field9() {
        return TMallProductcomments.T_MALL_PRODUCTCOMMENTS.REVIEWCONTENT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field10() {
        return TMallProductcomments.T_MALL_PRODUCTCOMMENTS.REVIEWDATE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field11() {
        return TMallProductcomments.T_MALL_PRODUCTCOMMENTS.REVIEWMARK;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field12() {
        return TMallProductcomments.T_MALL_PRODUCTCOMMENTS.REPLYCONTENT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field13() {
        return TMallProductcomments.T_MALL_PRODUCTCOMMENTS.REPLYDATE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field14() {
        return TMallProductcomments.T_MALL_PRODUCTCOMMENTS.COMMENTNAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field15() {
        return TMallProductcomments.T_MALL_PRODUCTCOMMENTS.ANONYMOUS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long value1() {
        return getId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long value2() {
        return getSuborderid();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long value3() {
        return getProductid();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long value4() {
        return getShopid();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value5() {
        return getShopname();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long value6() {
        return getUserid();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value7() {
        return getUsername();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value8() {
        return getEmail();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value9() {
        return getReviewcontent();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value10() {
        return getReviewdate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value11() {
        return getReviewmark();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value12() {
        return getReplycontent();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value13() {
        return getReplydate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value14() {
        return getCommentname();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value15() {
        return getAnonymous();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TMallProductcommentsRecord value1(Long value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TMallProductcommentsRecord value2(Long value) {
        setSuborderid(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TMallProductcommentsRecord value3(Long value) {
        setProductid(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TMallProductcommentsRecord value4(Long value) {
        setShopid(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TMallProductcommentsRecord value5(String value) {
        setShopname(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TMallProductcommentsRecord value6(Long value) {
        setUserid(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TMallProductcommentsRecord value7(String value) {
        setUsername(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TMallProductcommentsRecord value8(String value) {
        setEmail(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TMallProductcommentsRecord value9(String value) {
        setReviewcontent(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TMallProductcommentsRecord value10(Timestamp value) {
        setReviewdate(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TMallProductcommentsRecord value11(Integer value) {
        setReviewmark(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TMallProductcommentsRecord value12(String value) {
        setReplycontent(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TMallProductcommentsRecord value13(Timestamp value) {
        setReplydate(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TMallProductcommentsRecord value14(String value) {
        setCommentname(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TMallProductcommentsRecord value15(Integer value) {
        setAnonymous(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TMallProductcommentsRecord values(Long value1, Long value2, Long value3, Long value4, String value5, Long value6, String value7, String value8, String value9, Timestamp value10, Integer value11, String value12, Timestamp value13, String value14, Integer value15) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        value7(value7);
        value8(value8);
        value9(value9);
        value10(value10);
        value11(value11);
        value12(value12);
        value13(value13);
        value14(value14);
        value15(value15);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached TMallProductcommentsRecord
     */
    public TMallProductcommentsRecord() {
        super(TMallProductcomments.T_MALL_PRODUCTCOMMENTS);
    }

    /**
     * Create a detached, initialised TMallProductcommentsRecord
     */
    public TMallProductcommentsRecord(Long id, Long suborderid, Long productid, Long shopid, String shopname, Long userid, String username, String email, String reviewcontent, Timestamp reviewdate, Integer reviewmark, String replycontent, Timestamp replydate, String commentname, Integer anonymous) {
        super(TMallProductcomments.T_MALL_PRODUCTCOMMENTS);

        set(0, id);
        set(1, suborderid);
        set(2, productid);
        set(3, shopid);
        set(4, shopname);
        set(5, userid);
        set(6, username);
        set(7, email);
        set(8, reviewcontent);
        set(9, reviewdate);
        set(10, reviewmark);
        set(11, replycontent);
        set(12, replydate);
        set(13, commentname);
        set(14, anonymous);
    }
}
