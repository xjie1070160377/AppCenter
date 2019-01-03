/**
 * This class is generated by jOOQ
 */
package cn.mooc.jooq.demo.generated.tables;


import cn.mooc.jooq.demo.generated.AppcenterMallData;
import cn.mooc.jooq.demo.generated.Keys;
import cn.mooc.jooq.demo.generated.tables.records.TMallDistributorenchashmentRecord;

import java.math.BigDecimal;
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
import org.jooq.types.UInteger;


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
public class TMallDistributorenchashment extends TableImpl<TMallDistributorenchashmentRecord> {

    private static final long serialVersionUID = 1244125666;

    /**
     * The reference instance of <code>appcenter_mall_data.t_mall_distributorenchashment</code>
     */
    public static final TMallDistributorenchashment T_MALL_DISTRIBUTORENCHASHMENT = new TMallDistributorenchashment();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<TMallDistributorenchashmentRecord> getRecordType() {
        return TMallDistributorenchashmentRecord.class;
    }

    /**
     * The column <code>appcenter_mall_data.t_mall_distributorenchashment.Id</code>.
     */
    public final TableField<TMallDistributorenchashmentRecord, Long> ID = createField("Id", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>appcenter_mall_data.t_mall_distributorenchashment.EnchashmentTime</code>. 提现时间
     */
    public final TableField<TMallDistributorenchashmentRecord, Timestamp> ENCHASHMENTTIME = createField("EnchashmentTime", org.jooq.impl.SQLDataType.TIMESTAMP, this, "提现时间");

    /**
     * The column <code>appcenter_mall_data.t_mall_distributorenchashment.EnchashmentAmount</code>. 提现金额
     */
    public final TableField<TMallDistributorenchashmentRecord, BigDecimal> ENCHASHMENTAMOUNT = createField("EnchashmentAmount", org.jooq.impl.SQLDataType.DECIMAL.precision(8, 2).nullable(false), this, "提现金额");

    /**
     * The column <code>appcenter_mall_data.t_mall_distributorenchashment.Note</code>. 备注
     */
    public final TableField<TMallDistributorenchashmentRecord, String> NOTE = createField("Note", org.jooq.impl.SQLDataType.VARCHAR.length(100), this, "备注");

    /**
     * The column <code>appcenter_mall_data.t_mall_distributorenchashment.DistributorId</code>.
     */
    public final TableField<TMallDistributorenchashmentRecord, Long> DISTRIBUTORID = createField("DistributorId", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>appcenter_mall_data.t_mall_distributorenchashment.Status</code>. 订单状态（-1：作废，0：等待卖家审核，1：等待管理员审核，2：完结）
     */
    public final TableField<TMallDistributorenchashmentRecord, UInteger> STATUS = createField("Status", org.jooq.impl.SQLDataType.INTEGERUNSIGNED.nullable(false), this, "订单状态（-1：作废，0：等待卖家审核，1：等待管理员审核，2：完结）");

    /**
     * The column <code>appcenter_mall_data.t_mall_distributorenchashment.SellerApplyTime</code>. 卖家审核时间
     */
    public final TableField<TMallDistributorenchashmentRecord, Timestamp> SELLERAPPLYTIME = createField("SellerApplyTime", org.jooq.impl.SQLDataType.TIMESTAMP, this, "卖家审核时间");

    /**
     * The column <code>appcenter_mall_data.t_mall_distributorenchashment.CreateTime</code>. 申请时间
     */
    public final TableField<TMallDistributorenchashmentRecord, Timestamp> CREATETIME = createField("CreateTime", org.jooq.impl.SQLDataType.TIMESTAMP, this, "申请时间");

    /**
     * The column <code>appcenter_mall_data.t_mall_distributorenchashment.SellerRemark</code>. 卖家留言
     */
    public final TableField<TMallDistributorenchashmentRecord, String> SELLERREMARK = createField("SellerRemark", org.jooq.impl.SQLDataType.VARCHAR.length(100), this, "卖家留言");

    /**
     * The column <code>appcenter_mall_data.t_mall_distributorenchashment.AdminRemark</code>. 管理员留言
     */
    public final TableField<TMallDistributorenchashmentRecord, String> ADMINREMARK = createField("AdminRemark", org.jooq.impl.SQLDataType.VARCHAR.length(100), this, "管理员留言");

    /**
     * The column <code>appcenter_mall_data.t_mall_distributorenchashment.SellerManagerId</code>. 商家审核账户
     */
    public final TableField<TMallDistributorenchashmentRecord, Long> SELLERMANAGERID = createField("SellerManagerId", org.jooq.impl.SQLDataType.BIGINT, this, "商家审核账户");

    /**
     * The column <code>appcenter_mall_data.t_mall_distributorenchashment.AdminManagerId</code>. 管理员审核账户
     */
    public final TableField<TMallDistributorenchashmentRecord, Long> ADMINMANAGERID = createField("AdminManagerId", org.jooq.impl.SQLDataType.BIGINT, this, "管理员审核账户");

    /**
     * Create a <code>appcenter_mall_data.t_mall_distributorenchashment</code> table reference
     */
    public TMallDistributorenchashment() {
        this("t_mall_distributorenchashment", null);
    }

    /**
     * Create an aliased <code>appcenter_mall_data.t_mall_distributorenchashment</code> table reference
     */
    public TMallDistributorenchashment(String alias) {
        this(alias, T_MALL_DISTRIBUTORENCHASHMENT);
    }

    private TMallDistributorenchashment(String alias, Table<TMallDistributorenchashmentRecord> aliased) {
        this(alias, aliased, null);
    }

    private TMallDistributorenchashment(String alias, Table<TMallDistributorenchashmentRecord> aliased, Field<?>[] parameters) {
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
    public Identity<TMallDistributorenchashmentRecord, Long> getIdentity() {
        return Keys.IDENTITY_T_MALL_DISTRIBUTORENCHASHMENT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<TMallDistributorenchashmentRecord> getPrimaryKey() {
        return Keys.KEY_T_MALL_DISTRIBUTORENCHASHMENT_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<TMallDistributorenchashmentRecord>> getKeys() {
        return Arrays.<UniqueKey<TMallDistributorenchashmentRecord>>asList(Keys.KEY_T_MALL_DISTRIBUTORENCHASHMENT_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ForeignKey<TMallDistributorenchashmentRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<TMallDistributorenchashmentRecord, ?>>asList(Keys.T_MALL_DISTRIBUTORENCHASHMENT_IBFK_1);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TMallDistributorenchashment as(String alias) {
        return new TMallDistributorenchashment(alias, this);
    }

    /**
     * Rename this table
     */
    public TMallDistributorenchashment rename(String name) {
        return new TMallDistributorenchashment(name, null);
    }
}
