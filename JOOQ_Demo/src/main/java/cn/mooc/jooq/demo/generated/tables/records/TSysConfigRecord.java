/**
 * This class is generated by jOOQ
 */
package cn.mooc.jooq.demo.generated.tables.records;


import cn.mooc.jooq.demo.generated.tables.TSysConfig;

import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record6;
import org.jooq.Row6;
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
public class TSysConfigRecord extends UpdatableRecordImpl<TSysConfigRecord> implements Record6<String, String, Integer, Timestamp, Timestamp, String> {

    private static final long serialVersionUID = 255529957;

    /**
     * Setter for <code>appcenter_mall_data.t_sys_config.keyName</code>.
     */
    public void setKeyname(String value) {
        set(0, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_sys_config.keyName</code>.
     */
    public String getKeyname() {
        return (String) get(0);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_sys_config.keyValue</code>.
     */
    public void setKeyvalue(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_sys_config.keyValue</code>.
     */
    public String getKeyvalue() {
        return (String) get(1);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_sys_config.dataType</code>.
     */
    public void setDatatype(Integer value) {
        set(2, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_sys_config.dataType</code>.
     */
    public Integer getDatatype() {
        return (Integer) get(2);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_sys_config.createTime</code>.
     */
    public void setCreatetime(Timestamp value) {
        set(3, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_sys_config.createTime</code>.
     */
    public Timestamp getCreatetime() {
        return (Timestamp) get(3);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_sys_config.lastUpdate</code>.
     */
    public void setLastupdate(Timestamp value) {
        set(4, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_sys_config.lastUpdate</code>.
     */
    public Timestamp getLastupdate() {
        return (Timestamp) get(4);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_sys_config.note</code>.
     */
    public void setNote(String value) {
        set(5, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_sys_config.note</code>.
     */
    public String getNote() {
        return (String) get(5);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Record1<String> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record6 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row6<String, String, Integer, Timestamp, Timestamp, String> fieldsRow() {
        return (Row6) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row6<String, String, Integer, Timestamp, Timestamp, String> valuesRow() {
        return (Row6) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field1() {
        return TSysConfig.T_SYS_CONFIG.KEYNAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field2() {
        return TSysConfig.T_SYS_CONFIG.KEYVALUE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field3() {
        return TSysConfig.T_SYS_CONFIG.DATATYPE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field4() {
        return TSysConfig.T_SYS_CONFIG.CREATETIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field5() {
        return TSysConfig.T_SYS_CONFIG.LASTUPDATE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field6() {
        return TSysConfig.T_SYS_CONFIG.NOTE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value1() {
        return getKeyname();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value2() {
        return getKeyvalue();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value3() {
        return getDatatype();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value4() {
        return getCreatetime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value5() {
        return getLastupdate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value6() {
        return getNote();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TSysConfigRecord value1(String value) {
        setKeyname(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TSysConfigRecord value2(String value) {
        setKeyvalue(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TSysConfigRecord value3(Integer value) {
        setDatatype(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TSysConfigRecord value4(Timestamp value) {
        setCreatetime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TSysConfigRecord value5(Timestamp value) {
        setLastupdate(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TSysConfigRecord value6(String value) {
        setNote(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TSysConfigRecord values(String value1, String value2, Integer value3, Timestamp value4, Timestamp value5, String value6) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached TSysConfigRecord
     */
    public TSysConfigRecord() {
        super(TSysConfig.T_SYS_CONFIG);
    }

    /**
     * Create a detached, initialised TSysConfigRecord
     */
    public TSysConfigRecord(String keyname, String keyvalue, Integer datatype, Timestamp createtime, Timestamp lastupdate, String note) {
        super(TSysConfig.T_SYS_CONFIG);

        set(0, keyname);
        set(1, keyvalue);
        set(2, datatype);
        set(3, createtime);
        set(4, lastupdate);
        set(5, note);
    }
}
