/**
 * This class is generated by jOOQ
 */
package cn.mooc.jooq.demo.generated.tables.records;


import cn.mooc.jooq.demo.generated.tables.TMallTypebrands;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record3;
import org.jooq.Row3;
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
public class TMallTypebrandsRecord extends UpdatableRecordImpl<TMallTypebrandsRecord> implements Record3<Long, Long, Long> {

    private static final long serialVersionUID = 1650306003;

    /**
     * Setter for <code>appcenter_mall_data.t_mall_typebrands.Id</code>.
     */
    public void setId(Long value) {
        set(0, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_typebrands.Id</code>.
     */
    public Long getId() {
        return (Long) get(0);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_typebrands.TypeId</code>.
     */
    public void setTypeid(Long value) {
        set(1, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_typebrands.TypeId</code>.
     */
    public Long getTypeid() {
        return (Long) get(1);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_typebrands.BrandId</code>.
     */
    public void setBrandid(Long value) {
        set(2, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_typebrands.BrandId</code>.
     */
    public Long getBrandid() {
        return (Long) get(2);
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
    // Record3 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row3<Long, Long, Long> fieldsRow() {
        return (Row3) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row3<Long, Long, Long> valuesRow() {
        return (Row3) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Long> field1() {
        return TMallTypebrands.T_MALL_TYPEBRANDS.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Long> field2() {
        return TMallTypebrands.T_MALL_TYPEBRANDS.TYPEID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Long> field3() {
        return TMallTypebrands.T_MALL_TYPEBRANDS.BRANDID;
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
        return getTypeid();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long value3() {
        return getBrandid();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TMallTypebrandsRecord value1(Long value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TMallTypebrandsRecord value2(Long value) {
        setTypeid(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TMallTypebrandsRecord value3(Long value) {
        setBrandid(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TMallTypebrandsRecord values(Long value1, Long value2, Long value3) {
        value1(value1);
        value2(value2);
        value3(value3);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached TMallTypebrandsRecord
     */
    public TMallTypebrandsRecord() {
        super(TMallTypebrands.T_MALL_TYPEBRANDS);
    }

    /**
     * Create a detached, initialised TMallTypebrandsRecord
     */
    public TMallTypebrandsRecord(Long id, Long typeid, Long brandid) {
        super(TMallTypebrands.T_MALL_TYPEBRANDS);

        set(0, id);
        set(1, typeid);
        set(2, brandid);
    }
}