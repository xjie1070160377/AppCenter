/**
 * This class is generated by jOOQ
 */
package cn.mooc.jooq.demo.generated.tables.records;


import cn.mooc.jooq.demo.generated.tables.TMallWalletCashout;

import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record10;
import org.jooq.Row10;
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
public class TMallWalletCashoutRecord extends UpdatableRecordImpl<TMallWalletCashoutRecord> implements Record10<Long, Integer, Double, Integer, Timestamp, Integer, Long, Timestamp, String, String> {

    private static final long serialVersionUID = -498280568;

    /**
     * Setter for <code>appcenter_mall_data.t_mall_wallet_cashout.applyUserId</code>.
     */
    public void setApplyuserid(Long value) {
        set(0, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_wallet_cashout.applyUserId</code>.
     */
    public Long getApplyuserid() {
        return (Long) get(0);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_wallet_cashout.id</code>.
     */
    public void setId(Integer value) {
        set(1, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_wallet_cashout.id</code>.
     */
    public Integer getId() {
        return (Integer) get(1);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_wallet_cashout.cash</code>.
     */
    public void setCash(Double value) {
        set(2, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_wallet_cashout.cash</code>.
     */
    public Double getCash() {
        return (Double) get(2);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_wallet_cashout.bankType</code>.
     */
    public void setBanktype(Integer value) {
        set(3, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_wallet_cashout.bankType</code>.
     */
    public Integer getBanktype() {
        return (Integer) get(3);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_wallet_cashout.applyTime</code>.
     */
    public void setApplytime(Timestamp value) {
        set(4, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_wallet_cashout.applyTime</code>.
     */
    public Timestamp getApplytime() {
        return (Timestamp) get(4);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_wallet_cashout.status</code>.
     */
    public void setStatus(Integer value) {
        set(5, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_wallet_cashout.status</code>.
     */
    public Integer getStatus() {
        return (Integer) get(5);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_wallet_cashout.auditUserId</code>.
     */
    public void setAudituserid(Long value) {
        set(6, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_wallet_cashout.auditUserId</code>.
     */
    public Long getAudituserid() {
        return (Long) get(6);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_wallet_cashout.auditTime</code>.
     */
    public void setAudittime(Timestamp value) {
        set(7, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_wallet_cashout.auditTime</code>.
     */
    public Timestamp getAudittime() {
        return (Timestamp) get(7);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_wallet_cashout.reason</code>.
     */
    public void setReason(String value) {
        set(8, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_wallet_cashout.reason</code>.
     */
    public String getReason() {
        return (String) get(8);
    }

    /**
     * Setter for <code>appcenter_mall_data.t_mall_wallet_cashout.remark</code>.
     */
    public void setRemark(String value) {
        set(9, value);
    }

    /**
     * Getter for <code>appcenter_mall_data.t_mall_wallet_cashout.remark</code>.
     */
    public String getRemark() {
        return (String) get(9);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Record1<Integer> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record10 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row10<Long, Integer, Double, Integer, Timestamp, Integer, Long, Timestamp, String, String> fieldsRow() {
        return (Row10) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row10<Long, Integer, Double, Integer, Timestamp, Integer, Long, Timestamp, String, String> valuesRow() {
        return (Row10) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Long> field1() {
        return TMallWalletCashout.T_MALL_WALLET_CASHOUT.APPLYUSERID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field2() {
        return TMallWalletCashout.T_MALL_WALLET_CASHOUT.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Double> field3() {
        return TMallWalletCashout.T_MALL_WALLET_CASHOUT.CASH;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field4() {
        return TMallWalletCashout.T_MALL_WALLET_CASHOUT.BANKTYPE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field5() {
        return TMallWalletCashout.T_MALL_WALLET_CASHOUT.APPLYTIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field6() {
        return TMallWalletCashout.T_MALL_WALLET_CASHOUT.STATUS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Long> field7() {
        return TMallWalletCashout.T_MALL_WALLET_CASHOUT.AUDITUSERID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field8() {
        return TMallWalletCashout.T_MALL_WALLET_CASHOUT.AUDITTIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field9() {
        return TMallWalletCashout.T_MALL_WALLET_CASHOUT.REASON;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field10() {
        return TMallWalletCashout.T_MALL_WALLET_CASHOUT.REMARK;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long value1() {
        return getApplyuserid();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value2() {
        return getId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Double value3() {
        return getCash();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value4() {
        return getBanktype();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value5() {
        return getApplytime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value6() {
        return getStatus();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long value7() {
        return getAudituserid();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value8() {
        return getAudittime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value9() {
        return getReason();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value10() {
        return getRemark();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TMallWalletCashoutRecord value1(Long value) {
        setApplyuserid(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TMallWalletCashoutRecord value2(Integer value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TMallWalletCashoutRecord value3(Double value) {
        setCash(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TMallWalletCashoutRecord value4(Integer value) {
        setBanktype(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TMallWalletCashoutRecord value5(Timestamp value) {
        setApplytime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TMallWalletCashoutRecord value6(Integer value) {
        setStatus(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TMallWalletCashoutRecord value7(Long value) {
        setAudituserid(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TMallWalletCashoutRecord value8(Timestamp value) {
        setAudittime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TMallWalletCashoutRecord value9(String value) {
        setReason(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TMallWalletCashoutRecord value10(String value) {
        setRemark(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TMallWalletCashoutRecord values(Long value1, Integer value2, Double value3, Integer value4, Timestamp value5, Integer value6, Long value7, Timestamp value8, String value9, String value10) {
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
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached TMallWalletCashoutRecord
     */
    public TMallWalletCashoutRecord() {
        super(TMallWalletCashout.T_MALL_WALLET_CASHOUT);
    }

    /**
     * Create a detached, initialised TMallWalletCashoutRecord
     */
    public TMallWalletCashoutRecord(Long applyuserid, Integer id, Double cash, Integer banktype, Timestamp applytime, Integer status, Long audituserid, Timestamp audittime, String reason, String remark) {
        super(TMallWalletCashout.T_MALL_WALLET_CASHOUT);

        set(0, applyuserid);
        set(1, id);
        set(2, cash);
        set(3, banktype);
        set(4, applytime);
        set(5, status);
        set(6, audituserid);
        set(7, audittime);
        set(8, reason);
        set(9, remark);
    }
}