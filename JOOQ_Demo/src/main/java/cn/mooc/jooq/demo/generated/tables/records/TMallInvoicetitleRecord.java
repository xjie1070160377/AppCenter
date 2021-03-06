/**
 * This class is generated by jOOQ
 */
package cn.mooc.jooq.demo.generated.tables.records;


import cn.mooc.jooq.demo.generated.tables.TMallInvoicetitle;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record4;
import org.jooq.Row4;
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
public class TMallInvoicetitleRecord extends UpdatableRecordImpl<TMallInvoicetitleRecord> implements Record4<Long, Long, String, Byte> {

    private static final long serialVersionUID = 1840932764;

    /**
     * Setter for <code>appcenter_mall_data.t_mall_invoicetitle.Id</code>.
     */
    public void setId(Long value) {
        set(0, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_invoicetitle.Id</code>.
     */
    public Long getId() {
        return (Long) get(0);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_invoicetitle.UserId</code>. 用户ID
     */
    public void setUserid(Long value) {
        set(1, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_invoicetitle.UserId</code>. 用户ID
     */
    public Long getUserid() {
        return (Long) get(1);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_invoicetitle.Name</code>. 抬头名称
     */
    public void setName(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_invoicetitle.Name</code>. 抬头名称
     */
    public String getName() {
        return (String) get(2);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_invoicetitle.IsDefault</code>. 是否默认
     */
    public void setIsdefault(Byte value) {
        set(3, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_invoicetitle.IsDefault</code>. 是否默认
     */
    public Byte getIsdefault() {
        return (Byte) get(3);
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
    // Record4 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row4<Long, Long, String, Byte> fieldsRow() {
        return (Row4) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row4<Long, Long, String, Byte> valuesRow() {
        return (Row4) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Long> field1() {
        return TMallInvoicetitle.T_MALL_INVOICETITLE.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Long> field2() {
        return TMallInvoicetitle.T_MALL_INVOICETITLE.USERID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field3() {
        return TMallInvoicetitle.T_MALL_INVOICETITLE.NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Byte> field4() {
        return TMallInvoicetitle.T_MALL_INVOICETITLE.ISDEFAULT;
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
        return getUserid();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value3() {
        return getName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte value4() {
        return getIsdefault();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TMallInvoicetitleRecord value1(Long value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TMallInvoicetitleRecord value2(Long value) {
        setUserid(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TMallInvoicetitleRecord value3(String value) {
        setName(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TMallInvoicetitleRecord value4(Byte value) {
        setIsdefault(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TMallInvoicetitleRecord values(Long value1, Long value2, String value3, Byte value4) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached TMallInvoicetitleRecord
     */
    public TMallInvoicetitleRecord() {
        super(TMallInvoicetitle.T_MALL_INVOICETITLE);
    }

    /**
     * Create a detached, initialised TMallInvoicetitleRecord
     */
    public TMallInvoicetitleRecord(Long id, Long userid, String name, Byte isdefault) {
        super(TMallInvoicetitle.T_MALL_INVOICETITLE);

        set(0, id);
        set(1, userid);
        set(2, name);
        set(3, isdefault);
    }
}
